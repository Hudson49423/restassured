package com.octopusbeach.restassured;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.OnClickWrapper;
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
    @InjectView(R.id.grid)
    GridView gridView;


    @InjectView(R.id.fab)
    FloatingActionButton fab;

    private ArrayList<Item> data;
    private GridAdapter adapter;
    private Item deletedItem; // Holds the last delted Item so that it can be readded.
    private int deletedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        toolbar.setTitle("Reminders");
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
        data = new DBHelper(this).getItems();
        adapter = new GridAdapter(this, R.layout.grid_item, data);
        gridView.setAdapter(adapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deletedItem = data.get(i);
                DBHelper db = new DBHelper(HomeActivity.this);
                db.deleteItem(deletedItem);
                adapter.clear();
                data = db.getItems();
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
                SuperActivityToast superActivityToast = new SuperActivityToast(HomeActivity.this, SuperToast.Type.BUTTON);
                superActivityToast.setDuration(SuperToast.Duration.EXTRA_LONG);
                superActivityToast.setText("Item Deleted.");
                superActivityToast.setButtonIcon(SuperToast.Icon.Dark.UNDO, "UNDO");
                superActivityToast.setOnClickWrapper(onClickWrapper);
                superActivityToast.show();
                return false;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                data.get(i).setDate(Calendar.getInstance());
                adapter.notifyDataSetChanged();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(gridView);

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
        final View v = getLayoutInflater().inflate(R.layout.new_item, null);
        final CheckBox repeatBox = (CheckBox) v.findViewById(R.id.repeat_checkbox);

        v.findViewById(R.id.repeat_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatBox.setChecked(!repeatBox.isChecked());
            }
        });

        final RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.remind_layout);
        Spinner daySpinner = (Spinner) v.findViewById(R.id.spinner_day);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.date_items,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerAdapter);
        Spinner timeSpinner = (Spinner) v.findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.time_items,
                android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        final Button cancelReminder = (Button) v.findViewById(R.id.cancel_reminder);
        cancelReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(View.GONE);
                cancelReminder.setVisibility(View.GONE);
            }
        });
        v.findViewById(R.id.remind_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(View.VISIBLE);
                cancelReminder.setVisibility(View.VISIBLE);
            }
        });
        new AlertDialog.Builder(HomeActivity.this)
                .setCancelable(true)
                .setNegativeButton("Cancel", null)
                .setView(v)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (rl.getVisibility() == View.VISIBLE) {
                            // TODO this item is reminding.
                        } else {
                            Item newItem = new Item(((TextView) v.findViewById(R.id.new_item_title))
                                    .getText().toString(), ColorPicker.getColor(HomeActivity.this));
                            DBHelper db = new DBHelper(HomeActivity.this);
                            db.addItem(newItem);
                            data = db.getItems();
                            adapter.clear();
                            adapter.addAll(data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .show();
    }

    OnClickWrapper onClickWrapper = new OnClickWrapper("superactivitytoast", new SuperToast.OnClickListener() {

        @Override
        public void onClick(View view, Parcelable token) {
            if (deletedItem != null) {
                DBHelper db = new DBHelper(HomeActivity.this);
                db.addItem(deletedItem);
                adapter.clear();
                data = db.getItems();
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }

    });
}