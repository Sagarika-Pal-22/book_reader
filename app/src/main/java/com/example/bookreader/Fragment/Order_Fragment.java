package com.example.bookreader.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreader.Adapter.Order_Adapter;
import com.example.bookreader.Model.Order_Model;
import com.example.bookreader.R;

import java.util.ArrayList;
import java.util.List;

public class Order_Fragment extends Fragment {

    RecyclerView recycler_order;
    List<Order_Model> order_models;
    Order_Adapter order_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,container,false);

        order_models = new ArrayList<>();

        recycler_order = view.findViewById(R.id.recycler_order);
        recycler_order.setHasFixedSize(true);
        recycler_order.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
//        order_models.add(new Order_Model(R.drawable.crime,R.drawable.detective,"Shimarekha, Kolkata Ekaler Shekaler....",
//                "by Priyanka Jangid","50.00","5/10/2020"));
//        order_models.add(new Order_Model(R.drawable.rmnc,R.drawable.adv,"Shimarekha, Kolkata Ekaler Shekaler....",
//                "by Priyanka Jangid","50.00","5/10/2020"));
//        order_models.add(new Order_Model(R.drawable.book_4,R.drawable.book_3,"Shimarekha, Kolkata Ekaler Shekaler....",
//                "by Priyanka Jangid","50.00","5/10/2020"));
        order_adapter = new Order_Adapter(getActivity(),order_models);
        recycler_order.setAdapter(order_adapter);

        return view;
    }
}
