package com.alan.projetopadrao.data.api

import com.alan.projetopadrao.data.model.EventItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservice {

    @GET("events")
    suspend fun getEvents() : List<EventItem>

    @GET("events/{id}")
    suspend fun getDetailsEvent(@Path("id") id : String) : EventItem

    @POST("checkin")
    suspend fun setCheckin(@Query("eventId") id : String,
                   @Query("name") name : String,
                   @Query("email") email : String) : Response<Unit>
}