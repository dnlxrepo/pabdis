package com.example.pabdis.activity.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pabdis.R;
import com.example.pabdis.activity.helper.DatabaseHelper;
import com.example.pabdis.activity.helper.SessionManager;
import com.example.pabdis.activity.sign.SignatureActivity;
import com.example.pabdis.activity.survey.CattleActivity;
import com.example.pabdis.activity.survey.HouseholdActivity;
import com.example.pabdis.activity.updates.ListUpdateActivity;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class VaccinationActivity extends AppCompatActivity implements LocationListener {


    EditText otherbreed, othercolormark, txtpetname, txtdistinguish,txtsourceplace,txtxDateVacc;
    TextView dateSurvey,txtAge,strngbreed,txtvaccinatedby2, txtv4,txtstatus,txtDateStatus;
    Button btndate, chooseImg, btnVacc, dateVacc, btnUpdate,btnDateStatus, btnRefresh;
    FloatingActionButton skip;
    final Calendar myCalendar = Calendar.getInstance();
    TableRow tbl1;
    Double lang, longi;
    Double latitude, longitude;
    EditText tvLatitude, tvLongitude;

    String lat;
    String longit;
    ImageView imgView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public  static final int RequestPermissionCode  = 1 ;
    Spinner txtbreed, txtGender, txtSpecie, txtColorMark, txtvaccinatedby,txtsource, pet_status,spstatus;
    DatabaseHelper myDB;
    CheckBox cb;
    Context mContext;
    String age,ownerid, petid, vacc, update, status, pet,add, petstat;
    Integer pos, stat, ctr;
    Character first;
    private SessionManager session;
    ArrayList<String> mylist2 = new ArrayList<String>();
    ArrayList<String> mylistup = new ArrayList<String>();
    ArrayAdapter<CharSequence> species, breedsd,breedsc, sex,sources,colormarkings, vaccinatedby;
    LocationManager locationManager;
    public static String tvLongi;
    public static String tvLati;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacc);


        add = "add";

        myDB = new DatabaseHelper(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        btnRefresh = findViewById(R.id.btnRefresh2);
        dateSurvey = findViewById(R.id.txtdatesurvey);
        btndate = findViewById(R.id.btnDate);
        btnVacc = findViewById(R.id.btnVacc);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setVisibility(View.GONE);
        txtvaccinatedby2= findViewById(R.id.txtvaccinatedby2);
        txtv4 = findViewById(R.id.textView4);
        dateVacc = findViewById(R.id.btnDateVacc);
//        pet_status = findViewById(R.id.spstatus);
//        pet_status.setVisibility(View.GONE);
        tbl1 = findViewById(R.id.tableRow4);

        txtstatus =  findViewById(R.id.txtstatus);
        spstatus =  findViewById(R.id.spstatus);
        btnDateStatus =  findViewById(R.id.btnDateStatus);
        txtDateStatus =  findViewById(R.id.txtDateStatus);

        txtstatus.setVisibility(View.GONE);
        spstatus.setVisibility(View.GONE);
        btnDateStatus.setVisibility(View.GONE);
        txtDateStatus.setVisibility(View.GONE);


        txtsourceplace = findViewById(R.id.txtsourceplace);
        txtsource = findViewById(R.id.txtsource);
        chooseImg = findViewById(R.id.btnChoose);
        imgView = findViewById(R.id.imgPet);
        txtpetname = findViewById(R.id.txtpetname);
        txtbreed = findViewById(R.id.txtbreed);
        txtGender = findViewById(R.id.txtgender);
        txtSpecie = findViewById(R.id.txtspecies);
        txtColorMark = findViewById(R.id.txtcolormark);
        otherbreed = findViewById(R.id.txtbreedother);
        othercolormark = findViewById(R.id.txtcolorother);
        tvLongitude = findViewById(R.id.tv_longitude2);
        tvLatitude = findViewById(R.id.tv_latitude2);
        txtxDateVacc = findViewById(R.id.txtxDateVacc);
        txtvaccinatedby = findViewById(R.id.txtvaccinatedby);

        strngbreed = findViewById(R.id.strngbreed);
        txtAge = findViewById(R.id.txtAge);
        skip = findViewById(R.id.fab);
        txtdistinguish = findViewById(R.id.txtdistinguish);
        otherbreed.setVisibility(View.GONE);
        othercolormark.setVisibility(View.GONE);




        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ownerid= null;
                petid = null;
                status = null;
                pos = null;
                stat = 0;
            } else {
                ownerid= extras.getString("ownerid");
                petid= extras.getString("petid");
                update= extras.getString("update");
                status= extras.getString("add");
                pos= extras.getInt("position");
                stat= extras.getInt("stat");

            }
        } else {
            ownerid= (String) savedInstanceState.getSerializable("ownerid");
            petid = (String) savedInstanceState.getSerializable("petid");
            update = (String) savedInstanceState.getSerializable("update");
            status = (String) savedInstanceState.getSerializable("add");
            pos = (Integer) savedInstanceState.getSerializable("position");
            stat = (Integer) savedInstanceState.getSerializable("stat");



        }

        if(stat == 1)
        {
            dateVacc.setVisibility(View.GONE);
            txtvaccinatedby.setVisibility(View.GONE);
            txtvaccinatedby2.setVisibility(View.GONE);



        }

        Toast.makeText(VaccinationActivity.this, "Success!"+ stat , Toast.LENGTH_LONG).show();



        Cursor rs = myDB.getVacc(petid);
        rs.moveToFirst();

        if(rs.getCount() > 0 && status.equals("update")) {







            txtbreed.setEnabled(false);
            txtGender.setEnabled(false);
            txtSpecie.setEnabled(false);


            txtvaccinatedby.setVisibility(View.GONE);
            dateVacc.setVisibility(View.GONE);
            tbl1.setVisibility(View.GONE);
            txtvaccinatedby2.setVisibility(View.GONE);
            txtv4.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnVacc.setVisibility(View.GONE);

            txtstatus.setVisibility(View.VISIBLE);
            spstatus.setVisibility(View.VISIBLE);


//            String id = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_1));
//            String owner = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_3));
            String petname =  rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_4));
            String specie = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_5));
            String breed = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_6));
            String gender = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_7));

            String birth = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_8));
            String color = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_9));
            String feature = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_10));
            final String srcs = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_11));
