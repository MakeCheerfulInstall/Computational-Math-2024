package Lab3;

import Lab2.CalcParams;

public class SFunction6 implements ISFunction{
    @Override
    public double functionOf(double x) {
        return 1/Math.sqrt(2 - x);
    }

    @Override
    public String toString() {
        return "1/sqrt(2-x)";
    }

    private static int _thePoint = 2;
    /**
     * Метод разбивает интервал интегрирования вокруг точки разрыва
     * @param params параметры расчета с указанием начального интерввала интегрирования,
     * и с копией остальных параметров расчета
     * @return - массив параметров расчета
     */
    @Override
    public CalcParams[] splitInterval(CalcParams params) {
        var mas = new CalcParams[1];
        mas[0] = params.clone();
        if(mas[0].xb >= _thePoint) mas[0].xb =_thePoint - 0.001;
        return mas;
    }
}