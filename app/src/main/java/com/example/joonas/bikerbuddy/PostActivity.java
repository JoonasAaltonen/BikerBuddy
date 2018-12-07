package com.example.joonas.bikerbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PostActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    Intent backIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        backIntent = new Intent(this, ThreadActivity.class);
    }

    public void OnClickPublish(View view) {
        // Get the text from the text fields
        EditText postTitle = (EditText)findViewById(R.id.postTitle);
        EditText postContent = (EditText)findViewById(R.id.postContent);
        String titleText = postTitle.getText().toString();
        String contentText = postContent.getText().toString();

        // send the text to databaseHandler to be inserted into the table
        dbHandler = new DatabaseHandler(this);
        boolean insert = dbHandler.addData(titleText, contentText);
        //dbHandler.addData(titleText, contentText);
        if (insert == true) {
            // Go back to posts list
            startActivity(backIntent);
        }
    }

    public void OnClickExit(View view) {
        Intent backIntent = new Intent(this, ThreadActivity.class);
        startActivity(backIntent);
    }
}
