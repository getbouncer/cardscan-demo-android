package com.getbouncer.cardscan.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getbouncer.cardscan.ui.CardScanActivity;
import com.getbouncer.cardscan.ui.card.ScanResult;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        findViewById(R.id.scanCard1Button).setOnClickListener(v ->
                CardScanActivity.start(LaunchActivity.this, true, null, false, false));

        findViewById(R.id.scanCard1DebugButton).setOnClickListener(v ->
                CardScanActivity.start(LaunchActivity.this, true, null, false, true));

        findViewById(R.id.scanCard2Button).setOnClickListener(v ->
                CardScanActivity.start(LaunchActivity.this, true, null, true, false));

        findViewById(R.id.scanCard2DebugButton).setOnClickListener(v ->
                CardScanActivity.start(LaunchActivity.this, true, null, true, true));

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

    private void handleCardScanned(ScanResult scanResult) {
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

    private void handleCardScanCancelledUnknown() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unknown_reason);
        builder.show();
    }
}
