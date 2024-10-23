package com.example.dicodingeventapp.ui.detail_event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.remote.response.DetailEventItem
import com.example.dicodingeventapp.data.remote.response.DetailEventResponse
import com.example.dicodingeventapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel: ViewModel() {
    private val _detailEvent = MutableLiveData<DetailEventResponse>()
    val detailEvent: LiveData<DetailEventResponse> = _detailEvent

    private val _detailEventItem = MutableLiveData<DetailEventItem>()
    val detailEventItem: LiveData<DetailEventItem> = _detailEventItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    companion object{
        private const val TAG = "EventModel"
    }

//    init {
//        getDetailEvent()
//    }

    fun getDetailEvent(idEvent: String){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailEvent(idEvent)

        client.enqueue(object: Callback<DetailEventResponse> {
            override fun onResponse(call: Call<DetailEventResponse>, response: Response<DetailEventResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _detailEvent.value = responseBody!!
                    _detailEventItem.value = responseBody.event
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = "Gagal Memuat Data!"
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}