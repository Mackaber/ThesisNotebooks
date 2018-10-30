package me.mackaber.tesis.SingleObjective;

import org.uma.jmetal.solution.Solution;

import java.util.HashMap;
import java.util.List;

public interface GroupingSolution<T> extends Solution<T> {
    List<HashMap<String, String>> getSampleSolution(int n);
}
