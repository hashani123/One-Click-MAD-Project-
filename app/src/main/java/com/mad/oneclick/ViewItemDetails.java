package com.mad.oneclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.oneclick.adapter.itemDetailAdapter;
import com.mad.oneclick.model.ItemDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewItemDetails extends AppCompatActivity implements itemDetailAdapter.onitemclicklistener {
    DatabaseReference databaseReference;
    List<ItemDetails> itemlist;
    private static final String TAG = "ViewItemDetails";
    ///
    itemDetailAdapter adapter;

    RecyclerView recyclerView;

    Button logout;

    ProgressBar progressBar;

    ///


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_details);

        recyclerView=findViewById(R.id.recyclerid1);
        recyclerView.setHasFixedSize(true);

        logout=findViewById(R.id.lgout1);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ViewItemDetails.this,LoginActivity.class);
                startActivity(in);
            }
        });

        progressBar=findViewById(R.id.progressid);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        databaseReference= FirebaseDatabase.getInstance().getReference("Item details");
        itemlist=new ArrayList<>();

        //to read the value in the databaseReference in specific node- itemDetails start
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            itemlist.clear();
             for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                 ItemDetails itemDetails=dataSnapshot.getValue(ItemDetails.class);
                 itemlist.add(itemDetails);
                 Log.d(TAG, "onDataChange: "+itemDetails.getItemdetails());
             }

             progressBar.setVisibility(View.INVISIBLE);
                adapter=new itemDetailAdapter(ViewItemDetails.this,itemlist);
                adapter.setOnitemclicklistener(ViewItemDetails.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //to read the value in the databaseReference in specific node- itemDetails end


    }

    @Override
    public void removeitem(int position) {

    //    Toast.makeText(this, ""+itemlist.get(position).getItemid(), Toast.LENGTH_SHORT).show();

        databaseReference.child(itemlist.get(position).getItemid()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ViewItemDetails.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ViewItemDetails.this, "not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void update(int position) {
     EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit Description here")
                .setMessage("enter description")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String editTextInput = editText.getText().toString();

                        HashMap map = new HashMap();
                        map.put("itemdetails", editTextInput);

                        databaseReference.child(itemlist.get(position).getItemid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ViewItemDetails.this, "updated", Toast.LENGTH_SHORT).show();
                                }else
                                {

                                }
                            }
                        });

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}