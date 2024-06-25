package lab5.methods;

import java.util.ArrayList;
import java.util.List;

public class Newton extends Polynomial{
    @Override
    public double execute() {
        double x = getX();
        ArrayList<Double[]> values = getValues();
        double result = values.get(0)[1];
        for(int i = 2; i < values.size() + 1; i++){
            List<Double[]> mas = values.subList(0, i);
            double production = calculateDividedDifference(mas);
            for (int j = 0; j < i - 1; j++){
                production *= (x - values.get(j)[0]);
            }
            result += production;
        }
        return result;
    }

    public double calculateDividedDifference(List<Double[]> mas){
        List<Double[]> mas1 = mas.subList(1, mas.size());
        List<Double[]> mas2 = mas.subList(0, mas.size() - 1);
        if (mas1.size() == 1 && mas2.size() == 1) {
            return (
                    (mas1.get(0)[1] - mas2.get(0)[1]) /
                            (mas1.get(0)[0] - mas2.get(0)[0])
            );
        }
        return (
                (calculateDividedDifference(mas1) - calculateDividedDifference(mas2)) /
                        (mas.get(mas.size() - 1)[0] - mas.get(0)[0])
        );
    }
}
