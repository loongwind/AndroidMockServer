package com.loongwind.ardf.mock

import android.content.Context
import com.yanzhenjie.andserver.AndServer
import com.yanzhenjie.andserver.Server
import java.util.concurrent.TimeUnit

class MockServer {
    companion object {
        internal lateinit var mockRepository: MockRepository
            private set
        internal var enableMock = true
        internal var enableCache = true
        internal var mocking = false

        fun init(context: Context, enable: Boolean = true, enableCache: Boolean = true) {
            mockRepository = MockRepository(CacheRepository(context))
            enableMock = enable
            mocking = enableMock
            MockServer.enableCache = enableCache

            if (enable) {
                val server: Server = AndServer.webServer(context)
                    .port(8080)
                    .timeout(10, TimeUnit.SECONDS)
                    .build()
                server.startup()
            }
        }
    }

}