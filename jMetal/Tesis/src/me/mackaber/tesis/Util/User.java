package me.mackaber.tesis.Util;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

public class User {
    Integer id;
    Integer level;
    List<String> interests;
    Double part_prc;
    Double part_time;
    private HashMap<String,Double> interestVector;

    public User(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public User setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public List<String> getInterests() {
        return interests;
    }

    public User setInterests(List<String> interests) {
        this.interests = interests;
        return this;
    }

    public Double getPart_prc() {
        return part_prc;
    }

    public User setPart_prc(Double part_prc) {
        this.part_prc = part_prc;
        return this;
    }

    public Double getPart_time() {
        return part_time;
    }

    public User setPart_time(Double part_time) {
        this.part_time = part_time;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setInterestVector(HashMap<String,Double> interestVector) {
        this.interestVector = interestVector;
    }

    public HashMap<String,Double> getInterestVector() {
        return interestVector;
    }

    @Override
    public String toString() {
        return String.format("{ %s, %s, %s, %s, }",id,level,interests,part_prc);
    }

}