package com.c241ps093.ezfarm.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c241ps093.ezfarm.databinding.DetailRecyclerItemBinding

class DetailRecyclerAdapter(
    private val array : List<ToDo>,
    private val onSwitchChange : (isCompleted : Boolean, position: Int)-> Unit
) : RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder>() {

    class ViewHolder(binding: DetailRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val todoTitle = binding.deskripsiTitle
        val todoDesc = binding.deskripsiContent
        val switchComponent = binding.switchCompat
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = DetailRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoData = array[position]
        holder.apply {
            todoTitle.text = todoData.todoTitle
            todoDesc.text = todoData.todoDesc
            switchComponent.isChecked = todoData.todoCompleted
            switchComponent.setOnCheckedChangeListener { _, isChecked ->
                onSwitchChange(isChecked, position)
            }
        }
    }
    override fun getItemCount(): Int = array.size

}