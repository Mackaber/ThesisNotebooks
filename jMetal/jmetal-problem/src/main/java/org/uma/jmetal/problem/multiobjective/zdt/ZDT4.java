//  ZDT4.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//




//




// 



package org.uma.jmetal.problem.multiobjective.zdt;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing problem ZDT4
 */
@SuppressWarnings("serial")
public class ZDT4 extends AbstractDoubleProblem {

  /** Constructor. Creates a default instance of problem ZDT4 (10 decision variables */
  public ZDT4() {
    this(10);
  }

  /**
   * Creates a instance of problem ZDT4.
   *
   * @param numberOfVariables Number of variables.
   */
  public ZDT4(Integer numberOfVariables) {
    setNumberOfVariables(numberOfVariables);
    setNumberOfObjectives(2);
    setName("ZDT4");

    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

    lowerLimit.add(0.0);
    upperLimit.add(1.0);
    for (int i = 0; i < getNumberOfVariables(); i++) {
      lowerLimit.add(-5.0);
      upperLimit.add(5.0);
    }

    setLowerLimit(lowerLimit);
    setUpperLimit(upperLimit);
  }

  /** Evaluate() method */
  public void evaluate(DoubleSolution solution) {
    double[] f = new double[getNumberOfObjectives()];

    f[0] = solution.getVariableValue(0);
    double g = this.evalG(solution);
    double h = this.evalH(f[0], g);
    f[1] = h * g;

    solution.setObjective(0, f[0]);
    solution.setObjective(1, f[1]);
  }

  /**
   * Returns the value of the ZDT4 function G.
   *
   * @param solution Solution
   */
  public double evalG(DoubleSolution solution) {
    double g = 0.0;
    for (int var = 1; var < solution.getNumberOfVariables(); var++) {
      g += Math.pow(solution.getVariableValue(var), 2.0) +
        -10.0 * Math.cos(4.0 * Math.PI * solution.getVariableValue(var));
    }

    double constant = 1.0 + 10.0 * (solution.getNumberOfVariables() - 1);
    return g + constant;
  }

  /**
   * Returns the value of the ZDT4 function H.
   *
   * @param f First argument of the function H.
   * @param g Second argument of the function H.
   */
  public double evalH(double f, double g) {
    return 1.0 - Math.sqrt(f / g);
  }
}
