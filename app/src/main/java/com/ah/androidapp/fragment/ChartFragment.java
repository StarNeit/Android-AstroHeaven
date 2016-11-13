package com.ah.androidapp.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ah.androidapp.R;
import com.ah.androidapp.util.CommonFunc;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private WebView webview;

    public String chart_link;

    public ChartFragment() {
        // Required empty public constructor
    }

    public static ChartFragment newInstance(String chart_link)
    {
        ChartFragment fragment = new ChartFragment();
        fragment.chart_link = chart_link;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_chart, container, false);

        webview = (WebView)v.findViewById(R.id.webView);

        final ProgressDialog progressDialog = CommonFunc.createProgressDialog(getActivity());
        progressDialog.show();

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress >= 100)
                    progressDialog.dismiss();
            }
        });
        webview.clearCache(true);
        webview.clearHistory();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
//        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        webview.loadUrl(this.chart_link);
//        CommonFunc.ShowToast(getActivity(), this.chart_link);
        Log.e("link:", this.chart_link);
        return v;
    }
}
