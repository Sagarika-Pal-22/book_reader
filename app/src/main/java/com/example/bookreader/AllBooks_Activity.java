package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookreader.Adapter.NewRelease_Adapter;
import com.example.bookreader.Adapter.TopE_Book_Adapter;
import com.example.bookreader.Model.NewRelease_Model;
import com.example.bookreader.Model.TopE_Book_Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBooks_Activity extends AppCompatActivity {

    MyInterface myInterface;
    RecyclerView rv_all_book;
    List<NewRelease_Model> all_books;
    NewRelease_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myInterface = ApiClient.getApiClient().create(MyInterface.class);

        all_books = new ArrayList<>();



        rv_all_book = findViewById(R.id.rv_all_book);
        rv_all_book.setHasFixedSize(true);
        rv_all_book.setLayoutManager(new GridLayoutManager(this,3));
//        all_books.add(new NewRelease_Model(R.drawable.book_1,"The Jungle Book","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_2,"The Hike","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_3,"The Little Engine That Could","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_1,"The Jungle Book","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_2,"The Hike","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_3,"The Little Engine That Could","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_1,"The Jungle Book","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_2,"The Hike","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_3,"The Little Engine That Could","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_1,"The Jungle Book","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_2,"The Hike","Priyanka Jangid","50.00","4.3"));
//        all_books.add(new NewRelease_Model(R.drawable.book_3,"The Little Engine That Could","Priyanka Jangid","50.00","4.3"));
//        adapter = new NewRelease_Adapter(this,all_books);
//        rv_all_book.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(AllBooks_Activity.this, Home_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.search) {
            Toast.makeText(this, "Search your neccessity", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this,Search_Activity.class));
            return true;
        }else if(id == R.id.notification) {
//            startActivity(new Intent(this,Cart_Activity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}