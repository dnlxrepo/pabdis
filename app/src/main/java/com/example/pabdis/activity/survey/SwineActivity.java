package com.example.pabdis.activity.survey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pabdis.R;
import com.example.pabdis.activity.helper.DatabaseHelper;
import com.example.pabdis.activity.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SwineActivity extends AppCompatActivity {

    Button btnNext;
    EditText edtboarn, edtboaru, edtGrowN, edtGrowU, edtSowN, edtSowU, edtPigN,edtPigU,edtSwineTotal,edtSF_sw,edtSA_sw,edtSwineTotalArea,edtSwineTotalIncome;
    String ownerid;
    DatabaseHelper myDB;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_swine);
        btnNext = findViewById(R.id.btnProceedSurvey);
        edtboarn = findViewById(R.id.edtBoarN);
        edtboaru = findViewById(R.id.edtBoarU);
        edtGrowN = findViewById(R.id.edtGrowN);
        edtGrowU = findViewById(R.id.edtGrowU);
        edtSowN = findViewById(R.id.edtSowN);
        edtSowU = findViewById(R.id.edtSowU);
        edtPigN = findViewById(R.id.edtPigN);
        edtPigU = findViewById(R.id.edtPigU);
        edtSwineTotal = findViewById(R.id.edtSwineTotal);
        edtSF_sw = findViewById(R.id.edtSF_sw);
        edtSA_sw = findViewById(R.id.edtSA_sw);
        edtSwineTotalArea = findViewById(R.id.edtSwineTotalArea);
        edtSwineTotalIncome = findViewById(R.id.edtSwineTotalIncome);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ownerid= null;
            } else {
                ownerid= extras.getString("ownerid");
            }
        } else {
            ownerid= (String) savedInstanceState.getSerializable("ownerid");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String boarn = edtboarn.getText().toString();
                final String boaru = edtboaru.getText().toString();
                final String grown = edtGrowN.getText().toString();
                final String growu = edtGrowU.getText().toString();
                final String sown = edtSowN.getText().toString();
                final String sowu = edtSowU.getText().toString();
                final String pign = edtPigN.getText().toString();
                final String pigu = edtPigU.getText().toString();
                final String swntotal = edtSwineTotal.getText().toString();
                final String swn_sf = edtSF_sw.getText().toString();
                final String swn_sa = edtSA_sw.getText().toString();
                final String swn_totala = edtSwineTotalArea.getText().toString();
                final String swn_totali = edtSwineTotalIncome.getText().toString();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                final String created_at = format1.format(cal.getTime());

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(SwineActivity.this);

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
                                if (boarn.equals("") || boaru.equals("") || grown.equals("") ||
                                        growu.equals("") || sown.equals("") || sowu.equals("") ||
                                        pign.equals("") || pigu.equals("") || swntotal.equals("") ||
                                        swn_sf.equals("") || swn_sa.equals("") || swn_totala.equals("") || swn_totali.equals("")) {
                                    Toast.makeText(SwineActivity.this, "Check your input!" , Toast.LENGTH_SHORT).show();

                                }else {
                                    try {
                                        myDB.addSwine(ownerid, boarn, boaru, sown, sowu, grown, growu, pign, pigu, swntotal, swn_sf, swn_sa, swn_totala, swn_totali, created_at);
                                        Intent intent = new Intent(getApplicationContext(), ChickenActivity.class);
                                        intent.putExtra("owner_id", ownerid);
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

//    @Override
//    public void onBackPressed() {
////        DrawerLayout drawer = findViewById(R.id.drawer_layout);
////        if (drawer.isDrawerOpen(GravityCompat.START)) {
////            drawer.closeDrawer(GravityCompat.START);
////        } else {
////            super.onBackPressed();
////        }
//        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
//    }
}
