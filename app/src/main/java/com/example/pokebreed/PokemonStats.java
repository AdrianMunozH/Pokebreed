package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
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

public class PokemonStats extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView imageView;
    private List<String> attacks;
    private String pokemon;
    private TextView pokemonName;
    private JSONParser jp;
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



        //DVValues Spinner
        final Spinner KPSpinner= findViewById(R.id.spinnerKP);
        final Spinner AtkSpinner= findViewById(R.id.spinnerAtk);
        final Spinner DefSpinner= findViewById(R.id.spinnerDef);
        final Spinner SpAtkSpinner= findViewById(R.id.spinnerSpAtk);
        final Spinner SpDefSpinner= findViewById(R.id.spinnerSpDef);
        final Spinner SpeSpinner= findViewById(R.id.spinnerSpe);
        //Adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.DVValues,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        KPSpinner.setAdapter(adapter);
        KPSpinner.setOnItemSelectedListener(this);
        AtkSpinner.setAdapter(adapter);
        AtkSpinner.setOnItemSelectedListener(this);
        DefSpinner.setAdapter(adapter);
        DefSpinner.setOnItemSelectedListener(this);
        SpAtkSpinner.setAdapter(adapter);
        SpAtkSpinner.setOnItemSelectedListener(this);
        SpDefSpinner.setAdapter(adapter);
        SpDefSpinner.setOnItemSelectedListener(this);
        SpeSpinner.setAdapter(adapter);
        SpeSpinner.setOnItemSelectedListener(this);

        //SetAllBestBTN
        Button AllBest= findViewById(R.id.AllBest);
        AllBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KPSpinner.setSelection(5);
                AtkSpinner.setSelection(5);
                DefSpinner.setSelection(5);
                SpAtkSpinner.setSelection(5);
                SpDefSpinner.setSelection(5);
                SpeSpinner.setSelection(5);

            }
        });


        MutableLiveData pokeListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemon));
        pokeListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadPicture(jsonObject);
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

    //Für die DV Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}