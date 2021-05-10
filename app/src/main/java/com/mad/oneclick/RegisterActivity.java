package com.mad.oneclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.oneclick.model.Customer;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    DatabaseReference dbrefuserinfo;


    private TextView tvlogin;
    private EditText edfname,edlname,edphno,edemail,ednic,edpw1,edpw2;
    private Button btnsign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //--ui ids here

        btnsign=findViewById(R.id.btnsignup);
        edfname=findViewById(R.id.edtfname);
        edlname=findViewById(R.id.edtlname);
        edphno=findViewById(R.id.edtphno);
        edemail=findViewById(R.id.edtemail);
        ednic=findViewById(R.id.edtnic);
        edpw1=findViewById(R.id.edtpw1);
        edpw2=findViewById(R.id.edtpw2);

        tvlogin=findViewById(R.id.loginid);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        dbrefuserinfo= FirebaseDatabase.getInstance().getReference("USER INFO");

        //--btn works
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //to get input from the edit text



                    String fname=edfname.getText().toString().trim();
                    String lname=edlname.getText().toString().trim();
                    String phoneno=edphno.getText().toString().trim();
                    String email=edemail.getText().toString().trim();
                    String nic=ednic.getText().toString().trim();
                    String sgnpwd=edpw1.getText().toString().trim();
                    String sgnpwd2=edpw2.getText().toString().trim();

                    if(fname.equals("") && lname.equals("'") && phoneno.equals("") && email.equals("") && nic.equals("") && sgnpwd.equals("") && sgnpwd2.equals("") )
                    {
                        Toast.makeText(RegisterActivity.this, "every field must be filled", Toast.LENGTH_SHORT).show();

                    }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        edemail.setError("Enter valid email");

                    }
                    else if(!sgnpwd.equals(sgnpwd2))
                    {
                        Toast.makeText(RegisterActivity.this, "password did not match", Toast.LENGTH_SHORT).show();

                     }

                    else
                    {
                        mAuth.createUserWithEmailAndPassword(email,sgnpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String uid=mAuth.getUid();

                                    Customer customer=new Customer();
                                    customer.setUserid(uid);
                                    customer.setFname(fname);
                                    customer.setLname(lname);
                                    customer.setPhoneno(phoneno);
                                    customer.setEmail(""+(email));
                                    customer.setNic(""+(nic));

                                    //---//save to db
                                    dbrefuserinfo.child(uid).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(RegisterActivity.this, "done", Toast.LENGTH_SHORT).show();


                                            //user register success

                                        }
                                    });
                                    //---//



                                }
                                else
                                {
                                    //failed
                                    Toast.makeText(RegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
            }
        });



        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(in);
            }
        });

    }
}