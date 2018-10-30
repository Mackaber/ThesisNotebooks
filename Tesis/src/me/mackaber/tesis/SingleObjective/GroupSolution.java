package me.mackaber.tesis.SingleObjective;

import me.mackaber.tesis.Util.CombinationProblem;
import me.mackaber.tesis.Util.User;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;

import java.util.HashMap;
import java.util.List;

public class GroupSolution extends AbstractGenericSolution<List<User>, CombinationProblem> implements GroupingSolution<List<User>> {

    public GroupSolution(CombinationProblem problem) {
        super(problem);
    }

    /** Copy Constructor */
    public GroupSolution(GroupSolution solution) {
        super(solution.problem);

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
        return new GroupSolution(this);
    }

    @Override
    public List<HashMap<String, String>> getSampleSolution(int n) {
        return null;
    }
}
