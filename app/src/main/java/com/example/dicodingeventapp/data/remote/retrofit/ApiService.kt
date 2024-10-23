package com.example.dicodingeventapp.data.remote.retrofit

import com.example.dicodingeventapp.data.remote.response.DetailEventResponse
import com.example.dicodingeventapp.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /*
    * 1 : Menampilkan events yang aktif/akan datang (default)
      0 : Menampilkan events yang sudah selesai
     -1 : Menampilkan semua events.
    * */
    @GET("events")
    suspend fun getListEvents(
        @Query("active") active: String = "-1",
        @Query("limit") limit: String = "40"
    ): EventResponse

    // Get detail event
    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailEventResponse>
}