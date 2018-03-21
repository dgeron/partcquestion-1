package eu.dsgsystems.partcquestion1.Model;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Dimitris on 12/3/2018.
 */

public class MyMarker {
    String id;
    String name;
    LatLng latLng;
    String sound;
    String video;
    int points;
    private boolean collected;

    public MyMarker() {

    }

    public MyMarker(LatLng loc, String name, int points) {
        this.name = name;
        this.latLng = loc;
        this.points = points;
        this.collected = false;
    }

    public MyMarker(LatLng loc, String name, int points, String sound) {
        this.name = name;
        this.latLng = loc;
        this.sound = sound;
        this.points = points;
        this.collected = false;
    }

    public MyMarker(LatLng loc, String name, int points, String sound, String video) {
        this.name = name;
        this.latLng = loc;
        this.sound = sound;
        this.video = video;
        this.points = points;
        this.collected = false;
    }

    public MyMarker(String id, LatLng loc, String name, int points, String sound, String video) {
        this.id = id;
        this.name = name;
        this.latLng = loc;
        this.sound = sound;
        this.video = video;
        this.points = points;
        this.collected = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() { return latLng; }

    public void setLatLng(LatLng latLng) { this.latLng = latLng; }

    public String getSound() { return sound; }

    public void setSound(String sound) { this.sound = sound; }

    public String getVideo() { return video; }

    public void setVideo(String video) { this.video = video; }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCollected() { return collected;}

    public void setCollected(boolean collected) { this.collected = collected; }

}
