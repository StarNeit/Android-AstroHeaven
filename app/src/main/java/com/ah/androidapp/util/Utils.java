package com.ah.androidapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.ah.androidapp.util.Constant;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static SimpleDateFormat eventDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    private static SimpleDateFormat eventTimeFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat eventTimeAMPMFormat = new SimpleDateFormat("a");
    private static SimpleDateFormat eventDayFormat = new SimpleDateFormat("dd");
    private static SimpleDateFormat eventDayOfWeekFormat = new SimpleDateFormat("EEEE");
    private static SimpleDateFormat eventHourAMPMFormat = new SimpleDateFormat("HHa");
    private static SimpleDateFormat submitDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {

            return false;
        }
    }

    public static void showAlert(Context cxt, String title, String message) {
        showAlert(cxt, title, message, null);
    }

    public static void showAlert(Context cxt, String title, String message, DialogInterface.OnClickListener onClick) {
        AlertDialog.Builder bld = new AlertDialog.Builder(cxt);
        if (!title.equals("")) bld.setTitle(title);
        bld.setMessage(message);
        bld.setNeutralButton("OK", onClick);
        bld.create().show();
    }

    public static boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static Drawable getDrawable(Context cxt, String res) {
        int id;
        try {
            id = cxt.getResources().getIdentifier(res, "drawable", cxt.getPackageName());
            return cxt.getResources().getDrawable(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static String md5(String input) {
        String md5 = null;
        if (null == input)
            return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static DisplayImageOptions getDisplayImageOptionsInstance() {
        return new DisplayImageOptions.Builder().displayer(new FadeInBitmapDisplayer(Constant.FADE_TIME))
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public static DisplayImageOptions getDisplayImageOptionsInstance(int defaultIcon) {
        return new DisplayImageOptions.Builder().displayer(new FadeInBitmapDisplayer(Constant.FADE_TIME))
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(defaultIcon).showImageOnFail(defaultIcon).build();
    }

    public static String getEventDateStr(Date date) {
        if (date == null) return "";
        return eventDateFormat.format(date);
    }

    public static String getEventTimeStr(Date date) {
        if (date == null) return "";
        return eventTimeFormat.format(date);
    }

    public static String getEventTimeAMPMStr(Date date) {
        if (date == null) return "";
        return eventTimeAMPMFormat.format(date);
    }

    public static String getEventDayStr(Date date) {
        if (date == null) return "";
        return eventDayFormat.format(date);
    }

    public static String getEventDayOfWeekStr(Date date) {
        if (date == null) return "";
        return eventDayOfWeekFormat.format(date);
    }

    public static String getEventHourAMPMStr(Date date) {
        if (date == null) return "";
        return eventHourAMPMFormat.format(date);
    }

    public static String getSubmitDateStr(Date date) {
        if (date == null) return "";
        return submitDateFormat.format(date);
    }

    public static void setupUI(View view, final Activity activity) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                   return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView,activity);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try{
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){}
    }
}
