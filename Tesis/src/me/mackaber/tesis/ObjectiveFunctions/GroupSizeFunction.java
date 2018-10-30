package me.mackaber.tesis.ObjectiveFunctions;

import me.mackaber.tesis.Util.Function;
import me.mackaber.tesis.Util.User;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.util.List;

public class GroupSizeFunction implements Function {
    @Override
    public double eval(List<User> variableValue) {
        return Math.abs(variableValue.size() - 4.5);
    }
}
