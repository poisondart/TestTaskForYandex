package com.example.nix.testtaskforyandex;

import com.example.nix.testtaskforyandex.model.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Nix on 20.10.2017.
 */

public interface YandexTranslateAPI {
    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<Result> getResult(@Field("key") String key, @Field("text") String text, @Field("lang") String lang);
}
