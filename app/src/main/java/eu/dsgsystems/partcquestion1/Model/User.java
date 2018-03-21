package eu.dsgsystems.partcquestion1.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dimitris on 12/3/2018.
 */

public class User implements Comparable<User>, Serializable {
    String id;
    String name;
    String address;
    String email;
    String password;
    String mobile;
    String date;
    long score;
    ArrayList<MyMarker> collectedObjects;

    public User(){

    }

    public User(String name, String address, String email, String mobile, String password, String date, ArrayList<MyMarker> collectedObjects) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.collectedObjects = collectedObjects;
    }

    public User(String id, String name, String address, String email, String mobile, String password, String date, long score, ArrayList<MyMarker> collectedObjects) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.date = date;
        this.score = score;
        this.collectedObjects = collectedObjects;
    }

    public User (String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date =  date;
        this.score=0;
    }

    public User (String id, String name, String address, String email, String mobile, String password, String date) {
        this.id = id;
        this.name = name;
        this.address =address;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.date =  date;
        this.score=0;
    }

    public User (String name, String address, String email, String mobile, String password, String date) {
        this.name = name;
        this.address =address;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.date =  date;
        this.score=0;
    }

    public User (String id, String name, String email, String mobile, String password, String date, long score, ArrayList<MyMarker> collectedObjects) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.date = date;
        this.score = score;
        this.collectedObjects = collectedObjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getMobile() { return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public ArrayList<MyMarker> getCollectedObjects() {
        return collectedObjects;
    }

    public void setCollectedObjects(ArrayList<MyMarker> collectedObjects) { this.collectedObjects = collectedObjects; }

    public void addCollectedObject(MyMarker collectedObject) { collectedObjects.add(collectedObject); }

    public void removeCollectedObject(MyMarker collectedObject) { collectedObjects.remove(collectedObject); }

    public void setId(String id) { this.id = id; }

    public String getId() { return this.id; }

    public int compareTo(User o)
    {
        return (int) (score - o.score);
    }

}
