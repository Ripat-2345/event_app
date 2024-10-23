package com.example.dicodingeventapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.dicodingeventapp.data.local.entity.FinishedEventsEntity
import com.example.dicodingeventapp.data.local.entity.UpcomingEventsEntity
import com.example.dicodingeventapp.data.local.room.FinishedEventsDao
import com.example.dicodingeventapp.data.local.room.UpcomingEventsDao
import com.example.dicodingeventapp.data.remote.response.EventResponse
import com.example.dicodingeventapp.data.remote.retrofit.ApiService

class EventsRepository private constructor(
    private val apiService: ApiService,
    private val upcomingEventsDao: UpcomingEventsDao,
    private val finishedEventsDao: FinishedEventsDao
) {
    private val result = MediatorLiveData<Result<List<UpcomingEventsEntity>>>()

    // get upcoming events
    fun getUpcomingEvents(): LiveData<Result<List<UpcomingEventsEntity>>> = liveData{
        emit(Result.Loading)
        try {
            val response: EventResponse = apiService.getListEvents(active = "1")
            val events = response.listEvents
            val eventsList = events.map { event ->
                val isFavorite = upcomingEventsDao.isFavorite(event.id)
                UpcomingEventsEntity(
                    event.id,
                    event.summary,
                    event.mediaCover,
                    event.registrants,
                    event.imageLogo,
                    event.link,
                    event.description,
                    event.ownerName,
                    event.cityName,
                    event.quota,
                    event.name,
                    event.beginTime,
                    event.endTime,
                    event.category,
                    isFavorite
                )
            }
            upcomingEventsDao.deleteAll()
            upcomingEventsDao.insertEvents(eventsList)
        } catch (e: Exception){
            Log.d("EventsRepository", "getUpcomingEvents: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<UpcomingEventsEntity>>> = upcomingEventsDao.getUpcomingEvents().map { Result.Success(it) }
        emitSource(localData)
    }

    // get finished events
    fun getFinishedEvents(): LiveData<Result<List<FinishedEventsEntity>>> = liveData{
        emit(Result.Loading)
        try {
            val response: EventResponse = apiService.getListEvents(active = "0")
            val events = response.listEvents
            val eventsList = events.map { event ->
                val isFavorite = upcomingEventsDao.isFavorite(event.id)
                FinishedEventsEntity(
                    event.id,
                    event.summary,
                    event.mediaCover,
                    event.registrants,
                    event.imageLogo,
                    event.link,
                    event.description,
                    event.ownerName,
                    event.cityName,
                    event.quota,
                    event.name,
                    event.beginTime,
                    event.endTime,
                    event.category,
                    isFavorite
                )
            }
            finishedEventsDao.deleteAll()
            finishedEventsDao.insertEvents(eventsList)
        } catch (e: Exception){
            Log.d("EventsRepository", "getFinishedEvents: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<FinishedEventsEntity>>> = finishedEventsDao.getFinishedEvents().map { Result.Success(it) }
        emitSource(localData)
    }

    companion object{
        @Volatile
        private var instance: EventsRepository? = null
        fun getInstance(
            apiService: ApiService,
            upcomingEventsDao: UpcomingEventsDao,
            finishedEventsDao: FinishedEventsDao
        ): EventsRepository =
            instance ?: synchronized(this){
                instance ?: EventsRepository(apiService, upcomingEventsDao, finishedEventsDao)
            }.also { instance = it }
    }

}