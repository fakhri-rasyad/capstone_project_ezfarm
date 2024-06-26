package com.c241ps093.ezfarm.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.HomeRecyclerItemBinding
import com.c241ps093.ezfarm.ui.detail.DetailActivity

class HomeRecyclerViewAdapter(
    private val plantList: List<Plant>,
    private val deletePlant : (Plant) -> Unit
) :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(binding: HomeRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val plantName = binding.checkedChip
        val plantedDate = binding.plantedDateTv
        val harvestDate = binding.harvestDateTv
        val growthStatus = binding.plantGrowthStatus
        val deleteButton = binding.deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plantData = plantList[position]
        val plantDate = plantData.plantedDate
        val harvestedDate = plantData.harvestDate
        holder.apply {
            plantName.text = plantData.plantType
            plantedDate.text = holder.itemView.context.getString(R.string.planted_date, plantDate)
            harvestDate.text =
                holder.itemView.context.getString(R.string.harvest_date, harvestedDate)
            growthStatus.text = plantData.growthStatus
            deleteButton.setOnClickListener {
                deletePlant(plantData)
            }
        }

        holder.itemView.setOnClickListener {
            holder.itemView.context.apply {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(DetailActivity.PLANT_DATA, plantData)
                this.startActivity(intent)
            }
        }
    }
}