package com.loongwind.ardf.mock

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
internal data class MockData(
    @Id
    var id:Long = 0,
    var name:String,
    var url:String,
    var response:String,
    var queryParams:Map<String, String>?,
    var formatParams:Map<String, String>?,
    var jsonParams:Map<String, String>?,
)
