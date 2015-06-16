package com.octopusbeach.restassured;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.octopusbeach.restassured.model.Item;

import java.util.List;

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
        ((TextView)v.findViewById(R.id.item_title)).setText(item.getName());
        return v;
    }
}
