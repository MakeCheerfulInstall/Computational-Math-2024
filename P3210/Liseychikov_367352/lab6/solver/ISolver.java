package lab6.solver;

public interface ISolver {
    double[][] solve(double start, double end, double y0, double h, double e, FuncXY funcXY);
}
