package com.parentapp.mobile

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.parentapp.mobile.network.NetworkMonitor
import com.parentapp.mobile.ui.main.MainScreen
import com.parentapp.mobile.ui.theme.MobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
        networkMonitor = NetworkMonitor(this)
        networkMonitor.register()

        networkMonitor.isNetworkAvailable.observe(this) { isNetworkAvailable ->
            if (!isNetworkAvailable) {
                showPopUp()
            } else {
                dismissPopUp()
            }
        }
    }
    private fun showPopUp() {
        dialog = AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setCancelable(false)
            .setPositiveButton("Retry") { _, _ ->
                // retry button clicked
                networkMonitor.register()
            }
            .setNegativeButton("Exit") { _, _ ->
                // exit button clicked
                finish()
            }
            .create()
        dialog.show()
    }

    private fun dismissPopUp() {
        if (this::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.unregister()
    }
}
