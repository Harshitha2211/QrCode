package com.example.qrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    //private static final int PERMISSION_REQUEST_CAMERA = 0;
    TextView messageText, messageFormat;
   // Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageText = findViewById(R.id.textContent);
       // messageFormat = findViewById(R.id.textFormat);


    }

   public void Qrcode(View view) {
       System.out.println("button===");
       IntentIntegrator intentIntegrator = new IntentIntegrator(this);
       /*if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
           System.out.println("front===");
           intentIntegrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
           intentIntegrator.setPrompt("Scan a barcode or QR Code");
           //intentIntegrator.setOrientationLocked(true);

           intentIntegrator.setTorchEnabled(true);
           intentIntegrator.initiateScan();
           //Camera.open();
       }*/
       System.out.println("afeter if");
       System.out.println("Before if");
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
intentIntegrator.addExtra("SCAN_CAMERA_ID", getFrontCameraId());
        intentIntegrator.setTorchEnabled(true);
        intentIntegrator.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
    }
    private int getFrontCameraId() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                return cameraId;
            }
        }
        return -1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                messageText.setText(intentResult.getContents());
                //messageFormat.setText(intentResult.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
