package me.mackaber.tesis.SingleObjective;

import me.mackaber.tesis.Util.CombinationProblem;
import me.mackaber.tesis.Util.RandomFunctions;
import me.mackaber.tesis.Util.User;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;
import org.uma.jmetal.util.pseudorandom.BoundedRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultGroupingSolution extends AbstractGenericSolution<List<User>, CombinationProblem>  implements GroupingSolution<List<User>> {

    public DefaultGroupingSolution(CombinationProblem problem) {
        super(problem);
        ArrayList<User> users = (ArrayList<User>) problem.getUsers();
        List<User> pool = (ArrayList<User>) users.clone();
        for(int i = 0; i < getNumberOfVariables(); i++) {
            List<User> randomGroup = RandomFunctions.generateRandomGroup(pool,problem);
            setVariableValue(i,randomGroup);
        }
    }

    public DefaultGroupingSolution(DefaultGroupingSolution solution) {
        super(solution.problem) ;
        for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
            setObjective(i, solution.getObjective(i)) ;
        }

        for (int i = 0; i < problem.getNumberOfVariables(); i++) {
            setVariableValue(i, solution.getVariableValue(i));
        }

        attributes = new HashMap<>(solution.attributes);
    }


    @Override
    public String getVariableValueString(int index) {
        return getVariableValue(index).toString();
    }

    @Override
    public Solution<List<User>> copy() {
        return new DefaultGroupingSolution(this);
    }


    @Override
    public List<HashMap<String, String>> getSampleSolution(int n) {
        List<HashMap<String, String>> sampleGroups = new ArrayList<>();

        JavaRandomGenerator random = new JavaRandomGenerator();
        BoundedRandomGenerator<Integer> indexSelector = random::nextInt;
        for(int i = 0; i<n; i++) {
            List<User> group = getVariableValue(indexSelector.getRandomValue(0,getNumberOfVariables()));
            for(User user:group) {
                HashMap<String,String> sampleGroup = new HashMap<>();
                sampleGroup.put("group",i+"");
                sampleGroup.put("id",user.getId() + "");
                sampleGroup.put("level",user.getLevel() + "");
                sampleGroup.put("interests",user.getInterests() + "");
                sampleGroup.put("part_prc",user.getPart_prc() + "");
                sampleGroups.add(sampleGroup);
            }
            HashMap<String,String> result = new HashMap<>();
            result.put("group","Eval:");
            result.put("id",getObjective(0) + "");
            result.put("level","");
            result.put("interests","");
            result.put("part_prc","");
            sampleGroups.add(result);

            HashMap<String,String> spacer = new HashMap<>();
            spacer.put("group","");
            spacer.put("id","");
            spacer.put("level","");
            spacer.put("interests","");
            spacer.put("part_prc","");
            sampleGroups.add(spacer);
        }

        return sampleGroups;
    }
}
