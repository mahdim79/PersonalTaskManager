package com.task.taskmanager.presentation.updatetask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.task.taskmanager.databinding.FragmentUpdateTaskBinding
import com.task.taskmanager.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateTaskFragment : BaseFragment() {

    private lateinit var fragmentUpdateTaskBinding: FragmentUpdateTaskBinding
    private val viewModel by viewModels<UpdateTaskFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentUpdateTaskBinding = FragmentUpdateTaskBinding.inflate(layoutInflater)
        return fragmentUpdateTaskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

    }
}