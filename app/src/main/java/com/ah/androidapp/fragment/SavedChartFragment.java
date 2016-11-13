package com.ah.androidapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ah.androidapp.R;
import com.ah.androidapp.adapter.SavedChartAdapter;
import com.ah.androidapp.model.Charts;
import com.ah.androidapp.model.ChartsManangement;
import com.ah.androidapp.util.CommonFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedChartFragment extends Fragment {

    private ListView lv_saved_charts_lists;

    public SavedChartFragment() {
        // Required empty public constructor
    }

    public static SavedChartFragment newInstance(){
        SavedChartFragment fragment = new SavedChartFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_saved_chart, container, false);

        lv_saved_charts_lists = (ListView)v.findViewById(R.id.lv_saved_charts_lists);

        ArrayList<Charts> saved_charts = (ArrayList<Charts>)ChartsManangement.getAll();

        SavedChartAdapter adapter = new SavedChartAdapter(getActivity(), R.layout.adapter_saved_chartslist, saved_charts, 1);
        lv_saved_charts_lists.setAdapter(adapter);

        return v;
    }
}
