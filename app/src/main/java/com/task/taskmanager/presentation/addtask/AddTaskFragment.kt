package com.task.taskmanager.presentation.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.task.taskmanager.databinding.FragmentAddTaskBinding
import com.task.taskmanager.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : BaseFragment() {

    private val viewModel by viewModels<AddTaskFragmentViewModel>()

    private lateinit var fragmentAddTaskBinding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentAddTaskBinding = FragmentAddTaskBinding.inflate(layoutInflater)
        return fragmentAddTaskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}