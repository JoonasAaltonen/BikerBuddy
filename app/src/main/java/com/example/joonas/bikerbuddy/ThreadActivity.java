package com.example.joonas.bikerbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_thread);

        Intent intent = getIntent();
        String postTitle = intent.getStringExtra(ForumActivity.placeHolder);

        TextView titleText = (TextView)findViewById(R.id.forumTextView1);
        titleText.setText(postTitle);
    }

    public void OnClickReply(View view) {
        Intent postIntent = new Intent(this, PostActivity.class);
        startActivity(postIntent);
    }

    public void OnClickBack(View view) {
        Intent backIntent = new Intent(this, ForumActivity.class);
        startActivity(backIntent);
    }
}
