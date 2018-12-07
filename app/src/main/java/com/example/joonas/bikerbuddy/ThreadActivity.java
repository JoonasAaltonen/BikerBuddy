package com.example.joonas.bikerbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

// Code by Mark and Joonas
public class ThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_thread);

        TextView titleText = (TextView)findViewById(R.id.forumTextView1);
        TextView postText = (TextView)findViewById(R.id.forumTextView2);

        Intent intent = getIntent();

        String postTitle = intent.getStringExtra(ForumActivity.EXTRA_STRING);

        titleText.setText(postTitle);

        DatabaseHandler dbHandler = new DatabaseHandler(this);

        Cursor data = dbHandler.getPostContent(postTitle);
        //postText.setText(data.getString(0));
        while(data.moveToNext()){
            //Move the cursor to the first (and only) item and set the data to the text view
            postText.setText(data.getString(0));
        }



    }

    public void OnClickBack(View view) {
        Intent backIntent = new Intent(this, ForumActivity.class);
        startActivity(backIntent);
    }
}
