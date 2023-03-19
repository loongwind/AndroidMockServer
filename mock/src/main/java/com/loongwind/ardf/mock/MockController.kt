package com.loongwind.ardf.mock

import android.util.Log
import com.yanzhenjie.andserver.annotation.*


internal data class MockServerResponse<T>(val code:Int = 200, val data:T, val msg:String = "OK")

@RestController
@RequestMapping(path = ["/mock"])
internal class MockController {

    private val mockRepository = MockServer.mockRepository

    @PostMapping("/add")
    fun add(@RequestBody mockData: List<MockData>) : MockServerResponse<Boolean>{
        mockRepository.add(mockData)
        return MockServerResponse(data = true)
    }

    @PostMapping("/remove")
    fun remove(@RequestParam("uuid") uuid: String) : MockServerResponse<Boolean>{
        mockRepository.removeWithUuid(uuid)
        return MockServerResponse(data = true)
    }

    @PostMapping("/clean")
    fun clean() : MockServerResponse<Boolean>{
        mockRepository.clean()
        return MockServerResponse(data = true)
    }

    @PostMapping("/list")
    fun list() : MockServerResponse<List<MockData>>{
        val data = mockRepository.list()
        return MockServerResponse(data = data)
    }

    @PostMapping("/status")
    fun setMockStatus(@RequestParam("mocking") mocking : Boolean) : MockServerResponse<Boolean>{
        MockServer.mocking = mocking
        return MockServerResponse(data = true)
    }
    @GetMapping("/status/get")
    fun getMockStatus() : MockServerResponse<Boolean>{
        return MockServerResponse(data = MockServer.mocking)
    }
}