package com.example.joonas.bikerbuddy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import static android.app.AlertDialog.*;

// Initial list created by Mark,
// Click handling initially created by Marek
// Database connections and refactoring by Joonas
public class ForumActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    public static String EXTRA_STRING = "placeholder title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        dbHandler = new DatabaseHandler(this);

        // Using array adapter instead of SimpleCursorAdapter
        // used https://github.com/mitchtabian/SaveReadWriteDeleteSQLite/blob/master/SaveAndDisplaySQL/app/src/main/java/com/tabian/saveanddisplaysql/ListDataActivity.java
        // as an example
        Cursor queryData = dbHandler.getAllData();

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
                threadIntent.putExtra(EXTRA_STRING, postTitle);

                startActivity(threadIntent);
            }
        });

        // Used snippets from
        // https://stackoverflow.com/questions/23195208/how-to-pop-up-a-dialog-to-confirm-delete-when-user-long-press-on-the-list-item
        // To create dialog after long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ForumActivity.this);
                alert.setTitle("Delete or update post?");
                alert.setMessage("Select your option. Note that the deletion is final!");

                alert.setPositiveButton("UPDATE", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Object post = listView.getItemAtPosition(i);
                        String postTitle = post.toString();

                        dbHandler.close();
                        Intent postIntent = new Intent(ForumActivity.this, PostActivity.class);
                        postIntent.putExtra(EXTRA_STRING, postTitle);
                        startActivity(postIntent);
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("DELETE", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Object post = listView.getItemAtPosition(i);
                        String postTitle = post.toString();
                        dbHandler.deleteItem(postTitle);

                        dialog.dismiss();
                        onResume();
                    }
                });

                alert.show();

                return true;
            }

        });
    }

    public void OnClickNew(View view) {
        dbHandler.close();
        Intent postIntent = new Intent(ForumActivity.this, PostActivity.class);
        postIntent.putExtra(EXTRA_STRING, "newPost");
        startActivity(postIntent);
    }

    // Code snippet to refresh activity
    // https://stackoverflow.com/a/14390434
   /* @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    } */
}
