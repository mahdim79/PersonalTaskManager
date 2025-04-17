package com.task.taskmanager.presentation.mainactivity

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.task.core.helper.DataState
import com.task.core.interactors.GetLocalTasks
import com.task.taskmanager.R
import com.task.taskmanager.databinding.ActivityMainBinding
import com.task.taskmanager.presentation.base.BaseActivity
import com.task.taskmanager.utils.PermissionHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    private val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    private lateinit var navController: NavController

    @Inject
    lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        configureAppTheme()
        configureAppNavigation()
        checkAppPermissions()
    }

    private fun checkAppPermissions() {
        if (!permissionHandler.hasNotificationPermission())
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
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