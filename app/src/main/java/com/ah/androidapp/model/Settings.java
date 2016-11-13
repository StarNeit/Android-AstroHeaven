package com.ah.androidapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
/**
 * Created by coneits on 8/21/16.
 */

@Table(name = "Settings")
public class Settings extends Model{

    @Column(name = "chart_style")
    private String chart_style;

    @Column(name = "ayanamsa")
    private int ayanamsa;

    @Column(name = "node")
    private String node;

    @Column(name = "transit_location_name")
    private String transit_location_name;

    @Column(name = "transit_location_latitude")
    private String transit_location_latitude;

    @Column(name = "transit_location_longitude")
    private String transit_location_longitude;

    public Settings() { super(); }

    public String getChart_style(){return this.chart_style;}
    public void setChart_style(String chart_style){this.chart_style = chart_style;}

    public int getAyanamsa(){return this.ayanamsa;}
    public void setAyanamsa(int ayanamsa){this.ayanamsa = ayanamsa;}

    public String getNode(){return this.node;}
    public void setNode(String node){this.node = node;}

    public String getTransit_location_name(){return this.transit_location_name;}
    public void  setTransit_location_name(String transit_location_name){this.transit_location_name = transit_location_name;}

    public String getTransit_location_latitude(){return this.transit_location_latitude;}
    public void setTransit_location_latitude(String transit_location_latitude){this.transit_location_latitude = transit_location_latitude;}

    public String getTransit_location_longitude(){return this.transit_location_longitude;}
    public void setTransit_location_longitude(String transit_location_longitude){this.transit_location_longitude = transit_location_longitude;}

}
