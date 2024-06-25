package lab5.methods;


import java.util.ArrayList;

public class Lagrange extends Polynomial{
    @Override
    public double execute() {
        double x = getX();
        ArrayList<Double[]> values = getValues();
        double result = 0;
        for(int i = 0; i < values.size(); i++){
            double intermediateResult = 1;
            for(int j = 0; j < values.size(); j++){
                if (i == j) continue;
                intermediateResult *= (
                        (x - values.get(j)[0]) /
                                (values.get(i)[0] - values.get(j)[0])
                );
            }
            result += intermediateResult * values.get(i)[1];
        }
        return result;
    }
}
