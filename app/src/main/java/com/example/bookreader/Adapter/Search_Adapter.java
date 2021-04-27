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
import com.example.bookreader.Model.CategoryHome_Model;
import com.example.bookreader.Model.SearchModel;
import com.example.bookreader.R;
import com.example.bookreader.Search_Product_Activity;

import java.util.ArrayList;
import java.util.List;

public class Search_Adapter  extends RecyclerView.Adapter<Search_Adapter.MyViewHolder> {

    Context context;
    List<SearchModel> book_models;
    List<SearchModel> filterlist;

    public Search_Adapter(Context context, List<SearchModel> book_models) {
        this.context = context;
        this.book_models = book_models;
        filterlist=book_models;
    }

    public void filter(String filter){
        filterlist=new ArrayList<>();
        for (SearchModel s : book_models){
            if (s.getBook_name().toLowerCase().contains(filter.toLowerCase())) {
                //adding the element to filtered list
                filterlist.add(s);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_search, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.book_name.setText(book_models.get(position).getBook_name());

        if(book_models.get(position).getImg().equals("")){
            Glide.with(context)
                    .load(R.drawable.search_book)
                    .into(holder.image);
        }else{
            Glide.with(context)
                    .load(book_models.get(position).getImg())
                    .into(holder.image);
        }

//
//        if(book_models.get(position).getType().equals("product")){
//
//        }else if(book_models.get(position).getType().equals("category")){
//
//        }

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", book_models.get(position).getId());
                bundle.putString("type", book_models.get(position).getType());
                context.startActivity(new Intent(context, Search_Product_Activity.class).putExtras(bundle));
            }
        });


    }

    @Override
    public int getItemCount() {
        return book_models.size();
    }

    public void filterList(ArrayList<SearchModel> filterdNames) {
        this.book_models=filterdNames;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView book_name;
        LinearLayout linear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
            book_name = itemView.findViewById(R.id.book_name);
            linear = itemView.findViewById(R.id.linear);

        }
    }
}
