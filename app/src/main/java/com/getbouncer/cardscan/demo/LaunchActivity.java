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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (resultCode == Activity.RESULT_OK && data != null) {
                ScanResult scanResult = CardScanActivity.getResult(data);
                if (scanResult != null) {
                    builder.setMessage(scanResult.getPan());
                } else {
                    builder.setMessage(R.string.null_result);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                builder.setMessage(R.string.scan_canceled);
            }
            builder.show();
        }
    }
}
