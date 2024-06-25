package Lab3;

import Lab2.CalcErrorException;
import Lab2.CalcParams;

public class SFunction5 implements ISFunction{
    @Override
    public double functionOf(double x) {
        return 1/x;
    }

    @Override
    public String toString() {
        return "1/x";
    }

    @Override
    public CalcParams[] splitInterval(CalcParams params) {
        if(params.xa <=0 && params.xb >= 0) throw   new CalcErrorException("Интеграла не существует");
        CalcParams[] splits = new CalcParams[1];
        splits[0] = params.clone();
        return splits;
    }
}
