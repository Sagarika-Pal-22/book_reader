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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreader.EBook_Details_Activity;
import com.example.bookreader.Model.Library_Model;
import com.example.bookreader.R;

import java.util.List;

public class Library_Adapter extends RecyclerView.Adapter<Library_Adapter.MyViewHolder> {

    Context context;
    List<Library_Model> book_models;

    public Library_Adapter(Context context, List<Library_Model> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public Library_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_library, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.text_title.setText(book_models.get(position).getText_title());

        holder.linear_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EBook_Details_Activity.class));
            }
        });
        Glide.with(context)
                .load(book_models.get(position).getImage())
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return book_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text_title;
        LinearLayout linear_library;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text_title = itemView.findViewById(R.id.text_title);
            linear_library = itemView.findViewById(R.id.linear_library);

        }
    }
}
