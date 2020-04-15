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
import android.widget.ProgressBar;
import android.widget.TextView;

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
        Button bExtract = findViewById(R.id.btnExtract);
        bExtract.setOnClickListener(this);
        Button bReset = findViewById(R.id.btnReset);
        bReset.setOnClickListener(this);
    }

    public void analyze() {
        hideTextViewAndShowProgressBar();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.moby_dick_chapter_1);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        Task<FirebaseVisionText> t = detector.processImage(image);
        t.addOnCompleteListener(new OnCompleteListener<FirebaseVisionText>() {
            @Override
            public void onComplete(@NonNull Task<FirebaseVisionText> task) {
                hideProgressBarAndShowTextView();
                if (task.isSuccessful()) {
                    FirebaseVisionText result = task.getResult();
                    String text = result.getText();
                    TextView tv = findViewById(R.id.tvContent);
                    tv.setText(text);
                } else {
                    Log.d(TAG, "FAILED");
                    Exception e = task.getException();
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }

    protected void hideProgressBarAndShowTextView() {
        TextView tv = findViewById(R.id.tvContent);
        tv.setVisibility(View.VISIBLE);
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);
    }
    protected void hideTextViewAndShowProgressBar() {
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        TextView tv = findViewById(R.id.tvContent);
        tv.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Hello " + v.getId());
        switch (v.getId()) {
            case R.id.btnReset:
                TextView tv = findViewById(R.id.tvContent);
                tv.setText(R.string.empty);
                Log.d(TAG, "set to empty?");
                break;
            case R.id.btnExtract:
                analyze();
                break;
            default:
                throw new RuntimeException("Unexpected button");

        }
    }
}
