package com.example.pokebreed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private int id;
    private List<String> eggGroups = new ArrayList<>();
    private List<String> moves=new ArrayList<>();
    private List<String> abilitys = new ArrayList<>();
    private String spriteURL;
    private List<String> types=new ArrayList<>();


    //DVs(Stats) 0-31
    private int kp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    //Constructors
    public Pokemon(String name) {
        this.name = name;
    }

    public Pokemon(int id) {
        this.id = id;
    }

    //Getter & Setter
    public List<String> getEggGroups() {
        return eggGroups;
    }

    public void setEggGroups(List<String> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public List<String> getMoves() {
        return moves;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public List<String> getAbilitys() {
        return abilitys;
    }

    public void setAbilitys(List<String> abilitys) {
        this.abilitys = abilitys;
    }

    public String getSpriteURL() {
        return spriteURL;
    }

    public void setSpriteURL(String spriteURL) {
        this.spriteURL = spriteURL;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public int getKp() {
        return kp;
    }

    public void setKp(int kp) {
        this.kp = kp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
