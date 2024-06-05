#ifndef COMPUTATIONAL_MATHEMATICS_ERRORCALCULATION_H
#define COMPUTATIONAL_MATHEMATICS_ERRORCALCULATION_H

#include <vector>
#include <cmath>

typedef std::function<double(double)> ExactSolution;

double CalculateError(const std::vector<double> &numerical, ExactSolution solution, double x0, double h) {
    double max_error = 0.0;
    for (size_t i = 0; i < numerical.size(); i++) {
        double x = x0 + i * h;
        double exact_value = solution(x);
        double error = std::abs(exact_value - numerical[i]);
        if (error > max_error) {
            max_error = error;
        }
    }
    return max_error;
}

#endif