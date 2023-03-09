package com.loongwind.ardf.mock

import android.content.Context
import io.objectbox.BoxStore

internal class CacheRepository(private val context: Context) {
    private val store: BoxStore by lazy {
        MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    private val mockDataBox by lazy {
        store.boxFor(MockData::class.java)
    }


    fun saveMockData(mockData : MockData){
        mockDataBox.put(mockData)
    }

    fun saveMockData(mockData : List<MockData>){
        mockDataBox.put(mockData)
    }

    fun removeMockData(mockData: MockData){
        mockDataBox.remove(mockData)
    }
    fun removeWithId(id: Long){
        try {
            mockDataBox.remove(id)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun clean(){
        mockDataBox.removeAll()
    }

    fun loadMockData() : List<MockData>{
        return mockDataBox.all
    }


}