package com.example.dicodingeventapp.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.data.local.entity.UpcomingEventsEntity
import kotlinx.coroutines.launch


class UpcomingViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun getUpcomingEvents() = eventsRepository.getUpcomingEvents()

    fun checkIsFavorite(eventsId: Int): LiveData<UpcomingEventsEntity> {
        return eventsRepository.checkIsFavoriteUpcoming(eventsId)
    }

    fun saveEvent(eventsId: Int) {
        viewModelScope.launch {
            eventsRepository.setUpcomingFavorite(eventsId, 1)
        }
    }

    fun deleteEvent(eventsId: Int) {
        viewModelScope.launch {
            eventsRepository.setUpcomingFavorite(eventsId, 0)
        }
    }
}