package com.example.bookreader.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreader.Adapter.Genre_Adapter;
import com.example.bookreader.Adapter.NewRelease_Adapter;
import com.example.bookreader.ApiClient;
import com.example.bookreader.Model.Genre_Model;
import com.example.bookreader.Model.NewRelease_Model;
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

public class Genre_Fragment extends Fragment {

    MyInterface myInterface;
    RecyclerView rv_genre;
    List<Genre_Model> genre_models;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre, container, false);

        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        genre_models = new ArrayList<>();

        rv_genre = view.findViewById(R.id.rv_genre);
        rv_genre.setHasFixedSize(true);
        rv_genre.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        fetch_genre();

        return view;
    }

    private void fetch_genre() {
        Call<String> call = myInterface.fetch_genre();
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "loading", "Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String res = response.body();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() == 0) {
                            progressDialog.dismiss();
                            genre_models.clear();
                            rv_genre.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_genre.setVisibility(View.VISIBLE);
                            genre_models.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(!jsonObject.getString("genre").toString().equals("")){
                                    genre_models.add(new Genre_Model(jsonObject.getString("id"),
                                            jsonObject.getString("genre")));
                                }

                            }
                            Genre_Adapter genre_adapter = new Genre_Adapter(getActivity(), genre_models);
                            rv_genre.setAdapter(genre_adapter);
                            progressDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
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
