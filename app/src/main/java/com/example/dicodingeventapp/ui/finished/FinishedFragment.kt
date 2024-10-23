package com.example.dicodingeventapp.ui.finished

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dicodingeventapp.adapter.FinishedEventAdapter
import com.example.dicodingeventapp.data.Result
import com.example.dicodingeventapp.databinding.FragmentFinishedBinding
import com.example.dicodingeventapp.ui.ViewModelFactory

class FinishedFragment : Fragment() {
    private var fragmentFinishedBinding: FragmentFinishedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFinishedBinding = FragmentFinishedBinding.inflate(layoutInflater, container, false)
        return fragmentFinishedBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FinishedViewModel by viewModels<FinishedViewModel> {
            factory
        }
        val finishedEventAdapter = FinishedEventAdapter()

        viewModel.getFinishedEvents().observe(viewLifecycleOwner){result ->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        fragmentFinishedBinding?.progressBar?.visibility =  View.VISIBLE
                    }
                    is Result.Success -> {
                        fragmentFinishedBinding?.progressBar?.visibility = View.GONE
                        val finishedEventsData = result.data
                        finishedEventAdapter.submitList(finishedEventsData)
                    }
                    is Result.Error -> {
                        fragmentFinishedBinding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        fragmentFinishedBinding?.listFinishedEvent?.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = finishedEventAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentFinishedBinding = null
    }
}