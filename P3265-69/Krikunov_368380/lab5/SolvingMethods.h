#ifndef COMPUTATIONAL_MATHEMATICS_SOLVINGMETHODS_H
#define COMPUTATIONAL_MATHEMATICS_SOLVINGMETHODS_H

#include <functional>
#include <fstream>
#include "LagrangePolynomial.h"
#include "NewtonPolynomialFiniteDifference.h"
#include "NewtonPolynomialDividedDifference.h"
#include "Difference.h"

double Interpolate(std::vector<double> &x_axis, std::vector<double> &y_axis, double x,
                   std::function<double(std::vector<double> &, std::vector<double> &,
                                        double)> interpolationFunction) {
    return interpolationFunction(x_axis, y_axis, x);
}

double CustomFunction(double x, size_t choice) {
    switch (choice) {
        case 1:
            return sin(x);
        case 2:
            return cos(x);
        case 3:
            return x * x;
        default:
            return 0.0;
    }
}


#endif