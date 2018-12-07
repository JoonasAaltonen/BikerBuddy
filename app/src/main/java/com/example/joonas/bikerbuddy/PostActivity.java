package com.example.joonas.bikerbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

// Most of the code by Mark & Marek,
// refactoring & database connections by Joonas
public class PostActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    Intent backIntent;
    EditText postTitle;
    EditText postContent;
    boolean updating = false;
    String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        backIntent = new Intent(this, ForumActivity.class);

        dbHandler = new DatabaseHandler(this);

        Intent sendingIntent = getIntent();
        String postTitleText = "";
        String intentExtra = sendingIntent.getStringExtra(ForumActivity.EXTRA_STRING);


        // The forum will send "newPost" with the intent if the intent calls from new post button
        // and will send the title of the post when calling from the update post button
        // Ugly hack but we were running out of time..
        if (!intentExtra.equals("newPost")) {
            try {
                postTitleText = sendingIntent.getStringExtra(ForumActivity.EXTRA_STRING);
                oldTitle = postTitleText;
                postTitle = (EditText)findViewById(R.id.postTitle);
                postContent = (EditText)findViewById(R.id.postContent);

                postTitle.setText(postTitleText);

                Cursor data = dbHandler.getPostContent(postTitleText);
                while(data.moveToNext()){
                    //Move the cursor to the first (and only) item and set the data to the text view
                    postContent.setText(data.getString(0));
                }
                updating = true;
            }
            catch (Exception e) {

            }
        }

        

        
    }

    public void OnClickPublish(View view) {
        // Get the text from the text fields
        postTitle = (EditText)findViewById(R.id.postTitle);
        postContent = (EditText)findViewById(R.id.postContent);
        String titleText = postTitle.getText().toString();
        String contentText = postContent.getText().toString();

        // If updating the post, use update statement
        if (updating == true)
        {
            dbHandler = new DatabaseHandler(this);
            dbHandler.updateData(oldTitle, titleText, contentText);

            // Go back to posts list
            startActivity(backIntent);

        }
        // In other cases (when creating a new) insert a new
        else {
            // send the text to databaseHandler to be inserted into the table
            dbHandler = new DatabaseHandler(this);
            boolean insert = dbHandler.addData(titleText, contentText);
            if (insert == true) {
                // Go back to posts list
                startActivity(backIntent);
            }
        }

    }

    public void OnClickExit(View view) {
        startActivity(backIntent);
    }
}
