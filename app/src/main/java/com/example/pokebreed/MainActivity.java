package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;

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
    JSONParser jp = new JSONParser();

    //Button Attacken // Popupfenster attacken activity
    private Button attack_button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //API Instance für komplette App
        // !!!!!!!!!! muss nur einmal mit this aufgerufen werden !!!!!!!!!!!!!!!!!
        APIRequests.getInstance(this);
        // die listen müssen init werden auch wenn wir sie später ersetzen
        pokemons = new ArrayList<>();

        // Das beides muss immer nacheinander passieren.
        APIRequests.getInstance().requestGet("pokemon?limit=5");
        APIRequests.getInstance().listen.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    getAllPokemon(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //Start spinner
        //Assign variable
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // das funktioniert nicht wirklich außer wir machen immer das erste Element unserer Liste leer.
               if (position == 0){
                   //Display toast message
                   Toast.makeText(getApplicationContext(),
                          "Please Select one",Toast.LENGTH_SHORT).show();
                   //set empty value on textview
                   textView.setText("");
               }else{
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
                testNewApi();
            }
        });
        //ende spinner

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });


        //----------------------Anfang Attacken pop up fenster
        attack_button = (Button) findViewById(R.id.button_attacken);
        attack_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
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

    private void nextActivity() {
        Intent intent = new Intent(this,ProtoAttackWesen.class);
        intent.putExtra("pokeName",textView.getText().toString());
        startActivity(intent);
    }
    public void getAllPokemon(JSONObject jsonObject) throws JSONException {

        pokemons = jp.getAllPoke(jsonObject);
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,pokemons));
    }
    // total sinnlos, war nur dafür da um z testen ob das richtige jsonobject geladen wird -- delete later
    public void testNewApi() {
        APIRequests.getInstance(this).requestGet(APIRequests.getInstance().getPokemonList());
        APIRequests.getInstance().listen.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                Log.e("b4OnChange","succ");
                try {
                    Log.e("inChange","succ");
                    pokemons = jp.getAllPoke(jsonObject);
                    spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,pokemons));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}