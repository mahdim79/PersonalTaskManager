package com.task.taskmanager.presentation.addtask.adapters

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.task.core.domain.task.Task
import com.task.taskmanager.R
import com.task.taskmanager.databinding.ItemTaskBinding
import com.task.taskmanager.presentation.utils.TaskAction
import com.task.taskmanager.utils.Utils

class MainTaskRecyclerViewAdapter :
    ListAdapter<Task, MainTaskRecyclerViewAdapter.MainViewHolder>(callback) {

    private lateinit var onActionClickListener: (taskId: Int,action:TaskAction) -> Unit

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

        val showTime = Utils.convertCalendarTimeToShowFormat(Calendar.getInstance().apply { timeInMillis = item.time })
        holder.itemTaskBinding.tvTaskItemDeadLine.text = showTime

        holder.itemTaskBinding.ivItemTaskRemove.setOnClickListener {
            if (::onActionClickListener.isInitialized)
                item.id?.let {
                    onActionClickListener.invoke(it,TaskAction.ACTION_REMOVE)
                }
        }

        holder.itemTaskBinding.ivItemTaskEdit.setOnClickListener {
            if (::onActionClickListener.isInitialized)
                item.id?.let {
                    onActionClickListener.invoke(it,TaskAction.ACTION_EDIT)
                }
        }
    }

    fun setOnActionClickListener(listener: (taskId: Int,action:TaskAction) -> Unit) {
        this.onActionClickListener = listener
    }

    inner class MainViewHolder(itemView: View) : ViewHolder(itemView) {
        val itemTaskBinding = ItemTaskBinding.bind(itemView)
    }
}