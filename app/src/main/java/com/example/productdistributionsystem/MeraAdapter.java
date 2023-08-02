package com.example.productdistributionsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MeraAdapter extends FirebaseRecyclerAdapter<Items, MeraAdapter.MyViewHolder> {

    public MeraAdapter(@NonNull FirebaseRecyclerOptions<Items> options) {
        super(options);
    }

 //   to display the data at the specified position the below method is used
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Items model) {
        holder.itemBarCode.setText(model.getItemBarCode());
        holder.itemCategory.setText(model.getItemCategory());
        holder.itemName.setText(model.getItemName());
        holder.itemPrice.setText(model.getItemPrice());
    }

    //for creating a new View holder the below method is used
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        return new MyViewHolder(view);
    }

   // the below ViewHolder is a helper class that holds the View of a row or rows.
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView itemBarCode;
        TextView itemPrice;
        TextView itemCategory;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName=itemView.findViewById(R.id.viewitemname);
            itemBarCode=itemView.findViewById(R.id.viewitembarcode);
            itemPrice=itemView.findViewById(R.id.viewitemprice);
            itemCategory=itemView.findViewById(R.id.viewitemcategory);

        }
    }
}
