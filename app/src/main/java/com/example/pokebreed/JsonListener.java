package com.example.pokebreed;

import org.json.JSONObject;

public class JsonListener {
    private JSONObject json=null;
    private ChangeListener listener;

    public JSONObject getJson(){
        return json;
    }

    public void setJson(JSONObject json){
        this.json=json;
        if(listener!=null) listener.onChange();
    }

    public ChangeListener getListener(){
        return listener;
    }

    public void setListener(ChangeListener listener){
        this.listener=listener;
    }

    public interface ChangeListener{
        void onChange();
    }
}
