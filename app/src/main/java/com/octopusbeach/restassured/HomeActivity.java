package com.octopusbeach.restassured;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.octopusbeach.restassured.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                toolbar.setTitle("Home");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //TODO dummy data
        Item item = new Item("Turn Off The Stove", Calendar.getInstance());
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.DATE, -1);
        Item item1 = new Item("Turn Off The Stove", c2);
        Calendar c3 = Calendar.getInstance();
        c3.add(Calendar.DATE, -100);
        Item item2 = new Item("Turn Off The Stove", c3);

        ArrayList<Item> data = new ArrayList<>();
        data.add(item);
        data.add(item1);
        data.add(item2);

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(new ItemAdapter(this, R.id.list_item, data));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(list);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}