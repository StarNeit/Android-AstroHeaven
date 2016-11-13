package com.ah.androidapp.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by PLEASE on 18/02/16.
 */
public class FocusChangeListener implements View.OnFocusChangeListener {

    private Activity mActivity;

    public FocusChangeListener(Activity activity)
    {
        mActivity = activity;
    }

    public void onFocusChange(View v, boolean hasFocus){

        if(!hasFocus) {

            InputMethodManager imm =  (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
