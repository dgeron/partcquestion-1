package eu.dsgsystems.partcquestion1.Model;

import java.util.ArrayList;

/**
 * Created by Dimitris on 12/3/2018.
 */

public class Team {
    String id;
    String name;
    String date;
    ArrayList<User> users = new ArrayList<User>();

    public Team(String name, String date){
        this.name = name;
        this.date = date;
    }

    public Team(String id, String name, String date, ArrayList<User> users){
        this.id = id;
        this.name = name;
        this.date = date;
        this.users = users;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }
}
