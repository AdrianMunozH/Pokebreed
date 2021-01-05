package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private JSONParser jd;
    private Spinner abilitySpinner;
    private Spinner spinnerattacken;
    String sNumber;


    Pokemon currentPokemon;
    //Pokemon BaseStats
    int id=0;
    String nature;
    String ability;
    String move;

    //Dv
    List<String> dvs= new ArrayList<>();

    //Egg Groups
    List<String> egg_groups= new ArrayList<>();

    //EvoUrl
    String evourl;

    //other
    boolean transferDvs=false;

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
        setContentView(R.layout.activity_pokemon_stats);



        imageView = (ImageView) findViewById(R.id.pokemonPic);
        pokemonName = (TextView) findViewById(R.id.pokemonName);
        attacks = new ArrayList<>();
        jp = new JSONParser();
        jd = new JSONParser();
        // hier kriegen wir die daten aus der letzten Activity
        Intent intent = getIntent();
        pokemon = intent.getStringExtra("pokeName");
        pokemonName.setText("You Choose: "+pokemon.substring(0,1).toUpperCase()+pokemon.substring(1));

        currentPokemon=new Pokemon(pokemon);

        //Button
        Button AllBest= findViewById(R.id.AllBest);
        Button nextBtn = findViewById(R.id.button4);

        //Checkbox
        CheckBox transferDvsCheckbox = findViewById(R.id.transCheck);

        //moves Spinner
        abilitySpinner = findViewById(R.id.spFähigkeiten);
        //attackenspinner
        spinnerattacken = findViewById(R.id.spinner_attacken);

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


        MutableLiveData pokeListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(currentPokemon.getName()));
        pokeListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadPicture(jsonObject);
                    getPokemonAbilities(jsonObject);
                    getAllAttacken(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



        MutableLiveData speciesListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getSpeciesEggGroups(currentPokemon.getName()));
        speciesListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    Log.e("onChanged", "yes" );
                    getEggGroups(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        MutableLiveData evoChainListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getEvolutionChainUrl(currentPokemon.getName()));
        evoChainListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    getEvolutionChainUrl(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



        // Attacken

        //----------------------Anfang Attacken Spinner
        spinnerattacken.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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




        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(dvs.isEmpty()) {
                    dvs.add(0, KPSpinner.getSelectedItem().toString());
                    dvs.add(1, AtkSpinner.getSelectedItem().toString());
                    dvs.add(2, DefSpinner.getSelectedItem().toString());
                    dvs.add(3, SpAtkSpinner.getSelectedItem().toString());
                    dvs.add(4, SpDefSpinner.getSelectedItem().toString());
                    dvs.add(5, SpeSpinner.getSelectedItem().toString());

                }else{
                    dvs.set(0, KPSpinner.getSelectedItem().toString());
                    dvs.set(1, AtkSpinner.getSelectedItem().toString());
                    dvs.set(2, DefSpinner.getSelectedItem().toString());
                    dvs.set(3, SpAtkSpinner.getSelectedItem().toString());
                    dvs.set(4, SpDefSpinner.getSelectedItem().toString());
                    dvs.set(5, SpeSpinner.getSelectedItem().toString());

                }
                if(spinnerattacken.getSelectedItem()!=null){
                    move = spinnerattacken.getSelectedItem().toString();
                }else{
                    move="none";
                }

                ability=abilitySpinner.getSelectedItem().toString();
                nature=NatureSpinner.getSelectedItem().toString();








                setPokemon(id,nature,ability,move,dvs,egg_groups,transferDvs,evourl);

                Log.e("Current Pokemon: ",currentPokemon.getName());
                Log.e("Nature: ",currentPokemon.getNature());
                Log.e("Ability: ",currentPokemon.getAbility());
                Log.e("Move: ",currentPokemon.getMoves());

                Log.e("Egg-Group Size: ",String.valueOf(currentPokemon.getEggGroups().size()));
                Log.e("Egg-Group1: ",currentPokemon.getEggGroups().get(0));

                if(currentPokemon.getEggGroups().size()>1){
                    Log.e("Egg-Group2: ",currentPokemon.getEggGroups().get(1));
                }


                Log.e("KP: ",currentPokemon.getKp());
                Log.e("Attack: ",currentPokemon.getAttack());
                Log.e("Defense: ",currentPokemon.getDefense());
                Log.e("Special Attack: ",currentPokemon.getSpecialAttack());
                Log.e("Special Defense: ",currentPokemon.getSpecialDefense());
                Log.e("Speeed: ",currentPokemon.getSpeed());

                Log.e( "EvoChainUrl ",currentPokemon.getEvoUrl() );

                if(currentPokemon.isCalculateStats())Log.e("Transfer Dvs: ","true");
                if(!currentPokemon.isCalculateStats())Log.e("Transfer Dvs: ","false");

                if(currentPokemon.getEggGroups().get(0).equals("no-eggs")){
                    Toast t_unEggG = Toast.makeText(PokemonStats.this,"Please choose another Pokemon", Toast.LENGTH_SHORT);
                    t_unEggG.show();
                }else{
                    nextActivity();
                }


            }
        });


        transferDvsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                transferDvs=isChecked;
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
        Log.e("Selected text: ","Text at pos :"+position+" = " + text);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //attacken
    public void getAllAttacken(JSONObject jsonObject) throws JSONException {
        attacks = jd.getAllAttacks(jsonObject);
        spinnerattacken.setAdapter(new ArrayAdapter<>(PokemonStats.this,
                android.R.layout.simple_spinner_dropdown_item,attacks));

    }

    public void getPokemonAbilities(JSONObject jsonObject) throws JSONException {
        List<String> abilities = jp.getAllAbilities(jsonObject);
        abilitySpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,abilities));
    }


    public void getEggGroups(JSONObject jsonObject)throws JSONException{
        egg_groups = jp.getSpeciesEggGroups(jsonObject);
    }

    public void getEvolutionChainUrl(JSONObject jsonObject)throws JSONException{
        evourl = jp.getEvolutionChainUrl(jsonObject);
    }


    public void setPokemon(int id, String nature, String ability, String move,List<String> dvValues,List<String> eggGroups, boolean transferDvs,String evoUrl ){
        currentPokemon.setId(id);
        currentPokemon.setNature(nature);
        currentPokemon.setAbility(ability);
        currentPokemon.setMoves(move);
        currentPokemon.setCalculateStats(transferDvs);
        currentPokemon.setEggGroups(eggGroups);
        currentPokemon.setEvoUrl(evoUrl);
        //DV

            currentPokemon.setKp(dvValues.get(0));
            currentPokemon.setAttack(dvValues.get(1));
            currentPokemon.setDefense(dvValues.get(2));
            currentPokemon.setSpecialAttack(dvValues.get(3));
            currentPokemon.setSpecialDefense(dvValues.get(4));
            currentPokemon.setSpeed(dvValues.get(5));



    }

    public void nextActivity(){
        Intent intent = new Intent(this,ResultPokemon.class);
        intent.putExtra("childSelection",currentPokemon);
        intent.putExtra("transferDVs",transferDvs);
        startActivity(intent);
    }
}