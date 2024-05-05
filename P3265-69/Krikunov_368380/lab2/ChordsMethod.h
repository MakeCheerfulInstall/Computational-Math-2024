#ifndef CHORDSMETHOD_H
#define CHORDSMETHOD_H

#include <vector>
#include "Functions.h"

class ChordsMethod {
public:
    static double FindRoot(const std::vector<double> &segment, const short functionChoice) {

        double fa, fb;
        switch (functionChoice) {
            case 1:
                fa = F1(segment[0]);
                fb = F1(segment[1]);
                break;
            case 2:
                fa = F2(segment[0]);
                fb = F2(segment[1]);
                break;
            case 3:
                fa = F3(segment[0]);
                fb = F3(segment[1]);
                break;
            default:
                throw std::invalid_argument("Невозможно посчитать начальное приближение!");
        }

        if (fa * fb > 0) {
            throw std::invalid_argument("Начальные точки на отрезке не обеспечивают смены знака функции!");
        }

        return segment[0] - ((segment[1] - segment[0]) * fa / (fb - fa));
    }

    static double SolveEquation(std::vector<double> &segment, const double eps, const short functionChoice) {
        double x = FindRoot(segment, functionChoice);
        int counter = 0;

        switch (functionChoice) {
            case 1:
                while (std::abs(F1(x)) > eps) {
                    if (F1(segment[0]) * F1(x) < 0) {
                        segment[1] = x;
                    } else if (F1(segment[1]) * F1(x) < 0) {
                        segment[0] = x;
                    }
                    x = FindRoot(segment, functionChoice);
                    counter++;
                }
                break;
            case 2:
                while (std::abs(F2(x)) > eps) {
                    if (F2(segment[0]) * F2(x) < 0) {
                        segment[1] = x;
                    } else if (F2(segment[1]) * F2(x) < 0) {
                        segment[0] = x;
                    }
                    x = FindRoot(segment, functionChoice);
                    counter++;
                }
                break;
            case 3:
                while (std::abs(F3(x)) > eps) {
                    if (F3(segment[0]) * F3(x) < 0) {
                        segment[1] = x;
                    } else if (F3(segment[1]) * F3(x) < 0) {
                        segment[0] = x;
                    }
                    x = FindRoot(segment, functionChoice);
                    counter++;
                }
                break;
            default:;
        }

        std::cout << "Полученное значение x: " << x << std::endl;
        std::cout << "Количество итераций: " << counter << std::endl;
        switch (functionChoice) {
            case 1: {
                std::cout << "Значение функции в х:" << F1(x) << "\n";
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
                break;
            }
            case 2: {
                std::cout << "Значение функции в х:" << F1(x) << "\n";
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
                break;
            }
            case 3: {
                std::cout << "Значение функции в х:" << F1(x) << "\n";
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
                break;
            }
        }
        return x;
    }
};

#endif //CHORDSMETHOD_H
