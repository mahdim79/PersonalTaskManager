package com.task.taskmanager.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.core.helper.DataState
import com.task.taskmanager.R
import com.task.taskmanager.databinding.FragmentHomeBinding
import com.task.taskmanager.presentation.addtask.adapters.MainTaskRecyclerViewAdapter
import com.task.taskmanager.presentation.base.BaseFragment
import com.task.taskmanager.presentation.utils.TaskAction
import com.task.taskmanager.utils.ArgumentKeys
import com.task.taskmanager.utils.MockData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private val viewModel by viewModels<HomeFragmentViewModel>()

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

        viewModel.removeTaskLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> viewModel.getLocalTasks()
                else -> showToast(getString(R.string.error))
            }
        }

        viewModel.localTasksLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> {
                    taskRecyclerViewAdapter.submitList(it.value)
                    if(it.value.isEmpty())
                        mBinding.tvHomeNothing.visibility = View.VISIBLE
                    else
                        mBinding.tvHomeNothing.visibility = View.GONE
                }
                else -> showToast(getString(R.string.error))
            }
        }

        viewModel.getLocalTasks()
    }

    private fun initViews() {
        initRecyclerViewAdapter()

        mBinding.fabHomeAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }
    }

    private fun initRecyclerViewAdapter() {
        taskRecyclerViewAdapter = MainTaskRecyclerViewAdapter()
        mBinding.rvHomeTasks.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        taskRecyclerViewAdapter.setOnActionClickListener { taskId, action ->
            when(action){
                TaskAction.ACTION_EDIT -> findNavController().navigate(R.id.action_homeFragment_to_updateTaskFragment, Bundle().apply { putInt(ArgumentKeys.KEY_TASK_ID,taskId) })
                TaskAction.ACTION_REMOVE -> showRemoveTaskDialog(taskId)
            }
        }

        mBinding.rvHomeTasks.adapter = taskRecyclerViewAdapter
    }

    private fun showRemoveTaskDialog(taskId: Int) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.remove_task))
            .setMessage(getString(R.string.remove_task_message))
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton(getString(R.string.remove)) { dialogInterface, _ ->
                viewModel.removeTaskById(taskId)
                dialogInterface.dismiss()
            }
            .create()
        dialog.show()
    }

}