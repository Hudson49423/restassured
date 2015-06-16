package com.octopusbeach.restassured;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.octopusbeach.restassured.model.Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by hudson on 6/15/15.
 */
public class GridAdapter extends ArrayAdapter<Item> {
    private int[] colors;
    private Random rand;

    public GridAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        Resources res = context.getResources();
        colors = new int[]{
                res.getColor(R.color.cyan),
                res.getColor(R.color.yellow),
                res.getColor(R.color.teal),
                res.getColor(R.color.blue),
                res.getColor(R.color.grey),
                res.getColor(R.color.orange),
                res.getColor(R.color.indigo),
                res.getColor(R.color.pink),
                res.getColor(R.color.purple),
                res.getColor(R.color.red)
        };
        rand = new Random();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.grid_item, null);
        }

        Item item = getItem(position);
        TextView title = (TextView) v.findViewById(R.id.item_title);
        TextView completed = (TextView) v.findViewById(R.id.time);

        title.setText(item.getName());
        Calendar date = item.getDate();
        Calendar rightNow = Calendar.getInstance();
        if (date.get(Calendar.DAY_OF_YEAR) == rightNow.get(Calendar.DAY_OF_YEAR)
                && date.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR)) // Today
            completed.setText(" Today at " + new SimpleDateFormat("h:mm a").format(date.getTime()));
        else if (date.get(Calendar.DAY_OF_YEAR) == (rightNow.get(Calendar.DAY_OF_YEAR) - 1)
                && date.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR))  // Yesterday
            completed.setText(" Yesterday at " + new SimpleDateFormat("h:mm a").format(date.getTime()));
        else
            completed.setText(" " + new SimpleDateFormat("MMMM dd, h:mm a").format(date.getTime()));

        LayerDrawable bg = (LayerDrawable) v.findViewById(R.id.parent).getBackground();
        GradientDrawable bgShape = (GradientDrawable)bg.findDrawableByLayerId(R.id.background);
        bgShape.setColor(colors[rand.nextInt(colors.length)]);
        return v;
    }
}
