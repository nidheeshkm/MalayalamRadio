package com.nidheesh.emphasys.malayalamradio;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jyothi on 3/14/2016.
 */
public class stationAdapter extends ArrayAdapter<Station> {
    Context context;
    int resource;
    Station data[]=null;
    public stationAdapter(Context context, int resource, Station[] data) {
        super(context, resource, data);
        this.context=context;
        this.resource=resource;
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        StationHolder holder=null;
        if(row==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(R.layout.station_list_item,parent,false);
            holder=new StationHolder();
            holder.stationLogo=(ImageView)row.findViewById(R.id.imgStationImage);
            holder.tvStation=(TextView)row.findViewById(R.id.tvStationName);

            row.setTag(holder);
        }
        else{
            holder=(StationHolder)row.getTag();
        }
        Station station=data[position];
        holder.stationLogo.setImageResource(station.stationLogo);
        holder.stationLogo.setTag(station.stationLogo);
        holder.tvStation.setText(station.stationName);
        holder.url=station.streamUrl;
        return row;
    }
    public class StationHolder{
        ImageView stationLogo;
        TextView tvStation;
        String url;
    }
}
