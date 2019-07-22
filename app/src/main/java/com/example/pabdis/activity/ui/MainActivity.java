package com.example.pabdis.activity.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Pair;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pabdis.BuildConfig;
import com.example.pabdis.R;
import com.example.pabdis.activity.helper.DatabaseHelper;
import com.example.pabdis.activity.survey.CarabaoActivity;
import com.example.pabdis.activity.survey.CattleActivity;
import com.example.pabdis.activity.survey.ChickenActivity;
import com.example.pabdis.activity.survey.FisheryActivity;
import com.example.pabdis.activity.survey.GoatActivity;
import com.example.pabdis.activity.survey.HouseholdActivity;
import com.example.pabdis.activity.survey.OtherActivity;
import com.example.pabdis.activity.survey.SwineActivity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    EditText lastname, lastname2, firstname2, firstname, dateSurvey, houseno, contact, edtEstab, edtCoop,edtTotalHousehold;
    TextView tvLatitude, tvLongitude;
    Double lang, longi;
    Spinner muni, brgy, ownertype;
    public static String tvLongi;
    public static String tvLati;
    Integer position, year, ctr;
    DatabaseHelper myDB;
    Context mContext;
    Character first;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String ownerid, petid, ownerinfo, add, addrsss;
    Button btndate, proceedSurvey;
    LocationManager locationManager;
    Double latitude, longitude;
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    ArrayAdapter<CharSequence> munici, brgylt, brgy_kib, brgy_it, brgy_bug, brgy_kab, brgy_sab, brgy_man, brgy_bak, brgy_tba, brgy_tbl, brgy_at, brgy_bok, brgy_kap;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        lastname = findViewById(R.id.edtLastName);
        firstname = findViewById(R.id.edtFirstName);
        lastname2 = findViewById(R.id.edtLastName1);
        firstname2 = findViewById(R.id.edtFirstName1);
        contact = findViewById(R.id.edtContact);
        tvLongitude = findViewById(R.id.tv_longitude);
        tvLatitude = findViewById(R.id.tv_latitude);
        edtTotalHousehold = findViewById(R.id.edtTotalHousehold);
        houseno = findViewById(R.id.edtHouseNo);
        edtCoop = findViewById(R.id.edtCoop);
        edtEstab = findViewById(R.id.edtEstab);
        lastname.setVisibility(View.GONE);
        firstname.setVisibility(View.GONE);
        edtCoop.setVisibility(View.GONE);
        edtEstab.setVisibility(View.GONE);
        CheckPermission();






        proceedSurvey = findViewById(R.id.btnProceedSurvey);
