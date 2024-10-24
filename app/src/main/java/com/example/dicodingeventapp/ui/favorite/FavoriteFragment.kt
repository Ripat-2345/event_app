package com.example.dicodingeventapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.adapter.FavoriteEventAdapter
import com.example.dicodingeventapp.adapter.UpcomingEventAdapter
import com.example.dicodingeventapp.databinding.FragmentFavoriteBinding
import com.example.dicodingeventapp.databinding.FragmentUpcomingBinding
import com.example.dicodingeventapp.ui.ViewModelFactory
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel

class FavoriteFragment : Fragment() {
    private var fragmentFavoriteBinding: FragmentFavoriteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val upcomingViewModel: FavoriteViewModel by viewModels<FavoriteViewModel> { factory }
        val favoriteEventsAdapter = FavoriteEventAdapter()

        upcomingViewModel.getFavoriteEvents().observe(viewLifecycleOwner){event ->
//            fragmentFavoriteBinding?.progressBar?.visibility = View.GONE
            val favoriteEventsData = event
            favoriteEventsAdapter.submitList(favoriteEventsData)
        }

        fragmentFavoriteBinding?.listFavoriteEvent?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteEventsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentFavoriteBinding = null
    }
}