package ennbose.sinewers

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ennbose.sinewers.databinding.WebViewFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WebViewFragment : Fragment() {

    private var _binding: WebViewFragmentBinding? = null
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithFragment(this@WebViewFragment.requireActivity()) }

    private val binding get() = _binding!!
    private var webView: WebView? = null
    var controller: NavController? = null

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (webView?.canGoBack() == true) {
                webView?.goBack()
            }
        }
    }



    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = WebViewFragmentBinding.inflate(inflater, container, false)

        webView = binding.webview
        webView!!.settings.javaScriptEnabled = true
        webView!!.webViewClient = WebViewClient()
        webView!!.settings.domStorageEnabled = true
        webView!!.settings.javaScriptCanOpenWindowsAutomatically = true

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        webView!!.settings.loadWithOverviewMode = true
        webView!!.settings.useWideViewPort = true
        webView!!.settings.databaseEnabled = true
        webView!!.settings.setSupportZoom(false)
        webView!!.settings.allowFileAccess = true
        webView!!.settings.allowContentAccess = true

        modelProvider.checkLink()
        if (savedInstanceState != null)
            webView?.restoreState(savedInstanceState)
        else modelProvider.link?.let { webView?.loadUrl(it) }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView?.saveState(outState)
        super.onSaveInstanceState(outState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controller = findNavController()

        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // network available
            }

            override fun onLost(network: Network) {
                lifecycleScope.launch(Dispatchers.Main) {
                    findNavController().navigate(R.id.NoInternetFragment)
                }
            }
        }

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
        _binding = null
    }



}