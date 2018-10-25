package me.mackaber.tesis.SingleObjective;

import me.mackaber.tesis.ObjectiveFunctions.LevelFunction;
import me.mackaber.tesis.Util.CombinationProblem;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.comparator.DominanceComparator;

import java.util.Comparator;

public class SingleObjectiveGroupingRunner {
    public static void main(String[] args) throws Exception {
//        CombinationProblem problem = new SingleObjectiveGrouping(
//                "lol",
//                3,
//                6,
//                20,
//                new LevelFunction(),
//                new Mean()
//        );

        int improvementRounds = 10000 ;
        Comparator<GroupingSolution> comparator = new DominanceComparator<>(0) ;
        MutationOperator<GroupingSolution> mutationOperator = null;

//        LocalSearchOperator<GroupingSolution> localSearch = new BasicLocalSearch<>(
//                improvementRounds,
//                mutationOperator,
//                comparator,
//                problem);

//        GroupingSolution solution = problem.createSolution();
//        GroupingSolution newSolution = localSearch.execute(solution);

//        JMetalLogger.logger.info("Fitness: " + newSolution.getObjective(0));
//        JMetalLogger.logger.info("Solution: " + newSolution.getVariableValueString(0));
    }
}
