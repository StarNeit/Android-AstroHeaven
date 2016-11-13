package com.ah.androidapp.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ah.androidapp.R;
import com.ah.androidapp.util.CommonFunc;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance(){
        HelpFragment fragment = new HelpFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_help, container, false);

        final ProgressDialog progressDialog = CommonFunc.createProgressDialog(getActivity());
        progressDialog.show();

        WebView browser = (WebView) v.findViewById(R.id.webView);
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        browser.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress)
            {
                if(progress >= 100)
                    progressDialog.dismiss();
            }
        });
        browser.loadUrl("http://www.astrologersheaven.com/mobile/android/help.html");
        return v;
    }

}