//            final String petid = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_12));

            String latitude = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_17));
            String longitude = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_18));

            vaccinatedby = ArrayAdapter.createFromResource(this, R.array.pet_status, R.layout.support_simple_spinner_dropdown_item);


            String status = rs.getString(rs.getColumnIndex(DatabaseHelper.VACCCOL_13));
            status = status.replace("[", "");
            mylist2 = new ArrayList<String>(Arrays.asList(status.split(",")));

//            Toast.makeText(VaccinationActivity.this, "Check your input!" + mylist2, Toast.LENGTH_SHORT).show();


//             if(!mylist2.isEmpty())
//             {
//                 txtstatus.setText(mylist2.get(0));
//             }else{
//                 txtstatus.setText("");
//             }


            tvLongitude.setText(longitude);
            tvLatitude.setText(latitude);







            final DatePickerDialog.OnDateSetListener date5 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String myFormat = "MM/dd/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    txtDateStatus.setText(sdf.format(myCalendar.getTime()));
                    if(mylistup.size() > 1)
                    {
//                        mylistup.remove(1);
                        mylistup.add(txtDateStatus.getText().toString());
                    }else{
                        mylistup.add(txtDateStatus.getText().toString());
                    }




                }

            };


            btnDateStatus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    new DatePickerDialog(VaccinationActivity.this, date5, myCalendar
//                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                    DatePickerDialog dialog = new DatePickerDialog(VaccinationActivity.this, date5, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMaxDate(new Date().getTime());
                    dialog.show();

                }

            });

            if(status.equals("alive") && spstatus.getSelectedItem().toString().equals("Alive")) {



                btnDateStatus.setVisibility(View.GONE);
                txtDateStatus.setVisibility(View.GONE);


                    spstatus.setAdapter(vaccinatedby);
                    int spinnerPosition2 = vaccinatedby.getPosition("Alive");
                    spstatus.setSelection(spinnerPosition2);

            }else {

                status = status.replace("[", "");
                status = status.replace("]", "");
                status = status.replace(", ", ",");


                mylist2 = new ArrayList<String>(Arrays.asList(status.split(",")));


                if (mylist2.contains("dead")) {

                    spstatus.setAdapter(vaccinatedby);
                    int spinnerPosition2 = vaccinatedby.getPosition("Dead");
                    spstatus.setSelection(spinnerPosition2);

                    if(mylist2.size() > 1){
                        txtDateStatus.setText(mylist2.get(1));
                    }else{
                        txtDateStatus.setText("");
                    }

//                    Toast.makeText(getApplicationContext(), "" + mylist2 , Toast.LENGTH_SHORT).show();

                } else if (mylist2.contains("lost")) {

                    spstatus.setAdapter(vaccinatedby);
                    int spinnerPosition2 = vaccinatedby.getPosition("Lost");
                    spstatus.setSelection(spinnerPosition2);

                    if(mylist2.size() > 1){
                        txtDateStatus.setText(mylist2.get(1));
                    }else{
                        txtDateStatus.setText("");
                    }

//                    Toast.makeText(getApplicationContext(), "" + mylist2, Toast.LENGTH_SHORT).show();


                } else if (mylist2.contains("transferred")) {

                    spstatus.setAdapter(vaccinatedby);
                    int spinnerPosition2 = vaccinatedby.getPosition("Transferred");
                    spstatus.setSelection(spinnerPosition2);

                    if(mylist2.size() > 1){
                        txtDateStatus.setText(mylist2.get(1));
                    }else{
                        txtDateStatus.setText("");
                    }

//                    Toast.makeText(getApplicationContext(), "" + mylist2, Toast.LENGTH_SHORT).show();



                }
            }

            dateSurvey.setText(birth);
            txtpetname.setText(petname);
            chooseImg.setVisibility(View.GONE);

            species = ArrayAdapter.createFromResource(this, R.array.species, R.layout.support_simple_spinner_dropdown_item);
            if (specie != null) {
                int spinnerPosition = species.getPosition(specie);
                txtSpecie.setSelection(spinnerPosition);
            }
            breedsd=  ArrayAdapter.createFromResource(this, R.array.breed_dod, R.layout.support_simple_spinner_dropdown_item);
           breedsc = ArrayAdapter.createFromResource(this, R.array.breed_cat, R.layout.support_simple_spinner_dropdown_item);

            if(specie.equals("Cat") || txtSpecie.getSelectedItem().toString().equals("Cat")) {


                txtbreed.setVisibility(View.VISIBLE);
                    strngbreed.setVisibility(View.VISIBLE);
                    if (breed != null) {
                        txtbreed.setAdapter(breedsc);
                        int spinnerPosition2 = breedsc.getPosition(breed);

                        txtbreed.setSelection(spinnerPosition2);
                    }
            }else if(specie.equals("Dog") || txtSpecie.getSelectedItem().toString().equals("Dog"))
            {

                txtbreed.setVisibility(View.VISIBLE);
                strngbreed.setVisibility(View.VISIBLE);
                if (breed != null) {
                    txtbreed.setAdapter(breedsd);
                    int spinnerPosition1 = breedsd.getPosition(breed);
                    txtbreed.setSelection(spinnerPosition1);
                }
            }else if(specie.equals("Monkey")|| txtSpecie.getSelectedItem().toString().equals("Monkey"))
            {
                txtbreed.setVisibility(View.GONE);
                strngbreed.setVisibility(View.GONE);

            }

            sex = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.support_simple_spinner_dropdown_item);
            if (gender != null) {
                int spinnerPosition3 = sex.getPosition(gender);
                txtGender.setSelection(spinnerPosition3);
            }

            sources = ArrayAdapter.createFromResource(this, R.array.source_pet, R.layout.support_simple_spinner_dropdown_item);
            if(srcs.equals("Indigenous"))
            {
                if (srcs != null) {
                    int spinnerPosition4 = sources.getPosition(srcs);
                    txtsource.setSelection(spinnerPosition4);
                }
            }else{
                String sr = "Introduced";
                int spinnerPosition5 = sources.getPosition(sr);
                txtsource.setSelection(spinnerPosition5);

            }



            txtsource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    String src = txtsourceplace.getText().toString();


                    switch (selectedItem)
                    {
                        case "Indigenous":
                            txtsourceplace.setVisibility(View.GONE);
                            break;
                        case "Introduced":
                            txtsourceplace.setVisibility(View.VISIBLE);
                            txtsourceplace.setText(srcs);
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            colormarkings = ArrayAdapter.createFromResource(this, R.array.color_mark, R.layout.support_simple_spinner_dropdown_item);
            if(color.equals("White"))
            {

                    int spinnerPosition6 = colormarkings.getPosition(color);
                    txtColorMark.setSelection(spinnerPosition6);
                    othercolormark.setVisibility(View.GONE);
            }else if(color.equals("Black"))
            {

                int spinnerPosition6 = colormarkings.getPosition(color);
                txtColorMark.setSelection(spinnerPosition6);
                othercolormark.setVisibility(View.GONE);
            }else if(color.equals("Brown"))
            {

                int spinnerPosition6 = colormarkings.getPosition(color);
                txtColorMark.setSelection(spinnerPosition6);
                othercolormark.setVisibility(View.GONE);
            }else if(color.equals("Orange"))
            {

                int spinnerPosition6 = colormarkings.getPosition(color);
                txtColorMark.setSelection(spinnerPosition6);
                othercolormark.setVisibility(View.GONE);
            }else if(color.equals("Gray"))
            {

                int spinnerPosition6 = colormarkings.getPosition(color);
                txtColorMark.setSelection(spinnerPosition6);
                othercolormark.setVisibility(View.GONE);
            }else if(color.equals("Tricolor"))
            {

                int spinnerPosition6 = colormarkings.getPosition(color);
                txtColorMark.setSelection(spinnerPosition6);
                othercolormark.setVisibility(View.GONE);

            }
            else
            {

                String col = "Others";
                    int spinnerPosition7= colormarkings.getPosition(col);
                    txtColorMark.setSelection(spinnerPosition7);
                    othercolormark.setVisibility(View.VISIBLE);
                    othercolormark.setText(color);



            }


            txtdistinguish.setText(feature);


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String petname = txtpetname.getText().toString();
                    final String specie = txtSpecie.getSelectedItem().toString();
                    final String status = spstatus.getSelectedItem().toString();
                    final String breed;

                    if(specie == "Monkey"){
                        breed = "N/A";
                    }else{
                        breed = txtbreed.getSelectedItem().toString();
                    }
                    final String other_breed;

                    final String gender = txtGender.getSelectedItem().toString();
                    final String vacc_by;

                    final String birthdate = dateSurvey.getText().toString();
                    final String agepet = age;
                    final String colormark = txtColorMark.getSelectedItem().toString();
                     String othercolors;
                    final String feat ;
                    final String dis  = txtdistinguish.getText().toString();
                    final String datevacc = txtxDateVacc.getText().toString();

                    if(datevacc.equals("NOT YET VACCINATED"))
                    {
                        vacc_by = "N/A";
                    }else{
                        vacc_by = txtvaccinatedby.getSelectedItem().toString();
                    }
                    final String stat = "alive";
                    final byte imgv[]  ;
                    final String nulla = "N/A";

                    if(imgView.getDrawable() == null)
                    {
                        imgv = nulla.getBytes();


                    }else{

                        imgv = imageViewToByte(imgView);

                    }


                    if(dis.equals("")){
                        feat = "";

                    }else{
                        feat = dis;
                    }

                    String src = "";
                    String source2 = txtsource.getSelectedItem().toString();
                    final String place =  txtsourceplace.getText().toString();

                    if(colormark.equals("Others"))
                    {
                        othercolors = othercolormark.getText().toString();

                    }else{
                        othercolors = colormark;
                    }

                    final String othercolor = othercolors;


                        other_breed = breed;
//                    if(breed.equals("Others"))
//                    {
//                        other_breed =  otherbreed.getText().toString();
//                    }else{
//
//                    }


                    if(source2.equals("Introduced") ) {
                        final String sr = place;
                        src = sr;


                    }else if(source2.equals("Indigenous") ){

                        final String sr = source2;
                        src = sr;
                    }

                    final String souces = src;

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    final String created_at = format1.format(cal.getTime());


                    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                    StringBuilder salt = new StringBuilder();
                    Random rnd = new Random();
                    while (salt.length() < 7) { // length of the random string.
                        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                        salt.append(SALTCHARS.charAt(index));
                    }
                    final String end = salt.toString();

                    final byte imgv2[] = imgv;
                    final String stats  = mylistup.toString();



                    lat = tvLatitude.getText().toString();

                    longit = tvLongitude.getText().toString();

                    // Build an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(VaccinationActivity.this);

                    // Set a title for alert dialog
                    builder.setTitle("There's no going back.");

                    // Ask the final question
                    builder.setMessage("Do you want to save the update?");

                    // Set click listener for alert dialog buttons

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    // User clicked the Yes button

                                    if (petname.equals("")  || specie.equals("") || breed.equals("") || gender.equals("") || birthdate.equals("") ) {
                                        Toast.makeText(VaccinationActivity.this, "Check your input!"  , Toast.LENGTH_SHORT).show();
                                    }else{

                                        try {

                                            if(mylistup.size() == 1)
                                            {

                                                Toast.makeText(VaccinationActivity.this, "TRYING 1!" , Toast.LENGTH_SHORT).show();

                                                myDB.updateVaccination(petname,specie,other_breed,gender,birthdate,othercolor, feat,
                                                        souces,petid,mylistup.get(0), lat, longit);
                                                Toast.makeText(VaccinationActivity.this, "Success!" , Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), PetActivity.class);
                                                intent.putExtra("ownerid", ownerid);
                                                intent.putExtra("petid", petid);
                                                intent.putExtra("pos", pos);
                                                intent.putExtra("stat", stat);

                                                startActivity(intent);

                                            }else{

                                                Toast.makeText(VaccinationActivity.this, "TRYING 2!" , Toast.LENGTH_SHORT).show();

                                                myDB.updateVaccination(petname,specie,other_breed,gender,birthdate,othercolor, feat,
                                                        souces,petid,stats, lat, longit);
                                                Toast.makeText(VaccinationActivity.this, "Success!" , Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), PetActivity.class);
                                                intent.putExtra("ownerid", ownerid);
                                                intent.putExtra("petid", petid);
                                                intent.putExtra("pos", pos);
                                                intent.putExtra("stat", stat);
                                                startActivity(intent);

                                            }


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:


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




        EnableRuntimePermissionToAccessCamera();

        final ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.breed_dod, R.layout.support_simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.breed_cat, R.layout.support_simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.breed_monkey, R.layout.support_simple_spinner_dropdown_item);
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        btndate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(VaccinationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());


                dialog.show();
