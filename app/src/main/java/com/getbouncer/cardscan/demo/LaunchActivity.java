package com.getbouncer.cardscan.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.getbouncer.cardscan.ui.CardScanActivity;
import com.getbouncer.cardscan.ui.card.ScanResult;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        findViewById(R.id.scan_button).setOnClickListener(v ->
                CardScanActivity.start(LaunchActivity.this, true, null, false));

        findViewById(R.id.scanCardDebug).setOnClickListener(v ->
                CardScanActivity.start(LaunchActivity.this, true, null, true));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                int cancelledReason = data.getIntExtra(CardScanActivity.RESULT_CANCELED_REASON, -1);
                if (cancelledReason == CardScanActivity.CANCELED_REASON_ENTER_MANUALLY) {
                    handleEnterCardManually();
                } else if (cancelledReason == CardScanActivity.CANCELED_REASON_CAMERA_ERROR) {
                    handleCameraError();
                } else if (cancelledReason == CardScanActivity.CANCELED_REASON_USER) {
                    handleCardScanCancelled();
                } else {
                    handleCardScanCancelledUnknown();
                }
            }
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
