package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Kommentar von Daniel - ASDf
    public List<String> pokemons;
    String selectedMon = "";

    //Initialise variable
    private Spinner spinner;
    private TextView textView;

    //Button Attacken // Popupfenster attacken activity
    private Button attack_button;
    public Activity context;

    //create constructor
    public MainActivity(Activity context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //API Instance für komplette App
        APIRequests.getInstance(this);


        try {
            if (APIRequests.getInstance().getAllPoke() != null)
                pokemons = APIRequests.getInstance().getAllPoke();
            else {
                pokemons = new ArrayList<>();
                pokemons.add("test1");
                pokemons.add("test2");
                pokemons.add("test3");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Start spinner
        //Assign variable
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, pokemons));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Display toast message
                    Toast.makeText(getApplicationContext(),
                            "Please Select one", Toast.LENGTH_SHORT).show();
                    //set empty value on textview
                    textView.setText("");
                } else {
                    //get selected value
                    String sNumber = parent.getItemAtPosition(position).toString();
                    //set selected value on textview
                    textView.setText(sNumber);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pokemons = APIRequests.getInstance().getAllPoke();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, pokemons));
            }
        });
        //ende spinner

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIRequests.getInstance().getEggGroup("1");
            }
        });

        //----------------------Anfang Attacken pop up fenster
        attack_button = (Button) findViewById(R.id.button_attacken);
        attack_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                //set content view
                dialog.setContentView(R.layout.attacken_pop_up);
                //Initialise width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialise height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width, height);
                //Show dialog
                dialog.show();


                Button btUpdate = dialog.findViewById(R.id.bt_update);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();
                    }

                });
            }
        });
        //----------------Ende popup fenster

    }
}