package com.example.dicodingeventapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingeventapp.data.EventsRepository
import com.example.dicodingeventapp.data.local.entity.FavoriteEventsEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun getFavoriteEvents() = eventsRepository.getFavoriteEvents()

    fun insertFavoriteEvent(favoriteEventsEntity: FavoriteEventsEntity){
        viewModelScope.launch {
            eventsRepository.insertFavoriteEvent(favoriteEventsEntity)
        }
    }

    fun deleteFavoriteEvent(id: Int){
        viewModelScope.launch {
            eventsRepository.deleteFavoriteEvent(id)
        }
    }
}