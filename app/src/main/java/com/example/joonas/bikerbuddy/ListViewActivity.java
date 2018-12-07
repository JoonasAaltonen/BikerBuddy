package com.example.joonas.bikerbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        String[] mobileArray = new String[]{
                "How to make an account?",
                "How do I use the forums?",
                "Can I delete my forum thread/post/reply?",
                "What does the premium version contain?",
                "How do I use this app?",
                "Gestures Guidelines",
                "Contact the developer",};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_help_list_display, mobileArray);
        // Get ListView object from XML
        ListView listView = (ListView) findViewById(R.id.thread_list);
        listView.setAdapter(adapter);

    }
}
