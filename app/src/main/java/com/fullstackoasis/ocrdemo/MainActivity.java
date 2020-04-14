package com.fullstackoasis.ocrdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.button);
        b.setOnClickListener(this);
    }

    public void analyze() {
        //Drawable d = getResources().getDrawable(R.drawable.moby_dick_chapter_1);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.moby_dick_chapter_1);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        Task<FirebaseVisionText> t = detector.processImage(image);
        t.addOnCompleteListener(new OnCompleteListener<FirebaseVisionText>() {
            @Override
            public void onComplete(@NonNull Task<FirebaseVisionText> task) {
                if (task.isSuccessful()) {
                    FirebaseVisionText result = task.getResult();
                    String text = result.getText();
                    Log.d(TAG, text);
                } else {
                    Log.d(TAG, "FAILED");
                    Exception e = task.getException();
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        analyze();
    }
}
