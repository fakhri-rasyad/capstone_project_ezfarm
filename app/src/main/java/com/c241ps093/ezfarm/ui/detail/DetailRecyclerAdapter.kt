package com.c241ps093.ezfarm.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c241ps093.ezfarm.data.database.PlantTodo
import com.c241ps093.ezfarm.databinding.DetailRecyclerItemBinding

class DetailRecyclerAdapter(
    private val array: List<PlantTodo?>?,
    private val onSwitchChange: (isCompleted: Boolean, plantTodo: PlantTodo) -> Unit
) : RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder>() {

    class ViewHolder(binding: DetailRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val todoDesc = binding.deskripsiContent
        val switchComponent = binding.switchCompat
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            DetailRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoData = array?.get(position)
        holder.apply {
            if (todoData != null) {
                todoDesc.text = todoData.desc
                switchComponent.isChecked = todoData.status
                switchComponent.setOnCheckedChangeListener { _, isChecked ->
                    onSwitchChange(isChecked, todoData)
                }
            }
        }
    }

    override fun getItemCount(): Int = array?.size ?: 0

}