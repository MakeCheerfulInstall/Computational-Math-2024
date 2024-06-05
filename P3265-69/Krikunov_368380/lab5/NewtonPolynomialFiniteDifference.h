#ifndef COMPUTATIONAL_MATHEMATICS_NEWTONPOLYNOMIALCD_H
#define COMPUTATIONAL_MATHEMATICS_NEWTONPOLYNOMIALCD_H

double NewtonInterpolationFD(std::vector<double> &x_axis, std::vector<double> &y_axis, double x) {
    std::vector<std::vector<double>> dy(x_axis.size() - 1, std::vector<double>(x_axis.size() - 1, 0));
    double t, h, mult, sum;
    int factorial;

    h = (x_axis[1] - x_axis[0]);

    for (int i = 0; i < x_axis.size() - 1; i++) {
        dy[i][0] = y_axis[i + 1] - y_axis[i];
    }

    for (int i = 1; i < x_axis.size() - 1; i++) {
        for (int j = 0; j < x_axis.size() - 1 - i; ++j) {
            dy[j][i] = dy[j + 1][i - 1] - dy[j][i - 1];
        }
    }

    mult = 1;
    sum = y_axis[0];
    t = (x - x_axis[0]) / h;
    factorial = 1;
    for (int i = 0; i < x_axis.size() - 1; i++) {
        mult *= (t - i);
        factorial *= i + 1;
        sum += mult * dy[0][i] / (factorial);
    }

    t = (x - x_axis[x_axis.size() - 1]) / h;
    sum = y_axis[x_axis.size() - 1];
    mult = 1;
    factorial = 1;
    for (int i = 0; i < x_axis.size() - 1; i++) {
        mult *= (t + i);
        factorial *= i + 1;
        sum += mult * dy[x_axis.size() - 1 - i - 1][i] / (factorial);
    }

    return sum;
}

#endif