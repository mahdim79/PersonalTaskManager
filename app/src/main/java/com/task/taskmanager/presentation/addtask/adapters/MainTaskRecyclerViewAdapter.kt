package com.task.taskmanager.presentation.addtask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.task.core.domain.task.Task
import com.task.taskmanager.R
import com.task.taskmanager.databinding.ItemTaskBinding

class MainTaskRecyclerViewAdapter :
    ListAdapter<Task, MainTaskRecyclerViewAdapter.MainViewHolder>(callback) {

    private lateinit var onItemClickListener: (taskId: Int) -> Unit

    companion object {
        val callback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.time == newItem.time &&
                        oldItem.title == newItem.title &&
                        oldItem.description == newItem.description
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemTaskBinding.tvTaskItemTitle.text = item.title
        holder.itemTaskBinding.tvTaskItemDescription.text = item.description
        holder.itemTaskBinding.tvTaskItemDeadLine.text = item.time.toString()

        holder.itemView.setOnClickListener {
            if (::onItemClickListener.isInitialized)
                item.id?.let {
                    onItemClickListener.invoke(it)
                }
        }
    }

    fun setOnItemClickListener(listener: (taskId: Int) -> Unit) {
        this.onItemClickListener = listener
    }

    inner class MainViewHolder(itemView: View) : ViewHolder(itemView) {
        val itemTaskBinding = ItemTaskBinding.bind(itemView)
    }
}