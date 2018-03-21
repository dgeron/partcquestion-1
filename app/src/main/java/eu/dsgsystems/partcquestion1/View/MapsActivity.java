package eu.dsgsystems.partcquestion1.View;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.dsgsystems.partcquestion1.Controller.DataController;
import eu.dsgsystems.partcquestion1.Model.MyMarker;
import eu.dsgsystems.partcquestion1.Model.User;
import eu.dsgsystems.partcquestion1.PermissionUtils;
import eu.dsgsystems.partcquestion1.R;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    // Add a marker in Thessaloniki and move the camera
    private static final LatLng THESSALONIKI = new LatLng(40.6085, 22.985);
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private boolean mPermissionDenied = false;
    private LocationRequest mLocationRequest;
    private DataController dataController = new DataController();
    private User myUser = new User();
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private List<Marker> markers = new ArrayList<>();
    private List<MyMarker> myMarkers = new ArrayList<>();
    private Context context;
    private TextView timerValue;
    private TextView scoreValue;
    private Location lastLocation = new Location("last");
 //   private MusicService musicSrv;
 //   private Intent playIntent;
 //   private boolean musicBound=false;
    private int score;
    int collected = 0;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    //connect to the service
 /*   private ServiceConnection musicConnection = new ServiceConnection(){
        ArrayList<Song> songList = new ArrayList<>();

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            createSongList();
            musicSrv.setList(songList);
            musicBound = true;
        }

        private void createSongList() {
            List<MyMarker> mMarker = dataController.loadMarkers();
            for(int i = 0; i < mMarker.size(); i++)
            {
               songList.add(new Song(i,mMarker.get(i).getSound()));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    }; */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        timerValue = (TextView) findViewById(R.id.footer1_view);
        scoreValue = (TextView) findViewById(R.id.footer2_view);
        populateMyMarkers();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        context = this;
        mapFragment.getMapAsync(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myUser = (User) extras.get("User");
        }
        startLocationUpdates();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, Leaderboard.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MyListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("User", myUser);
                startActivity(intent);
            }
        });
    }

    private void populateMyMarkers() {
        myMarkers.add(new MyMarker("-L7gWus7qLnL9Lqgk4zi", new LatLng(40.60692585452209, 22.98313250244920), "cow", 10, "https://www.iema.gr/data/EducationalProjects/Melina/Tracks/Hxoi/Zwa_Agelades.mp3", "https://r1---sn-nv47lns7.googlevideo.com/videoplayback?source=youtube&sparams=clen,dur,ei,expire,gir,id,initcwndbps,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pcm2cms,pl,ratebypass,requiressl,source&pl=16&c=WEB&id=o-AADApNFNTR3Zt3KDJprGhfAeZCjJQ83GcZHH5ABeAhv1&expire=1521532461&ei=zWmwWrP4Mq-9-APUjIbABA&ip=159.65.71.212&lmt=1521386381407725&dur=150.070&gir=yes&signature=21DBC8C395C50836A95972368456A93B2BFA2304.196EFA345B4BC694BEB82DDF681096307F82A79F&key=cms1&itag=18&ipbits=0&fvip=1&clen=10965086&mime=video/mp4&requiressl=yes&ratebypass=yes&title=Cow+Videos+for+Children+More+Cows+for+KidsCow%20Videos%20for%20Children,%20More%20Cows%20for%20Kids_MobWon.Com.mp4&rm=sn-a8au-nh4e7s,sn-n4ve67e&req_id=158c71f7bb96a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=37.6.162.57&mm=29&mn=sn-nv47lns7&ms=rdu&mt=1521510817&mv=m"));
        myMarkers.add(new MyMarker("-L7gWxN-j9PPnfZwab2Z", new LatLng(40.60592524166101, 22.98340901932881), "cat", 4, "https://www.iema.gr/data/EducationalProjects/Melina/Tracks/Hxoi/Zwa_Gata.mp3", "http://download.itcuties.com/teaser/itcuties-teaser-480.mp4"));
        myMarkers.add(new MyMarker("-L7gWyP0Yhicwk8y5PU0", new LatLng(40.60853909129480, 22.98732614601619), "dog", 6, "https://www.iema.gr/data/EducationalProjects/Melina/Tracks/Hxoi/Zwa_Skhlos.mp3", "http://download.itcuties.com/teaser/itcuties-teaser-480.mp4"));
        myMarkers.add(new MyMarker("-L7pxfw_mHI5OjqsvYMl", new LatLng(40.60950863796019, 22.98605347908127), "sheep", 8, "https://www.iema.gr/data/EducationalProjects/Melina/Tracks/Hxoi/Zwa_Provata.mpe", "http://download.itcuties.com/teaser/itcuties-teaser-480.mp4"));
        myMarkers.add(new MyMarker("-L7pxiSuoMEyeU_4ppT8", new LatLng(40.60958118342667, 22.98455413993064), "horse", 10, "https://www.iema.gr/data/EducationalProjects/Melina/Tracks/Hxoi/Zwa_Alogo.mp3", "https://r5---sn-4g5e6nes.googlevideo.com/videoplayback?lmt=1513587513423775&dur=815.182&key=cms1&mime=video/mp4&sparams=clen,dur,ei,expire,gir,id,initcwndbps,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pcm2cms,pl,ratebypass,requiressl,source&source=youtube&expire=1521532706&gir=yes&id=o-AOSIqqUTlUenBira-oMo7aBOttM7--NA5O8woysUzpIX&c=WEB&signature=4AD93F632270D9DFD77DB668FAA84E873BE6F36F.7975E04417BC9CB71D1FD219FC4935174EFFEBD5&ip=159.65.71.212&pl=16&clen=58735829&ei=wmqwWvGKLsXc-wO7w53IAg&fvip=5&ipbits=0&requiressl=yes&itag=18&ratebypass=yes&title=Cute+And+funny+horse+Videos+Compilation+cute+moment+of+the+horses+Soo+Cute+15Cute%20And%20funny%20horse%20Videos%20Compilation&rm=sn-a8au-nh4e76,sn-n4ver76&req_id=31c4030bba1a3ee&ipbypass=yes&mip=37.6.162.57&redirect_counter=3&cm2rm=sn-nv4sz7s&cms_redirect=yes&mm=34&mn=sn-4g5e6nes&ms=ltu&mt=1521510990&mv=m"));
        myMarkers.add(new MyMarker("-L7pxkWCSE2uu-U49CZQ", new LatLng(40.60756554239220, 22.98478971107427), "pig", 8, "https://www.iema.gr/data/EducationalProjects/Melina/Tracks/Hxoi/Zwa_Goyroynia.mp3", "http://download.itcuties.com/teaser/itcuties-teaser-480.mp4"));
        myMarkers.add(new MyMarker("-L7pxn3nEjrpRn4FfNoq", new LatLng(40.60816605012378, 22.98313588372720), "chicken", 2, "https://www.iema.gr/data/EducationalProjects/Melina/Tracks/Hxoi/Zwa_Kotes.mp3", "http://download.itcuties.com/teaser/itcuties-teaser-480.mp4"));
        // myMarkers = dataController.loadMarkers();
    }

    @Override
    public void onPause() {

        super.onPause();
     //   if (mediaPlayer.isPlaying())
     //       mediaPlayer.stop();
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);


    }

    @Override
    public void onStart() {

        super.onStart();
       // startFusedLocation();
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
  /*      if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        } */

    }

