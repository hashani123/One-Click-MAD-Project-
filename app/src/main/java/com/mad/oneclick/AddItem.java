package com.mad.oneclick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mad.oneclick.model.ItemDetails;
import com.squareup.picasso.Picasso;

public class AddItem extends AppCompatActivity {

    EditText itmmod,itmname,itmprice,itmdesc;
    Button btn2;
    ImageButton imageButton;
    ProgressBar progressBar;

    String uldimgpath;


    DatabaseReference dbiteminfo;
    StorageReference storageRef;

    private static int REQCODE=1;
    Uri uri;

    private static final String TAG = "AddItem ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //UI ids here

        itmname=findViewById(R.id.itname);
        itmprice=findViewById(R.id.itprice);
        itmmod=findViewById(R.id.itmodel);
        itmdesc=findViewById(R.id.itdes);
        imageButton=findViewById(R.id.imageButton);
        btn2=findViewById(R.id.button2);

        progressBar=findViewById(R.id.progressBar);

        //database config
       storageRef= FirebaseStorage.getInstance().getReference("item path");

       dbiteminfo= FirebaseDatabase.getInstance().getReference().child("Item details");



        //step-1 : select image for upload

        imageButton.setOnClickListener((View v)->{
           Intent in=new Intent();
           in.setType("image/*");
           in.setAction(Intent.ACTION_GET_CONTENT);
           startActivityForResult(in,REQCODE);
        });

        //step-2 : click ADD ITEM button

        btn2.setOnClickListener((View v)->{

            btn2.setVisibility(View.INVISIBLE);



            if (uri != null) {
                final StorageReference filestoregereference = storageRef.child(System.currentTimeMillis() + "."
                        + getpictureextenction(uri));

                final String key = dbiteminfo.push().getKey();

                progressBar.setVisibility(View.VISIBLE);

                filestoregereference.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filestoregereference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downUri = task.getResult();

                            //   Log.d(TAG, "onComplete: Url: " + downUri.toString());
                            String itemname=itmname.getText().toString().trim();
                            String itemprice=itmprice.getText().toString().trim();
                            String itemmodel=itmmod.getText().toString().trim();
                            String itemdescription=itmdesc.getText().toString().trim();

                            String itid=dbiteminfo.push().getKey();

                            ItemDetails itdetails=new ItemDetails();
                            itdetails.setItemid(itid);
                            itdetails.setItemname(itemname);
                            itdetails.setItemprice(itemprice);
                            itdetails.setItemmodel(itemmodel);
                            itdetails.setItemdetails(itemdescription);
                            itdetails.setItemimg(downUri.toString());

                            dbiteminfo.child(itid).setValue(itdetails)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            btn2.setVisibility(View.VISIBLE);
                                          ///  startActivity(new Intent(AddItem.this, Admin_post.class));
                                         ///   finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddItem.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    }
                });

            }
            else
            {
                Toast.makeText(AddItem.this, "No Picture choosed", Toast.LENGTH_SHORT).show();
            }


        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQCODE && resultCode==RESULT_OK && data.getData()!=null){
            uri=data.getData();
            Picasso.get().load(uri).fit().into(imageButton);

        }
    }

    private String getpictureextenction(Uri uri)
    {
        ContentResolver cn=getApplication().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cn.getType(uri));
    }
}

/*
            if (task.isSuccessful()) {

                    Uri downloadUri = task.getResult();

                   uldimgpath= downloadUri.toString();

                    Log.d(TAG, "onCreate: inside uploading");

                    Toast.makeText(this, ""+uldimgpath, Toast.LENGTH_LONG).show();
                    //get data as string
                    String itemname=itmname.getText().toString().trim();
                    String itemprice=itmprice.getText().toString().trim();
                    String itemmodel=itmmod.getText().toString().trim();
                    String itemdescription=itmdesc.getText().toString().trim();

                    if(itemname.equals("") && itemprice.equals("'") && itemmodel.equals("") && itemdescription.equals("")  ) {
                        Toast.makeText(AddItem.this, "every field must be filled", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        //
                        //generate item id :itid
                        String itid=dbiteminfo.push().getKey();

                        ItemDetails itdetails=new ItemDetails();
                        itdetails.setItemid(itid);
                        itdetails.setItemname(itemname);
                        itdetails.setItemprice(itemprice);
                        itdetails.setItemmodel(itemmodel);
                        itdetails.setItemdetails(itemdescription);
                        itdetails.setItemimg(uldimgpath);


                        Log.d(TAG, "onCreate: inside realtime databAse");


                        //save the details in db
                        dbiteminfo.child(itid).setValue(itdetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(AddItem.this, "Done", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess: pushed data done item info realtime database");


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(AddItem.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "failiure: pushed data done item info realtime database");
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AddItem.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

///

                    }




                } else {
                    // Handle failures
                    // ...
                }

/
 */
