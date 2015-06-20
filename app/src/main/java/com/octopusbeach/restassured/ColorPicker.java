package com.octopusbeach.restassured;

import android.content.Context;
import android.content.res.Resources;

import java.util.Random;

/**
 * Created by hudson on 6/20/15.
 */
public class ColorPicker {

    public static int getColor(Context context) {
        Resources res = context.getResources();
        int[] colors = new int[]{
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
        Random rand = new Random();
        return colors[rand.nextInt(colors.length)];
    }
}
