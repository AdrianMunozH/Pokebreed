package com.example.pokebreed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class ResultPokemon extends AppCompatActivity {
    private Pokemon child;
    private boolean transferDVs;
    private JSONParser jp;


    List<String> possible_mons = new ArrayList<>();
    List<String> possible_mumMons = new ArrayList<>();
    List<String> parent_mons = new ArrayList<>();
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
    TextView MotherAttack;


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
    TextView FatherAttack;





    private ImageView FatherImage;
    boolean monsupdated;

    //Adapter
    ArrayAdapter FatherAdapter;
    ArrayAdapter MotherAdapter;
    ArrayAdapter FatherItemAdapter;
    ArrayAdapter MotherItemAdapter;


    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(MainMenu.PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(MainMenu.PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
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
        MotherAttack= findViewById(R.id.m_attack);



        FatherKP = findViewById(R.id.f_KP_Value);
        FatherAtk = findViewById(R.id.f_Atk_Value);
        FatherDef = findViewById(R.id.f_Def_Value);
        FatherSPAtk = findViewById(R.id.f_SPAtk_Value);
        FatherSPDef = findViewById(R.id.f_SpDef_Value);
        FatherSpe = findViewById(R.id.f_Spe_Value);
        FatherNature=findViewById(R.id.FatherNature);
        FatherAbility = findViewById(R.id.f_ability);
        FatherAttack= findViewById(R.id.f_attack);


        //ImageViews
        FatherImage =(ImageView) findViewById(R.id.FatherpokemonPic);
        MotherImage=(ImageView) findViewById(R.id.MotherpokemonPic);

        MotherItemsList.add("Select Item");
        FatherItemList.add("Select Item");
        FullItemList.add("Select Item");


        getDVValues();
        getItems();
        getMotherMons();


        Mother_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)return;



                String selectedMonName = parent.getItemAtPosition(position).toString();
                if(selectedMonName.equals("ditto")){
                    MotherAbility.setText("no matter");
                    FatherAbility.setText(child.getAbility());
                }else{
                    MotherAbility.setText(child.getAbility());
                }
                SelectedMother=selectedMonName;
                SelectedMotherPos=position;


                //Set Mother Image
                setMotherImage(SelectedMother);

                getFatherMons();

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
        // check which Pokemon can learn the selected attack

    }




    public void getEggGroups(JSONObject jsonObject)throws JSONException{
        if(SelectedMother.equals("ditto")){


            possible_mons=possible_mumMons;

            possible_mons.set(0,"Select Father");
            possible_mons.remove("ditto");
            FatherAdapter.clear();

        }else if(SelectedMotherPos==0){
            possible_mons.clear();
            FatherAdapter.clear();
            possible_mons.add(0,"Select Mother first");
        }else{
            possible_mons = jp.getPokemonOfEggGroup(jsonObject);
            //possible_mons.add(0,"ditto");
            Log.e( "Possible Mons: ",possible_mons.toString());
        }



        for (String s:possible_mons) {
            FatherAdapter.add(s);
            FatherAdapter.notifyDataSetChanged();
        }

        if(!child.getMoves().equals("none")&&!monsupdated){
            monsupdated=true;
            getAllPokemonWithMove(possible_mons);
        }

        if(SelectedMother.equals("ditto"))monsupdated=false;


    }

    public void getPossibleMom(JSONObject jsonObject)throws JSONException{

        possible_mumMons = jp.getEvoPokemon(jsonObject);
        possible_mumMons.add(0,"Select Mother");
        possible_mumMons.add(1,child.getName());
        possible_mumMons.add("ditto");
        Log.e( "Possible Mons Count: ",possible_mumMons.toString());

        for (String s:possible_mumMons) {
         MotherAdapter.add(s);
        }

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


    public void getAllPokemonWithMove(List<String> PokemonList){


        for ( String pokemon: PokemonList){

            mutableAttack(pokemon);

        }


    }

    public void getAllAttacken(JSONObject jsonObject,String pokemon) throws JSONException {
        List<String> attacks= new ArrayList<>();

        attacks = jp.getAllAttacks(jsonObject);


        if(attacks.contains(child.getMoves())){
            Log.e( "ParentMons ",child.getMoves()+": "+pokemon );
            parent_mons.add(pokemon);
            Log.e( "ParentMons",parent_mons.toString() );
            FatherAdapter.clear();
            for (String s:parent_mons) {
                FatherAdapter.add(s);
                FatherAdapter.notifyDataSetChanged();
            }
        };



    }


    public void mutableAttack(final String pokemon){
        MutableLiveData pokeMoveListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemon));
        pokeMoveListener.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    getAllAttacken(jsonObject, pokemon);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

}
