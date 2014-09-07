//  SBXCrossover.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal3.operator.crossover.impl;

import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.random.PseudoRandom;
import org.uma.jmetal3.encoding.BinarySolution;
import org.uma.jmetal3.encoding.NumericSolution;
import org.uma.jmetal3.operator.crossover.CrossoverOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows to apply a SBX crossover operator using two parent
 * solutions.
 */
public class SBXCrossover implements CrossoverOperator<List<NumericSolution<Double>>, List<NumericSolution<Double>>> {
  private static final long serialVersionUID = -880076874462438998L;

  /** EPS defines the minimum difference allowed between real values */
  private static final double EPS = 1.0e-14;

  private static final double ETA_C_DEFAULT = 20.0;
  private static final double DEFAULT_PROBABILITY = 0.9 ;

  private double distributionIndex ;
  private double crossoverProbability  ;

  /** Constructor */
  public SBXCrossover() {
    this.crossoverProbability = 0.9 ;
    this.distributionIndex = 20.0 ;
  }

  /** Constructor */
  public SBXCrossover(double crossoverProbability, double distributionIndex) {
    this.crossoverProbability = crossoverProbability ;
    this.distributionIndex = distributionIndex ;
  }

  /** Constructor */
  private SBXCrossover(Builder builder) {
    crossoverProbability = builder.crossoverProbability ;
    distributionIndex = builder.distributionIndex ;
  }

  /* Getters */
  public double getCrossoverProbability() {
    return crossoverProbability;
  }

  public double getDistributionIndex() {
    return distributionIndex;
  }

  /** Execute() method */
  @Override
  public List<NumericSolution<Double>> execute(List<NumericSolution<Double>> solutions) {
    if (null == solutions) {
      throw new JMetalException("Null parameter") ;
    } else if (solutions.size() != 2) {
      throw new JMetalException("There must be two parents instead of " + solutions.size()) ;
    }

    return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1)) ;
  }

  /** doCrossover method */
  public List<NumericSolution<Double>>  doCrossover(
          double probability, NumericSolution<Double> parent1, NumericSolution<Double> parent2) {
    List<NumericSolution<Double>> offspring = new ArrayList<NumericSolution<Double>>(2);

    offspring.add((NumericSolution<Double>) parent1.copy()) ;
    offspring.add((NumericSolution<Double>) parent2.copy()) ;

    int i;
    double rand;
    double y1, y2, yL, yu;
    double c1, c2;
    double alpha, beta, betaq;
    double valueX1, valueX2;

    if (PseudoRandom.randDouble() <= probability) {
      for (i = 0; i < parent1.getNumberOfVariables(); i++) {
        valueX1 = parent1.getVariableValue(i);
        valueX2 = parent2.getVariableValue(i);
        if (PseudoRandom.randDouble() <= 0.5) {
          if (Math.abs(valueX1 - valueX2) > EPS) {

            if (valueX1 < valueX2) {
              y1 = valueX1;
              y2 = valueX2;
            } else {
              y1 = valueX2;
              y2 = valueX1;
            }

            yL = parent1.getLowerBound(i);
            yu = parent1.getUpperBound(i);
            rand = PseudoRandom.randDouble();
            beta = 1.0 + (2.0 * (y1 - yL) / (y2 - y1));
            alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

            if (rand <= (1.0 / alpha)) {
              betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
            } else {
              betaq = Math
                .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
            }

            c1 = 0.5 * ((y1 + y2) - betaq * (y2 - y1));
            beta = 1.0 + (2.0 * (yu - y2) / (y2 - y1));
            alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

            if (rand <= (1.0 / alpha)) {
              betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
            } else {
              betaq = Math
                .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
            }

            c2 = 0.5 * (y1 + y2 + betaq * (y2 - y1));

            if (c1 < yL) {
              c1 = yL;
            }

            if (c2 < yL) {
              c2 = yL;
            }

            if (c1 > yu) {
              c1 = yu;
            }

            if (c2 > yu) {
              c2 = yu;
            }

            if (PseudoRandom.randDouble() <= 0.5) {
              offspring.get(0).setVariableValue(i, c2);
              offspring.get(1).setVariableValue(i, c1);
            } else {
              offspring.get(0).setVariableValue(i, c1);
              offspring.get(1).setVariableValue(i, c2);
            }
          } else {
            offspring.get(0).setVariableValue(i, valueX1);
            offspring.get(1).setVariableValue(i, valueX2);
          }
        } else {
          offspring.get(0).setVariableValue(i, valueX2);
          offspring.get(1).setVariableValue(i, valueX1);
        }
      }
    }

    return offspring;
  }

  /** Builder class */
  public static class Builder {
    private double distributionIndex ;
    private double crossoverProbability ;

    public Builder() {
      distributionIndex = ETA_C_DEFAULT ;
      crossoverProbability = DEFAULT_PROBABILITY ;
    }

    public Builder setDistributionIndex(double distributionIndex) {
      if (distributionIndex < 0) {
        throw new JMetalException("Distribution index invalid: " + distributionIndex) ;
      } else {
        this.distributionIndex = distributionIndex;
      }

      return this ;
    }

    public Builder setProbability(double probability) {
      if ((probability < 0) || (probability > 1.0)) {
        throw new JMetalException("Probability value invalid: " + probability) ;
      } else {
        crossoverProbability = probability;
      }

      return this ;
    }

    public SBXCrossover build() {
      return new SBXCrossover(this) ;
    }
  }
}
