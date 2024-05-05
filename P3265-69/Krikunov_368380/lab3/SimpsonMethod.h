#ifndef MATPLOTLIB_H_SIMPSONMETHOD_H
#define MATPLOTLIB_H_SIMPSONMETHOD_H

#include <cmath>
#include <vector>
#include <iostream>
#include "Functions.h"

class SimpsonMethod {
public:
    static double SimpsonSolving(double a, double b, double n, unsigned short functionChoice) {
        double h = (b - a) / n;
        double integral = 0;
        double even = 0, odd = 0;
        const double epsilon = 0.01;

        std::vector<double> x(n, 0);
        std::vector<double> y(n, 0);

        switch (functionChoice) {
            case 1:
                y[0] = F1(a);
                y[n] = F1(b);
                break;
            case 2:
                y[0] = F2(a);
                y[n] = F2(b);
                break;
            case 3:
                y[0] = F3(a);
                y[n] = F3(b);
                break;
        }

        for (int i = 1; i < n; i++) {
            x[i] = a + i * h;
            switch (functionChoice) {
                case 1:
                    while (!std::isfinite(F1(x[i]))) {
                        x[i] += epsilon;
                    }
                    y[i] = F1(x[i]);
                    break;
                case 2:
                    while (!std::isfinite(F2(x[i]))) {
                        x[i] += epsilon;
                    }
                    y[i] = F2(x[i]);
                    break;
                case 3:
                    while (!std::isfinite(F3(x[i]))) {
                        x[i] += epsilon;
                    }
                    y[i] = F3(x[i]);
                    break;
            }
        }

        for (int i = 1; i < n; i++) {
            if (i % 2 == 0) {
                even += y[i];
            } else {
                odd += y[i];
            }
        }
        even *= 2;
        odd *= 4;
        integral = (h / 3) * (y[0] + even + odd + y[n]);

        return integral;
    }

    static double CalculateIntegral(double a, double b, double n, double EPS, unsigned short functionChoice) {

        switch (functionChoice) {
            case 1:
                while (!std::isfinite(F1(a))) {
                    a += EPS;
                }
                while (!std::isfinite(F1(b))) {
                    b -= EPS;
                }
                break;
            case 2:
                while (!std::isfinite(F2(a))) {
                    a += EPS;
                }
                while (!std::isfinite(F2(b))) {
                    b -= EPS;
                }
                break;
            case 3:
                while (!std::isfinite(F3(a))) {
                    a += EPS;
                }
                while (!std::isfinite(F3(b))) {
                    b -= EPS;
                }
                break;
        }

        double I0 = SimpsonSolving(a, b, n, functionChoice);
        double n1 = n * 2;
        double I1 = SimpsonSolving(a, b, n1, functionChoice);

        while (std::abs(I1 - I0) > EPS) {
            I0 = I1;
            n = n1;
            n1 *= 2;
            I1 = SimpsonSolving(a, b, n1, functionChoice);
        }
        std::cout << "Значение определенного интеграла = " << I0 << "\n"
                  << "Количество разбиений = " << n;

        return I0;
    }
};

#endif