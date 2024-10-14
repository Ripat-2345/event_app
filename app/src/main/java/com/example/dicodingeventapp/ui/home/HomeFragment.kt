package com.example.dicodingeventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingeventapp.adapter.EventAdapter
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentHomeBinding
import com.example.dicodingeventapp.ui.finished.FinishedViewModel
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val homeViewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // upcoming event on home page
        homeViewModel.listUpcomingEvent.observe(viewLifecycleOwner){eventData ->
            setUpcomingEventData(eventData)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        val upcomingLayoutManager = LinearLayoutManager(activity)
        upcomingLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        _binding?.listUpcomingEvent?.layoutManager = upcomingLayoutManager

        // finished event on home page
        homeViewModel.listFinishedEvent.observe(viewLifecycleOwner){ eventData ->
            setFinishedEventData(eventData)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        val finishedLayoutManager = GridLayoutManager(activity,2)
        _binding?.listFinishedEvent?.layoutManager = finishedLayoutManager


        homeViewModel.toastText.observe(viewLifecycleOwner){ toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpcomingEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        _binding?.listUpcomingEvent?.adapter = adapter
    }

    private fun setFinishedEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        _binding?.listFinishedEvent?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        _binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}