//                new DatePickerDialog(VaccinationActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).
//                        getDatePicker().setMaxDate(new Date().
//                        getTime());
//
//                new DatePickerDialog(VaccinationActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txtxDateVacc.setText(sdf.format(myCalendar.getTime()));
                updateLabel2();
            }

        };


        dateVacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(VaccinationActivity.this, date2, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                DatePickerDialog dialog = new DatePickerDialog(VaccinationActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
//                dialog.getDatePicker().setMinDate(new Date(dateSurvey).getTime());

                dialog.show();

            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLocation();
                isLocationEnabled();

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(VaccinationActivity.this);
                // Set a title for alert dialog
                builder.setTitle("Skipping the process.");
                // Ask the final question
                builder.setMessage("Are you sure you want to skip this survey?");
                // Set click listener for alert dialog buttons
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // User clicked the Yes button
                                Intent intent = new Intent(getApplicationContext(), SignatureActivity.class);
                                intent.putExtra("pos", pos);
                                intent.putExtra("stat", stat);
                                startActivity(intent);
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



        btnVacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String petname = txtpetname.getText().toString();
                final String specie = txtSpecie.getSelectedItem().toString();
                final String breed;


                if(specie == "Monkey"){
                      breed = "N/A";
                }else{
                      breed = txtbreed.getSelectedItem().toString();
                }
                final String other_breed;

                final String gender = txtGender.getSelectedItem().toString();
                final String vacc_by;




                final String birthdate = dateSurvey.getText().toString();
                final String agepet = age;
                final String colormark = txtColorMark.getSelectedItem().toString();
                String othercolors;
                final String feat ;
                final String dis  = txtdistinguish.getText().toString();
                final String datevacc = txtxDateVacc.getText().toString();

                if(datevacc.equals("NOT YET VACCINATED"))
                {
                    vacc_by = "N/A";
                }else{
                    vacc_by = txtvaccinatedby.getSelectedItem().toString();
                }
                final String stat = "alive";
                 final byte imgv[]  ;
                 final String nulla = "N/A";



                if(imgView.getDrawable() == null)
                {
                    imgv = nulla.getBytes();


                }else{

                    imgv = imageViewToByte(imgView);

                }


                if(dis.equals("")){
                    feat = "";

                }else{
                    feat = dis;
                }

                 String src = "";
                 String source2 = txtsource.getSelectedItem().toString();
                final String place =  txtsourceplace.getText().toString();

                if(colormark.equals("Others"))
                {
                    othercolors = othercolormark.getText().toString();
                }else{
                    othercolors = colormark;
                }



                final String othercolor = othercolors;

                other_breed = breed;
//                if(breed == "Others")
//                {
//                    other_breed =  otherbreed.getText().toString();
//                }else{
//
//                }


                if(source2.equals("Introduced") ) {
                    final String sr = place;
                    src = sr;


                }else if(source2.equals("Indigenous") ){

                    final String sr = source2;
                    src = sr;
                }

                final String souces = src;

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                final String created_at = format1.format(cal.getTime());


                String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < 7) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }

                final String end = salt.toString();


                final byte imgv2[] = imgv;

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



                ctr = myDB.getCountPet(ownerid,specie);
                ctr++;

                first = specie.charAt(0);

                if (petid == null) {

                    String muni = myDB.getMuni(ownerid);
//                    String brgy = myDB.getBrgy(ownerid);

                    if(muni.equals("La Trinidad"))
                    petid = String.valueOf((muni.charAt(0))) + muni.charAt(3);
                    else{
                        petid = String.valueOf((muni.charAt(0)));
                    }

                }

                   pet = petid + end;


                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(VaccinationActivity.this);

                // Set a title for alert dialog

                builder.setTitle("There's no going back.");

                // Ask the final question
                builder.setMessage("Do you have any pet/s to be vaccinated?");

                // Set click listener for alert dialog buttons

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // User clicked the Yes button

                                if (petname.equals("") || breed.equals("") || gender.equals("") || birthdate.equals("") ) {
                                    Toast.makeText(VaccinationActivity.this, "Check your input!", Toast.LENGTH_SHORT).show();
                                }else{

                                    try {

                                            myDB.addVaccination(imgv,ownerid,petname,specie,other_breed,gender,birthdate,othercolor, feat,
                                                        souces,pet,stat,created_at, lat, longi);

                                            if(!petid.equals("") && !datevacc.equals("") && !vacc_by.equals("") && !created_at.equals("")) {

                                                myDB.addVaccinationDate(pet,datevacc,vacc_by.trim(),created_at);
                                                Intent intent = new Intent(getApplicationContext(), VaccinationActivity.class);
                                                intent.putExtra("ownerid", ownerid);
                                                intent.putExtra("petid", petid);
                                                intent.putExtra("add", add);
                                                intent.putExtra("stat", stat);
                                                startActivity(intent);

                                            }



                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                if (petname.equals("")  || breed.equals("") || gender.equals("") || birthdate.equals("") ) {
                                    Toast.makeText(VaccinationActivity.this, "Check your input!" + petid , Toast.LENGTH_SHORT).show();
                                }else{

                                    try {



                                            myDB.addVaccination(imgv,ownerid,petname,specie,other_breed,gender,birthdate,othercolor, feat, souces,pet,stat,created_at, lat, longi);




                                        if(!petid.equals("") && !datevacc.equals("") && !vacc_by.equals("") && !created_at.equals("")) {

                                            myDB.addVaccinationDate(pet,datevacc,vacc_by.trim(),created_at);
                                            Intent intent = new Intent(getApplicationContext(), SignatureActivity.class);
                                            Toast.makeText(VaccinationActivity.this, "Sucess!" , Toast.LENGTH_SHORT).show();
                                            intent.putExtra("ownerid", ownerid);
                                            intent.putExtra("petid", petid);
                                            intent.putExtra("add", add);
                                            intent.putExtra("stat", stat);
                                            startActivity(intent);

                                        }







                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

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



        if(ownerid != null && status.equals("add"))
        {

        txtsource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                String src = txtsourceplace.getText().toString();


                switch (selectedItem)
                {
                    case "Indigenous":
                        txtsourceplace.setVisibility(View.GONE);
                        break;
                    case "Introduced":
                        txtsourceplace.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            txtSpecie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedItem = adapterView.getItemAtPosition(i).toString();

                    switch (selectedItem)
                    {
                        case "Cat":
                            txtbreed.setVisibility(View.VISIBLE);
                            strngbreed.setVisibility(View.VISIBLE);
                            txtbreed.setAdapter(adapter2);
                            break;
                        case "Dog":
                            txtbreed.setVisibility(View.VISIBLE);
                            strngbreed.setVisibility(View.VISIBLE);
                            txtbreed.setAdapter(adapter);
                            break;
                        case "Monkey":
                            txtbreed.setVisibility(View.GONE);
                            strngbreed.setVisibility(View.GONE);
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });





        txtColorMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                switch (selectedItem)
                {
                    case "Others":
                        othercolormark.setVisibility(View.VISIBLE);
                        break;
                    case "White":
                        othercolormark.setVisibility(View.GONE);
                        break;
                    case "Black":
                        othercolormark.setVisibility(View.GONE);
                        break;
                    case "Orange":
                        othercolormark.setVisibility(View.GONE);
                        break;
                    case "Gray":
                        othercolormark.setVisibility(View.GONE);
                        break;
                    case "Tricolor":
                        othercolormark.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        txtbreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }


        spstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem)
                {
                    case "Alive":
                        btnDateStatus.setVisibility(View.GONE);
                        txtDateStatus.setVisibility(View.GONE);

                        mylistup.clear();
                        mylistup.add("alive");
//                        Toast.makeText(VaccinationActivity.this, "Check your input!" + selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    case "Dead":
                        btnDateStatus.setVisibility(View.VISIBLE);
                        txtDateStatus.setVisibility(View.VISIBLE);
                        mylistup.clear();
                        mylistup.add("dead");
                        break;

                    case "Transferred":
                        btnDateStatus.setVisibility(View.VISIBLE);
                        txtDateStatus.setVisibility(View.VISIBLE);
                        mylistup.clear();

                        mylistup.add("transferred");
                        break;

                    case "Lost":
                        btnDateStatus.setVisibility(View.VISIBLE);
                        txtDateStatus.setVisibility(View.VISIBLE);
                        mylistup.clear();
                        mylistup.add("lost");
//                        Toast.makeText(VaccinationActivity.this, "Check your input!" + selectedItem, Toast.LENGTH_SHORT).show();

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
    }

    private void isLocationEnabled() {

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            if(mContext != null) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Enable Location");
                alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
                alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        }
        else {
            if (mContext != null) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Confirm Location");
                alertDialog.setMessage("Your Location is enabled, please enjoy");
                alertDialog.setNegativeButton("Back to interface", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String age = calculateAge(myCalendar.getTime());
        Toast.makeText(VaccinationActivity.this, "Check your input!"+age, Toast.LENGTH_SHORT).show();
        txtAge.setText(age);

        dateSurvey.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtxDateVacc.setText(sdf.format(myCalendar.getTime()));
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public String calculateAge(Date date){

        int year =0;
        int months = 0;
        int days = 0;

        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date.getTime());
        long current = System.currentTimeMillis();
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(current);


        year = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int currMonth = today.get(Calendar.MONTH);
        int birthMonth = dob.get(Calendar.MONTH);

        months = currMonth - birthMonth;

        if( months < 0)
        {
            year--;
            months = 12 - birthMonth + currMonth;
            if(today.get(Calendar.DATE) < dob.get(Calendar.DATE))
            {
                months--;
            }else if(months == 0 && today.get(Calendar.DATE) < dob.get(Calendar.DATE))
            {
                year--;
                months = 11;
            }
        }


        if(today.get(Calendar.DATE) > dob.get(Calendar.DATE))
        {
            days = today.get(Calendar.DATE) - dob.get(Calendar.DATE);

        }else if(today.get(Calendar.DATE) < dob.get(Calendar.DATE)){

            int now = today.get(Calendar.DAY_OF_MONTH);
            today.add(Calendar.MONTH, -1);
            days = today.getActualMaximum(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH) + now;
        }
        else
        {
            days = 0;
            if (months == 12)
            {
                year++;
                months = 0;
            }
        }

        final String aged1 ;

        if(year > 0 && months > 0 && days > 0)
        {
            aged1 = year + " year/s, " + months + " month/s, " + days + " day/s";
        }else if(year == 0 && months > 0 && days > 0)
        {
            aged1 = months + " month/s, " + days + " day/s";
        }else if(year == 0 && months == 0 && days > 0){
            aged1 =  days + " day/s";
        }
        else if(year > 0 && months == 0 && days == 0){
            aged1 =  year + " year/s";
        }

        else if(year > 0 && months == 0 && days > 0){
            aged1 =  year + " year/s " + days + " day/s";
        }

        else if(year > 0 && months > 0 && days == 0){
            aged1 =  year + " year/s " + months + " month/s";
        }

        else if(year == 0 && months > 0 && days == 0){
            aged1 =  months + " month/s";
        }else{
            aged1 = "New Born";
        }




        return aged1;
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(VaccinationActivity.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(VaccinationActivity.this,"Permission Cancelled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void onRadioButtonClicked2(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rby:
                if (checked)

                    dateVacc.setEnabled(true);
                txtxDateVacc.setText("");
                txtvaccinatedby.setVisibility(View.VISIBLE);

                // Pirates are the best

                break;
            case R.id.rbn:
                if (checked)
                    txtxDateVacc.setText("NOT YET VACCINATED");
                txtvaccinatedby.setVisibility(View.GONE);
                txtvaccinatedby2.setVisibility(View.GONE);
                dateVacc.setEnabled(false);
                // Ninjas rule
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imageBitmap);
        }
    }

    public void EnableRuntimePermissionToAccessCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(VaccinationActivity.this, Manifest.permission.CAMERA)) {

            Toast.makeText(VaccinationActivity.this, "PABDIS wants to access your Camera", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(VaccinationActivity.this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        }

    }



    public static byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    @Override
    public void onBackPressed() {
        if(pos != null)
        {
            Intent i = new Intent(VaccinationActivity.this, PetActivity.class);
            i.putExtra("pos", pos);
            i.putExtra("stat", stat);
            startActivity(i);
//        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
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
////
//        try {
//            List<Address> addresses = geocoder.getFromLocation(lang, longi, 1);
//            add = addresses.get(0).getAddressLine(0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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
        Toast.makeText(VaccinationActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

    }
}
