package com.example.dicodingeventapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.di.Injection
import com.example.dicodingeventapp.ui.finished.FinishedViewModel
import com.example.dicodingeventapp.ui.home.HomeViewModel
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel

class ViewModelFactory private constructor(private val eventsRepository: EventsRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)){
            return UpcomingViewModel(eventsRepository) as T
        }
        if (modelClass.isAssignableFrom(FinishedViewModel::class.java)){
            return FinishedViewModel(eventsRepository) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(eventsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class "+ modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}