//        dateSurvey.setEnabled(false);
        muni = findViewById(R.id.muni);
        brgy = findViewById(R.id.brgy);
        ownertype = findViewById(R.id.ownertype);



        munici = ArrayAdapter.createFromResource(this, R.array.muni, R.layout.support_simple_spinner_dropdown_item);
        brgylt = ArrayAdapter.createFromResource(this, R.array.brgy_lt, R.layout.support_simple_spinner_dropdown_item);
        brgy_at = ArrayAdapter.createFromResource(this, R.array.brgy_at, R.layout.support_simple_spinner_dropdown_item);
        brgy_bak = ArrayAdapter.createFromResource(this, R.array.brgy_bak, R.layout.support_simple_spinner_dropdown_item);
        brgy_bok = ArrayAdapter.createFromResource(this, R.array.brgy_bok, R.layout.support_simple_spinner_dropdown_item);
        brgy_bug = ArrayAdapter.createFromResource(this, R.array.brgy_bug, R.layout.support_simple_spinner_dropdown_item);
        brgy_it = ArrayAdapter.createFromResource(this, R.array.brgy_it, R.layout.support_simple_spinner_dropdown_item);
        brgy_kab = ArrayAdapter.createFromResource(this, R.array.brgy_kab, R.layout.support_simple_spinner_dropdown_item);
        brgy_kap = ArrayAdapter.createFromResource(this, R.array.brgy_kap, R.layout.support_simple_spinner_dropdown_item);
        brgy_kib = ArrayAdapter.createFromResource(this, R.array.brgy_kib, R.layout.support_simple_spinner_dropdown_item);
        brgy_man = ArrayAdapter.createFromResource(this, R.array.brgy_man, R.layout.support_simple_spinner_dropdown_item);
        brgy_sab = ArrayAdapter.createFromResource(this, R.array.brgy_sab, R.layout.support_simple_spinner_dropdown_item);
        brgy_tba = ArrayAdapter.createFromResource(this, R.array.brgy_tba, R.layout.support_simple_spinner_dropdown_item);
        brgy_tbl = ArrayAdapter.createFromResource(this, R.array.brgy_tbl, R.layout.support_simple_spinner_dropdown_item);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);




        ownertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem)
                {
                    case "Household":
                        lastname.setVisibility(View.VISIBLE);
                        firstname.setVisibility(View.VISIBLE);
                        edtTotalHousehold.setVisibility(View.VISIBLE);
                        edtCoop.setVisibility(View.GONE);
                        edtEstab.setVisibility(View.GONE);
                        final String hlname = firstname.getText().toString();
                        final String hfname  = firstname.getText().toString();
                        ownerinfo = hlname + ", " + hfname;
//                        Toast.makeText(MainActivity.this, ""+ selectedItem + ownerinfo , Toast.LENGTH_SHORT).show();

                        break;
                    case "Cooperative":
                        edtCoop.setVisibility(View.VISIBLE);
                        lastname.setVisibility(View.GONE);
                        firstname.setVisibility(View.GONE);
                        edtEstab.setVisibility(View.GONE);
                        edtTotalHousehold.setVisibility(View.GONE);
                        ownerinfo =  edtEstab.getText().toString();
//                        Toast.makeText(MainActivity.this, ""+ selectedItem  , Toast.LENGTH_SHORT).show();

                        break;
                    case "Establishment":
                        edtEstab.setVisibility(View.VISIBLE);
                        lastname.setVisibility(View.GONE);
                        firstname.setVisibility(View.GONE);
                        edtCoop.setVisibility(View.GONE);
                        edtTotalHousehold.setVisibility(View.GONE);
                        ownerinfo = edtCoop.getText().toString();


//                        Toast.makeText(MainActivity.this, ""+ selectedItem  , Toast.LENGTH_SHORT).show();

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        muni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();


                switch (selectedItem) {
                    case "La Trinidad":
                        brgy.setAdapter(brgylt);
                        petid = String.valueOf((selectedItem.charAt(0))) + selectedItem.charAt(3);
                        break;
                    case "Itogon":
                        brgy.setAdapter(brgy_it);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Buguias":
                        brgy.setAdapter(brgy_bug);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Kabayan":
                        brgy.setAdapter(brgy_kab);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Sablan":
                        brgy.setAdapter(brgy_sab);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Mankayan":
                        brgy.setAdapter(brgy_man);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Kapangan":
                        brgy.setAdapter(brgy_kap);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Bokod":
                        brgy.setAdapter(brgy_bok);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Atok":
                        brgy.setAdapter(brgy_at);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Kibungan":
                        brgy.setAdapter(brgy_kib);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Tublay":
                        brgy.setAdapter(brgy_tbl);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Tuba":
                        brgy.setAdapter(brgy_tba);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                    case "Bakun":
                        brgy.setAdapter(brgy_bak);
                        petid = String.valueOf(selectedItem.charAt(0));
                        break;
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proceedSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String rfname = firstname2.getText().toString();
                final String rlname = lastname2.getText().toString();
                final String num = contact.getText().toString();
                final String house = houseno.getText().toString();
                final String mun = muni.getSelectedItem().toString();
                final String brg = brgy.getSelectedItem().toString();
                final String ownert = ownertype.getSelectedItem().toString();
                final String member;

                final String ownerin;
                final String contact;


                if(num.equals(""))
                {
                    contact = "N/A";

                }else{
                    contact = num;
                }

                if(ownert.equals("Household"))
                {

                    ownerin = lastname.getText().toString() + "," + firstname.getText().toString();
                    member =  edtTotalHousehold.getText().toString();

                }else if(ownert.equals("Establishment")){
                    ownerin = edtEstab.getText().toString();
                    member = "";

                }else{

                    ownerin = edtCoop.getText().toString();
                    member = "";
                }



                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                final String created_at = format1.format(cal.getTime());

                String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < 5) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }

                final String end = salt.toString();

                final String lat;
                final String longi;

                if(tvLati == null){

                    lat = "N/A";
                }else{
                    lat = tvLati;
                }

                if(tvLongi == null)
                {
                    longi = "N/A";

                }else{

                    longi = tvLongi;
                }

                position = brgy.getSelectedItemPosition();
                addrsss = "";
                position++;

                first = mun.charAt(0);
                year = Calendar.getInstance().get(Calendar.YEAR);
                ctr = myDB.getData(mun);
                ctr++;

                ownerid = first.toString() + position.toString() + year.toString() + "-" + end;





