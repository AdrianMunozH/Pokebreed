package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    private Spinner movesSpinner;
    private Button attack_button;
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


        //Button
        Button AllBest= findViewById(R.id.AllBest);
        //moves Spinner
        movesSpinner = findViewById(R.id.spFähigkeiten);

        // Spinner
        final Spinner KPSpinner= findViewById(R.id.spinnerKP);
        final Spinner AtkSpinner= findViewById(R.id.spinnerAtk);
        final Spinner DefSpinner= findViewById(R.id.spinnerDef);
        final Spinner SpAtkSpinner= findViewById(R.id.spinnerSpAtk);
        final Spinner SpDefSpinner= findViewById(R.id.spinnerSpDef);
        final Spinner SpeSpinner= findViewById(R.id.spinnerSpe);

        final Spinner NatureSpinner = findViewById(R.id.spWesen);
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

        ArrayAdapter<CharSequence> natureAdapter = ArrayAdapter.createFromResource(this,R.array.Natures,android.R.layout.simple_spinner_item);
        natureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NatureSpinner.setAdapter(natureAdapter);









        //SetAllBestBTN

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
                    getPokemonAbilities(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //----------------------Anfang Attacken pop up fenster
        attack_button = (Button) findViewById(R.id.btnAttack);
        attack_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("popup", "hat geklappt");
                final Dialog dialog = new Dialog(PokemonStats.this);
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

    public void loadPicture(JSONObject jsonObject) throws JSONException {
        // source code und doc ---  https://github.com/bumptech/glide
        Glide.with(this).load(jp.getPicture(jsonObject)).into(imageView);
    }

    //Für die DV Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getPokemonAbilities(JSONObject jsonObject) throws JSONException {
        List<String> abilities = jp.getAllAbilities(jsonObject);
        movesSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,abilities));
    }
}