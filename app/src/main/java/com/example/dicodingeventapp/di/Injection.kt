package com.example.dicodingeventapp.di

import android.content.Context
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.data.local.room.EventRoomDatabase
import com.example.dicodingeventapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventsRepository{
        val apiService = ApiConfig.getApiService()
        val database = EventRoomDatabase.getInstance(context)
        val upcomingEventsDao = database.upcomingEventsDao()
        val finishedEventsDao = database.finishedEventsDao()
        return EventsRepository.getInstance(apiService, upcomingEventsDao, finishedEventsDao)
    }
}