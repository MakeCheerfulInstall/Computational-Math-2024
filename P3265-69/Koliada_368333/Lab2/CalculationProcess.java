package Lab2;

public interface CalculationProcess {
     void startCalculation(IFunction func,IMethod method, CalcParams params);
     void startSysCalculation(ISysFunctions sysFunctions,IMethodSys method, CalcParams params);
}
