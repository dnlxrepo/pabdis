package com.example.pabdis.activity.survey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pabdis.R;
import com.example.pabdis.activity.helper.DatabaseHelper;
import com.example.pabdis.activity.ui.MainActivity;
import com.example.pabdis.activity.ui.MapActivity;
import com.example.pabdis.activity.ui.OwnerActivity;
import com.example.pabdis.activity.ui.PetActivity;
import com.example.pabdis.activity.ui.ProfileActivity;
import com.example.pabdis.activity.ui.VaccinationActivity;
import com.example.pabdis.activity.updates.ListUpdateActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class CattleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button btnNext, compute,btnUpdate;
    EditText edtBullD,edtBullM,edtCowD,edtCowM,edtCalfD,edtCalfM,edtTotal, edtSF_sw_kg,edtSF_sw_hd,edtSA_sw_kg,edtSA_sw_hd,edtSwineTotalArea,edtSwineTotalIncome;
    String ownerid, vaccstat, vacctype, deworm, petid, update,add;
    DatabaseHelper myDB;
    RadioButton rbyes, rbno, rbn, rby;
    CheckBox cbbl;
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayList<String> mylist2 = new ArrayList<String>();
    Integer pos;
    ArrayList<String> mylistup = new ArrayList<String>();
    Spinner vacc;
    FloatingActionButton skip, skip_vacc;
    TextView textView, txtincome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_survey_cattle);
        btnNext = findViewById(R.id.btnProceedSurvey);
        btnUpdate = findViewById(R.id.btnUpdate);
        skip = findViewById(R.id.fab);
        skip_vacc = findViewById(R.id.fab2);
        cbbl = findViewById(R.id.cbbl);
        cbbl.setVisibility(View.GONE);
        compute = findViewById(R.id.btnCompute);
        edtBullD = findViewById(R.id.edtBullD);
        edtTotal = findViewById(R.id.edtTotal);
        edtBullM = findViewById(R.id.edtBullM);
        edtCowD = findViewById(R.id.edtCowD);
        edtCowM = findViewById(R.id.edtCowM);
        edtCalfD = findViewById(R.id.edtCalfD);
        edtCalfM = findViewById(R.id.edtCalfM);
        edtSF_sw_kg = findViewById(R.id.edtSF_sw_kg);
        edtSF_sw_hd = findViewById(R.id.edtSF_sw_hd);
        edtSA_sw_kg = findViewById(R.id.edtSA_sw_kg);
        edtSA_sw_hd = findViewById(R.id.edtSA_sw_hd);
        edtSwineTotalArea = findViewById(R.id.edtSwineTotalArea);
        edtSwineTotalIncome = findViewById(R.id.edtSwineTotalIncome);
        rbno = findViewById(R.id.rb2);
        rbyes = findViewById(R.id.rb1);
        rbn = findViewById(R.id.rbn);
        rby = findViewById(R.id.rby);
        textView = findViewById(R.id.textView);
        Calendar now = Calendar.getInstance();
        now = Calendar.getInstance();
        now.add(Calendar.YEAR, -1);

        txtincome = findViewById(R.id.txtincome);
        txtincome.setText("Total Income for " + now.get(Calendar.YEAR));
        textView.setVisibility(View.GONE);
        edtTotal.setEnabled(false);
        btnUpdate.setVisibility(View.GONE);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ownerid= null;
                petid = null;
                update = null;
                pos = null;
            } else {
                ownerid= extras.getString("ownerid");
                petid= extras.getString("petid");

                pos= extras.getInt("position");
            }
        } else {
            ownerid= (String) savedInstanceState.getSerializable("ownerid");
            petid = (String) savedInstanceState.getSerializable("petid");
            update = (String) savedInstanceState.getSerializable("update");
            pos = (Integer) savedInstanceState.getSerializable("position");


        }



        Cursor rs = myDB.getCattle(ownerid);
        rs.moveToFirst();

        if(rs.getCount() > 0)
        {

            skip.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            cbbl.setVisibility(View.VISIBLE);
            cbbl.setChecked(false);




            String bulld = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_4));
            String bullm = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_5));
            String cowd = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_6));
            String cowm = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_7));
            String calfd = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_8));
            String calfm = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_9));

            String total_inv = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_10));
            String sl_f_kg = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_11));
            String sl_f_hd = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_12));
            String sl_a_kg = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_13));
            String sl_a_hd = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_14));
            String total_area = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_15));
            String total_inc = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_16));

            String vacc = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_17));

            if(vacc.equals("1"))
            {
                rbyes.setChecked(true);
                rbno.setChecked(false);
                vaccstat = "1";

            }else{
                rbno.setChecked(true);
                rbyes.setChecked(false);
                vaccstat = "2";

            }

            String vacctype = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_18));
            vacctype = vacctype.replace("[", "");
            vacctype = vacctype.replace("]", "");
            vacctype = vacctype.replace(", ", ",");



            mylist2 = new ArrayList<String>(Arrays.asList(vacctype.split(",")));




            final String vacca = vacctype;


            if(mylist2.contains(cbbl.getText().toString()))
            {
                mylistup.add(cbbl.getText().toString());
                cbbl.setChecked(true);

            }

            String dewormed = rs.getString(rs.getColumnIndex(DatabaseHelper.SURVEY3COL_19));


            if(dewormed.equals("1"))
            {
                rby.setChecked(true);
                rbn.setChecked(false);
                deworm = "1";


            }else{
                rbn.setChecked(true);
                rby.setChecked(false);
                deworm = "2";


            }

            edtBullD.setText(bulld);
            edtBullM.setText(bullm);
            edtCalfD.setText(calfd);
            edtCalfM.setText(calfm);
            edtCowD.setText(cowd);
            edtCowM.setText(cowm);
            edtTotal.setText(total_inv);
            edtSF_sw_kg.setText(sl_f_kg);
            edtSF_sw_hd.setText(sl_f_hd);
            edtSA_sw_kg.setText(sl_a_kg);
            edtSA_sw_hd.setText(sl_a_hd);
            edtSwineTotalArea.setText(total_area);
            edtSwineTotalIncome.setText(total_inc);


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String bulld = edtBullD.getText().toString();
                    final String bullm = edtBullM.getText().toString();
                    final String cowd = edtCowD.getText().toString();
                    final String cowm = edtCowM.getText().toString();
                    final String calfd = edtCalfD.getText().toString();
                    final String calfm = edtCalfM.getText().toString();
                    final String ca_sf_kg = edtSF_sw_kg.getText().toString();
                    final String ca_sf__hd = edtSF_sw_hd.getText().toString();
                    final String ca_sa_kg = edtSA_sw_kg.getText().toString();
                    final String ca_sa__hd = edtSA_sw_hd.getText().toString();
                    final String ca_totala = edtSwineTotalArea.getText().toString();
                    final String ca_totali = edtSwineTotalIncome.getText().toString();


                    final String total = edtTotal.getText().toString();

                    final String vacc = vaccstat;
                    final String vacct = mylistup.toString();
                    final String dewormed = deworm;


                    // Build an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(CattleActivity.this);

                    // Set a title for alert dialog
                    builder.setTitle("UPDATE.");

                    // Ask the final question
                    builder.setMessage("Are you sure you want to update the data?");

                    // Set click listener for alert dialog buttons
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    // User clicked the Yes button


                                    // User clicked the Yes button
                                    if (bulld.equals("") || bullm.equals("") || cowd.equals("") ||
                                            calfd.equals("") || calfm.equals("") || ca_sf_kg.equals("") ||
                                            ca_sf__hd.equals("") || ca_sa_kg.equals("") || ca_sa__hd.equals("") ||
                                            ca_totala.equals("") || ca_totali.equals("") || vacc.equals("") || vacct.equals("") || dewormed.equals("")) {
                                        Toast.makeText(CattleActivity.this, "Check your input!" , Toast.LENGTH_SHORT).show();

                                    }else {
                                        try {
                                            myDB.updateCattle(ownerid,bulld, bullm,cowd,cowm,calfd,calfm,total,ca_sf_kg,ca_sf__hd,ca_sa_kg,ca_sa__hd,ca_totala,
                                                    ca_totali ,vacc.trim(), vacct.trim(),dewormed.trim());
                                            Toast.makeText(CattleActivity.this, "Success!" , Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), ListUpdateActivity.class);
                                            intent.putExtra("ownerid",ownerid);
                                            intent.putExtra("petid", petid);
                                            intent.putExtra("position", pos);
                                            startActivity(intent);
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


        cbbl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    if(!Arrays.asList(mylist).contains(cbbl.getText().toString()))
                    {
                        mylist.add(cbbl.getText().toString());
                        mylistup.add(cbbl.getText().toString());

                    }
                }else{
                    mylist.remove(cbbl.getText().toString());
                    mylistup.remove(cbbl.getText().toString());

                }
            }
        });

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Integer boarn = Integer.parseInt(edtBullD.getText().toString());
                final Integer boaru = Integer.parseInt(edtBullM.getText().toString());
                final Integer grown = Integer.parseInt(edtCowD.getText().toString());
                final Integer growu = Integer.parseInt(edtCowM.getText().toString());
                final Integer sown = Integer.parseInt(edtCalfD.getText().toString());
                final Integer sowu = Integer.parseInt(edtCalfM.getText().toString());


                if(edtBullD.equals("") || edtBullM.equals("") || edtCowD.equals("") || edtCowM.equals("")
                        || edtCalfD.equals("") || edtCalfM.equals("") )
                {

                    Toast.makeText(CattleActivity.this, "Empty input!" + ownerid , Toast.LENGTH_SHORT).show();



                }else {

                    final Integer total = boarn + boaru + grown + growu +  sown + sowu ;
                    edtTotal.setText(String.valueOf(total));
                }






            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CarabaoActivity.class);
                intent.putExtra("ownerid",ownerid);
                intent.putExtra("petid", petid);
                intent.putExtra("pos", pos);
                startActivity(intent);
            }
        });

        skip_vacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add = "add";
                Intent intent = new Intent(getApplicationContext(), VaccinationActivity.class);
                intent.putExtra("ownerid",ownerid);
                intent.putExtra("petid", petid);
                intent.putExtra("pos", pos);
                intent.putExtra("add", add);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String bulld = edtBullD.getText().toString();
                final String bullm = edtBullM.getText().toString();
                final String cowd = edtCowD.getText().toString();
                final String cowm = edtCowM.getText().toString();
                final String calfd = edtCalfD.getText().toString();
                final String calfm = edtCalfM.getText().toString();
                final String ca_sf_kg = edtSF_sw_kg.getText().toString();
                final String ca_sf__hd = edtSF_sw_hd.getText().toString();
                final String ca_sa_kg = edtSA_sw_kg.getText().toString();
                final String ca_sa__hd = edtSA_sw_hd.getText().toString();
                final String ca_totala = edtSwineTotalArea.getText().toString();
                final String ca_totali = edtSwineTotalIncome.getText().toString();


                final String total = edtTotal.getText().toString();

                final String vacc = vaccstat;
                final String vacct = mylist.toString();
                final String dewormed = deworm;

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                final String created_at = format1.format(cal.getTime());


                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CattleActivity.this);

                // Set a title for alert dialog
                builder.setTitle("There's no going back.");

                // Ask the final question
                builder.setMessage("Are you sure you want to save the data?");

                // Set click listener for alert dialog buttons
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // User clicked the Yes button


                                // User clicked the Yes button
                                if (bulld.equals("") || bullm.equals("") || cowd.equals("") ||
                                        calfd.equals("") || calfm.equals("") || ca_sf_kg.equals("") ||
                                        ca_sf__hd.equals("") || ca_sa_kg.equals("") || ca_sa__hd.equals("") ||
                                        ca_totala.equals("") || ca_totali.equals("") || vacc.equals("") || vacct.equals("") || dewormed.equals("")) {
                                    Toast.makeText(CattleActivity.this, "Check your input!" , Toast.LENGTH_SHORT).show();

                                }else {
                                    try {
                                        myDB.addCattle(ownerid,bulld, bullm,cowd,cowm,calfd,calfm,total,ca_sf_kg,ca_sf__hd,ca_sa_kg,ca_sa__hd,ca_totala,
                                                ca_totali ,vacc.trim(), vacct.trim(),dewormed.trim(),created_at );
                                        Toast.makeText(CattleActivity.this, "Success!" , Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), CarabaoActivity.class);
                                        intent.putExtra("ownerid",ownerid);
                                        intent.putExtra("petid", petid);
                                        intent.putExtra("pos", pos);
                                        startActivity(intent);
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb1:
                if (checked)
                    // Pirates are the best
                vaccstat = "1";
                textView.setVisibility(View.VISIBLE);
                cbbl.setVisibility(View.VISIBLE);
                break;
            case R.id.rb2:
                if (checked)
                textView.setVisibility(View.GONE);
                cbbl.setVisibility(View.GONE);
                vaccstat = "2";
                vacctype = "";
                mylist.add("");
                // Ninjas rule
                break;
        }
    }

        // Is the button now checked?
        public void onRadioButtonClicked2(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rby:
                if (checked)
                    // Pirates are the best
                    deworm = "1";
                break;
            case R.id.rbn:
                if (checked)
                    deworm = "2";
                // Ninjas rule
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if(pos != null)
        {
            Intent i = new Intent(CattleActivity.this, ListUpdateActivity.class);
            i.putExtra("position", pos);
            i.putExtra("ownerid", ownerid);
            startActivity(i);
//        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
        }
    }

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

        }  else if (id == R.id.nav_list_owner) {

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
}