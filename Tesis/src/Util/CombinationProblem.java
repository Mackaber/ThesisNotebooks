package Util;

import SingleObjective.GroupingSolution;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;

import java.util.List;

public abstract class CombinationProblem extends AbstractGenericProblem<GroupingSolution<List<User>>> {

    public abstract List<User> generateRandomGroup();
}
