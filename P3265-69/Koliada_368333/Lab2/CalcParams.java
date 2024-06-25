package Lab2;

/**
 * Параметры расчета
 */
public class CalcParams {
    /**
     * Левая граница начального интервала
     */
    public CalcParams() {

    }
    public CalcParams(String sp){
        readFromString(sp);
    }

    /**
     * Заполняет параметры из текстовой строки
     * @param sp - строка с параметрами
     */
    private void readFromString(String sp){
        String[] ps = sp.split(" ");
        if(ps.length < 10)
            throw new FileFormatException("Файл должен содержать одну строку с 10-ю числами через пробел");
        int count = 0;
        try {
            xa = Double.parseDouble(ps[count++]);
            xb = Double.parseDouble(ps[count++]);
            startingDelta = Double.parseDouble(ps[count++]);
            precision = Double.parseDouble(ps[count++]);
            asMinX = Double.parseDouble(ps[count++]);
            asMaxX = Double.parseDouble(ps[count++]);
            asMinY = Double.parseDouble(ps[count++]);
            asMaxY = Double.parseDouble(ps[count++]);
            iaX = Double.parseDouble(ps[count++]);
            iaY = Double.parseDouble(ps[count++]);
        }
        catch(NumberFormatException e){
            throw  new FileFormatException(String.format("Ошибка в формате числа в файле в %d числе",count));
        }
    }

    /**
     * Преобразует параметры в строку
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s %s %s %s",
                String.valueOf(xa),
                String.valueOf(xb),
                String.valueOf(startingDelta),
                String.valueOf(precision),
                String.valueOf(asMinX),
                String.valueOf(asMaxX),
                String.valueOf(asMinY),
                String.valueOf(asMaxY),
                String.valueOf(iaX),
                String.valueOf(iaY));
    }

    public  CalcParams clone() {
        CalcParams clone = new CalcParams();
        clone.xa = xa;
        clone.xb = xb;
        clone.startingDelta = startingDelta;
        clone.precision = precision;
        clone.asMinX = asMinX;
        clone.asMaxX = asMaxX;
        clone.asMinY = asMinY;
        clone.asMaxY = asMaxY;
        clone.iaX = iaX;
        clone.iaY = iaY;
        clone.dotsNumber = dotsNumber;
        clone.argument = argument;
        return clone;
    }

    /**
     * Запрошенное количество точек на интервале
     */
    public int dotsNumber;

    /**
     * Левая граница начального интервала
     */
    public double xa;
    /**
     * Правая граница начального интервала
     */
    public double xb;
    /**
     * Начальное приближение
     */
    public double startingDelta;
    /**
     * Точность расчетов
     */
    public double precision;
    /**
     * Минимальный х области решений систему уравнений
     */
    public double asMinX;
    /**
     * Максимальный х области решений системы уравнений
     */
    public double asMaxX;
    /**
     * Начальное приближение по x
     */
    public double iaX;
    /**
     * Минимальный y области решений сисетмы уравнений
     */
    public double asMinY;
    /**
     * Максимальный y области решений системы уравнений
     */
    public double asMaxY;
    /**
     * Начальное приближение по y
     */
    public double iaY;

    public double argument;

}
