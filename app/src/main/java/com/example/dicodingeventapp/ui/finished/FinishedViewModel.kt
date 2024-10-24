package com.example.dicodingeventapp.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.data.local.entity.FinishedEventsEntity
import kotlinx.coroutines.launch

class FinishedViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun getFinishedEvents() = eventsRepository.getFinishedEvents()

    fun checkIsFavorite(eventsId: Int): LiveData<FinishedEventsEntity> {
        return eventsRepository.checkIsFavoriteFinished(eventsId)
    }

    fun saveEvent(eventsId: Int) {
        viewModelScope.launch {
            eventsRepository.setFinishedFavorite(eventsId, 1)
        }
    }

    fun deleteEvent(eventsId: Int) {
        viewModelScope.launch {
            eventsRepository.setFinishedFavorite(eventsId, 0)
        }
    }
}