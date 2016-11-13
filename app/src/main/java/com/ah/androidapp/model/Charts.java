package com.ah.androidapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by coneits on 8/21/16.
 */

@Table(name = "Charts")
public class Charts extends Model{

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "place")
    private String place;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "raw_offset")
    private String raw_offset;

    @Column(name = "dst_offset")
    private String dst_offset;

    @Column(name = "created_date")
    private Timestamp created_date;

    @Column(name = "last_accessed_date")
    private Timestamp last_accessed_date;

    public Charts(){ super(); }

    public Charts(String name, String date, String time, String place, String latitude, String longitude, String raw_offset, String dst_offset)
    {
        this.name = name;
        this.date = date;
        this.time = time;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.raw_offset = raw_offset;
        this.dst_offset = dst_offset;
        created_date = new Timestamp((new Date()).getTime());
        last_accessed_date = new Timestamp((new Date()).getTime());
    }

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public void setDate(String date){this.date = date;}
    public String getDate(){return this.date;}

    public void setTime(String time){this.time = time;}
    public String getTime(){return this.time;}

    public void setPlace(String place){this.place = place;}
    public String getPlace(){return this.place;}

    public void setLatitude(String latitude){this.latitude = latitude;}
    public String getLatitude(){return this.latitude;}

    public void setLongitude(String longitude){this.longitude = longitude;}
    public String getLogitude(){return this.longitude;}

    public void setRaw_offset(String raw_offset){this.raw_offset = raw_offset;}
    public String getRaw_offset(){return this.raw_offset;}

    public void setDst_offset(String dst_offset){this.dst_offset = dst_offset;}
    public String getDst_offset(){return this.dst_offset;}

    public void setCreated_date(Timestamp created_date){this.created_date = created_date;}
    public Timestamp getCreated_date(){return this.created_date;}

    public void setLast_accessed_date(Timestamp last_accessed_date){this.last_accessed_date = last_accessed_date;}
    public Timestamp getLast_accessed_date(){return this.last_accessed_date;}
}
