package com.example.dicodingeventapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventsRepository

class HomeViewModel(private val eventsRepository: EventsRepository) : ViewModel() {
    fun getUpcomingEvents() = eventsRepository.getUpcomingEvents()
    fun getFinishedEvents() = eventsRepository.getFinishedEvents()
}