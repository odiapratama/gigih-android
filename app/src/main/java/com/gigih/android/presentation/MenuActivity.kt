package com.gigih.android.presentation

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.gigih.android.R
import com.gigih.android.data.database.AppDataStore
import com.gigih.android.databinding.ActivityMenuBinding
import com.gigih.android.utils.AirplaneModeReceiver
import com.gigih.android.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStore: AppDataStore
    private lateinit var airplaneModeReceiver: AirplaneModeReceiver
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        showLifecycleToast("onCreate Activity")
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_menu)
        navController = navHost?.findNavController()
        navController?.let {
            appBarConfiguration = AppBarConfiguration(it.graph)
            setupActionBarWithNavController(it, appBarConfiguration)
        }

        initBroadcastReceiver()
    }

    override fun onStart() {
        super.onStart()
        showLifecycleToast("onStart Activity")

    }

    override fun onResume() {
        super.onResume()
        showLifecycleToast("onResume Activity")
    }

    override fun onPause() {
        super.onPause()
        showLifecycleToast("onPause Activity")
    }

    override fun onStop() {
        super.onStop()
        showLifecycleToast("onStop Activity")
        if (::airplaneModeReceiver.isInitialized) {
            unregisterReceiver(airplaneModeReceiver)
        }
    }

    override fun onDestroy() {
        showLifecycleToast("onDestroy Activity")
        super.onDestroy()
    }

    private fun showLifecycleToast(title: String) {
        lifecycleScope.launch {
            dataStore.getShowActivityLifecycle().collect {
                if (it) showToast(title)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp(appBarConfiguration) ?: false
                || super.onSupportNavigateUp()
    }

    private fun initBroadcastReceiver() {
        airplaneModeReceiver = AirplaneModeReceiver()
        registerReceiver(
            airplaneModeReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
    }
}