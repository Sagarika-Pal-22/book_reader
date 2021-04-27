package com.example.bookreader.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreader.Adapter.Library_Adapter;
import com.example.bookreader.ApiClient;
import com.example.bookreader.CartActivity;
import com.example.bookreader.Model.Cart_Model;
import com.example.bookreader.Model.Library_Model;
import com.example.bookreader.Model.User;
import com.example.bookreader.MyInterface;
import com.example.bookreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCart_Fragment extends Fragment {

//    MyInterface myInterface;
//    RecyclerView recycler_cart;
//    List<Cart_Model> cart_models;
//    Cart_Adapter cart_adapter;
//    Button btn_buy;
//    User user;
//    String user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycart,container,false);
//        myInterface = ApiClient.getApiClient().create(MyInterface.class);
//        user=new User(getActivity());
//        user_id=user.getUser_id();
//
//        btn_buy = view.findViewById(R.id.btn_buy);
//
//        cart_models = new ArrayList<>();
//        recycler_cart = view.findViewById(R.id.recycler_cart);
//        recycler_cart.setHasFixedSize(true);
//        recycler_cart.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
//        fetch_cart();
//
//        btn_buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(Cart_Activity.this,Address_Activity.class));
//            }
//        });

        return view;
    }

//    private void fetch_cart() {
//        Call<String> call = myInterface.fetch_cart(user_id);
//        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"loading","Please Wait");
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if(response.body()!=null){
//                    String res = response.body();
//                    try {
//                        JSONArray jsonArray = new JSONArray(res);
//                        if (jsonArray.length() == 0) {
//                            progressDialog.dismiss();
//                            cart_models.clear();
//                            recycler_cart.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
//                        } else {
//                            recycler_cart.setVisibility(View.VISIBLE);
//                            cart_models.clear();
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                cart_models.add(new Cart_Model(jsonObject.getString("id"),
//                                        jsonObject.getString("product_id"),
//                                        jsonObject.getString("image"),
//                                        jsonObject.getString("product_name"),
//                                        jsonObject.getString("selling_price")));
//                            }
//                            cart_adapter = new Cart_Adapter(getActivity(),cart_models);
//                            recycler_cart.setAdapter(cart_adapter);
//                            progressDialog.dismiss();
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        progressDialog.dismiss();
//                        Toast.makeText(getActivity(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(getActivity(), "No Response", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

 }

