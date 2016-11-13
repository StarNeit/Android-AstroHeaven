package com.ah.androidapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ah.androidapp.R;
import com.ah.androidapp.dto.FragmentStack;
import com.ah.androidapp.fragment.ChartFragment;
import com.ah.androidapp.fragment.ContactFragment;
import com.ah.androidapp.fragment.CreateChartFragment;
import com.ah.androidapp.fragment.HelpFragment;
import com.ah.androidapp.fragment.HomeFragment;
import com.ah.androidapp.fragment.LikeFragment;
import com.ah.androidapp.fragment.SavedChartFragment;
import com.ah.androidapp.fragment.SettingsFragment;
import com.ah.androidapp.fragment.TransitFragment;
import com.ah.androidapp.model.Settings;
import com.ah.androidapp.model.SettingsManangement;
import com.ah.androidapp.service.APIService;
import com.ah.androidapp.service.CallbackResponse;
import com.ah.androidapp.util.CommonFunc;
import com.ah.androidapp.util.Constant;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends BaseFragmentActivity {

    private SlidingMenu mSlidingMenu;
    private ImageView ivMenuShadow;
    private Context mContext;

    public int flagOfPlace;
    public String chosen_place_name;
    public String chosen_place_latitude;
    public String chosen_place_longitude;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        super.initTitleViews();

        initSlidingMenu();

        //-----------***-----------***----------***-----------//
        loadMenuMain();
        openView(HomeFragment.newInstance(), new FragmentStack("Home", true));

        //-----------***-----------***----------***-----------//
        chosen_place_name = "";
        chosen_place_latitude = "";
        chosen_place_longitude = "";


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);



        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void onClickSideMenu(View v)
    {
        mSlidingMenu.showMenu();
    }

    public void onClickTitleBack(View v)
    {
        if (fragmentStacks.size() > 1) {
            super.onBackPressed();
        }else{
            super.backView();
            openHomeFragment();
        }
    }

    //************************//
    //*      Slide Menu      *//
    //************************//
    private void initSlidingMenu() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setBehindWidth((3 * displaymetrics.widthPixels) / 5);
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setMenu(R.layout.menu_left);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        //shadow
        ivMenuShadow = (ImageView) findViewById(R.id.ivMenuShadow);
        mSlidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                ivMenuShadow.setVisibility(View.VISIBLE);
            }
        });
        mSlidingMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                ivMenuShadow.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.sidemenu_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
                onClickTitleBack(null);
                openHomeFragment();
            }
        });
        findViewById(R.id.sidemenu_create_chart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
                onClickTitleBack(null);
                openCreateChartFragment();
            }
        });
        findViewById(R.id.sidemenu_saved_chart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
                onClickTitleBack(null);
                openSavedChartFragment();
            }
        });
        findViewById(R.id.sidemenu_transit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
                onClickTitleBack(null);
                openTransitView();
            }
        });
        findViewById(R.id.sidemenu_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
                onClickTitleBack(null);
                openSettingsFragment();
            }
        });
        findViewById(R.id.sidemenu_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
                onClickTitleBack(null);
                openHelpFragment();
            }
        });
        findViewById(R.id.sidemenu_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
                onClickTitleBack(null);
                openContactFragment();
            }
        });
        findViewById(R.id.sidemenu_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlidingMenu.toggle();
//                openView(LikeFragment.newInstance(), new FragmentStack("Contact", false));
                isLike();
            }
        });
    }

    private void loadMenuMain() {
        //-----adjust left menu width-----
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mSlidingMenu.setBehindWidth((3 * displaymetrics.widthPixels) / 5);
    }

    public void isLike(){
        super.isLike();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000)
        {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

//                CommonFunc.ShowToast(this, flagOfPlace +"");//-----

                if (flagOfPlace == 1)
                {
                    EditText et_placename = (EditText)findViewById(R.id.et_placename);
                    et_placename.setText(place.getName().toString());
                }else if (flagOfPlace == 2)
                {
                    EditText et_placename = (EditText)findViewById(R.id.et_placename_c);
                    et_placename.setText(place.getName().toString());
//                    CommonFunc.ShowToast(this, place.getName().toString());//------
                }

                this.chosen_place_name = place.getName().toString();
                this.chosen_place_latitude = "" + place.getLatLng().latitude;
                this.chosen_place_longitude = "" + place.getLatLng().longitude;

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void showPlaceAutoComplete(){
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, 1000);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    public void openChartView(String chart_link, String chart_name)
    {
        //---banner ads---//
        mAdView.setVisibility(View.GONE);

        openView(ChartFragment.newInstance(chart_link), new FragmentStack(chart_name, true));
    }

    public void openSettingsFragment(){//
        openView(SettingsFragment.newInstance(), new FragmentStack("Settings", false));
        mAdView.setVisibility(View.VISIBLE);
    }

    public void openSavedChartFragment(){//
        openView(SavedChartFragment.newInstance(), new FragmentStack("Saved Chart", false));
        //---banner ads---//
        mAdView.setVisibility(View.VISIBLE);

        //---Initial ads---//
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest2);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    public void openHelpFragment(){
        openView(HelpFragment.newInstance(), new FragmentStack("Help", false));
        mAdView.setVisibility(View.GONE);
    }

    public void openCreateChartFragment(){//
        openView(CreateChartFragment.newInstance(), new FragmentStack("Create Chart", false));
        mAdView.setVisibility(View.VISIBLE);

        //---Initial ads---//
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest2);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    public void openContactFragment(){
        openView(ContactFragment.newInstance(), new FragmentStack("Contact", false));
        mAdView.setVisibility(View.GONE);
    }

    public void openHomeFragment() {//
        openView(HomeFragment.newInstance(), new FragmentStack("Home", false));
        mAdView.setVisibility(View.VISIBLE);
    }

    public void openTransitView(){
        mAdView.setVisibility(View.GONE);
        Settings settings = SettingsManangement.getSettings();

        if (settings == null || settings.getTransit_location_name().isEmpty())
        {
            CommonFunc.ShowAlertDialog(this, "", "Enter Transit Location", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openSettingsFragment();
                }
            });
        }else{
            final String latitude = settings.getTransit_location_latitude();
            final String longitude = settings.getTransit_location_longitude();
            final String name = "Transit";

            Date date = new Date();
            final String transitDate = String.format("%s/%s/%s", CommonFunc.leadZero(date.getDate()), CommonFunc.leadZero(date.getMonth()+1), date.getYear() + 1900);

            final String time = date.getHours() + ":" + date.getMinutes();
            final String place = settings.getTransit_location_name();

            String _chartStyle = "NORTH_INDIAN", _node = "MEAN_NODE";
            int _ayanamsa = 1;

            if (!settings.getChart_style().isEmpty())
                _chartStyle = settings.getChart_style();
            if (settings.getAyanamsa() > 0)
                _ayanamsa = settings.getAyanamsa();
            if (!settings.getNode().isEmpty())
                _node = settings.getNode();

            final String chartStyle = _chartStyle, node = _node;
            final int ayanamsa = _ayanamsa;

            //---calculate rawoffset, dstoffset---//
            final ProgressDialog dialog = CommonFunc.createProgressDialog(this);
            dialog.show();

            //https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=AIzaSyDxjc7X-0j_5qUiPKSvtVTaCHMfjheW1yU
            APIService.getInstance().getService().getTimezoneInfo(latitude + "," + longitude
                    , (new Date()).getTime() / 1000 + "", Constant.API_KEY, new Callback<CallbackResponse>() {
                @Override
                public void success(CallbackResponse callbackResponse, Response response) {
                    dialog.dismiss();
                    if (callbackResponse.getStatus().equals("OK"))
                    {
                        createChart(name, transitDate, time, place, latitude, longitude, callbackResponse.getRawOffset() + "", callbackResponse.getDstOffset() + "",  chartStyle,  ayanamsa,  node);
                    } else {
                        CommonFunc.ShowToast(MainActivity.this, callbackResponse.getStatus());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    CommonFunc.ShowToast(MainActivity.this, "Internet connection failed");
                }
            });
        }
    }
    public void createChart(String name, String date, String time, String place, String latitude, String longitude, String rawOffset, String dstOffset, String chartStyle, int ayanamsa, String node)
    {
        openChartView("http://www.astrologersheaven.com/mobile/android/horoscope?name=" + name + "&date=" + date + "&time=" + time + "&place=" + place + "&latitude=" + latitude +
                "&longitude=" + longitude + "&rawOffset=" + rawOffset + "&dstOffset=" + dstOffset + "&chartStyle=" + chartStyle + "&ayanamsa=" + ayanamsa + "&node=" + node, name);
    }

    public void reloadSavedChartsFragment()
    {
        onClickTitleBack(null);
        openSavedChartFragment();
    }

}