//                petid = first.toString() + position.toString() + year.toString();

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // Set a title for alert dialog
                builder.setTitle("There's no going back.");

                // Ask the final question
                builder.setMessage("You're OWNER ID is " + ownerid + ".  Are you sure you want to save the data?");

                // Set click listener for alert dialog buttons
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // User clicked the Yes button

                                if (rfname.equals("") || rlname.equals("") || house.equals("") || ownerin.equals("") ) {
                                    Toast.makeText(MainActivity.this, "Check your input!" + created_at, Toast.LENGTH_SHORT).show();

                                }else{

                                    try {
                                        myDB.addOwner(ownerid.trim(),ownert.trim(),ownerin.trim(),rfname.trim(),rlname.trim(),member.trim(),contact.trim(), mun.trim(), brg.trim(),house.trim(), lat.trim(), longi.trim(),addrsss.trim(),created_at);
                                        Toast.makeText(MainActivity.this, "Success!" , Toast.LENGTH_LONG).show();
//                                        showDebugDBAddressLogToast(MainActivity.this);

                                        if(ownert.equals("Establishment"))
                                        {
                                            Intent intent = new Intent(getApplicationContext(), VaccinationActivity.class);
                                            intent.putExtra("ownerid", ownerid);
                                            intent.putExtra("petid", petid);
                                            startActivity(intent);

                                        }else{
                                            Intent intent = new Intent(getApplicationContext(), SwineActivity.class);
                                            intent.putExtra("ownerid", ownerid);
                                            intent.putExtra("petid", petid);
                                            startActivity(intent);
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // User clicked the No button
                                break;
                        }
                    }
                };

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", dialogClickListener);

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No",dialogClickListener);

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();


            }
        });
    }


    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
    }


    /* Request updates at startup */
    @Override
    public void onResume() {
        super.onResume();
        getLocation();
//        isLocationEnabled();
    }


    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }




    @Override
    public void onBackPressed() {
         findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_survey) {
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_map) {

            Intent intent=new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_list_owner) {

            Intent intent=new Intent(getApplicationContext(), OwnerActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_list_pet) {

            Intent intent=new Intent(getApplicationContext(), PetActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void isLocationEnabled() {

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
        else{
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Confirm Location");
            alertDialog.setMessage("Your Location is enabled, please enjoy");
            alertDialog.setNegativeButton("Back to interface",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
    }




    @Override
    public void onLocationChanged(Location location) {
//
//
        tvLongi = String.valueOf(location.getLongitude());
        longi = location.getLongitude();
        tvLati= String.valueOf(location.getLatitude());
        lang = location.getLatitude();
//
//        // Setting Current Longitude
        tvLongitude.setText("Longitude:" + tvLongi);
//        // Setting Current Latitude
        tvLatitude.setText("Latitude:" + tvLati);
//
//        Toast.makeText(this, "Your location is set!" + longi,
//                Toast.LENGTH_SHORT).show();

//         latitude = location.getLatitude();
//         longitude = location.getLongitude();
//
        try {
            List<Address> addresses = geocoder.getFromLocation(lang, longi, 1);
            add = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addrsss = add;

        String msg="New Latitude: "+tvLati + "New Longitude: "+tvLongi;

        Toast.makeText(this, "Your location is set!" + msg,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider!" + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

    }
}
