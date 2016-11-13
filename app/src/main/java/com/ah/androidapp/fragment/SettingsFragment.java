package com.ah.androidapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.ah.androidapp.Activity.MainActivity;
import com.ah.androidapp.R;
import com.ah.androidapp.model.Settings;
import com.ah.androidapp.model.SettingsManangement;
import com.ah.androidapp.util.CommonFunc;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private Spinner spinner_chart_style;

    private Spinner spinner_ayanamsa;

    private Spinner spinner_node;

    private EditText et_placename;

    private Button btn_update_settings;


    private String value_chart_style;
    private int value_ayanamsa;
    private String value_node;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(){
        SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);

        //---Restore saved values---//
        Settings settings = SettingsManangement.getSettings();
        String chartStyle = "NORTH_INDIAN", node = "MEAN_NODE";
        int ayanamsa = 1;
        String place_name = "";

        if (settings != null && !settings.getChart_style().isEmpty())
            chartStyle = settings.getChart_style();
        if (settings != null && settings.getAyanamsa() > 0)
            ayanamsa = settings.getAyanamsa();
        if (settings != null && !settings.getNode().isEmpty())
            node = settings.getNode();
        if (settings != null && !settings.getTransit_location_name().isEmpty())
            place_name = settings.getTransit_location_name();


        //---Chart Style---//
        spinner_chart_style = (Spinner) v.findViewById(R.id.spinner_chart_style);
        final String[] chart_styles = {"North Indian","South Indian"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, chart_styles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_chart_style.setAdapter(adapter);
        spinner_chart_style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                {
                    value_chart_style = "NORTH_INDIAN";
                }else if (i == 1){
                    value_chart_style = "SOUTH_INDIAN";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //---retain Chart Style---//
        String str_chart_style = "North Indian";
        if (chartStyle.equals("NORTH_INDIAN"))
        {
            str_chart_style = "North Indian";
        }else if (chartStyle.equals("SOUTH_INDIAN"))
        {
            str_chart_style = "South Indian";
        }
        for (int i = 0 ; i < chart_styles.length; i ++)
        {
            if (chart_styles[i].equals(str_chart_style))
            {
                spinner_chart_style.setSelection(i);
                break;
            }
        }


        //---Ayanamsa---//
        spinner_ayanamsa = (Spinner) v.findViewById(R.id.spinner_ayanamsa);
        final String[] arr_ayanamsa = {"Lahiri","Raman","Krishnamurti","Yukteshwar","J.N Bhasin","Suryasiddhanta","Aryabhata"};
        ArrayAdapter<String> adapter_ayanmasa = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arr_ayanamsa);
        adapter_ayanmasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ayanamsa.setAdapter(adapter_ayanmasa);
        spinner_ayanamsa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        value_ayanamsa = 1;
                        break;
                    case 1:
                        value_ayanamsa = 3;
                        break;
                    case 2:
                        value_ayanamsa = 5;
                        break;
                    case 3:
                        value_ayanamsa = 7;
                        break;
                    case 4:
                        value_ayanamsa = 8;
                        break;
                    case 5:
                        value_ayanamsa = 21;
                        break;
                    case 6:
                        value_ayanamsa = 23;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //---retain ayanamsa---//
        String str_ayanamsa = "Lahiri";;
        switch (ayanamsa)
        {
            case 1:
                str_ayanamsa = "Lahiri";
                break;
            case 3:
                str_ayanamsa = "Raman";
                break;
            case 5:
                str_ayanamsa = "Krishnamurti";
                break;
            case 7:
                str_ayanamsa = "Yukteshwar";
                break;
            case 8:
                str_ayanamsa = "J.N Bhasin";
                break;
            case 21:
                str_ayanamsa = "Suryasiddhanta";
                break;
            case 23:
                str_ayanamsa = "Aryabhata";
                break;
        }
        for (int i = 0 ; i < arr_ayanamsa.length; i ++)
        {
            if (arr_ayanamsa[i].equals(str_ayanamsa))
            {
                spinner_ayanamsa.setSelection(i);
                break;
            }
        }


        //---Node---//
        spinner_node = (Spinner) v.findViewById(R.id.spinner_node);
        final String[] arr_node = {"Mean Node","True Node"};
        ArrayAdapter<String> adapter_node = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arr_node);
        adapter_node.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_node.setAdapter(adapter_node);
        spinner_node.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                {
                    value_node = "MEAN_NODE";
                }else if (i == 1)
                {
                    value_node = "TRUE_NODE";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //---retain Node---//
        String str_node = "Mean Node";
        if (node.equals("MEAN_NODE"))
            str_node = "Mean Node";
        else if (node.equals("TRUE_NODE"))
            str_node = "TRUE_NODE";
        for (int i = 0 ; i < arr_node.length; i ++)
        {
            if (arr_node[i].equals(str_node))
            {
                spinner_node.setSelection(i);
                break;
            }
        }


        //---PlaceEdit---//
        et_placename = (EditText) v.findViewById(R.id.et_placename);
        ((ImageButton)v.findViewById(R.id.btn_location)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).flagOfPlace = 1;
                ((MainActivity)getActivity()).showPlaceAutoComplete();
            }
        });
        et_placename.setText(place_name);


        //---UpdateSettingInfo---//
        btn_update_settings = (Button)v.findViewById(R.id.btn_update_settings);
        btn_update_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings settings = new Settings();

                settings.setChart_style(value_chart_style);
                settings.setAyanamsa(value_ayanamsa);
                settings.setNode(value_node);
                if (et_placename.getEditableText().toString().isEmpty()){
                    settings.setTransit_location_name("");
                    settings.setTransit_location_latitude("");
                    settings.setTransit_location_longitude("");
                }else{
                    settings.setTransit_location_name(((MainActivity)getActivity()).chosen_place_name);
                    settings.setTransit_location_latitude(((MainActivity)getActivity()).chosen_place_latitude);
                    settings.setTransit_location_longitude(((MainActivity)getActivity()).chosen_place_longitude);
                }

                SettingsManangement.removeSettings();
                SettingsManangement.saveSettings(settings);

                CommonFunc.ShowToast(getActivity(), "Successfully saved settings");
                ((MainActivity)getActivity()).onClickTitleBack(null);
            }
        });
        return v;
    }

}
