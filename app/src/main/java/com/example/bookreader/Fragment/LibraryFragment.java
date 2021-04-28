package com.example.bookreader.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreader.Adapter.Library_Adapter;
import com.example.bookreader.Adapter.Recommendation_Adapter;
import com.example.bookreader.ApiClient;
import com.example.bookreader.Model.CategoryHome_Model;
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

public class LibraryFragment extends Fragment {

    MyInterface myInterface;
    RecyclerView rv_library;
    List<Library_Model> library_modelList;
    LinearLayout lin_no_product;
    Button btn_add;
    User user;
    String user_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library,container,false);

        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        user = new User(getActivity());
        user_id = user.getUser_id();

        lin_no_product = view.findViewById(R.id.lin_no_product);
        btn_add = view.findViewById(R.id.btn_add);

        library_modelList = new ArrayList<>();
        rv_library = view.findViewById(R.id.rv_library);
        rv_library.setHasFixedSize(true);
        rv_library.setLayoutManager(new GridLayoutManager(getActivity(),3));
        fetch_libraryBooks();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Home_Fragment());
            }
        });

        return view;
    }

    private void fetch_libraryBooks() {
        Call<String> call = myInterface.fetch_library(user_id);
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
                            library_modelList.clear();
                            rv_library.setVisibility(View.GONE);
                            lin_no_product.setVisibility(View.VISIBLE);
//                            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_SHORT).show();
                        } else {
                            rv_library.setVisibility(View.VISIBLE);
                            lin_no_product.setVisibility(View.GONE);
                            library_modelList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                library_modelList.add(new Library_Model(jsonObject.getString("id"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("book_type"),
                                        jsonObject.getString("product_name")));
                            }
                            Library_Adapter library_adapter = new Library_Adapter(getActivity(),library_modelList);
                            rv_library.setAdapter(library_adapter);
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

    private void loadFragment(Home_Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.frame_library,fragment,fragment.getTag()).commit();
    }
}
