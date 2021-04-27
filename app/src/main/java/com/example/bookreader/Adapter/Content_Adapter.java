package com.example.bookreader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreader.Model.Content_Model;
import com.example.bookreader.R;

import java.util.List;

public class Content_Adapter extends RecyclerView.Adapter<Content_Adapter.MyViewHolder> {

    Context context;
    List<Content_Model> book_models;

    public Content_Adapter(Context context, List<Content_Model> book_models) {
        this.context = context;
        this.book_models = book_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.chap_no.setText(book_models.get(position).getChap_no());
        holder.chap_name.setText(book_models.get(position).getChap_name());

//        holder.linear_books.setOnClickListener(new View.OnClickListener() {
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

        TextView chap_no,chap_name;
        LinearLayout linear_books;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chap_no = itemView.findViewById(R.id.chap_no);
            chap_name = itemView.findViewById(R.id.chap_name);
        }
    }
}
