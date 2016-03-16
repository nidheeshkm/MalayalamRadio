package com.nidheesh.emphasys.malayalamradio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView stationList;
    protected static MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayStations();

    }
    public void displayStations()
    {
        Station station_data[]=new Station[]{
                new Station("Malayali FM","http://knight.wavestreamer.com:2631/Live",R.drawable.icon_malayali),
                new Station("Kerala FM","http://radio.hostonnet.com:8000/;",R.drawable.icon_keralafm),
                new Station("Gaanam Radio","http://viadj.viastreaming.net:7104/;",R.drawable.icon_ganam),
                new Station("Radio City","http://prclive1.listenon.in:9908/;",R.drawable.icon_radiocity),
                new Station("Mazhavil FM","http://mazhavilfm.out.airtime.pro:8000/mazhavilfm_a",R.drawable.icon_mazhavil),
                new Station("Red FM 94.7","http://192.240.97.69:8937/;",R.drawable.icon_red947),
                new Station("Raagam","http://109.169.59.107:8038/;",R.drawable.icon_raagam),
                new Station("Online_Malayalam_Radio","http://192.99.35.93:6436/;",R.drawable.icon_omr),
                new Station("super 94.7","http://192.240.97.69:8937/;",R.drawable.icon_super_94_7),
                new Station("Radio Hungama","http://123.176.41.8:8656/;",R.drawable.icon_hungama),
        };

        stationAdapter adapter=new stationAdapter(this,R.layout.station_list_item,station_data);
        stationList=(ListView)findViewById(R.id.station_list);
        stationList.setAdapter(adapter);
        stationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationAdapter.StationHolder st = (stationAdapter.StationHolder) view.getTag();
                String stationUrlFromList = st.url.toString();
                String stationName = st.tvStation.getText().toString();
                Integer albumImageTag=(Integer)st.stationLogo.getTag();
                int stationLogoId=R.drawable.icon_ganam;//default
                albumImageTag = albumImageTag == null ? R.drawable.icon_ganam : albumImageTag;
                switch(albumImageTag){
                    case R.drawable.icon_ganam:
                        stationLogoId=R.drawable.ganam;
                        break;
                    case R.drawable.icon_hungama:
                        stationLogoId=R.drawable.hungama;
                        break;
                    case R.drawable.icon_keralafm:
                        stationLogoId=R.drawable.keralafm;
                        break;
                    case R.drawable.icon_malayali:
                        stationLogoId=R.drawable.malayali;
                        break;
                    case R.drawable.icon_mazhavil:
                        stationLogoId=R.drawable.mazhavil;
                        break;
                    case R.drawable.icon_omr:
                        stationLogoId=R.drawable.omr;
                        break;
                    case R.drawable.icon_raagam:
                        stationLogoId=R.drawable.raagam;
                        break;
                    case R.drawable.icon_radiocity:
                        stationLogoId=R.drawable.radiocity;
                        break;
                    case R.drawable.icon_red947:
                        stationLogoId=R.drawable.red947;
                        break;
                    case R.drawable.icon_super_94_7:
                        stationLogoId=R.drawable.super_94_7;
                        break;

                }
                PlayActivity.mediaPlayer.stop();
                PlayActivity.mediaPlayer.reset();
                Intent playerIntent=new Intent(getApplicationContext(),PlayActivity.class);
                Bundle playerBundle=new Bundle();
                playerBundle.putString("streamUrl",stationUrlFromList);
                playerBundle.putString("station",stationName);
                playerBundle.putInt("albumArt", stationLogoId);
                playerIntent.putExtras(playerBundle);
                startActivity(playerIntent);



            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PlayActivity.mediaPlayer.release();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        displayStations();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {


        }  else if (id == R.id.nav_about) {
            LayoutInflater inflater=getLayoutInflater().inflate;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
