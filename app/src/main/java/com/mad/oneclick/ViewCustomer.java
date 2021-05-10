package com.mad.oneclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.mad.oneclick.adapter.CustomerDetailAdapter;
import com.mad.oneclick.adapter.itemDetailAdapter;
import com.mad.oneclick.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewCustomer extends AppCompatActivity implements CustomerDetailAdapter.onCustomerclicklistener{

    DatabaseReference dbreference;
    List<Customer> customerList;

    private static final String TAG = "Customer";

    CustomerDetailAdapter adapter;
    RecyclerView recyclerView;

    Button lout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        recyclerView=findViewById(R.id.recyclerid2);
        recyclerView.setHasFixedSize(true);

        lout=findViewById(R.id.button5);

        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ViewCustomer.this,LoginActivity.class);
                startActivity(in);
            }
        });



        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        dbreference= FirebaseDatabase.getInstance().getReference("USER INFO");
        customerList=new ArrayList<>();

        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerList.clear();
                for(DataSnapshot datasnapshot:snapshot.getChildren()){


                    Customer customer=datasnapshot.getValue(Customer.class);
                    customerList.add(customer);
                    Log.d(TAG,"onDataChange: "+customer.getUserid());
                    Toast.makeText(ViewCustomer.this, ""+customer.getLname(), Toast.LENGTH_SHORT).show();


                    adapter=new CustomerDetailAdapter(ViewCustomer.this,customerList);
                    adapter.setOnCustomerListener(ViewCustomer.this);
                    recyclerView.setAdapter(adapter);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    @Override
    public void removeCustomer(int position) {

        Toast.makeText(this, ""+customerList.get(position).getUserid(), Toast.LENGTH_SHORT).show();


        dbreference.child(customerList.get(position).getUserid()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ViewCustomer.this, "success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ViewCustomer.this, "failed", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public void update(int position) {
        EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit email here")
                .setMessage("enter email")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String editTextInput = editText.getText().toString();

                        HashMap map = new HashMap();
                        map.put("email", editTextInput);

                        dbreference.child(customerList.get(position).getUserid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ViewCustomer.this, "updated", Toast.LENGTH_SHORT).show();
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