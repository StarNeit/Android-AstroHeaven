package com.ah.androidapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

public class DisplayUtils {
    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public static void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void changeStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setBackgroundColor(color);
        }
    }

    public static void transitionBackgroundAnimation(View view, Drawable backgrounds[], int animationTime) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(backgrounds);
        view.setBackground(transitionDrawable);
        transitionDrawable.startTransition(animationTime);
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static String boldMessage(String message) {
        return "<b>" + message.replaceAll("\\n", "<br />") + "</b>";
    }

    private static String boldNumberInMessage(String format) {
        return format.replaceAll("%s", "<b>%s</b>").replaceAll("\\n", "<br />");
    }

    public static String pluralizeLightFoundMessage(String format, int numberOfLights) {
        String output = String.format(format, numberOfLights);
        if (numberOfLights == 1) output = output.replaceAll("Lights", "Light");
        return output;
    }

    public static String messageWithBoldInNumber(String format, int numberOfLights) {
        String htmlFormat = boldNumberInMessage(format);
        return pluralizeLightFoundMessage(htmlFormat, numberOfLights);
    }
}
