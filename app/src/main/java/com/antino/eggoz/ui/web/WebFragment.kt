package com.antino.eggoz.ui.web

import android.R
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.databinding.adapters.SeekBarBindingAdapter.setProgress
import androidx.fragment.app.Fragment
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentWebBinding
import com.antino.eggoz.view.CustomAlertLoading


class WebFragment(val context: MainActivity, val url: String) : Fragment() {

    private lateinit var binding: FragmentWebBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWebBinding.inflate(inflater, container, false)
        //only work with https link
//        opeWebView(true)

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {

                if (progress == 100) loadingdialog.dismiss()
            }
        }
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl(url)

        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadHome()
                true
            } else false
        }
        return binding.root
    }

    private fun opeWebView(isDoc: Boolean) {
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()
        val webSettings = binding.webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.domStorageEnabled = true
        webSettings.pluginState = WebSettings.PluginState.ON
        binding.webview.visibility = View.VISIBLE
        binding.webview.setDownloadListener { s, s1, s2, s3, l ->
            Log.d(
                "WebView",
                "onDownloadStart"
            )
        }
        val onlyonce = arrayOf(true)
        val finalUrl = url
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.d("WebView", "url $url")
                binding.webview.loadUrl(url)
                return true
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                Log.d("WebView", "onReceivedError")
//                loadingdialog.dismiss()
            }

            override fun onReceivedHttpError(
                view: WebView,
                request: WebResourceRequest,
                errorResponse: WebResourceResponse
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                if (onlyonce[0]) {
                    onlyonce[0] = false
                    binding.webview.loadUrl(finalUrl)
                }
                // Log.d("WebView","onReceivedHttpError"+errorResponse.getData().toString());
                /* finish();
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(finalUrl));
    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);*/
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                //                loadingdialog.stateLoading();
                Log.d("WebView", "onPagestart")
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (view.contentHeight == 0) {
                    view.reload()
                } else {
//                    loadingdialog.dismiss()
                }
                Log.d("WebView", "onPageFinished" + view.contentHeight)
            }
        }
        if (isDoc) binding.webview.loadUrl(url) else binding.webview.loadUrl(url)
    }

}