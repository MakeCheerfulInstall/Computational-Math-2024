#ifndef COMPUTATIONAL_MATHEMATICS_EULERMETHOD_H
#define COMPUTATIONAL_MATHEMATICS_EULERMETHOD_H

typedef std::function<double(double, double)> ODEFunction;

std::vector<double> EulerMethod(ODEFunction f, double x0, double y0, double h, int steps) {
    std::vector<double> y_values(steps + 1);
    y_values[0] = y0;
    double x = x0;
    for (int i = 1; i <= steps; i++) {
        y_values[i] = y_values[i - 1] + h * f(x, y_values[i - 1]);
        x += h;
    }
    return y_values;
}

#endif