package SingleObjective;

import Util.CombinationProblem;
import Util.Evaluation;
import Util.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.uma.jmetal.measure.impl.LastEvaluationMeasure;
import org.uma.jmetal.util.pseudorandom.BoundedRandomGenerator;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterestsGrouping extends CombinationProblem {
    private final Evaluation evaluation;
    private int n;
    private int min_size;
    private int max_size;
    private List<User> users;
    private List<User> pool;
    private int usersSize;

    public InterestsGrouping(String usersFile, int min_size, int max_size, int n, Evaluation evaluation) throws IOException {
        this.min_size = min_size;
        this.max_size = max_size;
        this.n = n;
        this.evaluation = evaluation;

        users = readProblem(usersFile);
        pool = users;
        setNumberOfVariables((int) Math.ceil(usersSize / min_size));
        setNumberOfObjectives(1);
        setName("InterestsGrouping");
    }

    //IDEA: Quizá sea buena idea sacar esto y hacerlo estatico para que lo use la mutación...
    @Override
    public List<User> generateRandomGroup() {
        JavaRandomGenerator random = new JavaRandomGenerator();
        int size = random.nextInt(min_size,max_size);
        BoundedRandomGenerator<Integer> indexSelector = random::nextInt;
        RandomGenerator<User> generator = RandomGenerator.forCollection(indexSelector,pool);

        List<User> generatedUserGroup = new ArrayList<>();
        for(int i = 0; i<size; i++) {
            User userToAdd = generator.getRandomValue();
            generatedUserGroup.add(userToAdd);
            pool.remove(userToAdd);
        }

        return generatedUserGroup;
    }

    @Override
    public void evaluate(GroupingSolution<List<User>> solution) {
        double fitness1;

        double[] evals = new double[getNumberOfVariables()];
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            if (solution.getVariableValueString(i).length() > 0) // The solution may contain empty groups as variables
                evals[i] = (evaluation.eval(solution.getVariableValue(i)));
        }

        Mean mean = new Mean();

        fitness1 = mean.evaluate(evals);
        solution.setObjective(0, fitness1);
    }

    @Override
    public GroupingSolution createSolution() {
        return null;
    }

    private List<User> readProblem(String file) throws IOException {
        List<User> problem_users = new ArrayList<>();

        Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        for (CSVRecord record : records) {
            List<String> interests = Arrays.asList(record.get(2), record.get(3), record.get(4));
            User user = new User(
                    Integer.parseInt(record.get(0)),
                    Integer.parseInt(record.get(1)),
                    interests,
                    Double.parseDouble(record.get(5)),
                    Double.parseDouble(record.get(6)));
            problem_users.add(user);
        }
        usersSize = ((CSVParser) records).getRecords().size();
        return problem_users;
    }
}
