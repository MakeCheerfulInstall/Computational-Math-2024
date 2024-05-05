#ifndef FUNCTIONS_H
#define FUNCTIONS_H

#include <cmath>

inline double F1(const double x) {
    return pow(x, 3) - 3.125 * pow(x, 2) - 3.5 * x + 2.458;
}

inline double DF1(const double x) {
    return 3 * pow(x, 2) - 6.25 * x - 3.5;
}

inline double DDF1(const double x) {
    return 6 * x - 6.25;
}

inline double F2(const double x) {
    return 2 * pow(x, 3) - 1.89 * pow(x, 2) - 5 * x + 2.34;
}

inline double DF2(const double x) {
    return 6 * pow(x, 2) - 3.78 * x - 5;
}

inline double DDF2(const double x) {
    return 12 * x - 3.78;
}

inline double F3(const double x) {
    return exp(x) - 3;
}

inline double DF3(const double x) {
    return exp(x);
}

inline double DDF3(const double x) {
    return exp(x);
}

inline double F4(const double x, const double y) {
    return pow(x,2) + pow(y, 2) - 4;
}

inline double F5(const double x, const double y) {
    return -3 * pow(x,2) + y;
}

inline double DF4_DX(const double x) {
    return 2 * x;
}

inline double DF4_DY(const double y) {
    return 2 * y;
}

inline double DF5_DX(const double x) {
    return -6 * x;
}

inline double DF5_DY() {
    return 1;
}

#endif //FUNCTIONS_H
