package com.c241ps093.ezfarm.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity)

        binding.apply {
            homeRv.apply {
                layoutManager = linearLayoutManager
            }
        }

        val arrayList = arrayListOf<DummyData>()
        for(i in 1..10){
            arrayList.add(
                DummyData(
                    "Padi",
                    "20 Mei 2024",
                    "20 Aprils 2020",
                    "Seeding"
                )
            )
        }

        setUpRecyclerView(arrayList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView(
        plantList : List<DummyData>
    ){
        binding.apply {
            val adapter = HomeRecyclerViewAdapter(plantList)
            this.homeRv.adapter = adapter
        }
    }

}