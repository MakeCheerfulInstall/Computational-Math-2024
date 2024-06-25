package Lab5;

/**
 * Многочлен Бесселя
 */
public class L5Method6 extends L5Method4{
    @Override
    public double functionOf(double x) {
        calcMiddle(x);

        double tMinusHalf = t -1/2.;
        double res = (ys[iToIndex(0)] + ys[iToIndex(1)])/2 + tMinusHalf*finalDiff(0,1);

        int k = 1; // Номер члена в многочлене
        double t1 = t; // Текущее значение коэффициента t*(t^2-1^2)....
        double tf = 1; // 1/f
        for(int i =  1; i <= n; i++){
            k++;
            if(i == 1)
                t1 = t1 *(t - 1);
            else {
                t1 = t1 *(t - i)*(t+i-1);
            }
            tf = tf/k;
            res += t1 * tf * (finalDiff(-i,k) + finalDiff(-i+1,k))/2;
            k++;

            tf=tf/k;
            if(tMinusHalf != 0) res += tMinusHalf * t1 * tf * finalDiff(-i,k);

        }
        return res;
    }

    @Override
    public String toString() {
        return "Многочлен Бесселя";
    }
}
