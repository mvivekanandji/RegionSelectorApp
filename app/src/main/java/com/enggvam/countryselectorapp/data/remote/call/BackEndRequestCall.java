package com.enggvam.countryselectorapp.data.remote.call;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.enggvam.countryselectorapp.data.remote.response.BackendResponse;
import com.enggvam.countryselectorapp.data.remote.BackendError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <h1>Class to make quick backend call using retrofit</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class BackEndRequestCall {

    /**
     * The interface Backend request listener.
     */
    public interface BackendRequestListener {
        /**
         * On success of the backend request call.
         *
         * @param tag          the {@link String} tag used while invoking
         *                     {{@link #enqueue(Call, String, BackendRequestListener)}}
         * @param responseBody the response body
         */
        void onSuccess(String tag, @NonNull Object responseBody);

        /**
         * On error of the backend request call.
         *
         * @param tag          the {@link String} tag used while invoking
         *                     {{@link #enqueue(Call, String, BackendRequestListener)}}
         * @param backendError the backend error
         * @see BackendError
         */
        default void onError(String tag, @NonNull final BackendError backendError) {
        }
    }

    /**
     * Method to hit the backend api (defined by {@code requestCall }) asynchronously, and
     * return the result of the api call (success or failure) using the help of
     * {@link BackendRequestListener}.
     * <p>
     * {@link BackendRequestListener#onSuccess(String, Object)} is invoked if the api hit is
     * successful ie if the api was reachable and the the server responds success and the response
     * payload {@link BackendResponse} has the code 200.
     * <p>
     * {@link BackendRequestListener#onError(String, BackendError)} is invoked in all other cases
     *
     * @param <T>         the type parameter
     * @param requestCall the {@link Call<T>} request call
     * @param tag         the {@link String} tag to identify/mark this request
     * @param listener    the {@link BackendRequestListener} listener
     * @see BackendRequestListener
     * @see BackendResponse
     * @see BackendError
     */
    public static <T> void enqueue(Call<T> requestCall, @Nullable String tag,
                                   @Nullable final BackendRequestListener listener) {
        requestCall.enqueue(new Callback<T>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                // server responded
                if (response.isSuccessful()) {//successful response form server
                    if (((BackendResponse) (response.body())).getCode() == 200) {
                        if (listener != null)
                            listener.onSuccess(tag, response.body());

                    } else {
                        if (listener != null)
                            listener.onError(tag, BackendError.INVALID_REQUEST);
                    }
                } else {
                    //UNSUCCESSFUL response form server
                    if (listener != null)
                        listener.onError(tag, BackendError.NOT_FOUND_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                if (t.getMessage() != null && t.getMessage().contains("Exception")) {// model parsing error
                    if (listener != null)
                        listener.onError(tag, BackendError.PARSING_ERROR);

                } else {// unable to reach server
                    if (listener != null)
                        listener.onError(tag, BackendError.NETWORK_ERROR);
                }
            }
        });
    }

}
