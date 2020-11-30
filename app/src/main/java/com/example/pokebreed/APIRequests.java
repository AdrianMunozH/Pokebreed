package com.example.pokebreed;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class APIRequests {
    final private String baseurl = "https://pokeapi.co/api/v2/";
    RequestQueue requestQueue;
    private static APIRequests instance = null;


    //test
    public JSONObject jsonObject;
    MutableLiveData<JSONObject> listen = new MutableLiveData<>();
    JsonListener result = new JsonListener();
    List<String> pokemonList = new ArrayList<>();

    public APIRequests(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized APIRequests getInstance(Context context) {
        if (instance == null) {
            instance = new APIRequests(context);
        }
        return instance;
    }
    public static synchronized APIRequests getInstance() {
        if (instance == null) {
           throw new IllegalStateException("There should be always be an instance!!");
        }
        return instance;
    }


    public void setJsonObject(JSONObject jsonObject) {
        Log.e("setter", jsonObject.toString());
        this.jsonObject = jsonObject;
    }

    /*
    public void getAllPoke() throws JSONException {
        requestGet("pokemon?limit=800");
        listen.observer(context, new Observer() {
            @Override
            public void update(Observable observable, Object o) {

            }
        });

        result.setListener(new JsonListener.OnChangeListener() {
            @Override
            public void onChange() {

                Log.e("onChange: ", "yes");
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject pokemon = jsonArray.getJSONObject(i);
                        String name = pokemon.getString("name");
                        pokemonList.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

     */

    public void requestGet(String urlEnd) {
        String url = baseurl.concat(urlEnd);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setJsonObject(response);
                listen.setValue(jsonObject);
                Log.e("REST","rest erfolgreich" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
                jsonObject = null;
            }
        });
        requestQueue.add(json);

    }
    
   /* public List<String> getEggGroup (String name) {
        String url= "/pokemon-species/" + name;
        List<String> eggGroupEntries = new ArrayList<>();
        JSONArray eggGrp;
        try {
            eggGrp = requestGet(url).getJSONArray("egg_groups");
            for (int i = 0;i < eggGrp.length(); i++) {
                JSONObject entry = eggGrp.getJSONObject(i);
                eggGroupEntries.add(entry.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eggGroupEntries;
    }*/
}