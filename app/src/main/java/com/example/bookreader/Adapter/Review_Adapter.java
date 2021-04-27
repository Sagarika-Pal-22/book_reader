package com.example.bookreader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreader.Model.Review_Model;
import com.example.bookreader.R;

import java.util.List;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.MyViewHolder> {

    Context context;
    List<Review_Model> review_models;

    public Review_Adapter(Context context, List<Review_Model> review_models) {
        this.context = context;
        this.review_models = review_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_reviews, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.text_person.setText(review_models.get(position).getText_person());
        holder.text_review.setText(review_models.get(position).getText_review());
        holder.text_date.setText(review_models.get(position).getText_date());
        holder.ratingBar.setRating(Float.parseFloat(review_models.get(position).getRating()));

    }

    @Override
    public int getItemCount() {
        return review_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_person,text_review,text_date;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            text_person = itemView.findViewById(R.id.text_person);
            text_review = itemView.findViewById(R.id.text_review);
            text_date = itemView.findViewById(R.id.text_date);
        }
    }
}
