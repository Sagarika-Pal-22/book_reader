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
import com.example.bookreader.Model.EBook_Model;
import com.example.bookreader.Model.TopE_Book_Model;
import com.example.bookreader.PDF_Reader_Activity;
import com.example.bookreader.R;

import java.util.List;

public class EBook_Adapter extends RecyclerView.Adapter<EBook_Adapter.MyViewHolder> {

    Context context;
    List<TopE_Book_Model> eBook_models;

    public EBook_Adapter(Context context, List<TopE_Book_Model> eBook_models) {
        this.context = context;
        this.eBook_models = eBook_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_ebooks, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Glide.with(context)
                .load(eBook_models.get(position).getImage())
                .into(holder.image);
        holder.book_name.setText(eBook_models.get(position).getBook_name());
        holder.rating.setText(eBook_models.get(position).getRating());

        if(eBook_models.get(position).getPrice().equals("")){
            holder.price.setText("0");
        }else{
            holder.price.setText(eBook_models.get(position).getPrice());
        }

        if (eBook_models.get(position).getWriter().equals("")){
            holder.writer.setText("Author: Unknown");
        }else {
            holder.writer.setText(eBook_models.get(position).getWriter());
        }

        if (eBook_models.get(position).getGenre().equals("")){
            holder.lin_genre.setVisibility(View.GONE);
        }else {
            holder.genre.setText(eBook_models.get(position).getGenre());
        }

//        holder.genre.setText(eBook_models.get(position).getGenre());

        holder.linear_ebooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eBook_models.get(position).getType().equals("1")||eBook_models.get(position).getType().equals("2")){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", eBook_models.get(position).getId());
                    bundle.putString("book_name", eBook_models.get(position).getBook_name());
                    bundle.putString("book_type", eBook_models.get(position).getType());
                    context.startActivity(new Intent(context, EBook_Details_Activity.class).putExtras(bundle));
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", eBook_models.get(position).getId());
                    bundle.putString("book_name", eBook_models.get(position).getBook_name());
                    bundle.putString("book_type", eBook_models.get(position).getType());
                    context.startActivity(new Intent(context, BookDetails_Activity.class).putExtras(bundle));
                }

//                context.startActivity(new Intent(context, PDF_Reader_Activity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return eBook_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView book_name,writer,rating,price,genre;
        LinearLayout linear_ebooks,lin_genre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            book_name = itemView.findViewById(R.id.book_name);
            writer = itemView.findViewById(R.id.writer);
            rating = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.price);
            genre = itemView.findViewById(R.id.genre);
            linear_ebooks = itemView.findViewById(R.id.linear_ebooks);
            lin_genre = itemView.findViewById(R.id.lin_genre);
        }
    }

}

