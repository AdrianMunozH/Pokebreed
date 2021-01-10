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
import org.json.JSONObject;


public class APIRequests {
    final private String baseurl = "https://pokeapi.co/api/v2/";
    RequestQueue requestQueue;
    private static APIRequests instance = null;

    public APIRequests(Context context) {

        requestQueue = Volley.newRequestQueue(context);

    }
    // Singleton Pattern, damit wir in der ganzen App das gleiche Objekt nutzen und alle Abfragen in eine Queue kommen
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

    // Die wirklich API Request Methode, gibt einen Listener und nicht die Daten selbst zurück, damit man keine NullPointerException bekommt
    public MutableLiveData<JSONObject> requestGet(String urlEnd) {
        final MutableLiveData listener = new MutableLiveData();
        String url = baseurl.concat(urlEnd);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.setValue(response);
                Log.e("REST","rest erfolgreich" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
            }
        });
        requestQueue.add(json);
        return listener;
    }
    public String getPokemonList() {
        return "pokemon?limit=800";
    }
    public String getPokemon(String name) {
        return "pokemon/" + name;
    }
    public String getSpeciesEggGroups(String name){return "pokemon-species/"+ name;}
    public String getPokemonOfEggGroup(String eggGroup){return "egg-group/"+eggGroup;}
    public String getEvoPokemon(String url){return url;}
    public String getItem(int itemId){return "item/"+itemId;}
    public String getItem(String itemId){return "item/"+itemId;}
    public String getEvolutionChainUrl(String name){return "pokemon-species/"+name; }
}
