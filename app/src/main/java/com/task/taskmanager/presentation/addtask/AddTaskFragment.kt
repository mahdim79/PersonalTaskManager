package com.task.taskmanager.presentation.addtask

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
import com.task.taskmanager.databinding.FragmentAddTaskBinding
import com.task.taskmanager.presentation.base.BaseFragment
import com.task.taskmanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddTaskFragment : BaseFragment() {

    private val viewModel by viewModels<AddTaskFragmentViewModel>()

    private lateinit var mBinding: FragmentAddTaskBinding

    private val userDateCalendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAddTaskBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.addTaskLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> {
                    showToast(getString(R.string.added))
                    findNavController().popBackStack()
                }
                else -> showToast(getString(R.string.error))
            }
        }
    }

    private fun initViews() {
        mBinding.btnAddTaskAdd.setOnClickListener {
            if (checkInputValidation())
                showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                userDateCalendar.set(Calendar.YEAR, year)
                userDateCalendar.set(Calendar.MONTH, month)
                userDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimePickerDialog()
            }, currentYear, currentMonth, currentDay
        )
        dialog.show()
    }


    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val dialog = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                userDateCalendar.set(Calendar.HOUR_OF_DAY, hour)
                userDateCalendar.set(Calendar.MINUTE, minute)
                if (Utils.validateTaskTime(userDateCalendar.timeInMillis)){
                    viewModel.addNewTask(Task(id = null, title = mBinding.tieAddTaskTitle.text.toString(), description = mBinding.tieAddTaskDescription.text.toString(), userDateCalendar.timeInMillis))
                }else{
                    showToast(getString(R.string.wrong_input_time))
                }

            }, currentHour, currentMinute, true
        )
        dialog.show()
    }

    private fun checkInputValidation(): Boolean {
        if (mBinding.tieAddTaskTitle.text.toString().isEmpty()) {
            showToast(getString(R.string.title_required_message))
            return false
        }

        if (mBinding.tieAddTaskDescription.text.toString().isEmpty()) {
            showToast(getString(R.string.description_required_message))
            return false
        }

        return true
    }

}