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

    public GridAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

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
        if (date != null) { // This item has been completed.
            Calendar rightNow = Calendar.getInstance();
            if (date.get(Calendar.DAY_OF_YEAR) == rightNow.get(Calendar.DAY_OF_YEAR)
                    && date.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR)) // Today
                completed.setText(" Today at " + new SimpleDateFormat("h:mm a").format(date.getTime()));
            else if (date.get(Calendar.DAY_OF_YEAR) == (rightNow.get(Calendar.DAY_OF_YEAR) - 1)
                    && date.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR))  // Yesterday
                completed.setText(" Yesterday at " + new SimpleDateFormat("h:mm a").format(date.getTime()));
            else
                completed.setText(" " + new SimpleDateFormat("MMMM dd, h:mm a").format(date.getTime()));
        } else { // This item has not yet been completed.
            completed.setText("Never");
        }

        LayerDrawable bg = (LayerDrawable) v.findViewById(R.id.parent).getBackground();
        GradientDrawable bgShape = (GradientDrawable) bg.findDrawableByLayerId(R.id.background);
        bgShape.setColor(item.getColor());
        return v;
    }
}
