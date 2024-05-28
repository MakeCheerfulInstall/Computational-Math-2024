#ifndef COMPUTATIONAL_MATHEMATICS_NEWTONPOLYNOMIALDIVIDEDDIFFERENCE_H
#define COMPUTATIONAL_MATHEMATICS_NEWTONPOLYNOMIALDIVIDEDDIFFERENCE_H

double DividedDifference(double x0, double x1, double y0, double y1) {
    return (y1 - y0) / (x1 - x0);
}

double NewtonInterpolationDD(const std::vector<double> &x_axis, const std::vector<double> &y_axis, double x) {

    if (x_axis.size() != y_axis.size() || x_axis.empty() || y_axis.empty()) {
        throw std::invalid_argument("Неверный ввод: размеры массивов не равны или один из массивов равен нулю");
    }

    if (x < x_axis[0] || x > x_axis[x_axis.size() - 1]) {
        throw std::invalid_argument("Точка x выходит за пределы интервала x_axis");
    }

    std::vector<std::vector<double>> F(x_axis.size(), std::vector<double>(x_axis.size()));

    for (int i = 0; i < x_axis.size(); i++) {
        F[i][0] = y_axis[i];
    }

    for (int j = 1; j < x_axis.size(); j++) {
        for (int i = 0; i < x_axis.size() - j; i++) {
            F[i][j] = DividedDifference(x_axis[i], x_axis[i + j], F[i][j - 1], F[i + 1][j - 1]);
        }
    }

    double result = F[0][0];
    double term = 1;
    for (int i = 1; i < x_axis.size(); i++) {
        term *= (x - x_axis[i - 1]);
        result += F[0][i] * term;
    }
    return result;
}

#endif