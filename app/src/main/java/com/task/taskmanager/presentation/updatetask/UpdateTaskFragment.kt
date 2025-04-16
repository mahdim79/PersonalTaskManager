package com.task.taskmanager.presentation.updatetask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
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
                if (Utils.validateTaskTime(editedCalendar.timeInMillis)){
                    selectedTask?.let { updatedTask ->
                        updatedTask.title = mBinding.tieUpdateTaskTitle.text.toString()
                        updatedTask.description = mBinding.tieUpdateTaskDescription.text.toString()
                        updatedTask.time = editedCalendar.timeInMillis
                        viewModel.updateTask(updatedTask)
                    }
                }else{
                    showToast(getString(R.string.wrong_input_time))
                }
            }
        }

        mBinding.btnUpdateTaskChangeTime.setOnClickListener {
            showDatePickerDialog()
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

    private fun showDatePickerDialog() {
        val taskYear = editedCalendar.get(java.util.Calendar.YEAR)
        val taskMonth = editedCalendar.get(java.util.Calendar.MONTH)
        val taskDay = editedCalendar.get(java.util.Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                editedCalendar.set(java.util.Calendar.YEAR, year)
                editedCalendar.set(java.util.Calendar.MONTH, month)
                editedCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth)
                updateTimeTextView()
                showTimePickerDialog()
            }, taskYear, taskMonth, taskDay
        )
        dialog.show()
    }


    private fun showTimePickerDialog() {
        val calendar = java.util.Calendar.getInstance()
        val currentHour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(java.util.Calendar.MINUTE)

        val dialog = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                editedCalendar.set(java.util.Calendar.HOUR_OF_DAY, hour)
                editedCalendar.set(java.util.Calendar.MINUTE, minute)
                updateTimeTextView()
            }, currentHour, currentMinute, true
        )
        dialog.show()
    }

    private fun validateInput(): Boolean {
        if (mBinding.tieUpdateTaskTitle.text.toString().isEmpty()) {
            showToast(getString(R.string.title_required_message))
            return false
        }

        if (mBinding.tieUpdateTaskDescription.text.toString().isEmpty()) {
            showToast(getString(R.string.description_required_message))
            return false
        }

        return true
    }
}