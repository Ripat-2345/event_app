package com.example.dicodingeventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.adapter.FinishedEventAdapter
import com.example.dicodingeventapp.adapter.UpcomingEventAdapter
import com.example.dicodingeventapp.data.Result
import com.example.dicodingeventapp.databinding.FragmentHomeBinding
import com.example.dicodingeventapp.ui.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels<HomeViewModel> {
            factory
        }

        // upcoming event in home page
        val upcomingEventsAdapter = UpcomingEventAdapter()
        viewModel.getUpcomingEvents().observe(viewLifecycleOwner){result ->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        _binding?.progressBarUpcomingHome?.visibility =  View.VISIBLE
                    }
                    is Result.Success -> {
                        _binding?.progressBarUpcomingHome?.visibility = View.GONE
                        val upcomingEventsData = result.data
                        if(upcomingEventsData.size >= 5){
                            val subListData = upcomingEventsData.slice(0..4)
                            upcomingEventsAdapter.submitList(subListData)
                        }else{
                            upcomingEventsAdapter.submitList(upcomingEventsData)
                        }
                    }
                    is Result.Error -> {
                        _binding?.progressBarUpcomingHome?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        _binding?.listUpcomingEvent?.apply {
            layoutManager = LinearLayoutManager(context)
            (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
            adapter = upcomingEventsAdapter
        }

        // finished event in home page
        val finishedEventAdapter = FinishedEventAdapter()
        viewModel.getFinishedEvents().observe(viewLifecycleOwner){result ->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        _binding?.progressBarFinishedHome?.visibility =  View.VISIBLE
                    }
                    is Result.Success -> {
                        _binding?.progressBarFinishedHome?.visibility = View.GONE
                        val finishedEventsData = result.data
                        if(finishedEventsData.size >= 5){
                            val subListData = finishedEventsData.slice(0..4)
                            finishedEventAdapter.submitList(subListData)
                        }else{
                            finishedEventAdapter.submitList(finishedEventsData)
                        }
                    }
                    is Result.Error -> {
                        _binding?.progressBarFinishedHome?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        _binding?.listFinishedEvent?.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = finishedEventAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}