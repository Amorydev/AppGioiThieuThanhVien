package com.amory.doanthuctap.retrofit

import com.amory.doanthuctap.model.TeamModel
import com.amory.doanthuctap.model.Teams
import com.amory.doanthuctap.model.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {
    @POST("dangki.php")
    @FormUrlEncoded
    fun dangkitaikhoan(
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("mssv") mssv:String,
    ):Call<UserModel>
    @POST("dangnhap.php")
    @FormUrlEncoded
    fun dangnhaptaikhoan(
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<UserModel>
    @POST("getthanhvien.php")
    fun getthanhvien(): Call<TeamModel>
    @POST("suathongtin.php")
    @FormUrlEncoded
    fun suathongtin(
        @Field("name") name:String,
        @Field("chucvu") chucvu:String,
        @Field("link_fb") link_fb:String,
        @Field("link_insta") link_insta:String,
        @Field("link_git") link_git:String,
        @Field("image_url") image_url:String,
        @Field("mssv") mssv:String
    ): Call<TeamModel>
    @POST("getthongtin.php")
    @FormUrlEncoded
    fun getthongtin(
        @Field("mssv") mssv:String
    ): Call<TeamModel>
    @POST("themthanhvien.php")
    @FormUrlEncoded
    fun addthanhvien(
        @Field("name") name:String,
        @Field("mssv") mssv:String,
        @Field("chucvu") chucvu:String,
        @Field("link_fb") link_fb:String,
        @Field("link_insta") link_insta:String,
        @Field("link_git") link_git:String,
        @Field("image_url") image_url:String
    ): Call<TeamModel>
    @Multipart
    @POST("uploadhinhanh.php")
    fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("file") name: RequestBody
    ): Call<TeamModel>
}