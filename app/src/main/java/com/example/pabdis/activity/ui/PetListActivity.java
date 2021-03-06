package com.example.pabdis.activity.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pabdis.R;
import com.example.pabdis.activity.helper.DatabaseHelper;
import com.example.pabdis.activity.helper.ListAdapter;
import com.example.pabdis.activity.helper.Owner;
import com.example.pabdis.activity.helper.Pet;
import com.example.pabdis.activity.helper.PetAdapter;
import com.example.pabdis.activity.helper.SessionManager;
import com.example.pabdis.activity.login.LoginActivity;
import com.example.pabdis.activity.updates.ListUpdateActivity;
import com.example.pabdis.activity.updates.PetVaccination;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseHelper myDB;
    ListView LISTVIEW;
    PetAdapter listAdapter;
    EditText searchView;
    Cursor cursor, curser2;
    Integer pos,stat;
    String update, petid,ownerid;
    private SessionManager session;
    ArrayList<Pet> PetList = new ArrayList<Pet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estbpetlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        myDB = new DatabaseHelper(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        LISTVIEW = findViewById(R.id.listView1);
        searchView = findViewById(R.id.searchEdt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        update = "update";


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                pos = null;
                ownerid= null;
                stat = null;
            } else {
                pos= extras.getInt("pos");
                ownerid= extras.getString("ownerid");
                stat= extras.getInt("stat");
            }
        } else {
            pos= (Integer) savedInstanceState.getSerializable("pos");
            ownerid= (String) savedInstanceState.getSerializable("ownerid");
            stat= (Integer) savedInstanceState.getSerializable("stat");
        }


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(pos != null) {
            LISTVIEW.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            LISTVIEW.post(new Runnable() {
                @Override
                public void run() {
                    LISTVIEW.requestFocus();
                    LISTVIEW.setSelection(pos);
//                    LISTVIEW.getChildAt(pos).setBackgroundColor(Color.GREEN);

                }
            });




        }




        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, long l) {

                final String code = listAdapter.getItem(position).getPetid();
                final String ownerid = listAdapter.getItem(position).getOwner_id();


                AlertDialog.Builder builder = new AlertDialog.Builder(PetListActivity.this);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete user?");



                builder.setPositiveButton("Update Pet Info", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        //  String code = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_2));

//                        Toast.makeText(PetActivity.this, "Check your input!"+ code, Toast.LENGTH_SHORT).show();

                        LISTVIEW.setSelection(position);
                        view.setBackgroundColor(Color.BLUE);
                        Intent i = new Intent(PetListActivity.this, VaccinationActivity.class);
                        i.putExtra("petid", code);
                        i.putExtra("position", position);
                        i.putExtra("ownerid", ownerid);
                        i.putExtra("add", update);
                        i.putExtra("stat", stat);
                        startActivity(i);



                    }
                });
                builder.setNegativeButton("Update Pet Vaccination Info", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(PetListActivity.this, PetVaccination.class);
                        i.putExtra("petid", code);
                        i.putExtra("position", position);
                        i.putExtra("ownerid", ownerid);
                        i.putExtra("add", update);
                        i.putExtra("stat", stat);
                        startActivity(i);

                    }
                });

                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Build an AlertDialog
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PetListActivity.this);

                        // Set a title for alert dialog
                        builder.setTitle("DELETE.");

                        // Ask the final question
                        builder.setMessage("Do you want to delete the data?");

                        // Set click listener for alert dialog buttons
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        // User clicked the Yes button


                                        myDB.deletePet(code);
                                        Toast.makeText(getApplicationContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(PetListActivity.this, PetActivity.class);
                                        i.putExtra("ownerid", code);
                                        i.putExtra("stat", stat);
                                        startActivity(i);

                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        // User clicked the No button


                                }
                            }
                        };

                        // Set the alert dialog yes button click listener
                        builder.setPositiveButton("Yes", dialogClickListener);

                        // Set the alert dialog no button click listener
                        builder.setNegativeButton("No",dialogClickListener);

                        android.app.AlertDialog dialog2 = builder.create();
                        // Display the alert dialog on interface
                        dialog2.show();
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata();
        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        SQLiteDatabase sqLiteDatabase = myDB.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT pvet_pet.petname, pvet_pet.species, pvet_pet.pet_id ,pvet_pet.birthday," +
                "pvet_pet.color_marking,pvet_pet.created_at, pvet_pet.status," +
                "pvet_owner.r_lname, pvet_owner.r_fname, pvet_owner.contact_no, pvet_owner.owner_info, pvet_owner.owner_id " +
                "FROM pvet_pet " +
                "INNER JOIN pvet_owner  on pvet_pet.owner_id = pvet_owner.owner_id " +
                "WHERE pvet_pet.pet_id NOT NULL AND pvet_pet.pet_id = '"+petid+"'", null);
//        curser2 = sqLiteDatabase.rawQuery("SELECT * FROM pvet_pet_vaccination WHERE ID = (SELECT MAX(ID) FROM pvet_pet_vaccination WHERE pet_id = '"+petid+"')", null);

        Pet pet;
        PetList = new ArrayList<Pet>();
        if (cursor.moveToFirst() && curser2.moveToFirst()) {
            do {
                String owner_id = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_3)));
                String petid = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_12)));
                String id = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_1)));
                String petname = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.OWNERCOL_4)));
                if (owner_id.equals(null)) {
                    owner_id = "";
                }
                String respondent = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.OWNERCOL_5))) + ", " + (cursor.getString(cursor.getColumnIndex(DatabaseHelper.OWNERCOL_6)));
                String specie = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_4)));
                String breed = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_5)));
                String sex = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.OWNERCOL_11))) + ", " + (cursor.getString(cursor.getColumnIndex(DatabaseHelper.OWNERCOL_10))) + ", " + (cursor.getString(cursor.getColumnIndex(DatabaseHelper.OWNERCOL_9)));
                String birth = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_8)));
                String color = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_9)));
                String created_at = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_15));
                String lastvacc = curser2.getString(cursor.getColumnIndex(DatabaseHelper.VACC_DATE_3));
                String pet_latitude = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_17)));
                String pet_longitude = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_18)));

                String owner_num = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_18)));
                String pet_stat = (cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCCOL_18)));
                pet = new Pet(id, owner_id, petid, respondent, petname, specie, breed, sex, birth, color, created_at,lastvacc, pet_latitude, pet_longitude, pet_stat, owner_num);
                PetList.add(pet);
            } while (cursor.moveToNext() && curser2.moveToNext());

            listAdapter = new PetAdapter(PetListActivity.this, R.layout.items_pet, PetList);
            LISTVIEW.setAdapter(listAdapter);
            cursor.close();
            curser2.close();

        }
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
            finish();
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            finish();
            Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_list_owner) {
            finish();
            Intent intent=new Intent(getApplicationContext(), OwnerActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_list_pet) {
            finish();
            Intent intent=new Intent(getApplicationContext(), PetActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            session.logoutUser();
            Intent i = new Intent(PetListActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
