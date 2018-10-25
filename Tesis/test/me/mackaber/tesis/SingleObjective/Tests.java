package me.mackaber.tesis.SingleObjective;

import me.mackaber.tesis.ObjectiveFunctions.InterestsCosineSimilarityFunction;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.junit.jupiter.api.Test;


class Tests {

    @Test
    public void Scratch() throws Exception {
        SingleObjectiveGrouping problem = new SingleObjectiveGrouping("res/synthetic.csv");

        problem.setGroupSizeRange(3, 6)
                .setUserSize(20)
                .setObjectiveFunction(new InterestsCosineSimilarityFunction("res/custom_interests.json"))
                .setCentralTendencyMeasure(new Mean())
                .build();

        GroupingSolution solution = problem.createSolution();
        problem.evaluate(solution);
        System.out.print(solution.getObjective(0));
    }
}