package com.getbouncer.cardscan.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getbouncer.cardscan.ui.CardScanActivity;
import com.getbouncer.cardscan.ui.card.ScanResult;

import org.jetbrains.annotations.NotNull;

public class LaunchActivity extends AppCompatActivity {

    private static final String API_KEY = "qOJ_fF-WLDMbG05iBq5wvwiTNTmM2qIn";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

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
            if (resultCode == Activity.RESULT_OK && data != null) {
                ScanResult scanResult = CardScanActivity.getScannedCard(data);
                if (scanResult != null) {
                    handleCardScanned(scanResult);
                } else {
                    handleCardScanCancelled();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                int canceledReason = getCanceledReason(data);
                if (canceledReason == CardScanActivity.CANCELED_REASON_ENTER_MANUALLY) {
                    handleEnterCardManually();
                } else if (canceledReason == CardScanActivity.CANCELED_REASON_CAMERA_ERROR) {
                    handleCameraError();
                } else if (canceledReason == CardScanActivity.CANCELED_REASON_USER) {
                    handleCardScanCancelled();
                } else if (canceledReason == CardScanActivity.CANCELED_REASON_ANALYZER_FAILURE) {
                    handleAnalyzerFailure();
                } else {
                    handleCardScanCancelledUnknown();
                }
            }
        }
    }

    private int getCanceledReason(@Nullable Intent data) {
        if (data != null) {
            return data.getIntExtra(CardScanActivity.RESULT_CANCELED_REASON, -1);
        } else {
            return -1;
        }
    }

    private void handleCardScanned(@NotNull ScanResult scanResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(scanResult.getPan());
        builder.show();
    }

    private void handleCardScanCancelled() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.scan_canceled);
        builder.show();
    }

    private void handleEnterCardManually() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.enter_manually);
        builder.show();
    }

    private void handleCameraError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.camera_error);
        builder.show();
    }

    private void handleAnalyzerFailure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.analyzer_error);
        builder.show();
    }

    private void handleCardScanCancelledUnknown() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unknown_reason);
        builder.show();
    }
}
