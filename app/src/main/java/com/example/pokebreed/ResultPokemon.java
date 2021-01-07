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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    TextView MotherKP;
    TextView MotherAtk;
    TextView MotherDef;
    TextView MotherSPAtk;
    TextView MotherSPDef;
    TextView MotherSpe;
    TextView MotherNature;
    TextView MotherAbility;
    TextView MotherMove;


    String SelectedMother;
    int SelectedMotherPos;

    private ImageView MotherImage;
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
    boolean monsupdated;
    boolean canlearnmove;


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
        child = (Pokemon) getIntent().getSerializableExtra("childSelection");
        transferDVs =getIntent().getBooleanExtra("transferDVs",false);
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
        MotherKP = findViewById(R.id.m_KP_Value);
        MotherAtk = findViewById(R.id.m_Atk_Value);
        MotherDef = findViewById(R.id.m_Def_Value);
        MotherSPAtk = findViewById(R.id.m_SPAtk_Value);
        MotherSPDef = findViewById(R.id.m_SPDef_Value);
        MotherSpe = findViewById(R.id.m_Spe_Value);
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

        MotherItemsList.add("Select Item");
        FatherItemList.add("Select Item");
        FullItemList.add("Select Item");




        getDVValues();
        getItems();
        getMotherMons();
        getFatherMons();

        getDittoMons();





        Mother_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)return;



                String selectedMonName = parent.getItemAtPosition(position).toString();
                if(selectedMonName.equals("ditto")){
                    MotherAbility.setText("no matter");
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
                    FatherAbility.setText("no matter");
                    mutableAttack(parent.getItemAtPosition(position).toString());
                }else{
                    FatherMove.setText(child.getMoves());
                    //mutableAttack(parent.getItemAtPosition(position).toString());
                }
                //set father Image
                setFatherImage(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Mother_Item_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e( "MotherItem Selected: ", parent.getItemAtPosition(position).toString());
                //If Everstone
                if(parent.getItemAtPosition(position).equals("everstone")){
                    MotherNature.setText(child.getNature());
                    Father_Item_Spinner.setSelection(1);
                    FatherItemAdapter.clear();
                    for (String s:FullItemList) {
                        FatherItemAdapter.add(s);
                        FatherItemAdapter.remove("everstone");
                    }
                }else if(parent.getItemAtPosition(position).equals("destiny-knot")){
                    Father_Item_Spinner.setSelection(1);
                    MotherNature.setText("no matter");
                    FatherItemAdapter.clear();
                    for (String s:FullItemList) {
                        FatherItemAdapter.add(s);
                        FatherItemAdapter.remove("destiny-knot");
                    }
                }else{

                    MotherNature.setText("-");
                    FatherItemAdapter.clear();
                    for (String s:FullItemList) {
                        FatherItemAdapter.add(s);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Father_Item_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e( "FatherItem Selected: ", parent.getItemAtPosition(position).toString());
                //If Everstone
                if(parent.getItemAtPosition(position).equals("everstone")){
                    if(FullItemList.size()>2)Mother_Item_Spinner.setSelection(1);
                    FatherNature.setText(child.getNature());
                    MotherItemAdapter.clear();
                    for (String s:FullItemList) {
                        MotherItemAdapter.add(s);
                        MotherAdapter.remove("everstone");
                    }

                }else if(parent.getItemAtPosition(position).equals("destiny-knot")){
                    if(FullItemList.size()>2)Mother_Item_Spinner.setSelection(1);
                    FatherNature.setText("no matter");
                    MotherItemAdapter.clear();
                    for (String s:MotherItemsList) {
                        MotherItemAdapter.add(s);
                        MotherAdapter.remove("destiny-knot");
                    }
                }else{
                    FatherNature.setText("-");
                    Log.e( "FatherItemList ",MotherItemsList.toString() );
                    Log.e( "FullItemList ",FullItemList.toString() );
                    MotherItemAdapter.clear();
                    for (String s:FullItemList) {
                        MotherItemAdapter.add(s);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // check wich Pokemon can learn the selected attack

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean start_intent=false;
                start_intent=setParentObjects();

                if(start_intent){
                    saveData(jp.pokemonToJson(child));
                    nextActivity();
                }else{
                    showError();
                }

            }
        });

    }




    public void getEggGroups(JSONObject jsonObject)throws JSONException{

            possible_dadMons = jp.getPokemonOfEggGroup(jsonObject);


            Log.e( "Possible Mons: ", possible_dadMons.toString());
            Log.e( "Possible Dad Mons with Attack: ",parent_mons_withAttack.toString());
    }

    public void getPossibleMom(JSONObject jsonObject)throws JSONException{

        possible_mumMons = jp.getEvoPokemon(jsonObject);
        Log.e( "getPossibleMom: ",possible_mumMons.toString() );
        possible_mumMons.add(0,"Select Mother");
        //possible_mumMons.add(1,child.getName());
        possible_mumMons.add("ditto");
        Log.e( "Possible Mons Count: ",possible_mumMons.toString());

        for (String s:possible_mumMons) {
         MotherAdapter.add(s);
        }

    }

    public void getPossibleDittoMons(JSONObject jsonObject) throws JSONException {
        dittoList = jp.getEvoPokemon(jsonObject);
        dittoList.add(0,"Select Father");
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
            MotherKP.setText(child.getKp());
            MotherAtk.setText(child.getAttack());
            MotherDef.setText(child.getDefense());
            MotherSPAtk.setText(child.getSpecialAttack());
            MotherSPDef.setText(child.getSpecialDefense());
            MotherSpe.setText(child.getSpeed());

            FatherKP.setText(child.getKp());
            FatherAtk.setText(child.getAttack());
            FatherDef.setText(child.getDefense());
            FatherSPAtk.setText(child.getSpecialAttack());
            FatherSPDef.setText(child.getSpecialDefense());
            FatherSpe.setText(child.getSpeed());
        }else{

            MotherKP.setText("-");
            MotherAtk.setText("-");
            MotherDef.setText("-");
            MotherSPAtk.setText("-");
            MotherSPDef.setText("-");
            MotherSpe.setText("-");

            FatherKP.setText("-");
            FatherAtk.setText("-");
            FatherDef.setText("-");
            FatherSPAtk.setText("-");
            FatherSPDef.setText("-");
            FatherSpe.setText("-");


            Toast t = Toast.makeText(this,"no DVs Selected", Toast.LENGTH_SHORT);
            t.show();
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

        if(!child.getNature().equals("Random")){
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


    public void canPokemonLearnMove( String PokemonName){

        Log.e( "canPokemonLearnMove: ","yes" );
        mutableAttack(PokemonName);


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

        Log.e( "mutableAttack: ","yes" );

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
        if(Mother_Item_Spinner.getSelectedItemPosition()!=0){
            mother.setItem(Mother_Item_Spinner.getSelectedItem().toString());
        }else{
            return false;
        }
        if(Father_Item_Spinner.getSelectedItemPosition()!=0){
            father.setItem(Father_Item_Spinner.getSelectedItem().toString());
        }else{
            return false;
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



        child.setMother(mother);
        child.setFather(father);

        return true;



    }


    private void nextActivity() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void showError(){
        Toast error = Toast.makeText(this,"Please check your Values", Toast.LENGTH_SHORT);
        error.show();
    }


    private void saveData(String json) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(JSONParser.FILE_NAME,MODE_PRIVATE);
            // vorher lesen ???
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
