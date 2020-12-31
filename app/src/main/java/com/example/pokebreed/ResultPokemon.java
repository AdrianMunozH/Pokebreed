package com.example.pokebreed;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultPokemon extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Pokemon child;
    private boolean transferDVs;
    private JSONParser jp;


    List<String> possible_mons = new ArrayList<>();
    List<String> possible_mumMons = new ArrayList<>();
    List<String> parent_mons = new ArrayList<>();
    List<String> MotherItemsList = new ArrayList<>();
    List<String> FatherItemList = new ArrayList<>();

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
        //Father
    TextView FatherKP;
    TextView FatherAtk;
    TextView FatherDef;
    TextView FatherSPAtk;
    TextView FatherSPDef;
    TextView FatherSpe;



    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        child = (Pokemon) getIntent().getSerializableExtra("childSelection");
        transferDVs =getIntent().getBooleanExtra("transferDVs",false);
        Log.e("Result Child Name: ",child.getName());
        Toast t_selectMother = Toast.makeText(this,"Please Select Your Mother", Toast.LENGTH_SHORT);
        t_selectMother.show();;

        jp = new JSONParser();

        //SetSpinner
        Mother_Spinner= findViewById(R.id.MotherSpinner);
        Father_Spinner= findViewById(R.id.FatherSpinner);
        Mother_Item_Spinner=findViewById(R.id.MotherItemSpinner);
        Father_Item_Spinner=findViewById(R.id.FatherItemSpinner);

        //SetTextViews
        MotherKP = findViewById(R.id.m_KP_Value);
        MotherAtk = findViewById(R.id.m_Atk_Value);
        MotherDef = findViewById(R.id.m_Def_Value);
        MotherSPAtk = findViewById(R.id.m_SPAtk_Value);
        MotherSPDef = findViewById(R.id.m_SPDef_Value);
        MotherSpe = findViewById(R.id.m_Spe_Value);


        MotherItemsList.add("Select Item");
        FatherItemList.add("Select Item");


        getDVValues();
        getItems();
        getMotherMons();
        getFatherMons();





        // check wich Pokemon can learn the selected attack

    }




    public void getEggGroups(JSONObject jsonObject)throws JSONException{
        possible_mons = jp.getPokemonOfEggGroup(jsonObject);
        possible_mons.add(0,"Select Mother first");
        possible_mons.add("ditto");
        Log.e( "Possible Mons Count: ",possible_mons.toString());

        Father_Spinner.setAdapter(new ArrayAdapter<>(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item,possible_mons));
    }

    public void getPossibleMom(JSONObject jsonObject)throws JSONException{

        possible_mumMons = jp.getEvoPokemon(jsonObject);
        possible_mumMons.add(0,"Select Mother");
        possible_mumMons.add(1,child.getName());
        possible_mumMons.add("ditto");
        Log.e( "Possible Mons Count: ",possible_mumMons.toString());

        Mother_Spinner.setAdapter(new ArrayAdapter<>(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item,possible_mumMons));
    }

    public void getItem(JSONObject jsonObject)throws JSONException{
        MotherItemsList.add(jp.getItem(jsonObject));
        FatherItemList.add(jp.getItem(jsonObject));

    }

    public void getMotherMons(){
        MutableLiveData PokemonEvoListener = APIRequests.getInstance().requestGet(APIRequests.getInstance().getEvoPokemon(1));
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
        if(child.getEggGroups().size()==1 && !child.getEggGroups().get(0).equals("unknown")){

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
        }else{

            MotherKP.setText("-");
            MotherAtk.setText("-");
            MotherDef.setText("-");
            MotherSPAtk.setText("-");
            MotherSPDef.setText("-");
            MotherSpe.setText("-");


            Toast t = Toast.makeText(this,"no DVs Selected", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void getItems(){
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



        Mother_Item_Spinner.setAdapter(new ArrayAdapter<>(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item,MotherItemsList));
        Father_Item_Spinner.setAdapter(new ArrayAdapter<>(ResultPokemon.this,android.R.layout.simple_spinner_dropdown_item,FatherItemList));
    }











    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
