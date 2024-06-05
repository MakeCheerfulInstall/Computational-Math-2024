#ifndef COMPUTATIONAL_MATHEMATICS_STIRLINGINTERPOLATION_H
#define COMPUTATIONAL_MATHEMATICS_STIRLINGINTERPOLATION_H

#include <vector>

int Factorial(int n) {
    if (n == 0)
        return 1;
    else
        return n * Factorial(n - 1);
}

double DividedDifference(std::vector<double>& x, std::vector<double>& y, int i, int j) {
    if (j == i)
        return y[i];
    else
        return (DividedDifference(x, y, i + 1, j) - DividedDifference(x, y, i, j - 1)) / (x[j] - x[i]);
}

double StirlingInterpolation(std::vector<double>& x_axis, std::vector<double>& y_axis, double x) {
    if (x_axis.size() != y_axis.size() || x_axis.empty() || y_axis.empty()) {
        throw std::invalid_argument("Неверный ввод: размеры массивов не равны или один из массивов равен нулю");
    }

    if (x < x_axis[0] || x > x_axis[x_axis.size() - 1]) {
        throw std::invalid_argument("Точка x выходит за пределы интервала x_axis");
    }

    int n = x_axis.size();
    double result = 0;

    for (int i = 0; i < n; i++) {
        double term = DividedDifference(x_axis, y_axis, 0, i);
        for (int j = 0; j < i; j++) {
            term *= (x - x_axis[j]);
        }
        result += term;
    }

    return result;
}

#endif