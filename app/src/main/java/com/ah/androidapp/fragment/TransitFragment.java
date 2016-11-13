package com.ah.androidapp.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ah.androidapp.Activity.MainActivity;
import com.ah.androidapp.R;
import com.ah.androidapp.model.Charts;
import com.ah.androidapp.model.ChartsManangement;
import com.ah.androidapp.model.Settings;
import com.ah.androidapp.model.SettingsManangement;
import com.ah.androidapp.service.APIService;
import com.ah.androidapp.service.CallbackResponse;
import com.ah.androidapp.util.CommonFunc;
import com.ah.androidapp.util.Constant;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransitFragment extends Fragment {


    public TransitFragment() {
        // Required empty public constructor
    }

    public static TransitFragment newInstance(){
        TransitFragment fragment = new TransitFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_transit, container, false);



        return v;
    }


}
