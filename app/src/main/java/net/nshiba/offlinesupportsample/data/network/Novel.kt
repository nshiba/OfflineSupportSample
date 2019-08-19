package net.nshiba.offlinesupportsample.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Novel(
    @Json(name = "ncode") val ncode: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "writer") val writer: String?
)
