package com.example.dicodingeventapp.ui.upcoming

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.adapter.UpcomingEventAdapter
import com.example.dicodingeventapp.data.Result
import com.example.dicodingeventapp.databinding.FragmentUpcomingBinding
import com.example.dicodingeventapp.ui.ViewModelFactory

class UpcomingFragment : Fragment() {
    private var fragmentUpcomingBinding: FragmentUpcomingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUpcomingBinding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)

        return fragmentUpcomingBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory:ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: UpcomingViewModel by viewModels<UpcomingViewModel> {
            factory
        }
        val upcomingEventsAdapter = UpcomingEventAdapter()

        viewModel.getUpcomingEvents().observe(viewLifecycleOwner){result ->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        fragmentUpcomingBinding?.progressBar?.visibility =  View.VISIBLE
                    }
                    is Result.Success -> {
                        fragmentUpcomingBinding?.progressBar?.visibility = View.GONE
                        val upcomingEventsData = result.data
                        upcomingEventsAdapter.submitList(upcomingEventsData)
                    }
                    is Result.Error -> {
                        fragmentUpcomingBinding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        fragmentUpcomingBinding?.listUpcomingEvent?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = upcomingEventsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentUpcomingBinding = null
    }
}