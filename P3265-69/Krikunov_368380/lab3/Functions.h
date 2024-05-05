#ifndef MATPLOTLIB_H_FUNCTIONS_H
#define MATPLOTLIB_H_FUNCTIONS_H

#include <cmath>

double F1(double x) {
    return pow(x,3) - 3 * pow(x, 2) + 7 * x - 10;
}

double F2(double x) {
    return 2 * pow(x,3) - 3 * pow(x, 2) + 5 * x - 9;
}

double F3(double x) {
    return 2 * pow(x,3) - 9 * pow(x, 2) - 7 * x + 11;
}

double F4(double x) {
    return 1 / sqrt(x);
}

double F5(double x) {
    return 1 / (1 - x);
}

#endif