package com.example.pokebreed;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
import java.util.Map;
import java.util.Observable;

public class APIRequests {
    final private String baseurl = "https://pokeapi.co/api/v2/";
    RequestQueue requestQueue;
    private static APIRequests instance = null;


    //test
    private JSONObject allPokemon;
    private JSONObject pokemon;
    private JSONObject types;


    private Map<String,MutableLiveData> listeners = new ArrayMap<>();



    public JSONObject jsonObject;
    private MutableLiveData<JSONObject> listen = new MutableLiveData<>();

    public APIRequests(Context context) {

        requestQueue = Volley.newRequestQueue(context);
        listeners.put("pokemon", new MutableLiveData());
        listeners.put("allPokemon", new MutableLiveData());
        listeners.put("types", new MutableLiveData());

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

    public Map<String, MutableLiveData> getListeners() {
        return listeners;
    }

    public void requestGet(String urlEnd, final String listenerKey) {
        String url = baseurl.concat(urlEnd);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //setJsonObject(response);
                listeners.get(listenerKey).setValue(response);
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
    public String getPokemonList() {
        return "pokemon?limit=800";
    }
    public String getPokemon(String name) {
        return "pokemon/" + name;
    }
    public String getPokemonOfType(String type) {
        return "type/"+type;
    }
}
