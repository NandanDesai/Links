package com.nayana.links;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static final String TAG = MainActivity.class.getSimpleName();
    Map<Integer, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase linksDb = openOrCreateDatabase("links.db",MODE_PRIVATE,null);
        try {
            linksDb.execSQL("CREATE TABLE IF NOT EXISTS Links(url VARCHAR primary key,info VARCHAR not null);");
        }catch(Exception e){
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(this, "Error creating the database", duration);
            toast.show();
        }
        //linksDb.execSQL("delete from links");
        Button addButton=(Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    addLink(v);
                }catch(Exception e){
                    //Log.d(TAG,"addLink() method",e);

                }
            }
        });
        LinearLayout linear=(LinearLayout) findViewById(R.id.linearLayout);
        Cursor resultSet = linksDb.rawQuery("Select * from Links",null);
        String link;
        String info;
        int i=0;
        Button linkButton;
        map = new HashMap<Integer, String>();
        View.OnClickListener linkButtonListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //I can use Intent to get carry the details to the next Activity
                /*
                * Create a Hash map with id as key and url as value
                * And you can add a webview to open that link directly*/
                //button;

                openNewActivity(v);
            }
        };
        //TextView textView;
        //resultSet.moveToFirst();
        if(resultSet.moveToFirst()) {
            while (true) {
                link = resultSet.getString(0);
                info=resultSet.getString(1);
                linkButton=new Button(this);
                linkButton.setText(info);
                linkButton.setId(i);

                map.put(i,link);
                linkButton.setOnClickListener(linkButtonListener);
                linear.addView(linkButton);
                //textView = new TextView(this);
                //textView.setText(link);
                //textView.setId(i);
                i++;
               // textView.setClickable(true);
                //linear.addView(textView);

                if(resultSet.isLast()){
                    break;
                }
                resultSet.moveToNext();
            }
        }

        resultSet.close();



        //linksDb.close();


    }

    /*
    public void openWebView(View view){
        String link=map.get(view.getId());
        Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
        internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(internetIntent);

    }
    */

    public void addLink(View view){
        try {
            Intent intent = new Intent(this, AddLink.class);
            startActivity(intent);
        }catch(Exception e){
            Log.d(TAG,"addLink() method",e);

        }
    }

    public void openNewActivity(View view) {
        try {
            String link = map.get(view.getId());
            Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            myWebLink.setData(Uri.parse(link));
            startActivity(myWebLink);
        }catch(Exception e){
            CharSequence text = "Error in opening the link: "+e;
            int duration = Toast.LENGTH_SHORT;
            System.out.println(e);
            final Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }
    /*
    public void add(View view){
        try {
            Intent intent = new Intent(this, AddLink.class);


            startActivity(intent);
        }catch(Exception e){
            Log.d(TAG,"addLink() method",e);

        }
    }
    */
}