/*    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    } */

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we add the markers on our game arena in Thessaloniki, Greece.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(40.60412075096054, 22.98356754269177), new LatLng(40.61015235454433, 22.98921074494638), new LatLng(40.61148068998608, 22.98586252654734), new LatLng(40.60815886939233, 22.98220240434413))
                .strokeColor(Color.RED));
        for (int i = 0; i < myMarkers.size(); i++) {
            int imageId = context.getResources().getIdentifier(myMarkers.get(i).getName(), "drawable", "eu.dsgsystems.partcquestion1");
            markers.add(mMap.addMarker(new MarkerOptions().position(myMarkers.get(i).getLatLng()).title(myMarkers.get(i).getName()).snippet("Points: " + myMarkers.get(i).getPoints()).icon(BitmapDescriptorFactory.fromResource(imageId)).visible(false)));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int index = 0;
                for (int i = 0; i < markers.size(); i++) {
                    if (markers.get(i).equals(marker)) {
                        index = i;
                        break;
                    }
                }
                if (!myMarkers.get(index).isCollected() && marker.equals(markers.get(index))) {
                    score += myMarkers.get(index).getPoints();
                    String tmp = "Score : " + score;
                    scoreValue.setText(tmp);
                    dataController.setScore(myUser.getId(), score);
                    myMarkers.get(index).setCollected(true);
                    dataController.putObject(myUser, myMarkers.get(index));
                    markers.get(index).setVisible(false);
                    Intent intent = new Intent(context, URLVideoActivity.class);
                    intent.putExtra("VIDEO_URI", myMarkers.get(index).getVideo());
                    context.startActivity(intent);
                    //break;
                }
                checkEndOfGame();
                return false;
            }
        });
        // Move the camera instantly to Thessaloniki with a zoom of 13.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(THESSALONIKI, 13));
        mMap.animateCamera(CameraUpdateFactory.zoomIn(), 5000, null);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        enableMyLocation();
    }

    private void checkEndOfGame() {
        for (int i = 0; i < myMarkers.size(); i++) {
            if (myMarkers.get(i).isCollected()) collected++;
        }
        if (collected == myMarkers.size())
            gameOver();           // || (SystemClock.uptimeMillis()-startTime) >= 72000
    }

    private void gameOver() {
        Toast.makeText(context, "Game Over", Toast.LENGTH_LONG).show();
        dataController.setScore(myUser.getId(), calculateFinalScore());
        finish();
    }

    private int calculateFinalScore() {
        return (int) (score + (7200000 - (startTime - SystemClock.uptimeMillis())) * 2);
    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

   /*  public boolean isGoogleApiClientConnected() {

        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    } */

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(loc)                 // Sets the center of the map to Mountain View
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        checkForMarker(location);
    }

    private void checkForMarker(Location location) {

        List<Location> locMarkers = new ArrayList<>();
        float volume = 0.5f;

        for(int i=0; i<myMarkers.size();i++) {
            locMarkers.add(new Location(String.valueOf(i)));
            locMarkers.get(i).setLatitude(myMarkers.get(i).getLatLng().latitude);
            locMarkers.get(i).setLongitude(myMarkers.get(i).getLatLng().longitude);
            float distance = Math.abs(location.distanceTo(locMarkers.get(i)));
            if(!myMarkers.get(i).isCollected()){
               /* if (distance < 50 && distance > 20.0) {
                    if(mediaPlayer.isPlaying())
                    {
                        volume = (float)(0.5+(10/distance));
                        mediaPlayer.setVolume(volume, volume);
                    } else {
                        soundDetect(myMarkers.get(i).getSound());
                    }
                   musicSrv.setSong(i);
                   musicSrv.playSong();
                } else */
                if (distance < 20.0) {
                    markers.get(i).setVisible(true);
                    //stopService(playIntent);
                    //musicSrv=null;
                    //mediaPlayer.stop();
                   // Toast.makeText(getApplicationContext(), "Distance less than 20 meters from marker.", Toast.LENGTH_LONG).show();
                }
            }
        }
        dataController.addLocation(location, myUser);
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current variables and classes.
        savedInstanceState.putInt("Score", score);
        savedInstanceState.putInt("Collected", collected);
        savedInstanceState.putLong("StartTime", startTime);
        savedInstanceState.putSerializable("User", myUser);

    }

    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved variables and classes.
        score = savedInstanceState.getInt("Score");
        collected = savedInstanceState.getInt("Collected");
        startTime = (savedInstanceState.getLong("StartTime"));
        myUser = (User) savedInstanceState.getSerializable("User");
    }

    public void soundDetect(String url) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

}
