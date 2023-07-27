package ennbose.sinewers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal
import ennbose.sinewers.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithActivity(this) }

    val ONESIGNAL_APP_ID = "9023a2d3-d1e2-4823-ae04-95e965e6a844"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modelProvider.initDatabase(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications();
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
