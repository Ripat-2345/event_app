package com.example.dicodingeventapp.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.DetailEventItem
import com.example.dicodingeventapp.data.response.DetailEventResponse
import com.example.dicodingeventapp.data.response.EventResponse
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {
    private val _event = MutableLiveData<EventResponse>()
    val event: LiveData<EventResponse> = _event

    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    companion object{
        const val TAG = "EventModel"
    }

    init {
        getFinishedEvent()
    }

    fun getFinishedEvent(limit: String = "40"){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getListEvents(active = "0", limit)

        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _event.value = responseBody!!
                    _listEvent.value = responseBody.listEvents
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = "Gagal Memuat Data!"
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}