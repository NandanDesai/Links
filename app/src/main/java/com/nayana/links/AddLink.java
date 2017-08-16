package com.nayana.links;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.support.design.widget.Snackbar;
import static android.R.attr.duration;

public class AddLink extends AppCompatActivity {
    SQLiteDatabase linksDb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);
        linksDb = openOrCreateDatabase("links.db",MODE_PRIVATE,null);
        Button addButton=(Button) findViewById(R.id.button2);
        final EditText urlText=(EditText) findViewById(R.id.editText4);
        final EditText infoText=(EditText) findViewById(R.id.editText);



        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertIntoDatabase(urlText.getText().toString(),infoText.getText().toString());
                //Snackbar mySnackbar = Snackbar.make(view, stringId, duration);

            }
        });

    }

    public void insertIntoDatabase(String url,String info){

        try {
            linksDb.execSQL("insert into Links values("+"'"+url+"','"+info+"')");
            CharSequence text = "Link has been added!";
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }catch(Exception e){
            int duration = Toast.LENGTH_SHORT;

            final Toast toast = Toast.makeText(this, "None of the fields can be blank", duration);
            toast.show();
        }



        //linksDb.close();
    }
}
