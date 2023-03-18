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
        cacheRepository.saveMockData(mockData)
    }
    fun add(mockData: MockData) {
        mockList.add(mockData)
        cacheRepository.saveMockData(mockData)
    }

    fun remove(id: Long) {
        mockList.removeIf {
            it.id == id
        }
        cacheRepository.removeWithId(id)
    }

    fun clean() {
        mockList.clear()
        cacheRepository.clean()
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
        val matchData = mockList.firstOrNull {
            it.url == url
        }
        //todo Match queryParams / formatParams / jsonBody
        return  matchData?.response?.decodeBase64()
    }
}