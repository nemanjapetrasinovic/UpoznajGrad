package com.example.nemanja.upoznajgrad;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

public class BarcodeActivity extends AppCompatActivity {

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        final SurfaceView cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        final TextView barcodeInfo = (TextView) findViewById(R.id.textView6);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480)
                .build();

        //runtimePermisions();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                }
                catch (Exception ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(    // Update the TextView
                                    "QR kod pronađen"
                            );
                        }
                    });

                    Intent openQuiz=new Intent(BarcodeActivity.this.getApplicationContext(),QuizActivity.class);
                    openQuiz.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    if(decodeString(barcodes.valueAt(0).displayValue).toLowerCase().equals("medijana"))
                        openQuiz.putExtra("spot","Medijana");
                    openQuiz.putExtra("spot",decodeString(barcodes.valueAt(0).displayValue).toLowerCase());
                    startActivity(openQuiz);
                }
            }
        });
    }

    public static String decodeString(String input){
        char c;
        byte b;
        String binarydecodedInput="";
        String decodedInput="";

        for(int i=0;i<input.length();i++){
            c=input.charAt(i);
            String s = Integer.toBinaryString(c);
            while (s.length()<8)
                s='0'+s;
            binarydecodedInput+=s;
        }

        int remainder= Integer.parseInt(binarydecodedInput.substring(0,8),2);
        binarydecodedInput=binarydecodedInput.substring(0,binarydecodedInput.length()-(remainder-1));
        binarydecodedInput=binarydecodedInput.substring(8);


        while(binarydecodedInput.length()!=0){
            String s=binarydecodedInput.substring(0,6);
            s="01"+s;
            decodedInput+=String.valueOf((char)Integer.parseInt(s,2));
            binarydecodedInput=binarydecodedInput.substring(6);
        }


        return decodedInput;
    }

}
