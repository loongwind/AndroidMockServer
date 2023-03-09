package com.loongwind.ardf.mock

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class MockInterceptor : Interceptor {

    private val mockRepository = MockServer.mockRepository

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.encodedPath
        val requestBody = request.body
        val contentType = requestBody?.contentType()
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
        val requestBodyData = buffer.readString(charset)

        val mockResponse = mockRepository.getMockResponse(url)
        if (mockResponse != null) {
            return Response.Builder()
                .code(200)
                .body(mockResponse.toResponseBody())
                .message("ok")
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .build()
        }

        return chain.proceed(request)
    }
}

fun OkHttpClient.Builder.addMockInterceptor() : OkHttpClient.Builder{
    if(MockServer.enableMock){
        addInterceptor(MockInterceptor())
    }
    return this
}