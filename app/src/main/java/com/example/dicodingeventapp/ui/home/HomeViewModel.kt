package com.example.dicodingeventapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.EventResponse
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.data.retrofit.ApiConfig
import com.example.dicodingeventapp.ui.finished.FinishedViewModel
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel.Companion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _upcomingEvent = MutableLiveData<EventResponse>()
    val upcomingEvent: LiveData<EventResponse> = _upcomingEvent

    private val _listUpcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val listUpcomingEvent: LiveData<List<ListEventsItem>> = _listUpcomingEvent

    private val _finishedEvent = MutableLiveData<EventResponse>()
    val finishedEvent: LiveData<EventResponse> = _finishedEvent

    private val _listFinishedEvent = MutableLiveData<List<ListEventsItem>>()
    val listFinishedEvent: LiveData<List<ListEventsItem>> = _listFinishedEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    companion object{
        private const val TAG = "EventModel"
    }

    init {
        getUpComingEvent()
        getFinishedEvent()
    }

    private fun getUpComingEvent(){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getListEvents(active = "1", limit = "5")

        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _upcomingEvent.value = responseBody!!
                    _listUpcomingEvent.value = responseBody.listEvents
                }else{
                    Log.e(UpcomingViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = "Gagal Memuat Data!"
                Log.e(UpcomingViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getFinishedEvent(){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getListEvents(active = "0", limit = "5")

        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _finishedEvent.value = responseBody!!
                    _listFinishedEvent.value = responseBody.listEvents
                }else{
                    Log.e(FinishedViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = "Gagal Memuat Data!"
                Log.e(FinishedViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }
}