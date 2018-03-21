package eu.dsgsystems.partcquestion1.Controller;

import android.location.Location;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import eu.dsgsystems.partcquestion1.Model.MyMarker;
import eu.dsgsystems.partcquestion1.Model.Team;
import eu.dsgsystems.partcquestion1.Model.User;

/**
 * Created by Dimitris on 12/3/2018.
 */

public class DataController {
    private final FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = myDatabase.getReference();
    private DatabaseReference markersRef = myRef.child("Markers");
    private DatabaseReference teamsRef = myRef.child("Teams");
    private DatabaseReference usersRef = myRef.child("Users");
    private DatabaseReference locationsRef = myRef.child("Locations");


    public void addLocation(Location location, User user) {
        usersRef.child(user.getId()).child("Locations").child(String.valueOf(location.getTime())).setValue(String.valueOf(location));
        String key = locationsRef.push().getKey();
        locationsRef.child(key).setValue(String.valueOf(location));
    }

    public void addTeam(Team team) {
        String key = usersRef.push().getKey();
        team.setId(key);
        teamsRef.child(key).setValue(team);
    }

    public void addUser(User myUser) {
        String key = usersRef.push().getKey();
        myUser.setId(key);
        usersRef.child(key).setValue(myUser);
    }

    public List<User> loadUsers() {
        final List<User> users = new ArrayList<>();
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return users;
    }

    public List<MyMarker> loadMarkers() {
        final List<MyMarker> markers = new ArrayList<>();
        markersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot markerSnapshot : dataSnapshot.getChildren()){
                    MyMarker marker = markerSnapshot.getValue(MyMarker.class);
                    markers.add(marker);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return markers;
    }

    public void setScore(String userId, int score) {
        usersRef.child(userId).child("score").setValue(score);
    }

    public void putObject(User myUser, MyMarker myMarker) {
        usersRef.child(myUser.getId()).child("CollectedObjects").child(myMarker.getId()).setValue(myMarker);
    }

}
