package com.loongwind.ardf.mock

internal class MockRepository(private val cacheRepository: CacheRepository) {
    private val mockList: ArrayList<MockData> = arrayListOf()

    init {
        val list = cacheRepository.loadMockData()
        mockList.addAll(list)
    }

    fun add(mockData: List<MockData>) {
        val oldData = mockData.filter {o ->
            mockList.firstOrNull { n ->
                o.uuid == n.uuid
            } != null
        }
        val newData = mockData.toMutableList().apply {
            removeAll(oldData)
        }
        mockList.forEach { o ->
            oldData.firstOrNull { n ->
                o.uuid == n.uuid
            }?.let {
                o.copyWithMock(it)
            }
        }
        mockList.addAll(newData)
        cacheRepository.saveMockData(mockList)
    }
    fun add(mockData: MockData) {
        mockList.add(mockData)
        cacheRepository.saveMockData(mockData)
    }

    fun remove(id: Long) {
        mockList.firstOrNull { it.id == id }?.let {
            mockList.remove(it)
        }
        cacheRepository.removeWithId(id)
    }

    fun removeWithUuid(uuid: String) {
        mockList.firstOrNull { it.uuid == uuid }?.let {
            mockList.remove(it)
        }
        cacheRepository.removeWithUuid(uuid)
    }

    fun clean() {
        mockList.clear()
//        cacheRepository.clean()
    }

    fun list(): List<MockData> {
        return mockList
    }

    fun getMockResponse(
        url: String,
        queryParams: Map<String, String>? = null,
        formatParams: Map<String, String>? = null,
        jsonBody: String? = null
    ) : String?{
        if(!MockServer.mocking){
            return null
        }
        val matchData = mockList.filter {
            it.url == url
        }.firstOrNull {
            it.enabled
        }
        //todo Match queryParams / formatParams / jsonBody
        return  matchData?.response?.decodeBase64()
    }
}