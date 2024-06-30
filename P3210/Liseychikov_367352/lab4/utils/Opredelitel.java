package lab4.utils;

public class Opredelitel {
    private Opredelitel() {
    }

    public static double findOpred3(double[][] arr) {
        double pos = arr[0][0] * arr[1][1] * arr[2][2] + arr[0][1] * arr[1][2] * arr[2][0] + arr[0][2] * arr[1][0] * arr[2][1];
        double neg = arr[0][2] * arr[1][1] * arr[2][0] + arr[0][1] * arr[1][0] * arr[2][2] + arr[0][0] * arr[1][2] * arr[2][1];
        return pos - neg;
    }

    public static double findOpred4(double[][] arr) {
        int sign = 1;
        int r = 0;
        double result = 0;
        for (int i = 0; i < 4; i++) {
            double[][] additionalArr = new double[3][3];
            for (int j = 0, ind = 0; j < 4; j++, ind++) {
                if (j == i) {
                    ind--;
                    continue;
                }
                for (int k = 1; k < 4; k++) {
                    additionalArr[k - 1][ind] = arr[k][j];
                }
            }
            result += sign * arr[r][i] * findOpred3(additionalArr);
            sign *= -1;
        }
        return result;
    }
}
