package lab5.methods;

public class Bessel extends Polynomial{
    @Override
    public double execute() {
        var values = Polynomial.getValues();

        float x[]=new float[10],y[][]=new float[10][10];
        double v = Polynomial.getX(), sum, u;
        int k;

        int n = values.size() - 1;

        int i,j;

        for (i = 1; i<n; i++)
            for (j = 0; j<n - i; j++)
                y[j][i] = y[j + 1][i - 1] - y[j][i - 1];

        for (i = 0; i<n; i++) {
            for (j = 0; j<n - i; j++)
                System.out.print(y[i][j]+"\t");
            System.out.println();
        }

        sum = (y[2][0] + y[3][0]) / 2;

        if (n % 2!=0)
            k = n / 2;
        else
            k = n / 2 - 1;

        u = (v - x[k]) / (x[1] - x[0]);

        for (i = 1; i<n; i++) {
            if (i % 2!=0)
                sum = (float) (sum+((u - 0.5)*cal_u(u, i - 1) * y[k][i]) / factorial(i));
            else
                sum = sum + (cal_u(u, i) * (y[k][i] + y[--k][i]) / (factorial(i) * 2));
        }
        System.out.println(sum);
        return sum;
    }

    int factorial(int n) {
        int fact = 1,i=2;
        for (i = 2; i<= n; i++)
            fact= fact*i;
        return fact;
    }

    double cal_u(double u, int n) {
        double tmp;
        int i;
        if (n == 0)
            return 1;
        tmp = u;
        for (i = 1; i<= n / 2; i++)
            tmp = tmp * (u - i);
        for (i = 1; i<n / 2; i++)
            tmp = tmp * (u + i);
        return tmp;
    }
}
