package Lab2;

public interface IMethodSys{
    void Calculate(ISysFunctions funcs, CalcParams params);
    void setLogger(ILogger logger);
}
