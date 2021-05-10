package com.mad.oneclick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.oneclick.R;
import com.mad.oneclick.model.ItemDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class itemDetailAdapter extends RecyclerView.Adapter<itemDetailAdapter.itemDetailViewholder> {
    private Context mcontext;
    private List<ItemDetails> mlist;
    private onitemclicklistener listener;

    public itemDetailAdapter(Context mcontext, List<ItemDetails> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public itemDetailViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.viewitemlayout,parent,false);

        return new itemDetailViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemDetailViewholder holder, int position) {
            ItemDetails itemDetails=mlist.get(position);

        Picasso.get().load(itemDetails.getItemimg()).fit().into(holder.imgview);

        holder.itname.setText(itemDetails.getItemname());
        holder.itdesc.setText(itemDetails.getItemdetails());
        holder.itmodel.setText(itemDetails.getItemmodel());
        holder.itprice.setText(itemDetails.getItemprice());


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class itemDetailViewholder extends RecyclerView.ViewHolder {
         ImageView imgview;
         TextView itname,itprice,itmodel,itdesc;
         Button rmvebtn;

        public itemDetailViewholder(@NonNull View itemView) {
            super(itemView);

            imgview=itemView.findViewById(R.id.img1);
            itname=itemView.findViewById(R.id.itemnameid1);
            itprice=itemView.findViewById(R.id.itemnameid2);
            itmodel=itemView.findViewById(R.id.itemnameid3);
            itdesc=itemView.findViewById(R.id.itemnameid4);
            rmvebtn=itemView.findViewById(R.id.rmvwbtn);

            imgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION) {
                            listener.update(position);

                        }
                    }
                }
            });

            rmvebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION) {
                            listener.removeitem(position);

                        }
                    }
                }
            });


        }
    }

    public interface onitemclicklistener{
        public void removeitem(int position);
        public void update(int position);
    }

    public void setOnitemclicklistener(onitemclicklistener onitemclicklistener)
    {
        listener=onitemclicklistener;
    }


}
