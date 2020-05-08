package com.getbouncer.cardscan.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getbouncer.cardscan.ui.CardScanActivity;
import com.getbouncer.cardscan.ui.CardScanActivityResultHandler;
import com.getbouncer.cardscan.ui.CardScanActivityResult;

import org.jetbrains.annotations.NotNull;

public class LaunchActivity extends AppCompatActivity implements CardScanActivityResultHandler {

    private static final String API_KEY = "qOJ_fF-WLDMbG05iBq5wvwiTNTmM2qIn";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // Because this activity displays card numbers, disallow screenshots.
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );

        findViewById(R.id.scanCardButton).setOnClickListener(v ->
                CardScanActivity.start(
                        /* activity */ LaunchActivity.this,
                        /* apiKey */ API_KEY,
                        /* enableEnterCardManually */ true,
                        /* displayCardPan */ false,
                        /* requiredCardNumber */ null,
                        /* displayCardScanLogo */ true,
                        /* enableDebug */ false
                )
        );

        findViewById(R.id.scanCardDebugButton).setOnClickListener(v ->
                CardScanActivity.start(
                        /* activity */LaunchActivity.this,
                        /* apiKey */ API_KEY,
                        /* enableEnterCardManually */ false,
                        /* displayCardPan */ true,
                        /* requiredCardNumber */ null,
                        /* displayCardScanLogo */ false,
                        /* enableDebug */ true
                )
        );

        CardScanActivity.warmUp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (CardScanActivity.isScanResult(requestCode)) {
            CardScanActivity.parseScanResult(resultCode, data, this);
        }
    }

    @Override
    public void cardScanned(@Nullable String scanId, @NotNull CardScanActivityResult scanResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(scanResult.getPan());
        builder.show();
    }

    @Override
    public void enterManually(@Nullable String scanId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.enter_manually);
        builder.show();
    }

    @Override
    public void userCanceled(@Nullable String scanId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.scan_canceled);
        builder.show();
    }

    @Override
    public void cameraError(@Nullable String scanId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.camera_error);
        builder.show();
    }

    @Override
    public void analyzerFailure(@Nullable String scanId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.analyzer_error);
        builder.show();
    }

    @Override
    public void canceledUnknown(@Nullable String scanId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unknown_reason);
        builder.show();
    }
}
