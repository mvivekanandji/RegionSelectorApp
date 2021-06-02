package com.enggvam.countryselectorapp.data.remote;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <h1>Class to get instance of {@link Retrofit}</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class RetrofitClient {
    /**
     * Method to get new Retrofit instance.<br><br>
     * It also adds the interceptor {@link HttpLoggingInterceptor} to log and view the request and
     * response calls.
     *
     * @param baseUrl Base url of the rest endpoint to make connection.
     * @return {@code new instance}
     */
    public Retrofit getClient(@NonNull final String baseUrl) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
