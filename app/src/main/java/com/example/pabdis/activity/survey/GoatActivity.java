package com.example.pabdis.activity.survey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pabdis.R;
import com.example.pabdis.activity.helper.DatabaseHelper;
import com.example.pabdis.activity.ui.MainActivity;
import com.example.pabdis.activity.ui.VaccinationActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GoatActivity extends AppCompatActivity {


    Button btnNext;
    String ownerid,vaccstat, vacctype, deworm, petid;
    RadioButton rbyes, rbno;
    FloatingActionButton skip;
    Spinner vacc;
    TextView textView,txtincome;
    DatabaseHelper myDB;
    EditText edtBuckD,edtBuckM,edtDoeD,edtDoeM,edtKidsD,edtKidsM,edtSF_sw_kg,edtSF_sw_hd,edtSA_sw_kg,edtSA_sw_hd,edtSwineTotalArea,edtSwineTotalIncome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_goat);
        myDB = new DatabaseHelper(getApplicationContext());
        btnNext = findViewById(R.id.btnProceedSurvey);
        skip = findViewById(R.id.fab);
        edtBuckD = findViewById(R.id.edtBuckD);
        edtBuckM = findViewById(R.id.edtBuckM);
        edtDoeD = findViewById(R.id.edtDoeD);
        edtDoeM = findViewById(R.id.edtDoeM);
        edtKidsD = findViewById(R.id.edtKidsD);
        edtKidsM = findViewById(R.id.edtKidsM);
        edtSF_sw_kg = findViewById(R.id.edtSF_sw_kg);
        edtSF_sw_hd = findViewById(R.id.edtSF_sw_hd);
        edtSA_sw_kg = findViewById(R.id.edtSA_sw_kg);
        edtSA_sw_hd = findViewById(R.id.edtSA_sw_hd);
        edtSwineTotalArea = findViewById(R.id.edtSwineTotalArea);
        edtSwineTotalIncome = findViewById(R.id.edtSwineTotalIncome);

        rbno = findViewById(R.id.rb2);
        rbyes = findViewById(R.id.rb1);
        vacc = findViewById(R.id.vaccination);
        textView = findViewById(R.id.textView);

        txtincome = findViewById(R.id.txtincome);
        txtincome.setText("Total Income for 2018");


        vacc.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ownerid= null;
                petid = null;
            } else {
                ownerid= extras.getString("ownerid");
                petid= extras.getString("ownerid");
            }
        } else {
            ownerid= (String) savedInstanceState.getSerializable("ownerid");
            petid = (String) savedInstanceState.getSerializable("petid");
        }

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(GoatActivity.this);

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
                                Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
                                intent.putExtra("owner_id",ownerid);
                                intent.putExtra("petid", petid.trim());
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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String buckd = edtBuckD.getText().toString();
                final String buckm = edtBuckM.getText().toString();
                final String doed = edtDoeD.getText().toString();
                final String doem = edtDoeM.getText().toString();
                final String kidsd = edtKidsD.getText().toString();
                final String kidsm = edtKidsM.getText().toString();
                final String go_sf_kg = edtSF_sw_kg.getText().toString();
                final String go_sf_hd = edtSF_sw_hd.getText().toString();
                final String go_sa_kg = edtSA_sw_kg.getText().toString();
                final String go_sa_hd = edtSA_sw_hd.getText().toString();
                final String go_totala = edtSwineTotalArea.getText().toString();
                final String go_totali = edtSwineTotalIncome.getText().toString();
                final String vacc = vaccstat;
                final String vacct = vacctype;
                final String dewormed = deworm;


                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                final String created_at = format1.format(cal.getTime());
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(GoatActivity.this);

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

                                if (buckd.equals("") || buckm.equals("") || doed.equals("") ||
                                        doem.equals("") || kidsd.equals("") || kidsm.equals("") ||
                                        go_sf_kg.equals("") ||  go_sf_hd.equals("") || go_sa_kg.equals("") || go_sa_hd.equals("") || go_totala.equals("") || go_totali.equals("")  ) {
                                    Toast.makeText(GoatActivity.this, "Check your input!" , Toast.LENGTH_SHORT).show();

                                }else {


                                    try {
                                        myDB.addGoat(ownerid, buckd, buckm, doed, doem, kidsd, kidsm, go_sf_kg,go_sf_hd, go_sa_kg,go_sa_hd,
                                                go_totala, go_totali,vacc.trim(), vacct.trim(), dewormed.trim(), created_at);
                                        Toast.makeText(GoatActivity.this, "Success!" , Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
                                        intent.putExtra("owner_id", ownerid);
                                        intent.putExtra("petid", petid.trim());
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
                    vacc.setVisibility(View.VISIBLE);
                vaccstat = "1";
                textView.setVisibility(View.VISIBLE);
                vacctype = vacc.getSelectedItem().toString();
                break;
            case R.id.rb2:
                if (checked)
                    vacc.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                vaccstat = "2";
                vacctype = "";
                // Ninjas rule
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
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
}