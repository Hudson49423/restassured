package com.octopusbeach.restassured;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.octopusbeach.restassured.model.Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by hudson on 6/14/15.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.list_item, null);
        }

        Item item = getItem(position);
        TextView title = (TextView) v.findViewById(R.id.item_title);
        TextView completed = (TextView) v.findViewById(R.id.last_completed_value);

        title.setText(item.getName());
        Calendar date = item.getDate();
        Calendar rightNow = Calendar.getInstance();
        if (date.get(Calendar.DAY_OF_YEAR) == rightNow.get(Calendar.DAY_OF_YEAR)
                && date.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR)) // Today
            completed.setText(" Today at " + new SimpleDateFormat("h:mm").format(date.getTime()));
        else if (date.get(Calendar.DAY_OF_YEAR) == (rightNow.get(Calendar.DAY_OF_YEAR) - 1)
                && date.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR))  // Yesterday
            completed.setText(" Yesterday at " + new SimpleDateFormat("h:mm").format(date.getTime()));
        else
            completed.setText(" " + new SimpleDateFormat("MMMM dd, h:mm a").format(date.getTime()));
        return v;
    }
}
