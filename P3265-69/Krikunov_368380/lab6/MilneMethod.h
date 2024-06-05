#ifndef COMPUTATIONAL_MATHEMATICS_MILNEMETHOD_H
#define COMPUTATIONAL_MATHEMATICS_MILNEMETHOD_H

std::vector<double> MilneMethod(ODEFunction f, double x0, double y0, double h, int steps) {
    std::vector<double> y_values(steps + 1);
    std::vector<double> predictor(steps + 1);
    std::vector<double> corrector(steps + 1);

    std::vector<double> RungeKutta_values = RungeKuttaMethod(f, x0, y0, h, 3);
    for (int i = 0; i < 4; i++) {
        y_values[i] = RungeKutta_values[i];
    }

    for (int i = 3; i < steps; i++) {
        double x = x0 + i * h;
        predictor[i + 1] = y_values[i - 3] + (4 * h / 3) * (2 * f(x, y_values[i]) - f(x - h, y_values[i - 1]) +
                                                            2 * f(x - 2 * h, y_values[i - 2]));
        corrector[i + 1] = y_values[i - 1] +
                           (h / 3) * (f(x + h, predictor[i + 1]) + 4 * f(x, y_values[i]) + f(x - h, y_values[i - 1]));
        y_values[i + 1] = corrector[i + 1];
    }
    return y_values;
}


#endif