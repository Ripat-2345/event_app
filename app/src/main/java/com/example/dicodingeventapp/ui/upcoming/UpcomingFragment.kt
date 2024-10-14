package com.example.dicodingeventapp.ui.upcoming

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.adapter.EventAdapter
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentUpcomingBinding
import com.google.android.material.snackbar.Snackbar

class UpcomingFragment : Fragment() {
    companion object {
        fun newInstance() = UpcomingFragment()
    }
    private lateinit var fragmentUpcomingBinding: FragmentUpcomingBinding
    private val upcomingViewModel: UpcomingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentUpcomingBinding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)

        return fragmentUpcomingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingViewModel.listEvent.observe(viewLifecycleOwner){eventData ->
            setUpcomingEventData(eventData)
        }

        upcomingViewModel.toastText.observe(viewLifecycleOwner){ toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        upcomingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(activity)
        fragmentUpcomingBinding.listUpcomingEvent.layoutManager = layoutManager
    }

    private fun setUpcomingEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        fragmentUpcomingBinding.listUpcomingEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        fragmentUpcomingBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}