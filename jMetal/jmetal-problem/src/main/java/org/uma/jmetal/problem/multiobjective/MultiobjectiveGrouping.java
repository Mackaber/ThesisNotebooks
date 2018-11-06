//package org.uma.jmetal.problem.multiobjective;
//
//
//import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
//import org.uma.jmetal.solution.PermutationSolution;
//import org.uma.jmetal.util.JMetalException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.StreamTokenizer;
//
///**
// * Class representing a bi-objective TSP (Traveling Salesman Problem) problem.
// * It accepts data files from TSPLIB:
// * http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/tsp/
// */
//@SuppressWarnings("serial")
//public class MultiobjectiveGrouping extends AbstractIntegerPermutationProblem {
//    protected int numberOfUsers;
//    protected double[] levels;
//    protected String[] interests;
//
//    /**
//     * Creates a new MultiobjectiveTSP problem instance
//     */
//    public MultiobjectiveGrouping(String levelFile) throws IOException {
//        readProblem(levelFile);
//
//        setNumberOfVariables(numberOfUsers);
//        setNumberOfObjectives(2);
//        setName("MultiobjectiveGrouping");
//    }
//
//    /**
//     * Evaluate() method
//     */
////    public void evaluate(PermutationSolution<Integer> solution) {
////        double fitness1;
////        double fitness2;
////
////        fitness1 = 0.0;
////        fitness2 = 0.0;
////
////        for (int i = 0; i < (numberOfCities - 1); i++) {
////            int x;
////            int y;
////
////            x = solution.getVariableValue(i);
////            y = solution.getVariableValue(i + 1);
////
////            fitness1 += distanceMatrix[x][y];
////            fitness2 += costMatrix[x][y];
////        }
////        int firstCity;
////        int lastCity;
////
////        firstCity = solution.getVariableValue(0);
////        lastCity = solution.getVariableValue(numberOfCities - 1);
////
////        fitness1 += distanceMatrix[firstCity][lastCity];
////        fitness2 += costMatrix[firstCity][lastCity];
////
////        solution.setObjective(0, fitness1);
////        solution.setObjective(1, fitness2);
////    }
//
//    private double[][] readProblem(String file) throws IOException {
//        double[][] matrix = null;
//
//        InputStream in = getClass().getResourceAsStream(file);
//        InputStreamReader isr = new InputStreamReader(in);
//        BufferedReader br = new BufferedReader(isr);
//
//        StreamTokenizer token = new StreamTokenizer(br);
//        try {
//            boolean found;
//            found = false;
//
//            token.nextToken();
//            while (!found) {
//                if ((token.sval != null) && ((token.sval.compareTo("DIMENSION") == 0)))
//                    found = true;
//                else
//                    token.nextToken();
//            }
//
//            token.nextToken();
//            token.nextToken();
//
//            numberOfUsers = (int) token.nval;
//
//            // Find the string SECTION
//            found = false;
//            token.nextToken();
//            while (!found) {
//                if ((token.sval != null) &&
//                        ((token.sval.compareTo("SECTION") == 0)))
//                    found = true;
//                else
//                    token.nextToken();
//            }
//
//            for (int i = 0; i < numberOfUsers; i++) {
//                token.nextToken();
//                int j = (int) token.nval;
//
//                token.nextToken();
//                levels[2 * (j - 1)] = token.nval;
//                token.nextToken();
//                interests[2 * (j - 1)] = token.sval;
//            } // for
//
//        } catch (Exception e) {
//            new JMetalException("Multi.readProblem(): error when reading data file " + e);
//        }
////    }

//    @Override
//    public int getPermutationLength() {
//        return numberOfUsers;
//    }
//
//    @Override
//    public void evaluate(PermutationSolution<Integer> solution) {
//
//    }
//}
