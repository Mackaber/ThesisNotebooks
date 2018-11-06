package me.mackaber.tesis.Util;

import me.mackaber.tesis.SingleObjective.GroupingSolution;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;

import java.util.List;

public abstract class CombinationProblem extends AbstractGenericProblem<GroupingSolution<List<User>>> {
    public abstract List<User> getUsers();

    public abstract int getMinSize();

    public abstract int getMaxSize();
}
