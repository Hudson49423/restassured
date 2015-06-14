package com.octopusbeach.restassured;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.melnykov.fab.FloatingActionButton;
import com.octopusbeach.restassured.model.Item;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.left_drawer)
    ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    @InjectView(R.id.list)
    ListView list;


    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                toolbar.setTitle("Reminders");
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,
                getResources().getStringArray(R.array.nav_items)));

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

    @OnClick(R.id.fab)
    void newItem() {
        AlertDialog ad = new AlertDialog.Builder(HomeActivity.this)
                .setCancelable(true)
                .setNegativeButton("Cancel", null)
                .setView(R.layout.new_item)
                .setPositiveButton("Save", null)
                .show();


        final CheckBox repeatBox = (CheckBox) ad.findViewById(R.id.repeat_checkbox);
        ad.findViewById(R.id.repeat_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatBox.setChecked(!repeatBox.isChecked());
                System.out.println("test");
            }
        });
        final RelativeLayout rl = (RelativeLayout) ad.findViewById(R.id.remind_layout);
        Spinner daySpinner = (Spinner) ad.findViewById(R.id.spinner_day);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.date_items,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        Spinner timeSpinner = (Spinner) ad.findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.time_items,
                android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        final Button cancelReminder = (Button) ad.findViewById(R.id.cancel_reminder);
        cancelReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(View.GONE);
                cancelReminder.setVisibility(View.GONE);
            }
        });
        ad.findViewById(R.id.remind_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(View.VISIBLE);
                cancelReminder.setVisibility(View.VISIBLE);
            }
        });

    }
}