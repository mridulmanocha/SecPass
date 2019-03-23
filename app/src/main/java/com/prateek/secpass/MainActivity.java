package com.prateek.secpass;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prateek.secpass.adapter.NoteAdapter;
import com.prateek.secpass.dao.Note;
import com.prateek.secpass.database.DatabaseHelper;
import com.prateek.secpass.drawerattr.SettingsActivity;
import com.prateek.secpass.drawerattr.TermsActivity;
import com.prateek.secpass.drawerattr.TrashActivity;
import com.prateek.secpass.splash_welcome.LogInActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SubMenu subMenu = null;
    private Timer timer;
    RecyclerView recyclerView;
    private ArrayList<Note> arrayList = new ArrayList<>();
    private DatabaseHelper dbhelper;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*Intent intent= getIntent();
        String title= getIntent().getExtras().getString("title");
        String mailid= getIntent().getExtras().getString("mailid");*/
        //getpasseddata();
        initobj();

    }

    private void initobj() {
        adapter = new NoteAdapter(this, arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        dbhelper = new DatabaseHelper(this);
        getDatafromSqlite();
    }

    private void getDatafromSqlite() {

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                arrayList.clear();
                arrayList.addAll(dbhelper.getAllData());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();

    }


    /*public void getpasseddata(){
        Intent intent= getIntent();
        String title= getIntent().getExtras().getString("title");
        String mailid= getIntent().getExtras().getString("mailid");
    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.labels) {
            final AlertDialog dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_category, null);
            dialogBuilder.setCancelable(true);

            final EditText editText = (EditText) dialogView.findViewById(R.id.addcat_edittext);
            Button button1 = (Button) dialogView.findViewById(R.id.add_cat_button);
            Button button2 = (Button) dialogView.findViewById(R.id.cancel_cat_button);

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogBuilder.dismiss();
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavigationView nav = findViewById(R.id.nav_view);
                    final Menu menu = nav.getMenu();
                    SharedPreferences catpreference = getSharedPreferences("CategoryItems", MODE_PRIVATE);
                    SharedPreferences.Editor editor = catpreference.edit();
                    editor.putString("label", editText.getText().toString());
                    editor.putString("cat", "Category");
                    editor.apply();


                    if (editText.getText().toString() == null || editText.getText().toString().length() <= 0) {
                        Toast.makeText(MainActivity.this, "Nothing to Add", Toast.LENGTH_SHORT).show();

                    } else {
                        if (subMenu == null && editText.getText().toString() != null) {
                            // subMenu = menu.addSubMenu(" Category");

                            SharedPreferences getsp = getSharedPreferences("CategoryItems", MODE_PRIVATE);
                            String cat = getsp.getString("label", "");
                            String label = getsp.getString("cat", "");
                            subMenu = menu.addSubMenu(label);
                            subMenu.add(cat);


                            Toast.makeText(MainActivity.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            SharedPreferences getshared = getSharedPreferences("CategoryItems", MODE_PRIVATE);
                            String categ = getshared.getString("label", "");
                            String label = getshared.getString("cat", "");
                            //subMenu= menu.addSubMenu(label);

                            subMenu.add(categ);

                            Toast.makeText(MainActivity.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    dialogBuilder.dismiss();
                }
            });

            dialogBuilder.setView(dialogView);
            dialogBuilder.show();

        } else if (id == R.id.trash) {
            startActivity(new Intent(MainActivity.this, TrashActivity.class));


        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));


        } else if (id == R.id.nav_feedbacks) {
            startActivity(new Intent(MainActivity.this, TermsActivity.class));

        } else if (id == R.id.nav_share) {
            Intent a = new Intent(Intent.ACTION_SEND);
            final String appPackageName = getApplicationContext().getPackageName();
            String AppLink = "";
            try {
                AppLink = "//link" + appPackageName;
            }//link
            catch (android.content.ActivityNotFoundException anfe) {

                AppLink = "//link" + appPackageName;
            }
            a.setType("text/link");
            String shareBody = "Hey!Please download the app from here." + "\n" + AppLink;
            String shareSub = "SecPass";
            a.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            a.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(a, "Share Using"));


        } else if (id == R.id.nav_send) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    protected void onPause() {
        super.onPause();

        timer = new Timer();
        Log.i("Main", "Invoking logout timer");
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.schedule(logoutTimeTask, 60000); //auto logout in 5 minutes
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            Log.i("Main", "cancel timer");
            timer = null;
        }
    }

    private class LogOutTimerTask extends TimerTask {

        @Override
        public void run() {

            //redirect user to login screen
            Intent i = new Intent(MainActivity.this, LogInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }*/
}
