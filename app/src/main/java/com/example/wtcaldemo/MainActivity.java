package com.example.wtcaldemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_wt_round);
        actionBar.setTitle(R.string.txtTitle);

        TextView txtViewResult = findViewById(R.id.txtViewResult);
        EditText editTextInputWt = findViewById(R.id.editTextInputWt);
        RadioGroup radGroupConv = findViewById(R.id.radGroupConv);// clean project if not name comes
        Button btnConvert = findViewById(R.id.btnConvertWt);
        // added another line  from local
        // checkChange Listner:initialy -1
        radGroupConv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radBtnKgsToLbs){
                    //Toast.makeText(MainActivity.this, "Kilos to Pounds", Toast.LENGTH_SHORT).show();
                    txtViewResult.setText("");
                } else if (checkedId==R.id.radBtnLbsToKgs) {
                    //Toast.makeText(MainActivity.this, "Pounds to Kilos", Toast.LENGTH_SHORT).show();
                    txtViewResult.setText("");
                }
            }
        });

        // radGroupConv.getCheckedRadioButtonId() --> -1 if nothing is onContextItemSelected()
        // --> R.id.radBtnLbsToKgs if that radio button is checked
        // --> R.id.radBtnKgsToLbs if the other radio button is checked

        //First, set up a button click listener for btnConvertWt
        //In that listener, do the following:
        //Display an error message if no radio button is checked (id is -1)
        //Display an error message if editTextInputWt is empty
        //If Pounds to Kilos is checked and parsed inputWt is > 1000, display an error message saying input weight in pounds must be <= 1000
        //Otherwise compute outputWtInKilos = inputWt/2.2
        //If Kilos to Pounds is checked and parsed inputWt is > 500, display an error message saying input weight in pounds must be <= 500
        //Otherwise compute outputWtInPounds = inputWt*2.2
        //Display the output weight in txtViewResults using correct
        //unit as Kgs (if converting from pounds to kilos) and
        //Lbs (if converting kilos to pounds)

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radGroupConv.getCheckedRadioButtonId()==-1){
                    Toast.makeText(MainActivity.this, "No radio button is selected", Toast.LENGTH_SHORT).show();
                } else if (editTextInputWt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter weight", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double numWeight = Double.parseDouble(editTextInputWt.getText().toString());
                        if(radGroupConv.getCheckedRadioButtonId()==R.id.radBtnLbsToKgs){
                             if(numWeight > 1000){
                                 Toast.makeText(MainActivity.this, "Input weight in pounds must be <= 1000", Toast.LENGTH_SHORT).show();
                             }else{
                                 double convertedWeight = numWeight/2.2;
                                 String unit = "Kg";
                                 displayWeight(convertedWeight, txtViewResult, unit);
                             }
                        }
                        if(radGroupConv.getCheckedRadioButtonId()==R.id.radBtnKgsToLbs){
                            if(numWeight > 500){
                                Toast.makeText(MainActivity.this, "Input weight in kilos must be <= 500", Toast.LENGTH_SHORT).show();
                            }else{
                                double convertedWeight = numWeight*2.2;
                                String unit = "Lbs";
                                displayWeight(convertedWeight, txtViewResult, unit);
                            }
                        }
                    }catch (Exception ex){
                        Toast.makeText(MainActivity.this, "Illegal entry", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private static void displayWeight(double convertedWeight, TextView txtViewResult, String unit) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String roundedWeight = decimalFormat.format(convertedWeight);
        txtViewResult.setText(roundedWeight +  " " + unit);
    }
}