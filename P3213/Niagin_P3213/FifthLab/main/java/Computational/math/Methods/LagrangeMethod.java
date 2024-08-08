package Computational.math.Methods;

import Computational.math.Utils.FunctionalTable;

public class LagrangeMethod extends AbstractMethod{
    public LagrangeMethod() {
        super("Полином Лагранжа");
    }

    @Override
    public Double apply(FunctionalTable functionalTable, double x_current) {
        var xArr = functionalTable.getxArr();
        var yArr = functionalTable.getyArr();
        double res = 0d;
        for (int i = 0; i < xArr.length; i++) {
            var p = 1d;
            for (int j = 0; j < yArr.length; j++) {
                if (i != j){
                    p*=(x_current - xArr[j])/(xArr[i]-xArr[j]);
                }
            }
            res += p*yArr[i];
        }
        return res;
    }
}
