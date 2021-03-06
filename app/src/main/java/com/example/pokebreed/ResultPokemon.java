package com.example.pokebreed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResultPokemon extends AppCompatActivity {
    private Pokemon child;
    private boolean transferDVs;
    private JSONParser jp;


    List<String> possible_dadMons = new ArrayList<>();
    List<String> possible_mumMons = new ArrayList<>();
    List<String> parent_mons_withAttack = new ArrayList<>();
    List<String> dittoList=new ArrayList<>();
    List<String> MotherItemsList = new ArrayList<>();
    List<String> FatherItemList = new ArrayList<>();
    List<String> FullItemList = new ArrayList<>();

    //Spinner
    Spinner Mother_Spinner;
    Spinner Father_Spinner;
    Spinner Mother_Item_Spinner;
    Spinner Father_Item_Spinner;

    //DV DisplayTextViews
        //Mother

    TextView MotherNature;
    TextView MotherAbility;
    TextView MotherMove;


    String SelectedMother;
    int SelectedMotherPos;

    private ImageView MotherImage;
    private ImageView MotherItemImage;
        //Father
    TextView FatherKP;
    TextView FatherAtk;
    TextView FatherDef;
    TextView FatherSPAtk;
    TextView FatherSPDef;
    TextView FatherSpe;
    TextView FatherNature;
    TextView FatherAbility;
    TextView FatherMove;

    //Parents
    Pokemon mother;
    Pokemon father;



    private ImageView FatherImage;
    private ImageView FatherItemImage;
    boolean monsupdated;
    boolean canlearnmove;
    boolean useEverstone=false;


    //Adapter
    ArrayAdapter FatherAdapter;
    ArrayAdapter MotherAdapter;
    ArrayAdapter FatherItemAdapter;
    ArrayAdapter MotherItemAdapter;

    //Button
    Button exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(MainMenu.PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(MainMenu.PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        } else {
            // Theme.AppCompat.Light.NoActionBar
            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // Pokemon Objekt aus der Pokemonstats enstanden
        child = (Pokemon) getIntent().getSerializableExtra("childSelection");
        transferDVs =getIntent().getBooleanExtra("transferDVs",false);
        useEverstone = getIntent().getBooleanExtra("useEverstone",false);
        Log.e("Result Child Name: ",child.getName());
        Toast t_selectMother = Toast.makeText(this,"Please Select Your Mother", Toast.LENGTH_SHORT);
        t_selectMother.show();

        jp = new JSONParser();
        monsupdated=false;


        //setButton
        exit=findViewById(R.id.ExitandSafe);

        //SetSpinner
        Mother_Spinner= findViewById(R.id.MotherSpinner);
        Father_Spinner= findViewById(R.id.FatherSpinner);

        FatherAdapter = new ArrayAdapter(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item);
        FatherAdapter.setNotifyOnChange(true);
        Father_Spinner.setAdapter(FatherAdapter);
        MotherAdapter= new ArrayAdapter(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item);
        MotherAdapter.setNotifyOnChange(true);
        Mother_Spinner.setAdapter(MotherAdapter);

        Mother_Item_Spinner=findViewById(R.id.MotherItemSpinner);
        Father_Item_Spinner=findViewById(R.id.FatherItemSpinner);

        FatherItemAdapter= new ArrayAdapter(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item);
        FatherItemAdapter.setNotifyOnChange(true);
        Father_Item_Spinner.setAdapter(FatherItemAdapter);

        MotherItemAdapter= new ArrayAdapter(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item);
        MotherItemAdapter.setNotifyOnChange(true);
        Mother_Item_Spinner.setAdapter(MotherItemAdapter);


        //SetTextViews
        MotherNature = findViewById(R.id.MotherNature);
        MotherAbility = findViewById(R.id.m_ability);
        MotherMove = findViewById(R.id.m_attack);



        FatherKP = findViewById(R.id.f_KP_Value);
        FatherAtk = findViewById(R.id.f_Atk_Value);
        FatherDef = findViewById(R.id.f_Def_Value);
        FatherSPAtk = findViewById(R.id.f_SPAtk_Value);
        FatherSPDef = findViewById(R.id.f_SpDef_Value);
        FatherSpe = findViewById(R.id.f_Spe_Value);
        FatherNature=findViewById(R.id.FatherNature);
        FatherAbility = findViewById(R.id.f_ability);
        FatherMove = findViewById(R.id.f_attack);


        //ImageViews
        FatherImage =(ImageView) findViewById(R.id.FatherpokemonPic);
        MotherImage=(ImageView) findViewById(R.id.MotherpokemonPic);
        FatherItemImage= findViewById(R.id.fItemImage);
        MotherItemImage = findViewById(R.id.mItemImage);


        MotherItemsList.add(getResources().getString(R.string.selectItem));
        FatherItemList.add(getResources().getString(R.string.selectItem));
        possible_dadMons.add(getResources().getString(R.string.selectMFirst));





        getDVValues();
        getItems();

        if(FullItemList.isEmpty()){
            MotherItemsList.clear();
            MotherItemsList.add(getResources().getString(R.string.noItemsNeeded));
            FatherItemList.clear();
            FatherItemList.add(getResources().getString(R.string.noItemsNeeded));
        }

        getMotherMons();
        getFatherMons();
        FatherAdapter.add(possible_dadMons);
        FatherAdapter.notifyDataSetChanged();

        getDittoMons();





        Mother_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    Log.e( "position==0: ","FatherAdapter cleared" );
                    FatherAdapter.clear();
                    FatherAdapter.add(getResources().getString(R.string.selectMFirst));
                    FatherAdapter.notifyDataSetChanged();
                    return;
                }


                //  Vater ist abhängig von der Auswahl der Mutter und wird hier geregelt
                String selectedMonName = parent.getItemAtPosition(position).toString();
                if(selectedMonName.equals("ditto")){
                    MotherAbility.setText(getResources().getString(R.string.noMatter));
                    FatherAbility.setText(child.getAbility());
                    MotherMove.setText("-");


                    FatherAdapter.clear();

                    for (String s: dittoList) {
                        FatherAdapter.add(s);
                        FatherAdapter.notifyDataSetChanged();
                    }


                }else {


                    FatherAdapter.clear();

                    for (String s : possible_dadMons) {
                        FatherAdapter.add(s);
                        FatherAdapter.notifyDataSetChanged();
                    }

                    MotherAbility.setText(child.getAbility());

                }//end else


                SelectedMother=selectedMonName;
                SelectedMotherPos=position;


                //Set Mother Image
                setMotherImage(SelectedMother);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Father_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)return;
                if(!Mother_Spinner.getSelectedItem().equals("ditto")){
                    MotherAbility.setText(child.getAbility());
                    FatherAbility.setText(getResources().getString(R.string.noMatter));
                    mutableAttack(parent.getItemAtPosition(position).toString());
                }else{
                    FatherMove.setText(child.getMoves());
                }
                setFatherImage(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Item spinner für die Mutter
        Mother_Item_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e( "MotherItem Selected: ", parent.getItemAtPosition(position).toString());
                //If Everstone
                if(parent.getItemAtPosition(position).equals("everstone")){
                    MotherNature.setText(child.getNature());
                    Father_Item_Spinner.setSelection(1);
                    FatherItemImage.setImageDrawable(null);
                    FatherItemAdapter.clear();
                    for (String s:FullItemList) {
                        FatherItemAdapter.add(s);
                        FatherItemAdapter.notifyDataSetChanged();
                    }
                    if (FullItemList.contains("destiny-knot"))FatherItemAdapter.remove("everstone");
                }else if(parent.getItemAtPosition(position).equals("destiny-knot")){
                    Father_Item_Spinner.setSelection(1);
                    MotherNature.setText(getResources().getString(R.string.noMatter));
                    FatherItemImage.setImageDrawable(null);
                    FatherItemAdapter.clear();
                    for (String s:FullItemList) {
                        FatherItemAdapter.add(s);

                    }
                    FatherItemAdapter.notifyDataSetChanged();

                    if(FullItemList.contains("destiny-knot"))FatherItemAdapter.remove("destiny-knot");
                }else if(position==1){
                    MotherItemImage.setImageDrawable(null);
                }else{

                    MotherNature.setText("-");
                    FatherItemAdapter.clear();
                    for (String s:FullItemList) {
                        FatherItemAdapter.add(s);

                    }

                    FatherItemAdapter.notifyDataSetChanged();
                }

                setMotherItemImage(parent.getItemAtPosition(position).toString());
                if(Father_Item_Spinner.getSelectedItem()!=null)setFatherItemImage(Father_Item_Spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        // Item spinner für den Vater
        Father_Item_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e( "FatherItem Selected: ", parent.getItemAtPosition(position).toString());
                //If Everstone
                if(parent.getItemAtPosition(position).equals("everstone")){
                    if(FullItemList.size()>2)Mother_Item_Spinner.setSelection(1);
                    FatherNature.setText(child.getNature());
                    MotherItemImage.setImageDrawable(null);

                    MotherItemAdapter.clear();
                    for (String s:FullItemList) {
                        MotherItemAdapter.add(s);
                    }
                    MotherAdapter.remove("everstone");
                    MotherAdapter.notifyDataSetChanged();

                    if(FullItemList.contains("everstone"))MotherAdapter.remove("everstone");

                }else if(parent.getItemAtPosition(position).equals("destiny-knot")){
                    if(FullItemList.size()>2)Mother_Item_Spinner.setSelection(1);
                    FatherNature.setText(getResources().getString(R.string.noMatter));
                    MotherItemImage.setImageDrawable(null);

                    MotherItemAdapter.clear();
                    for (String s:MotherItemsList) {
                        MotherItemAdapter.add(s);
                        MotherAdapter.notifyDataSetChanged();

                    }
                    if(FullItemList.contains("destiny-knot"))MotherAdapter.remove("destiny-knot");


                }else{
                    FatherNature.setText("-");
                    Log.e( "FatherItemList ",MotherItemsList.toString() );
                    Log.e( "FullItemList ",FullItemList.toString() );
                    MotherItemAdapter.clear();
                    for (String s:FullItemList) {
                        MotherItemAdapter.add(s);
                    }
                }

                setFatherItemImage(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Das fertige Pokemon wird in die JSON Datei "pokemonHistory.json" gespeichert bzw hinzugefügt.
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean start_intent=false;
                start_intent=setParentObjects();
                if(start_intent){
                    try {
                        // fügt das pokemon der datei hinzu
                        addPokemonToHistory(child);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // geht ins menü
                    nextActivity();
                }else{
                    showError();
                }

            }
        });

    }
    public JSONObject loadData() {
        FileInputStream fileInputStream = null;
        JSONObject j = new JSONObject();
        try {
            fileInputStream = openFileInput(JSONParser.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String outText;
            while((outText = bufferedReader.readLine()) != null) {
                stringBuilder.append(outText).append("\n");
            }
            j = new JSONObject(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }


    public void getEggGroups(JSONObject jsonObject)throws JSONException{

            possible_dadMons = jp.getPokemonOfEggGroup(jsonObject);
            possible_dadMons.set(0,getResources().getString(R.string.selectFather));

    }

    public void getPossibleMom(JSONObject jsonObject)throws JSONException{

        possible_mumMons = jp.getEvoPokemon(jsonObject);
        possible_mumMons.add(0,getResources().getString(R.string.selectMother));
        possible_mumMons.add("ditto");

        for (String s:possible_mumMons) {
         MotherAdapter.add(s);
        }

    }

    public void getPossibleDittoMons(JSONObject jsonObject) throws JSONException {
        dittoList = jp.getEvoPokemon(jsonObject);
        dittoList.add(0,getResources().getString(R.string.selectFather));
    }

    public void getItem(JSONObject jsonObject)throws JSONException{

        FullItemList.add(jp.getItem(jsonObject));

        Log.e( "FullItemList",FullItemList.toString() );


        MotherItemsList.add(jp.getItem(jsonObject));
        FatherItemList.add(jp.getItem(jsonObject));

    }

    public void getMotherMons(){


        MutableLiveData PokemonEvoListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getEvoPokemon(child.getEvoUrl()));
        PokemonEvoListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    Log.e("onChanged", "Mother" );
                    getPossibleMom(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getFatherMons(){
        //get All Pokemon With EggGroup 1 & 2 put them in possilble_mons List
        if(child.getEggGroups().size()==1){

            MutableLiveData PokemonEggGroupListener0 = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemonOfEggGroup(child.getEggGroups().get(0)));
            PokemonEggGroupListener0.observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject jsonObject) {
                    try {
                        Log.e("onChanged", "PokemonEggGroupListener2" );
                        getEggGroups(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }else{

            //EggGroup1
            MutableLiveData PokemonEggGroupListener1 = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemonOfEggGroup(child.getEggGroups().get(0)));
            PokemonEggGroupListener1.observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject jsonObject) {
                    try {
                        Log.e("onChanged", "PokemonEggGroupListener1" );
                        getEggGroups(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            //EggGroup2
            MutableLiveData PokemonEggGroupListener2 = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemonOfEggGroup(child.getEggGroups().get(1)));
            PokemonEggGroupListener2.observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject jsonObject) {
                    try {
                        Log.e("onChanged", "PokemonEggGroupListener2" );
                        getEggGroups(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

        }//end else
    }

    public void getDittoMons(){
        MutableLiveData PokemonEvoListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getEvoPokemon(child.getEvoUrl()));
        PokemonEvoListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    Log.e("onChanged", "Mother" );
                    getPossibleDittoMons(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getDVValues(){
        if(transferDVs){


            FatherKP.setText(child.getKp());
            FatherAtk.setText(child.getAttack());
            FatherDef.setText(child.getDefense());
            FatherSPAtk.setText(child.getSpecialAttack());
            FatherSPDef.setText(child.getSpecialDefense());
            FatherSpe.setText(child.getSpeed());
        }else{



            FatherKP.setText("-");
            FatherAtk.setText("-");
            FatherDef.setText("-");
            FatherSPAtk.setText("-");
            FatherSPDef.setText("-");
            FatherSpe.setText("-");



        }
    }

    public void getItems(){

        //Destiny Knot
        if(transferDVs){
            MutableLiveData DestinyKnotListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getItem(257));
            DestinyKnotListener.observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject jsonObject) {
                    try {
                        Log.e("onChanged", "Item" );
                        getItem(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        if(useEverstone){
            MutableLiveData EverstoneListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getItem(206));
            EverstoneListener.observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject jsonObject) {
                    try {
                        Log.e("onChanged", "Item" );
                        getItem(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }) ;


            FullItemList.add(0,getResources().getString(R.string.selectItem));
        }
        FatherItemAdapter.clear();
        for (String s:FatherItemList) {
            FatherItemAdapter.add(s);


        }

        MotherItemAdapter.clear();
        for (String s:MotherItemsList) {
            MotherItemAdapter.add(s);

        }


        Log.e( "MotherItemList",MotherItemsList.toString() );



    }

    public void loadPicture(JSONObject jsonObject, ImageView imageView) throws JSONException {
        // source code und doc ---  https://github.com/bumptech/glide
        Glide.with(this).load(jp.getPicture(jsonObject)).into(imageView);
    }

    public void loadItemPicture(JSONObject jsonObject, ImageView imageView) throws JSONException {
        // source code und doc ---  https://github.com/bumptech/glide
        Glide.with(this).load(jp.getPictureItem(jsonObject)).into(imageView);
    }

    public void setMotherImage(String pokemonName){
        MutableLiveData pokeMotherPicListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemonName));
        pokeMotherPicListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadPicture(jsonObject, MotherImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void setMotherItemImage(String itemImage){
        MutableLiveData pokeMotherPicListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getItem(itemImage));
        pokeMotherPicListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadItemPicture(jsonObject, MotherItemImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void setFatherItemImage(String itemImage){
        MutableLiveData pokeMotherPicListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getItem(itemImage));
        pokeMotherPicListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadItemPicture(jsonObject, FatherItemImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void setFatherImage(String pokemonName){
        MutableLiveData pokeMotherPicListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemonName));
        pokeMotherPicListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadPicture(jsonObject, FatherImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void getAllMoves(JSONObject jsonObject) throws JSONException {
        List<String> attacks;

        attacks = jp.getAllAttacks(jsonObject);

        Log.e( "getAllAttacken: ",attacks.toString() );

        if(attacks.contains(child.getMoves())){
            Log.e( Father_Item_Spinner.getSelectedItem().toString()+"can learn:",child.getMoves() );
            FatherMove.setText(child.getMoves());
            MotherMove.setText("-");

        }else{
            MotherMove.setText(child.getMoves());
            FatherMove.setText("-");
        }
    }


    public void mutableAttack(String pokemon){


        MutableLiveData pokemonMoveListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemon));
        pokemonMoveListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    getAllMoves(jsonObject);

                    Log.e( "getAllAttacken: ","erfolg" );
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e( "getAllAttacken: ","error" );
                }

            }
        });


    }


    public boolean setParentObjects(){

        if(Mother_Spinner.getSelectedItemPosition()!=0){
            mother = new Pokemon(Mother_Spinner.getSelectedItem().toString());
        }else{
            return false;
        }
         if(Father_Spinner.getSelectedItemPosition()!=0){
             father = new Pokemon(Father_Spinner.getSelectedItem().toString());
         }else{
             return false;
         }

         //Items
        if(FullItemList.size()==1 || FullItemList.size()==0){
            father.setItem("-");
            mother.setItem("-");
        }else if(FullItemList.size()==2){
            if(Father_Item_Spinner.getSelectedItemPosition()==0){
                father.setItem("-");
                mother.setItem(Mother_Item_Spinner.getSelectedItem().toString());
            }else if(Mother_Item_Spinner.getSelectedItemPosition()==0){
                mother.setItem("-");
                father.setItem(Father_Item_Spinner.getSelectedItem().toString());
            }else{
                return false;
            }
        }else if(FullItemList.size()==3){
                if(Father_Item_Spinner.getSelectedItemPosition()==0 || Mother_Item_Spinner.getSelectedItemPosition()==0){
                    return false;
                }else{
                    mother.setItem(Mother_Item_Spinner.getSelectedItem().toString());
                    father.setItem(Father_Item_Spinner.getSelectedItem().toString());
                }
        }


        if(child.isCalculateStats()) {
            //Mother DVs
            mother.setKp(child.getKp());
            mother.setAttack(child.getAttack());
            mother.setDefense(child.getDefense());
            mother.setSpecialAttack(child.getSpecialAttack());
            mother.setSpecialDefense(child.getSpecialDefense());
            mother.setSpeed(child.getSpeed());

            //fatherDvs
            father.setKp(child.getKp());
            father.setAttack(child.getAttack());
            father.setDefense(child.getDefense());
            father.setSpecialAttack(child.getSpecialAttack());
            father.setSpecialDefense(child.getSpecialDefense());
            father.setSpeed(child.getSpeed());
        }else{
            mother.setKp("-");
            mother.setAttack("-");
            mother.setDefense("-");
            mother.setSpecialAttack("-");
            mother.setSpecialDefense("-");
            mother.setSpeed("-");

            //fatherDvs
            father.setKp("-");
            father.setAttack("-");
            father.setDefense("-");
            father.setSpecialAttack("-");
            father.setSpecialDefense("-");
            father.setSpeed("-");
        }

        //get Nature
        mother.setNature(MotherNature.getText().toString());
        father.setNature(FatherNature.getText().toString());
        //get Move
        mother.setMoves(MotherMove.getText().toString());
        father.setMoves(FatherMove.getText().toString());

        //getAbility
        if(Mother_Spinner.getSelectedItem().equals("ditto")){
            father.setAbility(child.getAbility());
            mother.setAbility("-");
        }else {
            mother.setAbility(child.getAbility());
            father.setAbility("-");
        }


        child.setMother(mother);
        child.setFather(father);

        return true;



    }


    private void nextActivity() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void showError(){
        Toast error = Toast.makeText(this,getResources().getString(R.string.checkValues), Toast.LENGTH_SHORT);
        error.show();
    }


    // History
    public void addPokemonToHistory(Pokemon pokemon) throws JSONException {

        rewriteHistory(loadData(),jp.pokemonToJson(pokemon));
        Log.e("pokemon json",jp.pokemonToJson(pokemon).toString());
    }

    public void rewriteHistory(JSONObject file, JSONObject pokemon) throws JSONException {
        JSONArray jsonArray = file.getJSONArray("pokemonHistory");
        jsonArray.put(pokemon);
        Log.e("rewriteHitory",file.toString() + " : wurde hinzugefügt" + pokemon.toString());
        saveData(file.toString());
    }
    public void saveData(String json) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(JSONParser.FILE_NAME,MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
