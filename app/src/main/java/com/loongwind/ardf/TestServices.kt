package com.loongwind.ardf

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class User(var name:String, var img:String)

interface TestServices {

    @FormUrlEncoded
    @POST("user")
    suspend fun test(@Field("id") id:String, @Field("a") a :String = "1"): User

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username:String, @Field("password") password:String): User
}