package com.example.facedetections;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    Button cameraButton;

    private final static int REQUEST_IMAGE_CAPTURE = 124;
    FirebaseVisionImage image;
    FirebaseVisionFaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Face Detection App");

        FirebaseApp.initializeApp(this);

        cameraButton = findViewById(R.id.camera_button);

        cameraButton.setOnClickListener(
                v -> {

                    Intent intent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(
                            getPackageManager())
                            != null) {
                        startActivityForResult(
                                intent, REQUEST_IMAGE_CAPTURE);
                    } else {
                        Toast
                                .makeText(
                                        MainActivity.this,
                                        "Something went wrong",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,
                data);
        if (requestCode == REQUEST_IMAGE_CAPTURE
                && resultCode == RESULT_OK) {
            assert data != null;
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap) extra.get("data");
            detectFace(bitmap);
        }
    }

    private void detectFace(Bitmap bitmap) {
        FirebaseVisionFaceDetectorOptions options
                = new FirebaseVisionFaceDetectorOptions
                .Builder()
                .setModeType(
                        FirebaseVisionFaceDetectorOptions
                                .ACCURATE_MODE)
                .setLandmarkType(
                        FirebaseVisionFaceDetectorOptions
                                .ALL_LANDMARKS)
                .setClassificationType(
                        FirebaseVisionFaceDetectorOptions
                                .ALL_CLASSIFICATIONS)
                .build();
        try {
            image = FirebaseVisionImage.fromBitmap(bitmap);
            detector = FirebaseVision.getInstance()
                    .getVisionFaceDetector(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        detector.detectInImage(image)
                .addOnSuccessListener(firebaseVisionFaces -> {
                    String resultText = "";
                    int i = 1;
                    for (FirebaseVisionFace face :
                            firebaseVisionFaces) {
                        resultText
                                = resultText
                                .concat("\nFACE NUMBER. "
                                        + i + ": ")
                                .concat(
                                        "\nSmile: "
                                                + face.getSmilingProbability()
                                                * 100
                                                + "%")
                                .concat(
                                        "\nleft eye open: "
                                                + face.getLeftEyeOpenProbability()
                                                * 100
                                                + "%")
                                .concat(
                                        "\nright eye open "
                                                + face.getRightEyeOpenProbability()
                                                * 100
                                                + "%");
                        i++;
                    }

                    if (firebaseVisionFaces.size() == 0) {
                        Toast
                                .makeText(MainActivity.this,
                                        "NO FACE DETECT",
                                        Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(
                                LCOFaceDetection.RESULT_TEXT,
                                resultText);
                        DialogFragment resultDialog
                                = new ResultDialog();
                        resultDialog.setArguments(bundle);
                        resultDialog.setCancelable(true);
                        resultDialog.show(
                                getSupportFragmentManager(),
                                LCOFaceDetection.RESULT_DIALOG);
                    }
                })
                .addOnFailureListener(e -> Toast
                        .makeText(
                                MainActivity.this,
                                "Oops, Something went wrong",
                                Toast.LENGTH_SHORT)
                        .show());
    }


    @Override
    public void onBackPressed() {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("Cancel")
                .setConfirmText("Exit")
                .setConfirmClickListener(sweetAlertDialog -> {
                    //finish();
                    finishAffinity();
                })
                .showCancelButton(true)
                .setCancelClickListener(SweetAlertDialog::cancel)
                .setNeutralButton("Help", sweetAlertDialog -> Toast.makeText(MainActivity.this, "Press Exit to quit App", Toast.LENGTH_SHORT).show())
                .show();


    }
}