package com.task.taskmanager.presentation.updatetask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.taskmanager.R
import com.task.taskmanager.databinding.FragmentUpdateTaskBinding
import com.task.taskmanager.presentation.base.BaseFragment
import com.task.taskmanager.utils.ArgumentKeys
import com.task.taskmanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.Util
import java.util.Calendar

@AndroidEntryPoint
class UpdateTaskFragment : BaseFragment() {

    private lateinit var mBinding: FragmentUpdateTaskBinding
    private val viewModel by viewModels<UpdateTaskFragmentViewModel>()

    private val editedCalendar = Calendar.getInstance()

    private var selectedTask:Task? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentUpdateTaskBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        fetchSelectedTask()
    }

    private fun updateTimeTextView(){
        mBinding.tvUpdateTaskTime.text = Utils.convertCalendarTimeToShowFormat(editedCalendar)
    }

    private fun observeLiveData() {
        viewModel.getTaskLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> {

                    selectedTask = it.value
                    // update calendar
                    editedCalendar.timeInMillis = selectedTask!!.time

                    setupCurrentTaskOnUi()
                }
                else -> {
                    showToast(getString(R.string.error))
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.updateTaskLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> {
                    showToast(getString(R.string.Updated))
                    findNavController().popBackStack()
                }
                else -> showToast(getString(R.string.error))
            }
        }
    }

    private fun fetchSelectedTask() {
        val taskId = requireArguments().getInt(ArgumentKeys.KEY_TASK_ID,-1)
        if (taskId != -1){
            viewModel.getTaskById(taskId)
        }else{
            showToast(getString(R.string.error))
            findNavController().popBackStack()
        }
    }

    private fun setupButtons() {
        mBinding.btnUpdateTaskUpdate.setOnClickListener {
            if (validateInput()){
                selectedTask?.let { updatedTask ->
                    updatedTask.title = mBinding.tieUpdateTaskTitle.text.toString().trim()
                    updatedTask.description = mBinding.tieUpdateTaskDescription.text.toString().trim()
                    updatedTask.time = editedCalendar.timeInMillis
                    viewModel.updateTask(updatedTask)
                }
            }
        }

        mBinding.btnUpdateTaskChangeTime.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun setupCurrentTaskOnUi(){
        selectedTask?.let { task ->
            mBinding.tieUpdateTaskTitle.setText(task.title)
            mBinding.tieUpdateTaskDescription.setText(task.description)

            updateTimeTextView()
            setupButtons()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val dialog = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                editedCalendar.set(Calendar.HOUR_OF_DAY, hour)
                editedCalendar.set(Calendar.MINUTE, minute)
                updateTimeTextView()
            }, currentHour, currentMinute, true
        )
        dialog.show()
    }

    private fun validateInput(): Boolean {
        if (mBinding.tieUpdateTaskTitle.text.toString().trim().isEmpty()) {
            showToast(getString(R.string.title_required_message))
            return false
        }

        if (mBinding.tieUpdateTaskDescription.text.toString().trim().isEmpty()) {
            showToast(getString(R.string.description_required_message))
            return false
        }

        return true
    }
}