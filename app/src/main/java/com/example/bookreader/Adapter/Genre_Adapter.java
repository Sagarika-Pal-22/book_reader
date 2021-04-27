package com.example.bookreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreader.All_Ebooks_Activity;
import com.example.bookreader.Model.Genre_Model;
import com.example.bookreader.R;

import java.util.List;

public class Genre_Adapter extends RecyclerView.Adapter<Genre_Adapter.MyViewHolder> {

    Context context;
    List<Genre_Model> book_models;

    public Genre_Adapter(Context context, List<Genre_Model> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_genre, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.text_genre.setText(book_models.get(position).getText_genre());

        holder.linear_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "genre");
                bundle.putString("id", book_models.get(position).getId());
                context.startActivity(new Intent(context, All_Ebooks_Activity.class).putExtras(bundle));
            }
        });

                int colorRes = 0;
        switch(position % 5) {
            case 0: colorRes = R.drawable.subj_1;
                break;
            case 1: colorRes = R.drawable.subj_3;
                break;
            case 2: colorRes = R.drawable.subj_4;
                break;
            case 3: colorRes = R.drawable.subj_6;
                break;
            case 4: colorRes = R.drawable.sub_5;
                break;
        }
        holder.text_genre.setBackground(ContextCompat.getDrawable(context, colorRes));


    }

    @Override
    public int getItemCount() {
        return book_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_genre;
        LinearLayout linear_genre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_genre = itemView.findViewById(R.id.text_genre);
            linear_genre = itemView.findViewById(R.id.linear_genre);


        }
    }
}
