#ifndef COMPUTATIONAL_MATHEMATICS_RUNGERULE_H
#define COMPUTATIONAL_MATHEMATICS_RUNGERULE_H

double RungeRule(const std::vector<double>& y_h, const std::vector<double>& y_half_h) {
    double max_error = 0.0;
    size_t n = y_half_h.size() / 2;
    for (size_t i = 0; i < n; i++) {
        double error = abs(y_h[i] - y_half_h[2 * i]) / 15.0;
        if (error > max_error) {
            max_error = error;
        }
    }
    return max_error;
}

#endif