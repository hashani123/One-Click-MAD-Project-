package com.mad.oneclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    Button add,vwdlt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=findViewById(R.id.addbutton);
        vwdlt=findViewById(R.id.viewdelete);

        add.setOnClickListener((View v)->{
            Intent in=new Intent(MainActivity.this,AddItem.class);
            startActivity(in);
        });

        vwdlt.setOnClickListener((View v)->{
            Intent in=new Intent(MainActivity.this,ViewItemDetails.class);
            startActivity(in);
        });

    }


}