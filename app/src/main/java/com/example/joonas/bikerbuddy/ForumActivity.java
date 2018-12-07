package com.example.joonas.bikerbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    public static String placeHolder = "placeholder title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        dbHandler = new DatabaseHandler(this);

        //dbHandler.dropTable();
        //dbHandler.deleteItem("Adding an item");
        //dbHandler.addData("Thread with content??", "Something inside it");
        // dbHandler.createTable();

        // Using array adapter instead of SimpleCursorAdapter
        // used https://github.com/mitchtabian/SaveReadWriteDeleteSQLite/blob/master/SaveAndDisplaySQL/app/src/main/java/com/tabian/saveanddisplaysql/ListDataActivity.java
        // as an example
        Cursor queryData = dbHandler.getData();

        ArrayList<String> dataList = new ArrayList<>();
        while(queryData.moveToNext()){
            //get the value from the database in column 1 which is the post title
            //then add it to the ArrayList
            dataList.add(queryData.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_forum_list_display, dataList);
        final ListView listView = (ListView) findViewById(R.id.thread_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object post = listView.getItemAtPosition(i);
                String postTitle = post.toString();

                Intent threadIntent = new Intent(ForumActivity.this, ThreadActivity.class);
                threadIntent.putExtra(placeHolder, postTitle);

                startActivity(threadIntent);
            }
        });
    }

    public void OnClickNew(View view) {
        dbHandler.close();
        Intent postIntent = new Intent(this, PostActivity.class);
        startActivity(postIntent);
    }
}
