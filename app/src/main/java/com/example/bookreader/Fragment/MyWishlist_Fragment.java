package com.example.bookreader.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreader.ApiClient;
import com.example.bookreader.EBook_Details_Activity;
import com.example.bookreader.Model.Review_Model;
import com.example.bookreader.Model.User;
import com.example.bookreader.Model.WishList_Model;
import com.example.bookreader.MyInterface;
import com.example.bookreader.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWishlist_Fragment extends Fragment {

    MyInterface myInterface;
    RecyclerView rv_wishlist;
    List<WishList_Model> wishList_models;
    LinearLayout lin_no_product;
    Button btn_continue;
    User user;
    String user_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mywishlist,container,false);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user=new User(getActivity());
        user_id=user.getUser_id();

        lin_no_product = view.findViewById(R.id.lin_no_product);
        btn_continue = view.findViewById(R.id.btn_continue);

        wishList_models = new ArrayList<>();

        rv_wishlist = view.findViewById(R.id.rv_wishlist);
        rv_wishlist.setHasFixedSize(true);
        rv_wishlist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        fetch_wishlist();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loadFragment(new Home_Fragment());
            }
        });

        return view;
    }

    private void fetch_wishlist() {
        Call<String> call = myInterface.fetch_favourite(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            wishList_models.clear();
                            rv_wishlist.setVisibility(View.GONE);
                            lin_no_product.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_wishlist.setVisibility(View.VISIBLE);
                            lin_no_product.setVisibility(View.GONE);
                            wishList_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                wishList_models.add(new WishList_Model(jsonObject.getString("product_id"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("product_price")));
                            }
                            WishList_Adapter adapter = new WishList_Adapter(getActivity(),wishList_models);
                            rv_wishlist.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "No Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class WishList_Adapter extends RecyclerView.Adapter<WishList_Adapter.MyViewHolder> {

        Context context;
        List<WishList_Model> book_models;

        public WishList_Adapter(Context context, List<WishList_Model> book_models) {
            this.context = context;
            this.book_models = book_models;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.single_wishlist, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

            Glide.with(context)
                    .load(book_models.get(position).getImg_wish())
                    .into(holder.img_wish);
            holder.text_title.setText(book_models.get(position).getText_title());

            if (book_models.get(position).getWriter().equals("")){
                holder.writer.setText("Author: Unknown");
            }else {
                holder.writer.setText(book_models.get(position).getWriter());
            }

            if(book_models.get(position).getPrice().equals("")){
                holder.price.setText("0");
            }else{
                holder.price.setText(book_models.get(position).getPrice());
            }

            holder.fab_btn.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                }
                @Override
                public void unLiked(LikeButton likeButton) {
                    send_favourite(book_models.get(position).getId(), "unfav");
//                    Toast.makeText(getActivity(),""+book_models.get(position).getId(),Toast.LENGTH_SHORT).show();
                }
            });

            holder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", book_models.get(position).getId());
                    context.startActivity(new Intent(context, EBook_Details_Activity.class).putExtras(bundle));
                }
            });


        }

        @Override
        public int getItemCount() {
            return book_models.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView img_wish;
            TextView text_title,writer,price;
            LikeButton fab_btn;
            LinearLayout linear;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                img_wish = itemView.findViewById(R.id.img_wish);
                text_title = itemView.findViewById(R.id.text_title);
                writer = itemView.findViewById(R.id.writer);
                price = itemView.findViewById(R.id.price);
                fab_btn = itemView.findViewById(R.id.fab_btn);
                linear = itemView.findViewById(R.id.linear);

            }
        }

        private void send_favourite(String id, String status){
            Call<String> call = myInterface.add_favourite(id,user_id,status);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body()!=null){
                        String res = response.body();
                        if (res==null){
                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }else {
                            try {
                                JSONObject jsonObject=new JSONObject(res);
                                if (jsonObject.getString("rec").equals("1")){
                                    fetch_wishlist();
                                }else {

                                }


                            }catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "No Response", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void loadFragment(Home_Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.frame_wish,fragment,fragment.getTag()).commit();
    }

}
