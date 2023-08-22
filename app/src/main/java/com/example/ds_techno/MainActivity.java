package com.example.ds_techno;


import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    private List<String> categoryNames = new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.categoryRecyclerView);


        fetchDataFromApi();
    }


    private void fetchDataFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.25.24.23:8080/") // Replace with your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<CategoryResponse>> call = apiService.getCategories();


        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    List<CategoryResponse> categoryResponses = response.body();
                    categoryNames = getCategoryNames(categoryResponses);
                    setupRecyclerView();
                } else {
                    // Handle error response
                    String errorMessage = "Failed to fetch categories";
                    // Show an error message to the user
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                // Handle failure
                String errorMessage = "Network request failed";
                // Show an error message to the user
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<String> getCategoryNames(List<CategoryResponse> categoryResponses) {
        List<String> names = new ArrayList<>();
        for (CategoryResponse response : categoryResponses) {
            names.add(response.getName());
        }
        return names;
    }


    private void setupRecyclerView() {
        CategoryAdapter adapter = new CategoryAdapter(categoryNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
