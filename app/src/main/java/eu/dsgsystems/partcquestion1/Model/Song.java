package eu.dsgsystems.partcquestion1.Model;

/**
 * Created by Dimitris on 20/3/2018.
 */

public class Song {

    private int id;
    private String url;

    public Song(){

    }

    public Song(int id, String url){
        this.id = id;
        this.url=url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

