package com.example.hp_awareness_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CovidApiService {

@GET
    Call<ModelCovidCase> getStatus(@Url String url);
}
