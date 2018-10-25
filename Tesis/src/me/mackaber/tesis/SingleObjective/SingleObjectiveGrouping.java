package me.mackaber.tesis.SingleObjective;

import me.mackaber.tesis.Util.InterestsFunction;
import me.mackaber.tesis.Util.CombinationProblem;
import me.mackaber.tesis.Util.Function;
import me.mackaber.tesis.Util.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.uma.jmetal.util.pseudorandom.BoundedRandomGenerator;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleObjectiveGrouping extends CombinationProblem {
    private final String userFile;
    private Function function;
    private AbstractStorelessUnivariateStatistic ct_measure = new Mean();
    private int n = 10001;
    private int min_size = 3;
    private int max_size = 6;
    private List<User> users;
    private List<User> pool;
    private int usersSize;

    public SingleObjectiveGrouping(String usersFile) {
        this.userFile = usersFile;
    }

    public SingleObjectiveGrouping setUserSize(int n) {
        this.n = n;
        return this;
    }

    public SingleObjectiveGrouping setGroupSizeRange(int min_size, int max_size) {
        this.min_size = min_size;
        this.max_size = max_size;
        setNumberOfVariables((int) Math.ceil(usersSize / min_size));
        return this;
    }

    public SingleObjectiveGrouping setObjectiveFunction(Function function) {
        this.function = function;
        return this;
    }

    public SingleObjectiveGrouping setCentralTendencyMeasure(AbstractStorelessUnivariateStatistic ct_measure) {
        this.ct_measure = ct_measure;
        return this;
    }

    public void build() throws IOException {
        users = readProblem(userFile);
        pool = users;
        setNumberOfVariables((int) Math.ceil(usersSize / min_size));
        setNumberOfObjectives(1);
        setName("SingleObjectiveGrouping");
    }

    //IDEA: Quizá sea buena idea sacar esto y hacerlo estatico para que lo use la mutación...
    @Override
    public List<User> generateRandomGroup() {
        JavaRandomGenerator random = new JavaRandomGenerator();
        int size = random.nextInt(min_size, max_size);
        BoundedRandomGenerator<Integer> indexSelector = random::nextInt;
        RandomGenerator<User> generator = RandomGenerator.forCollection(indexSelector, pool);

        List<User> generatedUserGroup = new ArrayList<>();

        if (pool.size() <= max_size) {
            generatedUserGroup = pool;
            pool.removeAll(pool); // So, it doesn't fill the remaining variables (altough, maybe it could be an interesting hack
        } else { // Only if the pool still has users, otherwise it will return an empty list
            for (int i = 0; i < size; i++) {
                User userToAdd = generator.getRandomValue();
                generatedUserGroup.add(userToAdd);
                pool.remove(userToAdd);
            }
        }
        return generatedUserGroup;
    }

    @Override
    public void evaluate(GroupingSolution<List<User>> solution) {
        double fitness1;

        double[] results = new double[getNumberOfVariables()];
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            if (solution.getVariableValue(i).size() > 0) // The solution may contain empty groups as variables
                results[i] = (function.eval(solution.getVariableValue(i)));
            else {
                results = Arrays.copyOfRange(results, 0, i);
                break;
            }
        }

        fitness1 = ct_measure.evaluate(results);
        solution.setObjective(0, fitness1);
    }

    @Override
    public GroupingSolution createSolution() {
        return new DefaultGroupingSolution(this);
    }

    private List<User> readProblem(String file) throws IOException {
        List<User> problem_users = new ArrayList<>();

        Reader in = new FileReader(file);
        CSVParser records = CSVFormat.DEFAULT.parse(in);
        for (CSVRecord record : records) {
            List<String> interests = Arrays.asList(record.get(2), record.get(3), record.get(4));
            User user = new User(Integer.parseInt(record.get(0)));
            user.setLevel(Integer.parseInt(record.get(1)))
                    .setInterests(interests)
                    .setPart_prc(Double.parseDouble(record.get(5)))
                    .setPart_time(Double.parseDouble(record.get(6)));
            if(function instanceof InterestsFunction) {
                InterestsFunction interestsFunction = (InterestsFunction) function;
                user.setInterestVector(interestsFunction.getInterestVector(interests));
            }
            problem_users.add(user);
        }
        usersSize = problem_users.size();
        return problem_users;
    }
}
