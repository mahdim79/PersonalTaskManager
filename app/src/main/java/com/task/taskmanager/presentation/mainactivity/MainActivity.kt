package com.task.taskmanager.presentation.mainactivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.task.taskmanager.R
import com.task.taskmanager.databinding.ActivityMainBinding
import com.task.taskmanager.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        configureAppTheme()
        configureAppNavigation()

    }

    private fun configureAppNavigation() {
        navController = (supportFragmentManager.findFragmentById(R.id.fcv_main_navHost) as NavHostFragment).navController
    }

    private fun configureAppTheme() {

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}