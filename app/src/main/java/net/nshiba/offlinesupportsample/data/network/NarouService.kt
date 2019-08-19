package net.nshiba.offlinesupportsample.data.network

import retrofit2.Call
import retrofit2.http.GET

interface NarouService {

    @GET("/novelapi/api?out=json&of=t-w-n&lim=100&order=hyoka")
    fun fetchNovelsOrderHyoka(): Call<List<Novel>>

    @GET("/novelapi/api?out=json&of=t-w-n&lim=100&order=new")
    fun fetchNovelsOrderNew(): Call<List<Novel>>
}