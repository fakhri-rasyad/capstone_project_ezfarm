package com.c241ps093.ezfarm.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.databinding.HomeRecyclerItemBinding
import com.c241ps093.ezfarm.dateFormatter

class HomeRecyclerViewAdapter(private val plantList: List<DummyData>): RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(binding: HomeRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val plantName = binding.checkedChip
        val plantedDate = binding.plantedDateTv
        val harvestDate = binding.harvestDateTv
        val growthStatus = binding.plantGrowthStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent ,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plantData = plantList[position]
        val plantDate = dateFormatter(plantData.plantDate)
        val harvestedDate = dateFormatter(plantData.harvestDate)
        holder.apply {
            plantName.text = plantData.plantName
            plantedDate.text = holder.itemView.context.getString(R.string.planted_date, plantDate)
            harvestDate.text = holder.itemView.context.getString(R.string.harvest_date, harvestedDate)
            growthStatus.text = plantData.growthStage
        }
    }
}