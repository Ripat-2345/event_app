package com.example.dicodingeventapp.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.data.remote.response.DetailEventItem
import com.example.dicodingeventapp.data.remote.response.DetailEventResponse
import com.example.dicodingeventapp.data.remote.response.EventResponse
import com.example.dicodingeventapp.data.remote.response.ListEventsItem
import com.example.dicodingeventapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun getFinishedEvents() = eventsRepository.getFinishedEvents()
}