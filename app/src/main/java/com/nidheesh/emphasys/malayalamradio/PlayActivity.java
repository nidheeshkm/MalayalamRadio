package com.nidheesh.emphasys.malayalamradio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Activity playerActivity;
    private static  final int STATION_REQUEST_CODE=1;
    private String url;
    private String stationName;
    private int stationImageId;
    private ImageButton imgPlayPause;
    public static final MediaPlayer mediaPlayer=new MediaPlayer();
    private TextView tvStationNmae;
    private ImageButton imgAlbumArt;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        playerActivity=this;


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        imgPlayPause=(ImageButton)findViewById(R.id.imbPlayButton);
        imgPlayPause.setImageResource(R.drawable.btn_pause);
        imgPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    if(mediaPlayer!=null){
                        mediaPlayer.pause();
                        // Changing button image to play button
                        imgPlayPause.setImageResource(R.drawable.btn_play);
                    }
                }else{
                    // Resume song
                    if(mediaPlayer!=null){
                        mediaPlayer.start();
                        // Changing button image to pause button
                        imgPlayPause.setImageResource(R.drawable.btn_pause);
                    }
                }
            }
        });

        if (this.getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("streamUrl");
            stationName = getIntent().getExtras().getString("station");

            stationImageId = getIntent().getExtras().getInt("albumArt");
        }
        streamStation(url,stationName,stationImageId);
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
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
     /*   mediaPlayer.pause();
        imgPlayPause.setImageResource(R.drawable.btn_play);*/
        //mediaPlayer.start();
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

            Intent stationIntent=new Intent(getApplicationContext(),MainActivity.class);
            startActivityForResult(stationIntent, STATION_REQUEST_CODE);
        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == STATION_REQUEST_CODE) && (resultCode == RESULT_OK)) {
            //streamStation(String url);
        }

    }
    public void streamStation(String url,String name,int image){
        tvStationNmae=(TextView)findViewById(R.id.currentStationName);
        tvStationNmae.setText(name);
        imgAlbumArt=(ImageButton)findViewById(R.id.imageAlbumArt);
        imgAlbumArt.setImageResource(image);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        PowerManager powermanager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = powermanager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wakelock.acquire();
        {


            try {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
              //  mediaPlayer=new MediaPlayer();
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
            //mediaPlayer.setLooping(false);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    if(mp.isPlaying()) {
                        mp.release();

                    }
                  //  mp.setLooping(false);
                    mp.start();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mp.start();
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                    mp.stop();;
                }
            });

        }
        wakelock.release();

    }
}
