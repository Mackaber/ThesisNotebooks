package me.mackaber.tesis.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InterestsFunction implements Function {
    public abstract HashMap<String, Double> getInterestPath(String interest);

    public HashMap<String, Double> getInterestVector(List<String> interests) {
        HashMap<String,Double> userVector = new HashMap<>();
        for (String interest : interests) {
            HashMap<String, Double> path = getInterestPath(interest);
            for (Map.Entry<String, Double> branch : path.entrySet()) {
                updateVector(userVector, branch.getKey(), branch.getValue());
            }
        }
        return userVector;
    }

    private static void updateVector(HashMap<String, Double> user_vector, String name, Double value) {
        if (user_vector.containsKey(name))
            user_vector.put(name, Math.max(user_vector.get(name), (1.0 / (value + 1.0))));
        else
            user_vector.put(name, (1.0 / (value + 1.0)));
    }

}
