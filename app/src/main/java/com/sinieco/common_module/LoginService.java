package com.sinieco.common_module;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

//    @Headers("Content-Type:application/x-www-form-urlencoded")
//    @Headers("Content-Type:multipart/form-data")
    @POST("orgUserCommon/orgViewRender.do")
//    @GET("orgUserCommon/orgViewRender.do")
    @FormUrlEncoded
    Call<ResponseBody> passkey(@Field("name") String name);

}
