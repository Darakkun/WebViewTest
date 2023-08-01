package com.h2bet.sportsapp

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.onesignal.OneSignal
import com.h2bet.sportsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.Scanner


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithActivity(this) }

    val ONESIGNAL_APP_ID = "9023a2d3-d1e2-4823-ae04-95e965e6a844"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modelProvider.initDatabase(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(ONESIGNAL_APP_ID)
//        OneSignal.promptForPushNotifications()


    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
