package com.ah.androidapp.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ah.androidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment {


    public LikeFragment() {
        // Required empty public constructor
    }

    public static LikeFragment newInstance(){
        LikeFragment fragment = new LikeFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_like, container, false);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE://I like this app
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE://I don't like this app
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Like this app?").setPositiveButton("NO", dialogClickListener)
                .setNegativeButton("YES", dialogClickListener).show();

        return v;
    }

}
