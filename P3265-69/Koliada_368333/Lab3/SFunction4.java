package Lab3;

import Lab2.CalcParams;

public class SFunction4 implements ISFunction{
    @Override
    public double functionOf(double x) {
        if(x == 0) return Double.NaN;
        return 1 / (x*x);
    }

    @Override
    public String toString() {
        return "1/(x*x)";
    }

    /**
     * Метод разбивает интервал интегрирования вокруг точки разрыва
     * @param params параметры расчета с указанием начального интерввала интегрирования,
     * и с копией остальных параметров расчета
     * @return - массив параметров расчета
     */
    @Override
    public CalcParams[] splitInterval(CalcParams params) {
        if(params.xa < 0 && params.xb > 0) {
            var mas = new CalcParams[2];
            mas[0] = params.clone();
            mas[0].xb = -0.001;
            mas[1] = params.clone();
            mas[1].xa = 0.001;
            return mas;
        }
        var mas = new CalcParams[1];

        mas[0] = params.clone();
        if(params.xa == 0) mas[0].xa = 0.001;
        if(params.xb == 0) mas[0].xb = -0.001;
        return mas;
    }
}
