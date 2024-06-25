import methods.BreakPointsChecker;
import methods.MethodHandler;
import methods.RectangleMethod;
import storage.FunctionStorage;
import util.Printer;
import util.Reader;

import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.MatchResult;

public class Main {
    public static void main(String[] args) throws TimeoutException {
        int numberOfFunction = Reader.readNumberOfFunction();
        FunctionStorage.setNumberOfFunction(numberOfFunction);
        int typeOfMethod = Reader.readTypeOfMethod();
        MethodHandler.setTypeOfMethod(typeOfMethod);
        if (typeOfMethod == 1) RectangleMethod.setTypeOfRectangleMethod(Reader.readTypeOfRectangleMethod());
        double[] ab = Reader.readAB();
        double accuracy = Reader.readAccuracy();

        var checker = new BreakPointsChecker();

        List<Double> bp = checker.getBreakPoints(ab[0], ab[1], ((int) Math.ceil(ab[1] - ab[0])) * 1000);

        if (!bp.isEmpty()) {
            System.out.print("Обнаружены точки разрыва: функция имеет разрыв или не существует в точках: ");
            for (double e: bp) {
                System.out.print(e + " ");
            }

            System.out.println();

            double eps = 0.00001;
            boolean converges = true;
            for (double p: bp) {
                Double y1 = checker.tryToCompute(p - eps);
                Double y2 = checker.tryToCompute(p + eps);
                if (y1 != null && y2 != null && Math.abs(y1 - y2) > eps || (y1 != null && y1.equals(y2))) {
                    converges = false;
                    break;
                }
            }

            if (!converges) {
                System.out.println("Интеграл не сходится");
                Double res = 0d;
                int n = 0;
                if (bp.size() == 1) {
                    System.out.println(ab[0]);
                    System.out.println(bp.get(0) - eps);
                    var results = MethodHandler.execute(ab[0], bp.get(0) - eps, accuracy);
                    res += results[0];
                    n += results[1];
                    var results1 = MethodHandler.execute(bp.get(0) + eps, ab[1], accuracy);
                    res += results1[0];
                    n += results1[1];
                } else {

                    for (int i = 1; i < bp.size(); i++) {
                        double[] results;
                        if (i == 1) {
                            results = MethodHandler.execute(ab[0], bp.get(0) - eps, accuracy);
                            res += results[0];
                            n += results[1];
                        } else if (i == bp.size() - 1) {
                            results = MethodHandler.execute(bp.get(i), ab[1], accuracy);
                            res += results[0];
                            n += results[1];
                        } else {
                            var results1 = MethodHandler.execute(bp.get(i - 1), bp.get(i) - eps, accuracy);
                            res += results1[0];
                            n += results1[1];
                        }
                    }
                }
                System.out.println("Значение интеграла: " + res);
                System.out.println("Число разбиений: " + n);
                return;
            } else {
                System.out.println("Интеграл сходится");
                if (bp.size() == 1) {
                    if (bp.contains(ab[0])){
                        ab[0] += eps;
                    } else if (bp.contains(ab[1])) {
                        ab[1] -= eps;
                    }
                } else {
                    Double res = 0d;
                    int n  = 0;
                    if (!(checker.tryToCompute(ab[0]) == null || checker.tryToCompute(bp.get(0) - eps) == null)) {
                        var results = MethodHandler.execute(ab[0], bp.get(0) - eps, accuracy);
                        res += results[0];
                        n += (int) results[1];
                    }

                    if (!(checker.tryToCompute(ab[1]) == null || checker.tryToCompute(bp.get(0) + accuracy) == null)) {
                        var results = MethodHandler.execute(bp.get(0) + eps, ab[1], accuracy);
                        res += results[0];
                        n += (int) results[1];
                    }

                    int len = bp.size();

                    for (int bi = 0; bi < len - 1; bi++) {
                        double b_cur = bp.get(bi);
                        double b_next = bp.get(bi + 1);

                        if (! (checker.tryToCompute(b_cur + eps) == null || checker.tryToCompute(b_next - accuracy) == null)) {
                            var results = MethodHandler.execute(b_cur + eps, b_next - eps, accuracy);
                            res += results[0];
                            n += (int) results[1];
                        }
                    }

                    System.out.println("Значение интеграла: " + res);
                    System.out.println("Число разбиений: " + n);
                    if (bp.isEmpty() || bp.contains(ab[0] - eps) || bp.contains(ab[1] + eps)) {
                        double[] results = MethodHandler.execute(ab[0], ab[1], accuracy);
                        System.out.println("Значение интеграла: " + results[0]);
                        System.out.println("Количество разбиений: " + results[1]);
                    }
                }
            }
            return;
        }

        try {
            MethodHandler.execute(ab[0], ab[1], accuracy);
        }catch (TimeoutException e){
            System.out.println(Printer.getRedText("Error!"));
            System.out.println(Printer.getRedText("There is a discontinuity of the first kind in the selected interval"));
        }
    }
}