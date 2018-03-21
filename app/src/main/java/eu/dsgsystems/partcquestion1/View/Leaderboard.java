package eu.dsgsystems.partcquestion1.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import eu.dsgsystems.partcquestion1.Controller.DataController;
import eu.dsgsystems.partcquestion1.Model.User;
import eu.dsgsystems.partcquestion1.R;

/**
 * Created by Dimitris on 21/3/2018.
 */

public class Leaderboard extends Activity {

    private DataController dataController = new DataController();
    private List<User> myUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        this.setTitle("Leaderboard");
        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = getLeadScorers();

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        TextView tv = new TextView(getApplicationContext());
        tv.setText("Leaderboard");
        tv.setTextColor(Color.RED);
        tv.setBackgroundColor(Color.YELLOW);
        listview.addHeaderView(tv);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
    }

    private String[] getLeadScorers() {
        String[] leadScorers = new String[10];
        myUsers = dataController.loadUsers();
        if(myUsers.size()>0) {
            for (int i = 0; i < myUsers.size(); i++) {
                Collections.sort(myUsers);
            }
            for (int i = 0; i < 10; i++) {
                leadScorers[i] = ("Name : " + myUsers.get(i).getName() + "   Score : " + myUsers.get(i).getScore());
            }
        } else {
            leadScorers[0] = "Name: Dimitris    Score: 150";
            leadScorers[1] = "Name: Lina        Score: 140";
            leadScorers[2] = "Name: Alex        Score: 138";
            leadScorers[3] = "Name: Ntina       Score: 130";
            leadScorers[4] = "Name: Kostas      Score: 100";
            for(int i=5; i<10; i++) {
                leadScorers[i] =  " ";
            }
        }
        return leadScorers;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
        String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}


