package com.example.bookreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreader.BookDetails_Activity;
import com.example.bookreader.Model.Order_Model;
import com.example.bookreader.R;

import java.util.List;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.MyViewHolder> {

    Context context;
    List<Order_Model> book_models;

    public Order_Adapter(Context context, List<Order_Model> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.book_name.setText(book_models.get(position).getBook_name());
        holder.invoice.setText("Invoice:  "+ book_models.get(position).getInvoice());
        holder.price.setText(book_models.get(position).getPrice());
        holder.date.setText(book_models.get(position).getDate());

        Glide.with(context)
                .load(book_models.get(position).getImg_1())
                .into(holder.img_1);

        if(book_models.get(position).getImg_2().equals("")){
            holder.card_2.setVisibility(View.GONE);
        }else{
            Glide.with(context)
                    .load(book_models.get(position).getImg_2())
                    .into(holder.img_2);
        }


        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, BookDetails_Activity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return book_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_1,img_2;
        CardView card_2;
        TextView book_name,invoice,price,date;
        LinearLayout linear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_1 = itemView.findViewById(R.id.img_1);
            img_2 = itemView.findViewById(R.id.img_2);
            card_2 = itemView.findViewById(R.id.card_2);
            book_name = itemView.findViewById(R.id.book_name);
            invoice = itemView.findViewById(R.id.invoice);
            price = itemView.findViewById(R.id.price);
            date = itemView.findViewById(R.id.date);
            linear = itemView.findViewById(R.id.linear);

        }
    }
}
