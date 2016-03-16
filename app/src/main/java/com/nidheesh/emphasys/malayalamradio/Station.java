package com.nidheesh.emphasys.malayalamradio;

/**
 * Created by jyothi on 3/14/2016.
 */
public class Station {
    String stationName;
    String streamUrl;
    int stationLogo;
    public Station(){super();}
    public Station(String stationName,String streamUrl,int stationLogo){
        super();
        this.stationName=stationName;
        this.streamUrl=streamUrl;
        this.stationLogo=stationLogo;
    }
}
