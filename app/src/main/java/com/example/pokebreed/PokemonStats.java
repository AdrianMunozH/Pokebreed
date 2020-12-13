package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokemonStats extends AppCompatActivity {
    private ImageView imageView;
    private List<String> attacks;
    private String pokemon;
    private TextView pokemonName;
    private JSONParser jp;
    private Spinner spinnerwes;
    private List<String> wesen = new ArrayList<>( Arrays.asList("Robust",
            "Sanft",
            "Zaghaft",
            "Kauzig",
            "Ernst",
            "Solo: -Vert. +Angr.",
            "Hart: -Sp.Angr. +Angr.",
            "Frech: -Sp.Vert. +Angr.",
            "Mutig: -Init. +Angr.",
            "Kühn: -Angr. +Vert.",
            "pfiffig: -Sp.Angr. +Vert.",
            "Lasch: -Sp.Vert. +Vert.",
            "Locker: -Init. +Vert.",
            "Mäßig: -Angr. +Sp.Angr.",
            "Mild: -Vert. +Sp.Angr.",
            "Hitzig: -Sp.Vert. +Sp.Angr.",
            "Ruhig: -Init. +Sp.Angr.",
            "Still: -Angr. +Sp.Vert."),
            "Zart: -Vert. +Sp.Vert.",
            "Sacht: -Sp.Angr. +Sp.Vert.",
            "Forsch: -Init. +Sp.Vert.",
            "Scheu: -Angr. +Init.",
            "Hastig: -Vert. +Init.",
            "Froh: -Sp.Angr. +Init.",
            "Naiv: -Sp.Vert. +Init."
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_stats);

        imageView = (ImageView) findViewById(R.id.pokemonPic);
        pokemonName = (TextView) findViewById(R.id.pokemonName);
        attacks = new ArrayList<>();
        jp = new JSONParser();
        // hier kriegen wir die daten aus der letzten Activity
        Intent intent = getIntent();
        pokemon = intent.getStringExtra("pokeName");

        pokemonName.setText(pokemon);

        APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemon));
        APIRequests.getInstance().listen.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadPicture(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        spinnerwes = findViewById(R.id.spWesen);
        spinnerwes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                sNumber = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loadPicture(JSONObject jsonObject) throws JSONException {
        // source code und doc ---  https://github.com/bumptech/glide
        Glide.with(this).load(jp.getPicture(jsonObject)).into(imageView);
    }


}