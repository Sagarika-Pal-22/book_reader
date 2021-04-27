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
import com.example.bookreader.BookDetails_Activity;
import com.example.bookreader.EBook_Details_Activity;
import com.example.bookreader.Model.NewRelease_Model;
import com.example.bookreader.R;

import java.util.List;

public class NewRelease_Adapter extends RecyclerView.Adapter<NewRelease_Adapter.MyViewHolder> {

    Context context;
    List<NewRelease_Model> book_models;

    public NewRelease_Adapter(Context context, List<NewRelease_Model> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_new_release, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.book_name.setText(book_models.get(position).getBook_name());
        holder.writer.setText(book_models.get(position).getWriter());
        holder.rating.setText(book_models.get(position).getRating());

        Glide.with(context)
                .load(book_models.get(position).getImage())
                .into(holder.image);

        if(book_models.get(position).getPrice().equals("")){
            holder.price.setText("0");
        }else{
            holder.price.setText(book_models.get(position).getPrice());
        }

        holder.linear_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_models.get(position).getType().equals("1")||book_models.get(position).getType().equals("2")){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", book_models.get(position).getId());
                    bundle.putString("genre_id", book_models.get(position).getGenre());
                    bundle.putString("book_name", book_models.get(position).getBook_name());
                    bundle.putString("book_type", book_models.get(position).getType());
                    context.startActivity(new Intent(context, EBook_Details_Activity.class).putExtras(bundle));
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", book_models.get(position).getId());
                    bundle.putString("genre_id", book_models.get(position).getGenre());
                    bundle.putString("book_name", book_models.get(position).getBook_name());
                    bundle.putString("book_type", book_models.get(position).getType());
                    context.startActivity(new Intent(context, BookDetails_Activity.class).putExtras(bundle));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return book_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView book_name,writer,price,rating;
        LinearLayout linear_books;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            book_name = itemView.findViewById(R.id.book_name);
            writer = itemView.findViewById(R.id.writer);
            price = itemView.findViewById(R.id.price);
            rating = itemView.findViewById(R.id.rating);
            linear_books = itemView.findViewById(R.id.linear_books);

        }
    }
}
