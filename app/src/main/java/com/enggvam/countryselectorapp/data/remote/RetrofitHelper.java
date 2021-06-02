package com.enggvam.countryselectorapp.data.remote;

import androidx.annotation.NonNull;

import static com.enggvam.countryselectorapp.data.remote.BaseUrl.REGIONS;

/**
 * <h1>Class to get {@link RetrofitApiInterface} for the api endpoints to be used with retrofit</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class RetrofitHelper {
    /**
     * Method to create retrofit api interface.
     *
     * @return the retrofit api interface
     * @see RetrofitApiInterface
     */
    private static RetrofitApiInterface create(@NonNull final String baseUrl) {
        return new RetrofitClient().getClient(baseUrl).create(RetrofitApiInterface.class);
    }

    public static RetrofitApiInterface getRegionInterface() {
        return create(REGIONS);
    }

}
