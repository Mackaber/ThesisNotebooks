package me.mackaber.tesis.ObjectiveFunctions;

import me.mackaber.tesis.Util.InterestsFunction;
import me.mackaber.tesis.Util.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterestsCosineSimilarityFunction extends InterestsFunction {
    private final String interestTreeFile;
    private final HashMap<String,HashMap<String,Double>> interestsTree = new HashMap<>();

    public InterestsCosineSimilarityFunction(String interestTreeFile) throws FileNotFoundException {
        this.interestTreeFile = interestTreeFile;
        buildInterestTree();
    }

    private void buildInterestTree() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        FileReader reader = new FileReader(interestTreeFile);
        JsonElement interestsJson = parser.parse(reader);

        JsonArray interests = interestsJson.getAsJsonArray();
        for(JsonElement entry: interests){
            JsonObject interest = (JsonObject) entry;
            JsonArray path = interest.get("path").getAsJsonArray();

            HashMap<String,Double> path_values = new HashMap<>();
            Double i = (double) path.size();
            for(JsonElement element: path.getAsJsonArray()){
                path_values.put(element.getAsString(),i);
                i--;
            }
            path_values.put(interest.get("raw_name").getAsString(),0.0);
            interestsTree.put(interest.get("raw_name").getAsString(),path_values);
        }
    }

    private static Double cosineSimilarity(HashMap<String, Double> vector1, HashMap<String, Double> vector2) {
        // Merge Vectors before comparing...
        double sumSqrt1 = 0.0;
        double sumSqrt2 = 0.0;

        for (Map.Entry<String, Double> interest : vector1.entrySet()) {
            if (!vector2.containsKey(interest.getKey()))
                vector2.put(interest.getKey(), 0.0);
            sumSqrt1 += interest.getValue() * interest.getValue();
        }

        for (Map.Entry<String, Double> interest : vector2.entrySet()) {
            if (!vector1.containsKey(interest.getKey()))
                vector1.put(interest.getKey(), 0.0);
            sumSqrt2 += interest.getValue() * interest.getValue();
        }


        double sumProduct = 0.0;
        for (Map.Entry<String, Double> interest : vector1.entrySet()) {
            sumProduct += interest.getValue() * vector2.get(interest.getKey());
        }

        return (sumProduct / (Math.sqrt(sumSqrt1) * Math.sqrt(sumSqrt2)));
    }


    @Override
    public double eval(List<User> variableValue) {
        List<Double> evals = new ArrayList<>();
        for (int i = 0; i < variableValue.size() - 1; i++) {
            for (int j = i + 1; j < variableValue.size(); j++) {
                evals.add(cosineSimilarity(variableValue.get(i).getInterestVector(), variableValue.get(j).getInterestVector()));
            }
        }
        Mean mean = new Mean();
        return mean.evaluate(ArrayUtils.toPrimitive(evals.toArray(new Double[evals.size()])));
    }

    @Override
    public HashMap<String, Double> getInterestPath(String interest) {
        return interestsTree.get(interest);
    }
}
