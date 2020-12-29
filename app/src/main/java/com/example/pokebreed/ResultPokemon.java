package com.example.pokebreed;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultPokemon extends AppCompatActivity {
    private Pokemon child;

    List<String> possible_mons = new ArrayList<>();
    List<String> parent_mons = new ArrayList<>();


    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        child = (Pokemon) getIntent().getSerializableExtra("childSelection");
        Log.e("Result Child Name: ",child.getName());


        //get All Pokemon With EggGroup 1 & 2 put them in possilble_mons List
        

        // check wich Pokemon can learn the selected attack

    }
}
