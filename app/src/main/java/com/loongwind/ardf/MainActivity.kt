package com.loongwind.ardf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.loongwind.ardf.mock.MockServer
import com.loongwind.ardf.mock.addMockInterceptor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val text : TextView by lazy {
        findViewById(R.id.text)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        com.loongwind.ardf.mock.BuildConfig.DEBUG
        MockServer.init(this, enable = true, enableCache = false)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addMockInterceptor()
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.fastmock.site/mock/6d5084df89b4c7a49b28052a0f51c29a/test/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val testServices = retrofit.create(TestServices::class.java)

        findViewById<Button>(R.id.button).setOnClickListener {
            MainScope().launch {
                val user = testServices.test("cmad")
                text.text = user.toString()
            }
        }
    }
}