package com.ah.androidapp.service;

import java.util.List;

public class CallbackResponse {

    private String dstOffset;

    private String rawOffset;

    private String status;

    public void setDstOffset(String dstOffset){this.dstOffset = dstOffset;}
    public String getDstOffset(){return this.dstOffset;}

    public void setRawOffset(String rawOffset){this.rawOffset = rawOffset;}
    public String getRawOffset(){return this.rawOffset;}

    public void setStatus(String status){this.status = status;}
    public String getStatus(){return this.status;}
}
