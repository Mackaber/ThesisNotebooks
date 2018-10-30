package me.mackaber.tesis.ObjectiveFunctions;

import me.mackaber.tesis.Util.Function;
import me.mackaber.tesis.Util.User;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.List;

public class LevelFunction implements Function {
    @Override
    public double eval(List<User> variableValue) {
        int numberOfUsers = variableValue.size();
        double[] levels = new double[numberOfUsers];

        for(int i=0;i<numberOfUsers;i++) {
            levels[i] =  Double.valueOf(variableValue.get(i).getLevel());
        }

        StandardDeviation sd = new StandardDeviation();
        return sd.evaluate(levels);
    }
}
