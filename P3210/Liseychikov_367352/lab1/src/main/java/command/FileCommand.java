package command;

import Util.ParseUtil;
import algo.IterationSolution;
import algo.Solution;
import model.Data;
import model.Result;

import java.io.File;


public class FileCommand implements Command {
    private final Solution solution = new IterationSolution();
    private final ParseUtil parseUtil = new ParseUtil();

    @Override
    public Result execute() {
        File file = parseUtil.parseFileName();
        Data data = parseUtil.readDataFromFile(file);
        return solution.compute(data);
    }
}