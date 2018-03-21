package eu.dsgsystems.partcquestion1.View;

/**
 * Created by Dimitris on 21/3/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import eu.dsgsystems.partcquestion1.R;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        // Change the icon for Windows and iPhone
        String s = values[position].toLowerCase();
        if (s.startsWith("cow")) {
            imageView.setImageResource(R.drawable.cow);
        } else if (s.startsWith("cat")){
            imageView.setImageResource(R.drawable.cat);
        }  else if (s.startsWith("dog")){
            imageView.setImageResource(R.drawable.dog);
        }  else if (s.startsWith("horse")){
            imageView.setImageResource(R.drawable.horse);
        }  else if (s.startsWith("pig")){
            imageView.setImageResource(R.drawable.pig);
        }  else if (s.startsWith("chicken")){
            imageView.setImageResource(R.drawable.chicken);
        }  else if (s.startsWith("sheep")){
            imageView.setImageResource(R.drawable.sheep);
        }

        return rowView;
    }
}