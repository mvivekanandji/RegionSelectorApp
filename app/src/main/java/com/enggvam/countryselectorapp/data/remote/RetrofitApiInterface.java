package com.enggvam.countryselectorapp.data.remote;

import com.enggvam.countryselectorapp.data.remote.response.RegionResponse;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * <h1>Interface containing endpoint used in this app (accessed using Retrofit)</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public interface RetrofitApiInterface {

    /**
     * Method to create retrofit api interface.
     *
     * @return the {@link Call< RegionResponse >}
     */
    @GET("countries/")
    Call<RegionResponse> getAllRegions();
}
