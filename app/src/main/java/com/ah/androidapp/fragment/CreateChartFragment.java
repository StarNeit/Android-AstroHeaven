package com.ah.androidapp.fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

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
import com.ah.androidapp.util.Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateChartFragment extends Fragment {

    private EditText et_chartname;
    private EditText et_date;
    private EditText et_time;
    private EditText et_placename;

    private Date lastDate;
    private Date lastTime;

    private WebView browser;


    public CreateChartFragment() {
        // Required empty public constructor
    }

    public static CreateChartFragment newInstance(){
        CreateChartFragment fragment = new CreateChartFragment();

        return fragment;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            String selectDate = String.format("%s/%s/%s", CommonFunc.leadZero(dayOfMonth), CommonFunc.leadZero(monthOfYear+1), year);

            lastDate = new Date();
            lastDate.setYear(year);
            lastDate.setMonth(monthOfYear);
            lastDate.setDate(dayOfMonth);

            et_date.setText(selectDate);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            et_time.setText(hourOfDay + ":" + minute);

            lastTime = new Date();
            lastTime.setHours(hourOfDay);
            lastTime.setMinutes(minute);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_create_chart, container, false);

        //keyboard control
        Utils.setupUI(v.findViewById(R.id.parent), getActivity());

        //initialize
        et_chartname = (EditText)v.findViewById(R.id.et_chartname);
        et_date = (EditText)v.findViewById(R.id.et_date);
        et_time = (EditText)v.findViewById(R.id.et_time);
        et_placename = (EditText)v.findViewById(R.id.et_placename_c);

        browser = (WebView) v.findViewById(R.id.webView);

        v.findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_date.getEditableText().toString().isEmpty()){
                    new DatePickerDialog(getActivity(),dateSetListener,(new Date()).getYear() + 1900,(new Date()).getMonth(),(new Date()).getDate()).show();
                }else{
                    new DatePickerDialog(getActivity(),dateSetListener,lastDate.getYear(), lastDate.getMonth(), lastDate.getDate()).show();
                }
            }
        });

        v.findViewById(R.id.btn_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_time.getEditableText().toString().isEmpty()){
                    new TimePickerDialog(getActivity(), timeSetListener, (new Date()).getHours(), (new Date()).getMinutes(), false).show();
                }else{
                    new TimePickerDialog(getActivity(), timeSetListener, lastTime.getHours(), lastTime.getMinutes(), false).show();
                }
            }
        });

        v.findViewById(R.id.btn_place).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).flagOfPlace = 2;
                ((MainActivity) getActivity()).showPlaceAutoComplete();
            }
        });

        v.findViewById(R.id.btn_create_chart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_chartname.getEditableText().toString().trim().isEmpty()) {
                    CommonFunc.ShowToast(getActivity(), "Name is required");
                    return;
                }
                if (et_date.getEditableText().toString().isEmpty()) {
                    CommonFunc.ShowToast(getActivity(), "Date is required");
                    return;
                }
                if (et_time.getEditableText().toString().isEmpty()) {
                    CommonFunc.ShowToast(getActivity(), "Time is required");
                    return;
                }
                if (et_placename.getEditableText().toString().isEmpty()) {
                    CommonFunc.ShowToast(getActivity(), "Place is required");
                    return;
                }

                //---calculate timestamp---//
                Date date = new Date();
                date.setYear(lastDate.getYear() - 1900);
                date.setMonth(lastDate.getMonth() + 1);
                date.setDate(lastDate.getDate());
                date.setHours(lastTime.getHours());
                date.setMinutes(lastTime.getMinutes());
                date.setSeconds(0);
                long time = date.getTime() / 1000;


                //---calculate rawoffset, dstoffset---//
                final ProgressDialog dialog = CommonFunc.createProgressDialog(getActivity());
                dialog.show();

                //https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=AIzaSyDxjc7X-0j_5qUiPKSvtVTaCHMfjheW1yU
                APIService.getInstance().getService().getTimezoneInfo(((MainActivity) getActivity()).chosen_place_latitude + "," + ((MainActivity) getActivity()).chosen_place_longitude
                        , time + "", Constant.API_KEY, new Callback<CallbackResponse>() {
                    @Override
                    public void success(CallbackResponse callbackResponse, Response response) {
                        dialog.dismiss();
                        if (callbackResponse.getStatus().equals("OK"))
                        {
//                            CommonFunc.ShowToast(getActivity(), callbackResponse.getDstOffset() + "," + callbackResponse.getRawOffset());
                            Settings settings = SettingsManangement.getSettings();
                            String chartStyle = "NORTH_INDIAN", node = "MEAN_NODE";
                            int ayanamsa = 1;

                            if (settings != null && !settings.getChart_style().isEmpty())
                                chartStyle = settings.getChart_style();
                            if (settings != null && settings.getAyanamsa() > 0)
                                ayanamsa = settings.getAyanamsa();
                            if (settings != null && !settings.getNode().isEmpty())
                                node = settings.getNode();

                            //---save chart---//
                            Charts chart = new Charts(et_chartname.getEditableText().toString(), et_date.getEditableText().toString(), et_time.getEditableText().toString(), ((MainActivity) getActivity()).chosen_place_name,
                                    ((MainActivity) getActivity()).chosen_place_latitude, ((MainActivity) getActivity()).chosen_place_longitude, callbackResponse.getRawOffset() + "", callbackResponse.getDstOffset() + "");
                            ChartsManangement.saveChart(chart);

                            //---display chart---//
                            ((MainActivity)getActivity()).onClickTitleBack(null);
                            ((MainActivity)getActivity()).createChart(et_chartname.getEditableText().toString(), et_date.getEditableText().toString(), et_time.getEditableText().toString(), ((MainActivity) getActivity()).chosen_place_name,
                                    ((MainActivity) getActivity()).chosen_place_latitude, ((MainActivity) getActivity()).chosen_place_longitude, callbackResponse.getRawOffset() + "", callbackResponse.getDstOffset() + "",
                                    chartStyle, ayanamsa, node);
                        } else {
                            CommonFunc.ShowToast(getActivity(), callbackResponse.getStatus());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialog.dismiss();
                        CommonFunc.ShowToast(getActivity(), "Internet connection failed");
                    }
                });
            }
        });
        return v;
    }
}
