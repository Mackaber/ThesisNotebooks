package SingleObjective;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.JMetalException;

import java.io.*;
import java.util.Arrays;

/**
 * Class representing a single-objective TSP (Traveling Salesman Problem) problem.
 * It accepts data files from TSPLIB:
 * http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/tsp/
 */
@SuppressWarnings("serial")
public class SingleobjectiveGrouping extends AbstractIntegerPermutationProblem {
    private int numberOfUsers;
    private double[] levels;

    /**
     * Creates a new TSP problem instance
     */
    public SingleobjectiveGrouping(String levelFile) throws IOException {
        levels = readProblem(levelFile);

        setNumberOfVariables(numberOfUsers);
        setNumberOfObjectives(1);
        setName("Single Objective Grouping");
    }

    /**
     * Evaluate() method
     */
    public void evaluate(PermutationSolution<Integer> solution) {
        double fitness1;

        // Standard deviations
        double[] values = new double[numberOfUsers];

        for (int i = 0; i < numberOfUsers; i++) {
            values[i] = levels[solution.getVariableValue(i)];
        }

        double[][] groups = splitGroups(values, 4);
        StandardDeviation sd = new StandardDeviation();
        double[] std = new double[groups.length];

        for (int i = 0; i < groups.length; i++) {
            std[i] = sd.evaluate(groups[i]);
        }

        Mean mean = new Mean();
        fitness1 = mean.evaluate(std);

        solution.setObjective(0, fitness1);
    }

    private static double[][] splitGroups(double[] arr, int gsz) {
        int len = arr.length;
        int counter = 0;
        int i;

        double[][] newArray = new double[len / gsz][gsz];

        for (i = 0; i < len - gsz + 1; i += gsz)
            System.arraycopy(arr, i, newArray[counter++], 0, gsz);

        if (len % gsz == 1) {
            newArray[0] = ArrayUtils.add(newArray[0], arr[len - 1]);
        }

        if (len % gsz == 2) {
            newArray[0] = ArrayUtils.add(newArray[0], arr[len - 1]);
            newArray[counter - 1] = ArrayUtils.add(newArray[counter - 1], arr[len - 2]);
        }

        if (len % gsz == 3) {
            newArray = ArrayUtils.add(newArray, Arrays.copyOfRange(arr, i, len));
        }

        return newArray;
    }

    private double[] readProblem(String file) throws IOException {
        double[] level_values = null;

        InputStream in = getClass().getResourceAsStream(file);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);

        StreamTokenizer token = new StreamTokenizer(br);
        try {
            boolean found;
            found = false;

            token.nextToken();
            while (!found) {
                if ((token.sval != null) && ((token.sval.compareTo("DIMENSION") == 0)))
                    found = true;
                else
                    token.nextToken();
            }

            token.nextToken();
            token.nextToken();

            numberOfUsers = (int) token.nval;
            level_values = new double[numberOfUsers];

            // Find the string SECTION
            found = false ;
            token.nextToken();
            while(!found) {
                if ((token.sval != null) &&
                        ((token.sval.compareTo("SECTION") == 0)))
                    found = true ;
                else
                    token.nextToken() ;
            }

            for (int i = 0; i < numberOfUsers; i++) {
                token.nextToken() ;
                int j = (int)token.nval;

                token.nextToken() ;
                level_values[2*(j-1)] = token.nval;
            }

        } catch (Exception e) {
            new JMetalException("SingleobjectiveGrouping.readProblem(): error when reading data file " + e);
        }
        return level_values;
    }

    @Override
    public int getPermutationLength() {
        return numberOfUsers;
    }
}
