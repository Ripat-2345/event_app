package com.example.dicodingeventapp.ui

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.di.Injection
import com.example.dicodingeventapp.ui.favorite.FavoriteViewModel
import com.example.dicodingeventapp.ui.finished.FinishedViewModel
import com.example.dicodingeventapp.ui.home.HomeViewModel
import com.example.dicodingeventapp.ui.setting.SettingPreferences
import com.example.dicodingeventapp.ui.setting.SettingViewModel
import com.example.dicodingeventapp.ui.setting.dataStore
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel

class ViewModelFactory private constructor(private val eventsRepository: EventsRepository, private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {

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
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(eventsRepository) as T
        }
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class "+ modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, pref: SettingPreferences): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context), pref)
            }.also { instance = it }
    }
}