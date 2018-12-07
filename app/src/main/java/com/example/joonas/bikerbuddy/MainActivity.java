package com.example.joonas.bikerbuddy;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// Swipe gesture code by Joonas

public class MainActivity extends AppCompatActivity {

    GestureLibrary gestureLibrary;
    TextView textView;
    ImageView imageView;
    Intent forumIntent;
    Intent listIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forumIntent = new Intent(this, ForumActivity.class);
        listIntent = new Intent(this, ListViewActivity.class);

        imageView = (ImageView)findViewById(R.id.imageView);
        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestureLibrary.load()) {
            finish();
        }

        final GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.gestureView);

        GestureOverlayView.OnGesturePerformedListener gestureListener = new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictionArray = gestureLibrary.recognize(gestureView.getGesture());
                for (Prediction prediction : predictionArray) {
                    if (prediction.score > 10) {
                        startActivity(forumIntent);
                    }
                }
            }
        };

        gestureView.addOnGesturePerformedListener(gestureListener);
    }

    public void OnClickHelp(View view) {
        startActivity(listIntent);
    }
}
