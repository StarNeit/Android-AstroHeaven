package com.ah.androidapp.model;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by coneits on 8/21/16.
 */
public class ChartsManangement {

    public static void saveChart(Charts charts){charts.save();}

    public static void removeChart(String name, String place){
//        Charts.delete(Charts.class, index);
        new Delete().from(Charts.class).where("name = ? AND place = ?", name, place).execute();

    }


    public static List<Charts> getAll(){
        return new Select().from(Charts.class).orderBy("name ASC").limit(1000).execute();
    }
}
