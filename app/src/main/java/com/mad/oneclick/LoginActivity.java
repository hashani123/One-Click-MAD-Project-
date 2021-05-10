package com.mad.oneclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    TextView tvregister;

    private Button btnlogin;
    private EditText edmail,edpwd;



    @Override
    protected void onStart() {
        super.onStart();
//        if(mAuth.getCurrentUser()!=null&& mAuth.getUid().equals("8z01alakoVc5YdBkB9P738MO4Fy2"))
//        {
//                //username==admin & password=123456
//                goToPage(MainActivity.class);
//            }
//        else if(mAuth.getCurrentUser()!=null&& mAuth.getUid().equals("YG6hLSlpcMVRHLKfaRM6XKHygLC3")){
//            goToPage(ViewCustomer.class);
//        }
//            else if(mAuth.getCurrentUser()!=null){
//                goToPage(Home.class);
//            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //--ui ids here
        tvregister=findViewById(R.id.idregister);

        btnlogin=findViewById(R.id.button);

        edmail=findViewById(R.id.editTextTextEmailAddress);
        edpwd=findViewById(R.id.editTextTextPassword);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //----
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to get the input from the edittext
               String usermail= edmail.getText().toString().trim();
               String userpwd=edpwd.getText().toString().trim();


               if(usermail.equals(""))
               {
                   Toast.makeText(LoginActivity.this, "usermail can't be empty", Toast.LENGTH_SHORT).show();
               }
               else if(userpwd.equals("")){
                   Toast.makeText(LoginActivity.this, "password can't be empty", Toast.LENGTH_SHORT).show();

               }
               else{
                   mAuth.signInWithEmailAndPassword(usermail,userpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful())
                           {
                               if(usermail.equals("a@g.com")&&userpwd.equals("123456")){
                                   //username==admin & password=123456
                                   goToPage(MainActivity.class);

                               }
                               else if (usermail.equals("hashani@gmail.com") && userpwd.equals("123456")){
                                   goToPage(ViewCustomer.class);

                               }

                           }
                           else
                           {

                                   Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();

                           }

                       }
                   });


               }


               // Toast.makeText(LoginActivity.this, " "+usermail+"\n"+userpwd, Toast.LENGTH_LONG).show();
            }
        });

        tvregister.setOnClickListener((View v)-> {
                Intent in=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(in);


        });

    }

    private void goToPage(Object obj1) {
        Intent in1=new Intent(LoginActivity.this, (Class<?>) obj1);
        startActivity(in1);
        finish();
        Toast.makeText(LoginActivity.this, "successfully login", Toast.LENGTH_SHORT).show();


    }

}