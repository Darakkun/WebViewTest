package com.h2bet.sportsapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.RenderProcessGoneDetail
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.h2bet.sportsapp.util.Preference
import com.h2bet.sportsapp.util.Preference.cokies
import com.h2bet.sportsapp.databinding.WebViewFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class WebViewFragment : Fragment() {

    private var _binding: WebViewFragmentBinding? = null
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithFragment(this@WebViewFragment.requireActivity()) }
    private val kookiManager: CookieManager by lazy { CookieManager.getInstance() }
    private var pref: SharedPreferences? = null
    private val binding get() = _binding!!
    private var betLayout: WebView? = null
    var controller: NavController? = null
    private var mCapturedImageURI: Uri? = null
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private var mCameraPhotoPath: String? = null
    private var mUploadMessage: ValueCallback<Uri?>? = null

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (betLayout?.canGoBack() == true) {
                betLayout?.goBack()
            }
        }
    }



    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = WebViewFragmentBinding.inflate(inflater, container, false)
        betLayout = binding.webview

        betLayout!!.settings.domStorageEnabled= true
        betLayout!!.settings.javaScriptEnabled = true
        betLayout!!.settings.useWideViewPort = true
        betLayout!!.settings.databaseEnabled = true
        betLayout!!.settings.javaScriptCanOpenWindowsAutomatically = true
        betLayout!!.settings.cacheMode = WebSettings.LOAD_DEFAULT

        CookieManager.getInstance().setAcceptCookie(true)
        pref = Preference.getPrefeence(this@WebViewFragment.requireContext())
        kookiManager.setAcceptCookie(true)
        kookiManager.setAcceptThirdPartyCookies(betLayout, true);

        betLayout!!.webViewClient = WebChekerClient()
        betLayout!!.webChromeClient=ChromeClient()
//        modelProvider.checkLink()

        if (savedInstanceState != null)
            betLayout?.restoreState(savedInstanceState)
       else betLayout?.loadUrl(modelProvider.link)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        betLayout?.saveState(outState)
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

    private fun checkPermission(permissions: Array<String>): Boolean {
        var permissionTrue = true
        permissions.forEach {
            if (checkPermission(it).not())
                permissionTrue = false
        }
        return permissionTrue
    }

    private fun checkPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            this@WebViewFragment.requireContext(), permission
        ) == PackageManager.PERMISSION_GRANTED


    inner class WebChekerClient: WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request != null) {
                view?.loadUrl(request.url.toString())
            }
            return false
        }
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url!!)
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            kookiManager.setCookie(url, pref!!.cokies)
        }

        override fun onRenderProcessGone(
            view: WebView?,
            detail: RenderProcessGoneDetail?
        ): Boolean {
            binding.protScreen.visibility=View.INVISIBLE
            return super.onRenderProcessGone(view, detail)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.protScreen.visibility=View.INVISIBLE
            super.onPageFinished(view, url)
            CookieManager.getInstance().flush();
            pref!!.cokies = kookiManager.getCookie(url)
        }
//        override fun onReceivedHttpError(
//            view: WebView,
//            request: WebResourceRequest?,
//            errorResponse: WebResourceResponse
//        ) {
//            if(errorResponse.getStatusCode().toString()=="404")
//        controller?.navigate(R.id.QuizStartFragment)
//        }

//        override fun onReceivedError(
//            view: WebView?,
//            request: WebResourceRequest?,
//            error: WebResourceError?
//        ) {
//            controller?.navigate(R.id.QuizStartFragment)
//        }
//
//        override fun onReceivedError(
//            view: WebView?,
//            errorCode: Int,
//            description: String?,
//            failingUrl: String?
//        ){
//            controller?.navigate(R.id.QuizStartFragment)
//        }
//        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?){
//            controller?.navigate(R.id.QuizStartFragment)
//        }
    }

    inner class ChromeClient : WebChromeClient() {

        // For Android 5.0
        override fun onShowFileChooser(
            view: WebView,
            filePath: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {

                // Double check that we don't have any existing callbacks
                if (mFilePathCallback != null) {
                    mFilePathCallback!!.onReceiveValue(null)
                }
                mFilePathCallback = filePath
                var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent!!.resolveActivity(this@WebViewFragment.requireActivity().packageManager) != null) {
                    // Create the File where the photo should go
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        Log.e("ErrorCreatingFile", "Unable to create Image File", ex)
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.absolutePath
                        takePictureIntent.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile)
                        )
                    } else {
                        takePictureIntent = null
                    }
                }
                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "image/*"
                val intentArray: Array<Intent?>
                intentArray = takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (!checkPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    ActivityCompat.requestPermissions(
                        (context as Activity?)!!,
                        arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                        41
                    )
                    return true
                }
            }

            if (!checkPermission(arrayOf(Manifest.permission.CAMERA))) {
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!,
                    arrayOf<String>(Manifest.permission.CAMERA),
                    40
                )
            }
            return true

        }

        // openFileChooser for Android 3.0+
        // openFileChooser for Android < 3.0
        @JvmOverloads
        fun openFileChooser(uploadMsg: ValueCallback<Uri?>?, acceptType: String? = "") {

            mUploadMessage = uploadMsg
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard
            val imageStorageDir = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                ), "AndroidExampleFolder"
            )
            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs()
            }

            // Create camera captured image file path and name
            val file = File(
                imageStorageDir.toString() + File.separator + "IMG_"
                        + System.currentTimeMillis().toString() + ".jpg"
            )
            mCapturedImageURI = Uri.fromFile(file)

            // Camera capture image intent
            val captureIntent = Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
            )
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI)
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"

            // Create file chooser intent
            val chooserIntent = Intent.createChooser(i, "Image Chooser")

            // Set camera intent to file chooser
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, arrayOf<Parcelable>(captureIntent)
            )

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE)
        }

        //openFileChooser for other Android versions
        fun openFileChooser(
            uploadMsg: ValueCallback<Uri?>?,
            acceptType: String?,
            capture: String?
        ) {
            openFileChooser(uploadMsg, acceptType)
        }

        override fun onPermissionRequest(request: PermissionRequest) {
            val permissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    request.grant(request.resources)
                }
                else {
                    // Do otherwise
                }
            }
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }



    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            var results: Array<Uri>? = null
            if (resultCode == ComponentActivity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = arrayOf(Uri.parse(mCameraPhotoPath))
                    }
                } else {
                    val dataString = data.dataString
                    if (dataString != null) {
                        results = arrayOf(Uri.parse(dataString))
                    }
                }
            }
            mFilePathCallback!!.onReceiveValue(results)
            mFilePathCallback = null
        } catch (e:java.lang.Exception){
            Toast.makeText(this@WebViewFragment.requireContext(),e.toString(),Toast.LENGTH_SHORT).show();
            Log.e("scanner", e.toString())
        }
    }

    companion object {
        private const val INPUT_FILE_REQUEST_CODE = 1
        private const val FILECHOOSER_RESULTCODE = 1
    }



}