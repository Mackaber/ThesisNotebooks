package me.mackaber.tesis.ObjectiveFunctions;

import me.mackaber.tesis.SingleObjective.GroupingSolution;
import me.mackaber.tesis.Util.Function;
import me.mackaber.tesis.Util.InterestsFunction;
import me.mackaber.tesis.Util.User;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class WeightedFunction extends InterestsFunction {
    private final GroupSizeFunction groupSizeFunction;
    private final InterestsCosineSimilarityFunction interestsFunction;
    private final LevelFunction levelFunction;
    private final ParticipationStyleFunction participationStyleFunction;
    private final String interestsFile;
    private double w1 = 1.0;
    private double w2 = 1.0;
    private double w3 = 1.0;
    private double w4 = 1.0;


    public WeightedFunction(String interestsFile) throws FileNotFoundException {
        this.interestsFile = interestsFile;
        groupSizeFunction = new GroupSizeFunction();
        interestsFunction = new InterestsCosineSimilarityFunction(interestsFile);
        levelFunction = new LevelFunction();
        participationStyleFunction = new ParticipationStyleFunction();
    }

    public WeightedFunction setW1(double w1) {
        this.w1 = w1;
        return this;
    }

    public WeightedFunction setW2(double w2) {
        this.w2 = w2;
        return this;
    }

    public WeightedFunction setW3(double w3) {
        this.w3 = w3;
        return this;
    }

    public WeightedFunction setW4(double w4) {
        this.w4 = w4;
        return this;
    }

    @Override
    public HashMap<String, Double> getInterestPath(String interest) {
        return interestsFunction.getInterestPath(interest);
    }

    @Override
    public double eval(List<User> variableValue) {
        return  w1 * groupSizeFunction.eval(variableValue) +
                w2 * interestsFunction.eval(variableValue) +
                w3 * levelFunction.eval(variableValue) +
                w4 * participationStyleFunction.eval(variableValue);
    }
}
