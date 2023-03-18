package com.loongwind.ardf.mock

import android.util.Base64
import java.nio.charset.Charset


internal fun String.toBase64() : String{
    return Base64.encodeToString(toByteArray(),Base64.DEFAULT)
}

internal fun String.decodeBase64() : String{
    return String(Base64.decode(this,Base64.DEFAULT), Charsets.UTF_8)
}