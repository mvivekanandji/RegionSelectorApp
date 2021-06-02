package com.enggvam.countryselectorapp.helper;

import androidx.annotation.NonNull;

import com.enggvam.countryselectorapp.data.remote.BaseUrl;

public class UrlHelper {

    public static String getFlagUrl(@NonNull final String regionCode){
        return BaseUrl.FLAGS+regionCode+"/flat/64.png";
    }
}
