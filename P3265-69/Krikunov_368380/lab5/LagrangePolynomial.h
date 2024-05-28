#ifndef COMPUTATIONAL_MATHEMATICS_LAGRANGEPOLYNOMIAL_H
#define COMPUTATIONAL_MATHEMATICS_LAGRANGEPOLYNOMIAL_H

#include <vector>

double LagrangeInterpolation(std::vector<double> &x_axis, std::vector<double> &y_axis, double x) {
    if (x_axis.size() != y_axis.size() || x_axis.empty() || y_axis.empty()) {
        throw std::invalid_argument("Неверный ввод: размеры массивов не равны или один из массивов равен нулю");
    }

    if (x < x_axis[0] || x > x_axis[x_axis.size() - 1]) {
        throw std::invalid_argument("Точка x выходит за пределы интервала x_axis");
    }

    double result = 0;

    for (size_t i = 0; i < x_axis.size(); i++) {
        double p = y_axis[i];
        for (size_t j = 0; j < x_axis.size(); j++) {
            if (i != j) {
                if (x_axis[i] == x_axis[j]) {
                    throw std::invalid_argument("Невозможно вычислить интерполяционное значение: точки x_axis совпадают");
                }
                p *= ((x - x_axis[j]) / (x_axis[i] - x_axis[j]));
            }
        }
        result += p;
    }
    return result;
}

#endif