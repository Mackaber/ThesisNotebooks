package me.mackaber.tesis.ObjectiveFunctions;

import me.mackaber.tesis.Util.Function;
import me.mackaber.tesis.Util.User;

import java.util.List;

public class ParticipationStyleFunction implements Function {
    @Override
    public double eval(List<User> variableValue) {
        Double sum = 0.0;

        for(int i=0;i<variableValue.size();i++) {
            sum += variableValue.get(i).getPart_prc();
        }

        return (1/sum);
    }
}
