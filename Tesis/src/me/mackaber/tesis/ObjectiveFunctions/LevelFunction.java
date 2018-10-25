package me.mackaber.tesis.ObjectiveFunctions;

import me.mackaber.tesis.Util.Function;
import me.mackaber.tesis.Util.User;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import java.util.List;

public class LevelFunction implements Function {
    @Override
    public double eval(List<User> variableValue) {
        double[] levels = new double[variableValue.size()];

        for(int i=0;i<variableValue.size();i++) {
            levels[i] =  Double.valueOf(variableValue.get(i).getLevel());
        }

        Mean mean = new Mean();
        return mean.evaluate(levels);
    }
}
