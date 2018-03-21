package eu.dsgsystems.partcquestion1.View;

import android.app.ListActivity;
import android.os.Bundle;

import eu.dsgsystems.partcquestion1.Model.User;

/**
 * Created by Dimitris on 21/3/2018.
 */

public class MyListActivity extends ListActivity {

    private User myUser;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myUser = (User) extras.get("User");
        }
        String[] values = new String[7];
        if (myUser.getCollectedObjects() != null) {
            for (int i = 0; i < myUser.getCollectedObjects().size(); i++) {
                values[i] = myUser.getCollectedObjects().get(i).getName();
            }
        } else {
            values[0] = "Cow";
            values[1] = "Cat";
            values[2] = "Dog";
            values[3] = "Horse";
            values[4] = "Pig";
            values[5] = "Chicken";
            values[6] = "Sheep";
        }
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
        setListAdapter(adapter);
    }

}


