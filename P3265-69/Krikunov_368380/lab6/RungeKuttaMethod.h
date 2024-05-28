#ifndef COMPUTATIONAL_MATHEMATICS_RUNGEKUTTAMETHOD_H
#define COMPUTATIONAL_MATHEMATICS_RUNGEKUTTAMETHOD_H

std::vector<double> RungeKuttaMethod(ODEFunction f, double x0, double y0, double h, int steps) {
    std::vector<double> y_values(steps + 1);
    y_values[0] = y0;
    double x = x0;
    for (int i = 1; i <= steps; i++) {
        double k1 = h * f(x, y_values[i - 1]);
        double k2 = h * f(x + h / 2, y_values[i - 1] + k1 / 2);
        double k3 = h * f(x + h / 2, y_values[i - 1] + k2 / 2);
        double k4 = h * f(x + h, y_values[i - 1] + k3);
        y_values[i] = y_values[i - 1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
        x += h;
    }
    return y_values;
}

#endif