package com.example.pokebreed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;

public class pop_up_attacken extends AppCompatActivity  {

    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    String sNumber; String sNumber2;
    String sNumber3; String sNumber4;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinner1 = findViewById(R.id.spinner_1);
        spinner2 = findViewById(R.id.spinner_2);
        spinner3 = findViewById(R.id.spinner_3);
        spinner4 = findViewById(R.id.spinner_4);



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // das funktioniert nicht wirklich außer wir machen immer das erste Element unserer Liste leer.
                if (position == 0) {
                    //Display toast message
                    Toast.makeText(getApplicationContext(),
                            "Please Select one", Toast.LENGTH_SHORT).show();
                    //set empty value on textview

                } else {
                    //get selected value
                    String sNumber = parent.getItemAtPosition(position).toString();
                    //set selected value on textview

                }

                sNumber = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner: 2
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // das funktioniert nicht wirklich außer wir machen immer das erste Element unserer Liste leer.
                if (position == 0) {
                    //Display toast message
                    Toast.makeText(getApplicationContext(),
                            "Please Select one", Toast.LENGTH_SHORT).show();
                    //set empty value on textview

                } else {
                    //get selected value
                    String sNumber2 = parent.getItemAtPosition(position).toString();
                    //set selected value on textview

                }

                sNumber2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner3
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // das funktioniert nicht wirklich außer wir machen immer das erste Element unserer Liste leer.
                if (position == 0) {
                    //Display toast message
                    Toast.makeText(getApplicationContext(),
                            "Please Select one", Toast.LENGTH_SHORT).show();
                    //set empty value on textview

                } else {
                    //get selected value
                    String sNumber3 = parent.getItemAtPosition(position).toString();
                    //set selected value on textview

                }

                sNumber3 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner4
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // das funktioniert nicht wirklich außer wir machen immer das erste Element unserer Liste leer.
                if (position == 0) {
                    //Display toast message
                    Toast.makeText(getApplicationContext(),
                            "Please Select one", Toast.LENGTH_SHORT).show();
                    //set empty value on textview

                } else {
                    //get selected value
                    String sNumber4 = parent.getItemAtPosition(position).toString();
                    //set selected value on textview

                }

                sNumber4 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
