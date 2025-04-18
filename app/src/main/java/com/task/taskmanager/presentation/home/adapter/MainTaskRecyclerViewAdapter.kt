package com.task.taskmanager.presentation.home.adapter

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
import java.util.Calendar

class MainTaskRecyclerViewAdapter :
    ListAdapter<Task, MainTaskRecyclerViewAdapter.MainViewHolder>(callback) {

    private lateinit var onActionClickListener: (task: Task,action:TaskAction) -> Unit
    private lateinit var onItemClickListener: (task: Task) -> Unit

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
                    onActionClickListener.invoke(item,TaskAction.ACTION_REMOVE)
                }
        }

        holder.itemView.setOnClickListener {
            if (::onItemClickListener.isInitialized)
                item.id?.let {
                    onItemClickListener.invoke(item)
                }
        }

        holder.itemTaskBinding.ivItemTaskEdit.setOnClickListener {
            if (::onActionClickListener.isInitialized)
                item.id?.let {
                    onActionClickListener.invoke(item,TaskAction.ACTION_EDIT)
                }
        }


        if (position == currentList.lastIndex)
            holder.itemTaskBinding.vItemTaskDivider.visibility = View.GONE
        else
            holder.itemTaskBinding.vItemTaskDivider.visibility = View.VISIBLE
    }

    fun setOnActionClickListener(listener: (task: Task,action:TaskAction) -> Unit) {
        this.onActionClickListener = listener
    }

    fun setOnItemClickListener(listener: (task: Task) -> Unit) {
        this.onItemClickListener = listener
    }

    inner class MainViewHolder(itemView: View) : ViewHolder(itemView) {
        val itemTaskBinding = ItemTaskBinding.bind(itemView)
    }
}