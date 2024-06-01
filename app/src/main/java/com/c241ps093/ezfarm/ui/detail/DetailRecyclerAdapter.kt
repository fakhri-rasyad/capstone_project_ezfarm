package com.c241ps093.ezfarm.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c241ps093.ezfarm.databinding.ItemDetailBinding

class DetailRecyclerAdapter(private val array : List<Int>) : RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val todoTitle = binding.deskripsiTitle
        val todoDesc = binding.deskripsiContent
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            todoTitle.text = array[position].toString()
            todoDesc.text = array[position].toString()
        }
    }

    override fun getItemCount(): Int = array.size
}