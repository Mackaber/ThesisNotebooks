package Util;

import java.util.List;
import java.util.Vector;

public class User {
    Integer id;
    Integer level;
    List<String> interests;
    Double part_prc;
    Double part_time;

    public User(Integer id,Integer level, List<String> interests, Double part_prc, Double part_time){
        this.id = id;
        this.level = level;
        this.interests = interests;
        this.part_prc = part_prc;
        this.part_time = part_time;
    }
}