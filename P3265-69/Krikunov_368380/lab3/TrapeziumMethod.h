#ifndef MATPLOTLIB_H_TRAPEZIUMMETHOD_H
#define MATPLOTLIB_H_TRAPEZIUMMETHOD_H

#include "Functions.h"

class TrapeziumMethod {
public:
    static double TrapeziumSolving(double a, double b, double n, unsigned short functionChoice) {
        double h = (b - a) / n;
        double integral = 0;

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

        for (int i = 1; i <= n; i++) {
            x[i] = a + i * h;
            switch (functionChoice) {
                case 1:
                    y[i] = F1(x[i]);
                    break;
                case 2:
                    y[i] = F2(x[i]);
                    break;
                case 3:
                    y[i] = F3(x[i]);
                    break;
            }
        }

        for (int i = 1; i < n; i++) {
            integral += y[i];
        }

        integral += (y[0] + y[n]) / 2;
        integral *= h;

        return integral;
    }

    static double CalculateIntegral(double a, double b, double n, double EPS, unsigned short functionChoice) {
        double I0 = TrapeziumSolving(a, b, n, functionChoice);
        double n1 = n * 2;
        double I1 = TrapeziumSolving(a, b, n1, functionChoice);

        while (std::abs(I1 - I0) > EPS) {
            I0 = I1;
            n = n1;
            n1 *= 2;
            I1 = TrapeziumSolving(a, b, n1, functionChoice);
        }
        std::cout << "Значение определенного интеграла = " << I0 << "\n"
                  << "Количество разбиений = " << n;

        return I0;
    }
};

#endif //MATPLOTLIB_H_TRAPEZIUMMETHOD_H