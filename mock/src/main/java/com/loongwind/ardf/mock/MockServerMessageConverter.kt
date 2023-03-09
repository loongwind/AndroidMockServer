package com.loongwind.ardf.mock

import com.google.gson.Gson
import com.yanzhenjie.andserver.annotation.Converter
import com.yanzhenjie.andserver.framework.MessageConverter
import com.yanzhenjie.andserver.framework.body.JsonBody
import com.yanzhenjie.andserver.http.ResponseBody
import com.yanzhenjie.andserver.util.IOUtils
import com.yanzhenjie.andserver.util.MediaType
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset

private val gson = Gson()
@Converter
internal class MockServerMessageConverter : MessageConverter {

    override fun convert(output: Any?, mediaType: MediaType?): ResponseBody {
        return JsonBody(gson.toJson(output))
    }

    override fun <T : Any?> convert(stream: InputStream, mediaType: MediaType?, type: Type?): T? {
        val charset: Charset = mediaType?.charset
            ?: return gson.fromJson(IOUtils.toString(stream), type)
        return gson.fromJson(IOUtils.toString(stream, charset), type)
    }


}