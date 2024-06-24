package Lab5;



public class L5Method5 extends L5Method4{

    @Override
    public double functionOf(double x) {
        calcMiddle(x);

        double res = ys[iToIndex(0)];

        int k = 1; // Номер члена в многочлене
        double t1 = 1; // Текущее значение коэффициента t*(t^2-1^2)....
        double tf = 1; // 1/f
        t1 =1;
        k = 1;
        tf = 1;
        double tt = t; // используется при i =1
        double tt2 = t*t; // В дальнейшем используем t в квадрате
        for(int i =  1; i <= n; i++){
            if(i> 1) tt = tt2;

            tf = tf/k; // в начале 1,для к = 3  будет 1/6
            t1 *= (tt - (i-1)*(i-1)); // в начале t, для k = 3 будет е*(
            res += tf * t1 * (finalDiff(-i,k) + finalDiff(-i+1,k))/2;
            k++;
            tf = tf/k; //В начале 1/2 на второй итерации 1/fac(4)
            res += t*tf *t1 * finalDiff(-i,k);
            k++;
        }
        return res;
    }

    @Override
    public String toString() {
        return "Многочлен Стирлинга";
    }
}

