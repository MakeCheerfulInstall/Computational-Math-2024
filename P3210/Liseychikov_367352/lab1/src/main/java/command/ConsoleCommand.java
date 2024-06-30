package command;

import Util.ParseUtil;
import algo.IterationSolution;
import algo.Solution;
import model.Data;
import model.Result;

public class ConsoleCommand implements Command {
    private final Solution iterationSolution = new IterationSolution();
    private final ParseUtil parseUtil = new ParseUtil();

    @Override
    public Result execute() {
        int size = parseUtil.parseSize();
        double accuracy = parseUtil.parseAccuracy();
        double[][] matrix = parseUtil.parseMatrix(size);
        Data data = new Data(matrix, accuracy);
        return iterationSolution.compute(data);
    }
}
