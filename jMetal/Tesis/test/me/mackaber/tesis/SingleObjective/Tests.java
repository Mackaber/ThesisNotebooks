package me.mackaber.tesis.SingleObjective;

import me.mackaber.tesis.ObjectiveFunctions.InterestsCosineSimilarityFunction;
import me.mackaber.tesis.ObjectiveFunctions.WeightedFunction;
import me.mackaber.tesis.Util.Function;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.junit.jupiter.api.Test;

class Tests {

    @Test
    public void Scratch() throws Exception {
        SingleObjectiveGrouping problem = new SingleObjectiveGrouping("res/synthetic_10001.csv");

        WeightedFunction function = new WeightedFunction("res/custom_interests.json");
        function.setW1(1.0); // Group Size
        function.setW2(1.0); // Interests
        function.setW3(1.0); // Level
        function.setW4(1.0); // Participation Style

        problem.setGroupSizeRange(3, 6)
                .setObjectiveFunction(function)
                .setCentralTendencyMeasure(new Mean())
                .build();

        for (int i = 0; i < 5; i++) {
            GroupingSolution solution = problem.createSolution();
            problem.evaluate(solution);
            System.out.println(solution.getObjective(0));
            System.out.print(solution.getSampleSolution(2));
        }
    }
}