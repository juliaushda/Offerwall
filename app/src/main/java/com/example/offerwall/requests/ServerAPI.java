package com.example.offerwall.requests;

import com.example.offerwall.models.ModelA;
import com.example.offerwall.models.ModelB;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServerAPI {

    @GET("/api/v1/trending")
    Call<List<ModelA>> getData();

    @GET("/api/v1/object/{id}")
    Call<ModelB> getObject(@Path("id") Integer id);
}
