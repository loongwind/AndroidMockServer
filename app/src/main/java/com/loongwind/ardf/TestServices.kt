package com.loongwind.ardf

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class User(var name:String, var img:String)

interface TestServices {

    @FormUrlEncoded
    @POST("user")
    suspend fun test(@Field("id") id:String, @Field("a") a :String = "1"): User

    @GET("get-test")
    suspend fun list(@Query("id") id:String, @Query("a") a :String = "1"): List<String>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username:String, @Field("password") password:String): User
}