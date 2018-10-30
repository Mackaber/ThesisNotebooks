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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleObjectiveGrouping extends CombinationProblem {
    private final String userFile;
    private Function function;
    private AbstractStorelessUnivariateStatistic ct_measure = new Mean();
    private int min_size = 3;
    private int max_size = 6;
    private int usersSize;
    private List<User> users;

    public SingleObjectiveGrouping(String usersFile) {
        this.userFile = usersFile;
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
        setNumberOfVariables((int) Math.ceil(usersSize / min_size));
        setNumberOfObjectives(1);
        setName("SingleObjectiveGrouping");
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

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public int getMinSize() {
        return min_size;
    }

    @Override
    public int getMaxSize() {
        return max_size;
    }
}
