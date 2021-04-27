package com.example.bookreader.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookreader.Address_Activity;
import com.example.bookreader.ApiClient;
import com.example.bookreader.Home_Activity;
import com.example.bookreader.Login_Activity;
import com.example.bookreader.Model.User;
import com.example.bookreader.MyInterface;
import com.example.bookreader.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Fragment extends Fragment {

    MyInterface myInterface;
    String user_id;
    ImageView edit_account,edit_adrs;
    Button btn_logout,btn_update;
    EditText edit_name,edit_number,edit_email,edit_pin,edit_address,edit_locality,edit_city,edit_state;
    TextView text_add;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        myInterface = ApiClient.getApiClient().create(MyInterface.class);
        final User user=new User(getActivity());
        user_id=user.getUser_id();

        btn_update = view.findViewById(R.id.btn_update);
        btn_logout = view.findViewById(R.id.btn_logout);
        edit_account = view.findViewById(R.id.edit_account);
        edit_name = view.findViewById(R.id.edit_name);
        edit_number = view.findViewById(R.id.edit_number);
        edit_email = view.findViewById(R.id.edit_email);
        edit_pin = view.findViewById(R.id.edit_pin);
        edit_address = view.findViewById(R.id.edit_address);
        edit_locality = view.findViewById(R.id.edit_locality);
        edit_city = view.findViewById(R.id.edit_city);
        edit_state = view.findViewById(R.id.edit_state);
//        edit_adrs = view.findViewById(R.id.edit_adrs);
        edit_account = view.findViewById(R.id.edit_account);
//        text_add = view.findViewById(R.id.text_add);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.remove();
                startActivity(new Intent(getActivity(), Login_Activity.class));
            }
        });
        edit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_name.setEnabled(true);
                edit_name.setBackgroundResource(R.color.greyL);
                edit_number.setEnabled(true);
                edit_number.setBackgroundResource(R.color.greyL);
                edit_email.setEnabled(false);
                edit_pin.setEnabled(true);
                edit_pin.setBackgroundResource(R.color.greyL);
                edit_address.setEnabled(true);
                edit_address.setBackgroundResource(R.color.greyL);
                edit_locality.setEnabled(true);
                edit_locality.setBackgroundResource(R.color.greyL);
                edit_city.setEnabled(true);
                edit_city.setBackgroundResource(R.color.greyL);
                edit_state.setEnabled(true);
                edit_state.setBackgroundResource(R.color.greyL);

                btn_update.setVisibility(View.VISIBLE);
            }
        });
        fetch_profile();
        
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_name.getText().toString().equals("")){
                    edit_name.setError("Please fill this field");
                }else if (edit_number.getText().toString().equals("")){
                    edit_number.setError("Please fill this field");
                }else if (edit_pin.getText().toString().equals("")){
                    edit_pin.setError("Please fill this field");
                }else if (edit_address.getText().toString().equals("")){
                    edit_address.setError("Please fill this field");
                }else if (edit_locality.getText().toString().equals("")){
                    edit_locality.setError("Please fill this field");
                }else if (edit_city.getText().toString().equals("")){
                    edit_city.setError("Please fill this field");
                }else if (edit_state.getText().toString().equals("")){
                    edit_state.setError("Please fill this field");
                }else {
                    updateProfile();
                }
            }
        });

//        text_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), Address_Activity.class));
//            }
//        });
        return view;
    }

    private void fetch_profile() {
        Call<String> call = myInterface.fetch_profile(user_id);
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res=response.body();
                if (res!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        edit_name.setText(jsonObject.getString("name"));
                        edit_number.setText(jsonObject.getString("number"));
                        edit_email.setText(jsonObject.getString("email"));

                        if(jsonObject.getString("pincode").equals("null")){
                            edit_pin.setHint("Add Pin");
                        }else {
                            edit_pin.setText(jsonObject.getString("pincode"));
                        }

                        if(jsonObject.getString("address").equals("null")){
                            edit_address.setHint("Add address");
                        }else {
                            edit_address.setText(jsonObject.getString("address"));
                        }

                        if(jsonObject.getString("locality").equals("null")){
                            edit_locality.setHint("Add locality");
                        }else {
                            edit_locality.setText(jsonObject.getString("locality"));
                        }

                        if(jsonObject.getString("city").equals("null")){
                            edit_city.setHint("Add city");
                        }else {
                            edit_city.setText(jsonObject.getString("city"));
                        }

                        if(jsonObject.getString("state").equals("null")){
                            edit_state.setHint("Add State");
                        }else {
                            edit_state.setText(jsonObject.getString("state"));
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Response null", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Slow network find", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void updateProfile() {
        String name=edit_name.getText().toString();
        String number=edit_number.getText().toString();
        String pincode=edit_pin.getText().toString();
        String address=edit_address.getText().toString();
        String locality=edit_locality.getText().toString();
        String city=edit_city.getText().toString();
        String state=edit_state.getText().toString();
        Call<String> call= myInterface.profile_update(user_id,name,number,pincode,address,locality,city,state);
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"loading","Please Wait");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!=null){
                    String res=response.body();
                    try {
                        JSONObject object=new JSONObject(res);
                        if (object.getString("rec").equals("1")){
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Profile Updated Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), Home_Activity.class));

                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Profile not Updated."+object, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Slow network find", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


//                        if(jsonObject.getString("address").equals("null")){
//                            edit_address.setEnabled(true);
//                            text_add.setVisibility(View.VISIBLE);
//                            edit_adrs.setVisibility(View.GONE);
//                        }else{
//                            edit_address.setText(jsonObject.getString("pincode")+jsonObject.getString("address")+
//                                    jsonObject.getString("locality")+jsonObject.getString("city")+
//                                    jsonObject.getString("state"));
//                            edit_address.setEnabled(false);
//                            text_add.setVisibility(View.GONE);
//                            edit_adrs.setVisibility(View.VISIBLE);
//                        }

