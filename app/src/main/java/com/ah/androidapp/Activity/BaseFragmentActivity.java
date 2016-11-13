package com.ah.androidapp.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ah.androidapp.R;
import com.ah.androidapp.dto.FragmentStack;
import com.ah.androidapp.fragment.HomeFragment;
import com.ah.androidapp.util.Constant;

import java.util.Stack;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseFragmentActivity extends FragmentActivity {

    private Context mContext;

    protected Stack<FragmentStack> fragmentStacks = new Stack<>();

    protected ImageView ivTitleBack;

    protected TextView ivTitleText;

    protected LinearLayout ivTitleTextContainer;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (fragmentStacks.size() > 1) {
            backView();
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            System.exit(1);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Are you sure to Exit?").setPositiveButton("YES", dialogClickListener)
                    .setNegativeButton("NO", dialogClickListener).show();
        }
    }

    protected void initTitleViews() {
        mContext = this;
        ivTitleBack = (ImageView) findViewById(R.id.ivTitleBack);
        ivTitleText = (TextView) findViewById(R.id.ivTitleText);
        ivTitleTextContainer = (LinearLayout) findViewById(R.id.ivTitleTextContainer);
    }

    protected void backToRootView() {
        while (!fragmentStacks.isEmpty()) {
            backView();
        }
    }

    protected void openView(final Fragment fragment, final FragmentStack fragmentStack) {
        openView(fragment, fragmentStack, false);
    }

    protected void openView(final Fragment fragment, final FragmentStack fragmentStack, final boolean openAsRoot) {
        if (openAsRoot) {
            backToRootView();
        }
/*
        //hide calendar
        if (!fragmentStacks.isEmpty()) {
            Fragment curFragment = getSupportFragmentManager().findFragmentByTag(fragmentStacks.peek().getId());
            if (curFragment instanceof HomeFragment) {
                getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .hide(curFragment)
                        .commit();
            }
        }*/

        String id = System.currentTimeMillis() + "";
        fragmentStack.setId(id);
        fragmentStacks.push(fragmentStack);

        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .add(R.id.content, fragment, id)
                .commit();

        loadTitleBar(fragmentStack);
    }

    protected void backView() {
        FragmentStack fragmentStack = fragmentStacks.pop();

        String id = fragmentStack.getId();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(id);
        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .remove(fragment)
                .commit();

        try {
            fragmentStack = fragmentStacks.peek();
            loadTitleBar(fragmentStack);
        }catch(Exception e){}
    }

    private void loadTitleBar(FragmentStack fragmentStack) {

            ivTitleBack.setVisibility(View.INVISIBLE);

        if (fragmentStack.getShowTitle())
        {
            ivTitleText.setText(fragmentStack.getTitle());
            ivTitleTextContainer.setVisibility(View.VISIBLE);
        }else{
            ivTitleText.setText(fragmentStack.getTitle());
            ivTitleTextContainer.setVisibility(View.GONE);
        }
    }

    public void isLike(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Like this app?").setPositiveButton("YES", dialogClickListener)
                .setNegativeButton("NO", dialogClickListener).show();
    }
}
