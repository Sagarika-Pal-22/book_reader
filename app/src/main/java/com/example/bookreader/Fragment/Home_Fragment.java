package com.example.bookreader.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.bookreader.Adapter.NewRelease_Adapter;
import com.example.bookreader.Adapter.Recommendation_Adapter;
import com.example.bookreader.Adapter.SliderAdapter;
import com.example.bookreader.Adapter.TopE_Book_Adapter;
import com.example.bookreader.Adapter.Top_Hard_Adapter;
import com.example.bookreader.Adapter.ViewPagerAdapter;
import com.example.bookreader.AllBooks_Activity;
import com.example.bookreader.All_Ebooks_Activity;
import com.example.bookreader.ApiClient;
import com.example.bookreader.BookDetails_Activity;
import com.example.bookreader.EBook_Details_Activity;
import com.example.bookreader.Home_Activity;
import com.example.bookreader.Model.Banner_Model;
import com.example.bookreader.Model.NewRelease_Model;
import com.example.bookreader.Model.CategoryHome_Model;
import com.example.bookreader.Model.SliderItem;
import com.example.bookreader.Model.TopE_Book_Model;
import com.example.bookreader.Model.Top_Hard_Model;
import com.example.bookreader.Model.User;
import com.example.bookreader.MyInterface;
import com.example.bookreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout;

public class Home_Fragment extends Fragment {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    String[] slideImages ={""};
    private static int currentPage=0;
    private static int numPages=0;
    RecyclerView rv_for_you,rv_recommend,rv_top_rated,rv_top_ebooks,rv_most_liked,rv_top_hard,rv_top_audio,rv_top_digital;
    List<CategoryHome_Model> categoryHome_models;
    List<NewRelease_Model> newRelease_models;
    List<TopE_Book_Model> topE_book_models;
    List<SliderItem> sliderItems, most_liked_list;
    List<Top_Hard_Model> top_hard_model,top_audio_model,top_digital_model;
    TextView cat_1, cat_2, cat_3, cat_4, see_best_seller, see_top_rated, see_pblshrs,see_top_hard,see_most_liked,see_audio,see_top_digital;
    View view_1, view_2, view_3, view_4;
    LinearLayout linear_1,linear_2,linear_3,linear_4;
    LinearLayoutManager layoutManager_slide,layoutManager_category,layoutManager_most_liked;
    MyInterface myInterface;
    List<Banner_Model> banner_models = new ArrayList<>();
    TextView text_best_seller,text_publisher,text_top_rated,text_top_hard,text_most_liked,text_audio,text_digital;
    User user;
    String user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        myInterface= ApiClient.getApiClient().create(MyInterface.class);
        user = new User(getActivity());
        user_id = user.getUser_id();

        categoryHome_models = new ArrayList<>();
        newRelease_models = new ArrayList<>();
        topE_book_models = new ArrayList<>();
        sliderItems = new ArrayList<>();
        most_liked_list = new ArrayList<>();
        top_hard_model = new ArrayList<>();
        top_audio_model = new ArrayList<>();
        top_digital_model = new ArrayList<>();

