package com.example.bookreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreader.BookDetails_Activity;
import com.example.bookreader.EBook_Details_Activity;
import com.example.bookreader.Model.SliderItem;
import com.example.bookreader.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {

    Context context;
    List<SliderItem> book_models;

    public SliderAdapter(Context context, List<SliderItem> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_swipe_recommened, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.book_name.setText(book_models.get(position).getBook_name());
        holder.type_name.setText(book_models.get(position).getType_name());
        holder.writer.setText(book_models.get(position).getWriter());
        holder.description.setText(book_models.get(position).getDescription());
        holder.rating.setRating(Float.parseFloat(book_models.get(position).getRating()));

        Glide.with(context)
                .load(book_models.get(position).getImage())
                .into(holder.image);



        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_models.get(position).getType().equals("1")||book_models.get(position).getType().equals("2")){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", book_models.get(position).getId());
                    bundle.putString("genre_id", book_models.get(position).getGenre_id());
                    bundle.putString("book_name", book_models.get(position).getBook_name());
                    bundle.putString("book_type", book_models.get(position).getType());
                    context.startActivity(new Intent(context, EBook_Details_Activity.class).putExtras(bundle));
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", book_models.get(position).getId());
                    bundle.putString("genre_id", book_models.get(position).getGenre_id());
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
        TextView book_name,type_name,writer,description;
        RatingBar rating;
        RelativeLayout relative;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_slide);
            book_name = itemView.findViewById(R.id.book_name);
            type_name = itemView.findViewById(R.id.type_name);
            writer = itemView.findViewById(R.id.writer);
            description = itemView.findViewById(R.id.description);
            rating = itemView.findViewById(R.id.rating);
            relative = itemView.findViewById(R.id.relative);

        }
    }
}
