#ifndef SIMPLEITERATIONSMETHOD_H
#define SIMPLEITERATIONSMETHOD_H

#include "Functions.h"

class SimpleIterationsMethod {
public:
    static double SolveEquation(const std::vector<double>&segment, const double eps, const int functionChoice) {
        double lambda, x0, x;
        int counter = 0;
        switch (functionChoice) {
            case 1:
                lambda = -1 / std::max(DF1(segment[0]), DF1(segment[1]));
                if (1 + lambda * DF1(segment[0]) > 1 || 1 + lambda * DF1(segment[1]) > 1) {
                    throw std::runtime_error("Условие сходимости не выполняется.");
                }
                x0 = segment[0];
                while (true) {
                    x = x0 + lambda * F1(x0);
                    counter++;
                    if (std::abs(x - x0) <= eps) {
                        std::cout << "Корень уравнения: " << x << "\n";
                        std::cout << "Значение функции в х:" << F1(x) << "\n";
                        std::cout << "Количество итераций: " << counter << std::endl;
                        std::vector<double> x_plot(100);
                        for (int i = 0; i < 100; ++i) {
                            x_plot[i] = segment[0] + i * (segment[1] - segment[0]) / 99.0;
                        }

                        std::vector<double> y(100);
                        for (int i = 0; i < 100; ++i) {
                            y[i] = F1(x_plot[i]);
                        }

                        plt::plot(x_plot, y);
                        plt::xlabel("x");
                        plt::ylabel("f(x)");
                        plt::title("Graph of x^3 - 3.125x^2 - 3.5x + 2.458");
                        plt::grid(true);
                        plt::show();
                        return x;
                    }
                    x0 = x;
                }
            case 2:
                lambda = -1 / std::max(DF2(segment[0]), DF2(segment[1]));
                if (1 + lambda * DF2(segment[0]) > 1 || 1 + lambda * DF2(segment[1]) > 1) {
                    throw std::runtime_error("Условие сходимости не выполняется.");
                }
                x0 = segment[0];
                while (true) {
                    x = x0 + lambda * F2(x0);
                    counter++;
                    if (std::abs(x - x0) < eps) {
                        std::cout << "Корень уравнения: " << x << "\n";
                        std::cout << "Значение функции в х:" << F2(x) << "\n";
                        std::cout << "Количество итераций: " << counter << std::endl;
                        std::vector<double> x_plot(100);
                        for (int i = 0; i < 100; ++i) {
                            x_plot[i] = segment[0] + i * (segment[1] - segment[0]) / 99.0;
                        }

                        std::vector<double> y(100);
                        for (int i = 0; i < 100; ++i) {
                            y[i] = F2(x_plot[i]);
                        }

                        plt::plot(x_plot, y);
                        plt::xlabel("x");
                        plt::ylabel("f(x)");
                        plt::title("Graph of x^3 - 3.125x^2 - 3.5x + 2.458");
                        plt::grid(true);
                        plt::show();
                        return x;
                    }
                    x0 = x;
                }
            case 3:
                lambda = -1 / std::max(DF3(segment[0]), DF3(segment[1]));
                if (1 + lambda * DF3(segment[0]) > 1 || 1 + lambda * DF3(segment[1]) > 1) {
                    throw std::runtime_error("Условие сходимости не выполняется.");
                }
                x0 = segment[0];
                while (true) {
                    x = x0 + lambda * F3(x0);
                    counter++;
                    if (std::abs(x - x0) < eps) {
                        std::cout << "Корень уравнения: " << x << "\n";
                        std::cout << "Значение функции в х:" << F3(x) << "\n";
                        std::cout << "Количество итераций: " << counter << std::endl;
                        std::vector<double> x_plot(100);
                        for (int i = 0; i < 100; ++i) {
                            x_plot[i] = segment[0] + i * (segment[1] - segment[0]) / 99.0;
                        }

                        std::vector<double> y(100);
                        for (int i = 0; i < 100; ++i) {
                            y[i] = F3(x_plot[i]);
                        }

                        plt::plot(x_plot, y);
                        plt::xlabel("x");
                        plt::ylabel("f(x)");
                        plt::title("Graph of x^3 - 3.125x^2 - 3.5x + 2.458");
                        plt::grid(true);
                        plt::show();
                        return x;
                    }
                    x0 = x;
                }
            default:
                throw std::invalid_argument("Невозможно посчитать начальное приближение!");
        }
    }
};

#endif //SIMPLEITERATIONSMETHOD_H
