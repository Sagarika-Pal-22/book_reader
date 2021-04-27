package com.example.bookreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreader.EBook_Details_Activity;
import com.example.bookreader.Model.Books_Chkout_Model;
import com.example.bookreader.Model.CategoryHome_Model;
import com.example.bookreader.R;

import java.util.List;

public class CheckOut_Book_Adapter extends RecyclerView.Adapter<CheckOut_Book_Adapter.MyViewHolder> {

    Context context;
    List<Books_Chkout_Model> book_models;

    public CheckOut_Book_Adapter(Context context, List<Books_Chkout_Model> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_total_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.text_books.setText(book_models.get(position).getText_books());
        holder.price.setText(book_models.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return book_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_books,price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_books = itemView.findViewById(R.id.text_books);
            price = itemView.findViewById(R.id.price);

        }
    }
}
