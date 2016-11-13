package com.ah.androidapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ah.androidapp.Activity.MainActivity;
import com.ah.androidapp.R;
import com.ah.androidapp.model.Charts;
import com.ah.androidapp.model.ChartsManangement;
import com.ah.androidapp.model.Settings;
import com.ah.androidapp.model.SettingsManangement;
import com.ah.androidapp.util.CommonFunc;

import java.util.ArrayList;

/**
 * Created by coneits on 5/25/16.
 */
public class SavedChartAdapter extends ArrayAdapter<Charts>{
    private Context context;
    int layout;
    ArrayList<Charts> saved_charts_result = new ArrayList<>();
    int is_delete;

    public SavedChartAdapter(Context context, int layout, ArrayList<Charts> saved_charts_result, int flagOfDelete) {
        super(context, layout, saved_charts_result);

        this.context = context;
        this.layout = layout;
        this.saved_charts_result = saved_charts_result;
        this.is_delete = flagOfDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Charts chartUnit = saved_charts_result.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        try {
            //-----------***-----------***----------***-----------//
            TextView tv_saved_chartname = (TextView) convertView.findViewById(R.id.tv_saved_chartname);
            tv_saved_chartname.setText(chartUnit.getName());

            ((Button) convertView.findViewById(R.id.btn_click_savedchart)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Settings settings = SettingsManangement.getSettings();
                    String chartStyle = "NORTH_INDIAN", node = "MEAN_NODE";
                    int ayanamsa = 1;

                    if (settings != null && !settings.getChart_style().isEmpty())
                        chartStyle = settings.getChart_style();
                    if (settings != null && settings.getAyanamsa() > 0)
                        ayanamsa = settings.getAyanamsa();
                    if (settings != null && !settings.getNode().isEmpty())
                        node = settings.getNode();

                    ((MainActivity) context).createChart(chartUnit.getName(), chartUnit.getDate(), chartUnit.getTime(), chartUnit.getPlace(), chartUnit.getLatitude(), chartUnit.getLogitude(), chartUnit.getRaw_offset(),
                            chartUnit.getDst_offset(), chartStyle, ayanamsa, node);
                }
            });

            if (this.is_delete == 1)//saved_charts
            {
                ((Button) convertView.findViewById(R.id.btn_click_savedchart)).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                    {
                                        ChartsManangement.removeChart(chartUnit.getName(), chartUnit.getPlace());
                                        ((MainActivity)context).reloadSavedChartsFragment();
                                    }
                                    break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to delete " + chartUnit.getName() + "?").setPositiveButton("YES", dialogClickListener)
                                .setNegativeButton("NO", dialogClickListener).show();

                        return false;
                    }
                });
            }
        }catch (Exception e){}

        return convertView;
    }
}
