package com.example.dicodingeventapp.ui.finished

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingeventapp.adapter.EventAdapter
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    companion object {
        fun newInstance() = FinishedFragment()
    }

    private lateinit var fragmentFinishedBinding: FragmentFinishedBinding
    private val finishedViewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFinishedBinding = FragmentFinishedBinding.inflate(layoutInflater, container, false)
        return fragmentFinishedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finishedViewModel.listEvent.observe(viewLifecycleOwner){ eventData ->
            setFinishedEventData(eventData)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        finishedViewModel.toastText.observe(viewLifecycleOwner){ toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        fragmentFinishedBinding.listFinishedEvent.layoutManager = layoutManager
    }

    private fun setFinishedEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        fragmentFinishedBinding.listFinishedEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        fragmentFinishedBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}