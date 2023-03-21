package com.loongwind.ardf.mock

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
internal data class MockData(
    @Id
    var id: Long = 0,
    @Unique
    var uuid: String,
    var name: String,
    var url: String,
    var response: String,
    var enabled: Boolean = true,
    var queryParams: Map<String, String>?,
    var formatParams: Map<String, String>?,
    var jsonParams: Map<String, String>?,
) {

    fun copyWithMock(mockData: MockData) {
        this.name = mockData.name
        this.url = mockData.url
        this.response = mockData.response
        this.enabled = mockData.enabled
        this.queryParams = mockData.queryParams
        this.formatParams = mockData.formatParams
        this.jsonParams = mockData.jsonParams
    }

}