        viewPager = view.findViewById(R.id.viewPager);
        cat_1 = view.findViewById(R.id.cat_1);
        cat_2 = view.findViewById(R.id.cat_2);
        cat_3 = view.findViewById(R.id.cat_3);
        cat_4 = view.findViewById(R.id.cat_4);
        view_1 = view.findViewById(R.id.view_1);
        view_2 = view.findViewById(R.id.view_2);
        view_3 = view.findViewById(R.id.view_3);
        view_4 = view.findViewById(R.id.view_4);
        linear_1 = view.findViewById(R.id.linear_1);
        linear_2 = view.findViewById(R.id.linear_2);
        linear_3 = view.findViewById(R.id.linear_3);
        linear_4 = view.findViewById(R.id.linear_4);
        see_best_seller = view.findViewById(R.id.see_best_seller);
        see_top_rated = view.findViewById(R.id.see_top_rated);
        see_pblshrs = view.findViewById(R.id.see_pblshrs);
        see_audio = view.findViewById(R.id.see_audio);
        see_top_digital = view.findViewById(R.id.see_top_digital);
        text_best_seller = view.findViewById(R.id.text_best_seller);
        text_publisher = view.findViewById(R.id.text_publisher);
        text_top_rated = view.findViewById(R.id.text_top_rated);
        text_top_hard = view.findViewById(R.id.text_top_hard);
        see_top_hard = view.findViewById(R.id.see_top_hard);
        text_most_liked = view.findViewById(R.id.text_most_liked);
        text_audio = view.findViewById(R.id.text_audio);
        text_digital = view.findViewById(R.id.text_digital);
        see_most_liked = view.findViewById(R.id.see_most_liked);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;

            }

            @Override
            public void onPageScrollStateChanged(int i) {

                if (i == ViewPager.SCROLL_STATE_IDLE) {
                    int pagecount = slideImages.length;
                    if (currentPage == 0) {
                        viewPager.setCurrentItem(pagecount - 1, false);
                    } else if (currentPage == pagecount - 1) {
                        viewPager.setCurrentItem(0, false);
                    }
                }
            }
        });
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage==numPages){
                    currentPage=0;
                }
                viewPager.setCurrentItem(currentPage++,true);
            }
        };
        Timer swipe = new Timer();
        swipe.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },1000,1000);

        see_best_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "best_seller");
                bundle.putString("heading", text_best_seller.getText().toString());
                startActivity(new Intent(getActivity(), All_Ebooks_Activity.class).putExtras(bundle));
            }
        });
        see_pblshrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "publishers");
                bundle.putString("heading", text_publisher.getText().toString());
                startActivity(new Intent(getActivity(), All_Ebooks_Activity.class).putExtras(bundle));
            }
        });
        see_top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "new_release");
                bundle.putString("heading", text_top_rated.getText().toString());
                startActivity(new Intent(getActivity(), All_Ebooks_Activity.class).putExtras(bundle));
            }
        });
        see_most_liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "most_liked");
                bundle.putString("heading", text_most_liked.getText().toString());
                startActivity(new Intent(getActivity(), All_Ebooks_Activity.class).putExtras(bundle));
            }
        });
        see_top_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "top_hard");
                bundle.putString("heading", text_top_hard.getText().toString());
                startActivity(new Intent(getActivity(), All_Ebooks_Activity.class).putExtras(bundle));
            }
        });
        see_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "top_audio");
                bundle.putString("heading", text_audio.getText().toString());
                startActivity(new Intent(getActivity(), All_Ebooks_Activity.class).putExtras(bundle));
            }
        });
        see_top_digital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "top_digital");
                bundle.putString("heading", text_digital.getText().toString());
                startActivity(new Intent(getActivity(), All_Ebooks_Activity.class).putExtras(bundle));
            }
        });



        rv_for_you = view.findViewById(R.id.rv_for_you);
        rv_for_you.setHasFixedSize(true);
        layoutManager_category = new ZoomRecyclerLayout(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rv_for_you.setLayoutManager(layoutManager_category);

        rv_top_ebooks = view.findViewById(R.id.rv_top_ebooks);
        rv_top_ebooks.setHasFixedSize(true);
        rv_top_ebooks.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv_recommend = view.findViewById(R.id.rv_recommend);
        rv_recommend.setHasFixedSize(true);
        layoutManager_slide = new ZoomRecyclerLayout(getActivity());
        layoutManager_slide.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager_slide.setReverseLayout(true);
        layoutManager_slide.setStackFromEnd(true);
        rv_recommend.setLayoutManager(layoutManager_slide);


        rv_top_rated = view.findViewById(R.id.rv_top_rated);
        rv_top_rated.setHasFixedSize(true);
        rv_top_rated.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv_most_liked = view.findViewById(R.id.rv_most_liked);
        rv_most_liked.setHasFixedSize(true);
        layoutManager_most_liked = new ZoomRecyclerLayout(getActivity());
        layoutManager_most_liked.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager_most_liked.setReverseLayout(true);
        layoutManager_most_liked.setStackFromEnd(true);
        rv_most_liked.setLayoutManager(layoutManager_most_liked);

        rv_top_hard = view.findViewById(R.id.rv_top_hard);
        rv_top_hard.setHasFixedSize(true);
        rv_top_hard.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv_top_audio = view.findViewById(R.id.rv_top_audio);
        rv_top_audio.setHasFixedSize(true);
        rv_top_audio.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv_top_digital = view.findViewById(R.id.rv_top_digital);
        rv_top_digital.setHasFixedSize(true);
        rv_top_digital.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        fetchBanner();
        fetch_home_category();
        fetch_best_seller();
        fetch_publisherChoice();
        fetch_top_rated();
        fetch_most_liked();
        fetch_top_hard();
        fetch_top_audio();
        fetch_top_digital();


        return view;
    }

    private void fetch_top_digital() {
        Call<String> call = myInterface.fetch_top_digital();
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
                            top_digital_model.clear();
                            rv_top_digital.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_top_digital.setVisibility(View.VISIBLE);
                            top_digital_model.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                top_digital_model.add(new Top_Hard_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("selling_price")));
                            }

                            Top_Hard_Adapter top_hard_adapter = new Top_Hard_Adapter(getActivity(),top_digital_model);
                            rv_top_digital.setAdapter(top_hard_adapter);
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

    private void fetch_top_audio(){
        Call<String> call = myInterface.fetch_top_audio();
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
                            top_audio_model.clear();
                            rv_top_audio.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_top_audio.setVisibility(View.VISIBLE);
                            top_audio_model.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                top_audio_model.add(new Top_Hard_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("selling_price")));
                            }
                            Top_Hard_Adapter top_hard_adapter = new Top_Hard_Adapter(getActivity(),top_audio_model);
                            rv_top_audio.setAdapter(top_hard_adapter);
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

    private void fetch_top_hard() {
        Call<String> call = myInterface.fetch_top_hard();
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
                            top_hard_model.clear();
                            rv_top_hard.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_top_hard.setVisibility(View.VISIBLE);
                            top_hard_model.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                top_hard_model.add(new Top_Hard_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("selling_price")));
                            }
                            Top_Hard_Adapter top_hard_adapter = new Top_Hard_Adapter(getActivity(),top_hard_model);
                            rv_top_hard.setAdapter(top_hard_adapter);
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

    private void fetch_most_liked() {
        Call<String> call = myInterface.fetch_most_liked();
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
                            most_liked_list.clear();
                            rv_most_liked.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_most_liked.setVisibility(View.VISIBLE);
                            most_liked_list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                most_liked_list.add(new SliderItem(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("book_type_category"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("description")));
                            }
                            SliderAdapter sliderAdapter = new SliderAdapter(getActivity(),most_liked_list);
                            rv_most_liked.setAdapter(sliderAdapter);
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

    private void fetch_top_rated() {
        Call<String> call = myInterface.fetch_top_rated();
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
                            newRelease_models.clear();
                            rv_top_rated.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_top_rated.setVisibility(View.VISIBLE);
                            newRelease_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                newRelease_models.add(new NewRelease_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("book_type_category"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("selling_price"),
                                        jsonObject.getString("rate")));
                            }
                            NewRelease_Adapter newRelease_adapter = new NewRelease_Adapter(getActivity(),newRelease_models);
                            rv_top_rated.setAdapter(newRelease_adapter);
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

    private void fetch_publisherChoice() {
        Call<String> call = myInterface.fetch_publisher_choice();
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
                            sliderItems.clear();
                            rv_recommend.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_recommend.setVisibility(View.VISIBLE);
                            sliderItems.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                sliderItems.add(new SliderItem(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("book_type_category"),
                                        jsonObject.getString("genre_id"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("author_name"),
                                        jsonObject.getString("rate"),
                                        jsonObject.getString("description")));
                            }
                            SliderAdapter sliderAdapter = new SliderAdapter(getActivity(),sliderItems);
                            rv_recommend.setAdapter(sliderAdapter);
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

    private void fetch_best_seller() {
        Call<String> call = myInterface.fetch_best_seller();
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
                            topE_book_models.clear();
                            rv_top_ebooks.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_top_ebooks.setVisibility(View.VISIBLE);
                            topE_book_models.clear();
                            if(jsonArray.length()>2) {
                                for (int i = 0; i < 2; i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    topE_book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                            jsonObject.getString("book_type"),
                                            jsonObject.getString("book_type_category"),
                                            jsonObject.getString("p_image"),
                                            jsonObject.getString("product_name"),
                                            jsonObject.getString("author_name"),
                                            jsonObject.getString("actual_price"),
                                            jsonObject.getString("rate"),
                                            jsonObject.getString("genre")));
                                }
                                TopE_Book_Adapter adapter = new TopE_Book_Adapter(getActivity(), topE_book_models);
                                rv_top_ebooks.setAdapter(adapter);
                                progressDialog.dismiss();
                            }else {
                                for (int i = 0; i <jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    topE_book_models.add(new TopE_Book_Model(jsonObject.getString("id"),
                                            jsonObject.getString("book_type"),
                                            jsonObject.getString("book_type_category"),
                                            jsonObject.getString("p_image"),
                                            jsonObject.getString("product_name"),
                                            jsonObject.getString("author_name"),
                                            jsonObject.getString("actual_price"),
                                            jsonObject.getString("rate"),
                                            jsonObject.getString("genre")));
                                }
                                TopE_Book_Adapter adapter = new TopE_Book_Adapter(getActivity(), topE_book_models);
                                rv_top_ebooks.setAdapter(adapter);
                                progressDialog.dismiss();
                            }
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

    private void fetch_home_category() {
        Call<String> call=myInterface.home_category_fetch();
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response!=null) {
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            linear_1.setVisibility(View.GONE);
                            linear_2.setVisibility(View.GONE);
                            linear_3.setVisibility(View.GONE);
                            linear_4.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (i == 0) {
                                    cat_1.setText(jsonObject.getString("category_name"));
                                    final String cat_id = jsonObject.getString("id");
                                    linear_1.setVisibility(View.VISIBLE);
                                    view_1.setBackgroundColor(getResources().getColor(R.color.white));
                                    fetch_product(cat_id);
                                    cat_1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            view_1.setBackgroundColor(getResources().getColor(R.color.white));
                                            view_2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            rv_for_you.setVisibility(View.VISIBLE);
                                            fetch_product(cat_id);
                                        }
                                    });
                                } else if (i == 1) {
                                    cat_2.setText(jsonObject.getString("category_name"));
                                    final String cat_id = jsonObject.getString("id");
                                    linear_2.setVisibility(View.VISIBLE);
                                    cat_2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            view_2.setBackgroundColor(getResources().getColor(R.color.white));
                                            view_1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            rv_for_you.setVisibility(View.VISIBLE);
                                            fetch_product(cat_id);
                                        }
                                    });
                                } else if (i == 2) {
                                    cat_3.setText(jsonObject.getString("category_name"));
                                    final String cat_id = jsonObject.getString("id");
                                    linear_3.setVisibility(View.VISIBLE);
                                    cat_3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            view_3.setBackgroundColor(getResources().getColor(R.color.white));
                                            view_2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            rv_for_you.setVisibility(View.VISIBLE);
                                            fetch_product(cat_id);
                                        }
                                    });
                                } else {
                                    cat_4.setText(jsonObject.getString("category_name"));
                                    final String cat_id = jsonObject.getString("id");
                                    linear_4.setVisibility(View.VISIBLE);
                                    cat_4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            view_4.setBackgroundColor(getResources().getColor(R.color.white));
                                            view_3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            view_1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                            rv_for_you.setVisibility(View.VISIBLE);
                                            fetch_product(cat_id);
                                        }
                                    });
                                }
                            }
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

    private void fetch_product(String cat_id) {
        Call<String> call = myInterface.fetch_product_by_category(cat_id);
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
                            categoryHome_models.clear();
                            rv_for_you.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_for_you.setVisibility(View.VISIBLE);
                            categoryHome_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                categoryHome_models.add(new CategoryHome_Model(jsonObject.getString("id"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("p_image"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("rate")));
                            }
                            Recommendation_Adapter adapter = new Recommendation_Adapter(getActivity(),categoryHome_models);
                            rv_for_you.setAdapter(adapter);
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

    private void fetchBanner(){
        Call<String> call=myInterface.banner_fetch();
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
                        viewPager.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No Banner found", Toast.LENGTH_SHORT).show();
                    } else {
                        viewPager.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            banner_models.add(new Banner_Model(jsonObject.getString("id"),
                                    jsonObject.getString("banner_image")));
                        }
                        viewPagerAdapter = new ViewPagerAdapter(getActivity(), banner_models);
                        viewPager.setAdapter(viewPagerAdapter);
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

    private void checkCart() {
        Call<String> call = myInterface.fetch_cart(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    String res = response.body();
                    try {
                        JSONObject jsonObject_1 = new JSONObject(res);
                        if(!jsonObject_1.getString("count").equals("0")){
                            ((Home_Activity)getActivity()).cart_count.setVisibility(View.VISIBLE);
                            ((Home_Activity)getActivity()).cart_count.setText(jsonObject_1.getString("count"));
                            progressDialog.dismiss();
                        }else {
                            ((Home_Activity)getActivity()).cart_count.setVisibility(View.GONE);
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

}
