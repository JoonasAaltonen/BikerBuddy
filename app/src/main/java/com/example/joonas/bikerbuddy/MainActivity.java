package com.example.joonas.bikerbuddy;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GestureLibrary gestureLibrary;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent firstIntent = new Intent(this, SecondActivity.class);
        final Intent secondIntent = new Intent(this, ListView.class);


        textView = (TextView)findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.imageView);
        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestureLibrary.load()) {
            finish();
        }
        ArrayList<Gesture> arrayList = gestureLibrary.getGestures("action_up");
        for(Gesture gest : arrayList) {
            textView.setText(gest.toString());
            imageView.setImageBitmap(gest.toBitmap(100, 100, 0, R.color.colorPrimaryDark));


        }

        final GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.gestureView);

        GestureOverlayView.OnGesturePerformedListener gestureListener = new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictionArray = gestureLibrary.recognize(gestureView.getGesture());
                for (Prediction prediction : predictionArray) {
                    if (prediction.score > 1) {
                        textView.setText(prediction.name + " score: " + prediction.score);
                        //startActivity(firstIntent);
                    }
                    if (prediction.name == "action_up") {
                        textView.setText(prediction.name + " score: " + prediction.score);
                        startActivity(secondIntent);
                    }
                }
            }
        };

        gestureView.addOnGesturePerformedListener(gestureListener);

    }
}
