package com.task.taskmanager.presentation.home

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.taskmanager.R
import com.task.taskmanager.TaskApplication
import com.task.taskmanager.databinding.DialogTaskDetailsBinding
import com.task.taskmanager.databinding.FragmentHomeBinding
import com.task.taskmanager.presentation.home.adapter.MainTaskRecyclerViewAdapter
import com.task.taskmanager.presentation.base.BaseFragment
import com.task.taskmanager.presentation.utils.TaskAction
import com.task.taskmanager.utils.AlarmHandler
import com.task.taskmanager.utils.ArgumentKeys
import com.task.taskmanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject
import androidx.core.graphics.drawable.toDrawable
import com.task.taskmanager.databinding.DialogRemoveTaskBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private val viewModel by viewModels<HomeFragmentViewModel>()

    @Inject
    lateinit var alarmHandler: AlarmHandler

    private lateinit var taskRecyclerViewAdapter: MainTaskRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.removeTaskLiveData.observe(viewLifecycleOwner) {
            if (it == null)
                return@observe

            when (it) {
                is DataState.Success -> {
                    alarmHandler.cancelTaskAlarm(it.value)
                    viewModel.getLocalTasks()
                    viewModel.removeTaskLiveData.value = null
                }

                else -> showToast(getString(R.string.error))
            }
        }

        viewModel.localTasksLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    taskRecyclerViewAdapter.submitList(it.value)
                    if (it.value.isEmpty())
                        mBinding.tvHomeNothing.visibility = View.VISIBLE
                    else
                        mBinding.tvHomeNothing.visibility = View.GONE
                }

                else -> showToast(getString(R.string.error))
            }
        }

        viewModel.syncTasksLiveData.observe(viewLifecycleOwner){
            mBinding.srlHomeRefresh.isRefreshing = false
            if (it is DataState.Success){
                viewModel.getLocalTasks()
            }
        }

        viewModel.getLocalTasks()
        mBinding.srlHomeRefresh.isRefreshing = true
        viewModel.startSyncOperation()
    }

    private fun initViews() {
        initRecyclerViewAdapter()

        mBinding.fabHomeAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }

        mBinding.scHomeTheme.isChecked = (requireActivity().application as TaskApplication).isInitialDarkModeEnable

        mBinding.scHomeTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkModeEnabled(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mBinding.srlHomeRefresh.setOnRefreshListener {
            viewModel.startSyncOperation()
        }
    }

    private fun initRecyclerViewAdapter() {
        taskRecyclerViewAdapter = MainTaskRecyclerViewAdapter()
        mBinding.rvHomeTasks.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        taskRecyclerViewAdapter.apply {
            setOnActionClickListener { task, action ->
                when (action) {
                    TaskAction.ACTION_EDIT -> {
                        task.id?.let { tId ->
                            findNavController().navigate(
                                R.id.action_homeFragment_to_updateTaskFragment,
                                Bundle().apply { putInt(ArgumentKeys.KEY_TASK_ID, tId) })
                        }
                    }

                    TaskAction.ACTION_REMOVE -> showRemoveTaskDialog(task)
                }
            }

            setOnItemClickListener {
                showTaskDetailsDialog(it)
            }
        }

        mBinding.rvHomeTasks.adapter = taskRecyclerViewAdapter
    }

    private fun showTaskDetailsDialog(task: Task) {
        val dialog = Dialog(requireContext())
        val view = DialogTaskDetailsBinding.inflate(layoutInflater)
        view.apply {
            btnTaskDetailsOk.setOnClickListener {
                dialog.dismiss()
            }
            tvTaskDetailsTitle.text = task.title
            tvTaskDetailsDescription.text = task.description
            tvTaskDetailsTime.text = Utils.convertCalendarTimeToShowFormat(Calendar.getInstance().apply { timeInMillis = task.time })
        }

        dialog.apply {
            setContentView(view.root)

            val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            val dialogParams: ViewGroup.LayoutParams = view.cvTaskDetailsContainer.layoutParams
            dialogParams.width = width
            view.cvTaskDetailsContainer.layoutParams = dialogParams

            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            window?.setDimAmount(0.4f)
            dialog.show()
        }
    }

    private fun showRemoveTaskDialog(task: Task) {

        val dialog = Dialog(requireContext())
        val view = DialogRemoveTaskBinding.inflate(layoutInflater)

        view.apply {
            btnRemoveTaskYes.setOnClickListener {
                viewModel.removeTask(task)
                dialog.dismiss()
            }
            btnRemoveTaskCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.apply {
            setContentView(view.root)

            val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            val dialogParams: ViewGroup.LayoutParams = view.cvTaskDetailsContainer.layoutParams
            dialogParams.width = width
            view.cvTaskDetailsContainer.layoutParams = dialogParams

            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            window?.setDimAmount(0.4f)
            dialog.show()
        }
    }

}