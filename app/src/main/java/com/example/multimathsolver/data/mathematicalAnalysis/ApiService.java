package com.example.multimathsolver.data.mathematicalAnalysis;

import com.example.multimathsolver.BuildConfig;
import com.example.multimathsolver.domain.models.LimitResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("query?appid=" + BuildConfig.API_KEY + "&output=json")
    Single<LimitResponse> getLimitResponse(@Query("input") String sequence);

}
