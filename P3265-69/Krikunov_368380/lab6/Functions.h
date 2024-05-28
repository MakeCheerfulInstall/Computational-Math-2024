#ifndef COMPUTATIONAL_MATHEMATICS_FUNCTIONS_H
#define COMPUTATIONAL_MATHEMATICS_FUNCTIONS_H

double F1(double x, double y) {
    return y + (1 + x) * pow(y, 2);
}

double F2(double x, double y) {
    return y * log(x + 1);
}

double SolutionF1(double x) {
    return -(pow(2.71, x)) / (x * pow(2.71, x));
}

double SolutionF2(double x) {
    return exp((x * (x + 2)) / 2);
}


#endif