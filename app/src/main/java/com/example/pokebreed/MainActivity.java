package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
    private String currentPokemon;
    JSONParser jp = new JSONParser();

    //Button Attacken // Popupfenster attacken activity
    private Button attack_button;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(MainMenu.PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(MainMenu.PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        } else {
            // Theme.AppCompat.Light.NoActionBar
            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //API Instance für komplette App
        // !!!!!!!!!! muss nur einmal mit this aufgerufen werden !!!!!!!!!!!!!!!!!
        APIRequests.getInstance(this);
        // die listen müssen init werden auch wenn wir sie später ersetzen
        pokemons = new ArrayList<>();

        // Das beides muss immer nacheinander passieren.
        MutableLiveData allPokelistener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemonList());
        allPokelistener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    getAllPokemon(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.pokemonPicMain);

        //Start spinner
        //Assign variable
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               currentPokemon = parent.getItemAtPosition(position).toString();
               apiLoadPicture();
               // das funktioniert nicht wirklich außer wir machen immer das erste Element unserer Liste leer.
               if (position == 0){
                   //Display toast message
                   Toast.makeText(getApplicationContext(),
                          "Please Select one",Toast.LENGTH_SHORT).show();
                   //set empty value on textview

               }else{
                   //get selected value
                   String sNumber = parent.getItemAtPosition(position).toString();
                   //set selected value on textview




               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });

    }


    private void loadStats(JSONObject jsonObject) throws JSONException {
        TextView tvHP = (TextView) findViewById(R.id.tvHP);
        TextView tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        TextView tvAttack = (TextView) findViewById(R.id.tvAttack);
        TextView tvDefense = (TextView) findViewById(R.id.tvDefense);
        TextView tvSA = (TextView) findViewById(R.id.tvSA);
        TextView tvSD = (TextView) findViewById(R.id.tvSD);
        TextView tvType1 = (TextView) findViewById(R.id.tvType1);
        TextView tvType2 = (TextView) findViewById(R.id.tvType2);
        TextView[] tvArr = {tvHP,tvAttack,tvDefense,tvSA,tvSD,tvSpeed};
        List<String> stats = jp.getBaseStats(jsonObject);
        List<String> types = jp.getType(jsonObject);
        String[] statStrings = getResources().getStringArray(R.array.Stats);
        int prevstat=0;
        int roundpassed=0;
        int defaultColor= tvArr[0].getCurrentTextColor();;
        for (TextView t:tvArr) {
            t.setTextColor(defaultColor);
        }
        for (int i = 0; i < tvArr.length ; i++) {
            tvArr[i].setText(statStrings[i] + " " + stats.get(i));

            if(i==0) {

                tvArr[i].setTextColor(getColor(android.R.color.holo_red_light));
                prevstat=Integer.parseInt(stats.get(i));
                roundpassed++;

            }else if(prevstat<Integer.parseInt(stats.get(i))){

                //Log.e( "prevstat set to:",prevstat+"(Value="+ statStrings[i] + " )");
                prevstat=Integer.parseInt(stats.get(i));
                tvArr[i].setTextColor(getColor(android.R.color.holo_red_light));
                tvArr[i-roundpassed].setTextColor(defaultColor);

            }else{
                roundpassed++;
            }

            Log.e( "loadStats: ",statStrings[i]+""+prevstat+"" );
        }
        prevstat=0;
        roundpassed=0;
        if(types.size() == 1) {
            tvType2.setText("");
            tvType1.setText("Type: " + types.get(0));
        } else if(types.size() == 2) {
            tvType1.setText("Type 1: " + types.get(0));
            tvType2.setText("Type 2: " + types.get(1));
        }

    }

    private void apiLoadPicture() {
        MutableLiveData picListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(currentPokemon));
        picListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadPicture(jsonObject);
                    loadStats(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadPicture(JSONObject jsonObject) throws JSONException {
        // source code und doc ---  https://github.com/bumptech/glide
        Glide.with(this).load(jp.getPicture(jsonObject)).into(imageView);
    }
    private void baseStats(JSONObject jsonObject) throws JSONException {
        List arr = jp.getBaseStats(jsonObject);

    }
    private void nextActivity() {
        Intent intent = new Intent(this,PokemonStats.class);
        intent.putExtra("pokeName", currentPokemon.toString());
        startActivity(intent);
    }
    public void getAllPokemon(JSONObject jsonObject) throws JSONException {
        pokemons = jp.getAllPoke(jsonObject);
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,pokemons));
    }





}