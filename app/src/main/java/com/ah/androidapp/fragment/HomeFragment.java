package com.ah.androidapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ah.androidapp.Activity.MainActivity;
import com.ah.androidapp.R;
import com.ah.androidapp.adapter.SavedChartAdapter;
import com.ah.androidapp.model.Charts;
import com.ah.androidapp.model.ChartsManangement;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView lv_saved_charts_lists;

    private LinearLayout view_intro;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<Charts> saved_charts = (ArrayList<Charts>) ChartsManangement.getAll();

        view_intro = (LinearLayout) v.findViewById(R.id.view_intro);
        lv_saved_charts_lists = (ListView)v.findViewById(R.id.lv_saved_charts_lists);

        if (saved_charts.size() > 2)
        {
            lv_saved_charts_lists.setVisibility(View.VISIBLE);
            view_intro.setVisibility(View.GONE);

            ArrayList<Charts> charts_list = new ArrayList<Charts>();
            for (int i = 0 ; i < 5; i ++)
            {
                if (i < saved_charts.size())
                    charts_list.add(saved_charts.get(i));
            }

            SavedChartAdapter adapter = new SavedChartAdapter(getActivity(), R.layout.adapter_saved_chartslist, charts_list, 2);
            lv_saved_charts_lists.setAdapter(adapter);
        }else{
            lv_saved_charts_lists.setVisibility(View.GONE);
            view_intro.setVisibility(View.VISIBLE);
        }

        ((Button)v.findViewById(R.id.btn_settings)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openSettingsFragment();
            }
        });

        ((Button)v.findViewById(R.id.btn_create_chart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openCreateChartFragment();
            }
        });

        ((Button)v.findViewById(R.id.btn_help)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openHelpFragment();
            }
        });

        return v;
    }

}
