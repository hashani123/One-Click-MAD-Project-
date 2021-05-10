package com.mad.oneclick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.oneclick.R;
import com.mad.oneclick.model.Customer;

import java.util.List;

public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.CustomerViewHolder> {

    private Context ccontext;
    private List<Customer> customerList;

    private  onCustomerclicklistener listener;


    public CustomerDetailAdapter(Context ccontext, List<Customer> customerList) {
        this.ccontext = ccontext;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ccontext).inflate(R.layout.viewcustomerlayout, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);

        holder.cusfname.setText(customer.getFname());
        holder.cuslname.setText(customer.getLname());
        holder.cusemail.setText(customer.getEmail());
        holder.cusnic.setText(customer.getNic());
        holder.cusphoneno.setText(customer.getPhoneno());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        ImageView cimg;
        TextView cusfname, cuslname, cusphoneno, cusemail, cusnic;
        Button dltbtn,updatebutton;


        public CustomerViewHolder(@NonNull View custView) {
            super(custView);


            cusfname = custView.findViewById(R.id.textView);
            cuslname = custView.findViewById(R.id.textView4);
            cusphoneno = custView.findViewById(R.id.textView5);
            cusemail = custView.findViewById(R.id.textView6);
            cusnic = custView.findViewById(R.id.textView7);

            dltbtn = custView.findViewById(R.id.button3);

            updatebutton=custView.findViewById(R.id.button4);


            dltbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.removeCustomer(position);
                    }
                    }

                }
            });

            updatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.update(position);
                        }
                    }
                }
            });

        }
    }

    public interface onCustomerclicklistener {
        public void removeCustomer(int position);

        public void update(int position);
    }

    public void setOnCustomerListener(onCustomerclicklistener onCustomerclicklistener){
        listener=onCustomerclicklistener;


    }


}






