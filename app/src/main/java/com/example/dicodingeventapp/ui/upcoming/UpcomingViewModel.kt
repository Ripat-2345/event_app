package com.example.dicodingeventapp.ui.upcoming

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.data.remote.response.EventResponse
import com.example.dicodingeventapp.data.remote.response.ListEventsItem
import com.example.dicodingeventapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpcomingViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun getUpcomingEvents() = eventsRepository.getUpcomingEvents()
}