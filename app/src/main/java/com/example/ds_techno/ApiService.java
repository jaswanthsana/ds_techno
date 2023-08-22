package com.example.ds_techno;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {

    @GET("api/categories") // Replace with your actual API endpoint
    Call<List<CategoryResponse>> getCategories();
}
