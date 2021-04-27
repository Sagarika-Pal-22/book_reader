package com.example.bookreader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreader.Model.Notification_Model;
import com.example.bookreader.R;

import java.util.List;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.MyViewHolder> {

    Context context;
    List<Notification_Model> book_models;

    public Notification_Adapter(Context context, List<Notification_Model> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_notification, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.notification.setText(book_models.get(position).getNotification());
        holder.time.setText(book_models.get(position).getTime());
        holder.date.setText(book_models.get(position).getDate());
        Glide.with(context)
                .load(book_models.get(position).getImage())
                .into(holder.image);

//        holder.linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context, BookDetails_Activity.class));
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return book_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView notification,date,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            notification = itemView.findViewById(R.id.notification);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

        }
    }
}
