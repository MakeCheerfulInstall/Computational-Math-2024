#ifndef NEWTON_METHOD_H
#define NEWTON_METHOD_H

#include <vector>
#include "Functions.h"
#include "matplotlibcpp.h"

namespace plt = matplotlibcpp;

class NewtonMethod {
public:
    static double FindRoot(const std::vector<double> &segment, short functionChoice) {
        double x0 = 0;

        switch (functionChoice) {
            case 1:
                if (F1(segment[0]) * DDF1(segment[0]) > 0) {
                    x0 = segment[0];
                } else if (F1(segment[1]) * DDF1(segment[1]) > 0) {
                    x0 = segment[1];
                } else if (F1(segment[0]) * DDF1(segment[0]) < 0 && F1(segment[1]) * DDF1(segment[1]) < 0) {
                    x0 = std::abs(segment[0] + segment[1]) / 2.0;
                } else {
                    throw std::invalid_argument("Невозможно выбрать начальное приближение!");
                }
                break;
            case 2:
                if (F2(segment[0]) * DDF2(segment[0]) > 0) {
                    x0 = segment[0];
                } else if (F2(segment[1]) * DDF2(segment[1]) > 0) {
                    x0 = segment[1];
                } else if (F2(segment[0]) * DDF2(segment[0]) < 0 && F2(segment[1]) * DDF2(segment[1]) < 0) {
                    x0 = std::abs(segment[0] + segment[1]) / 2.0;
                } else {
                    throw std::invalid_argument("Невозможно выбрать начальное приближение!");
                }
                break;
            case 3:
                if (F3(segment[0]) * DDF3(segment[0]) > 0) {
                    x0 = segment[0];
                } else if (F3(segment[1]) * DDF3(segment[1]) > 0) {
                    x0 = segment[1];
                } else if (F3(segment[0]) * DDF3(segment[0]) < 0 && F3(segment[1]) * DDF3(segment[1]) < 0) {
                    x0 = std::abs(segment[0] + segment[1]) / 2.0;
                } else {
                    throw std::invalid_argument("Невозможно выбрать начальное приближение!");
                }
                break;
            default:;
        }

        return x0;
    }

    static double SolveEquation(const std::vector<double> &segment, const double eps, const short functionChoice) {
        double x = FindRoot(segment, functionChoice);
        int counter = 0;

        switch (functionChoice) {
            case 1: {
                while (std::abs(F1(x)) > eps) {
                    x = x - (F1(x) / DF1(x));
                    counter++;
                }
                break;
            }
            case 2:
                while (std::abs(F2(x)) > eps) {
                    x = x - (F2(x) / DF2(x));
                    counter++;
                }
                break;
            case 3:
                while (std::abs(F3(x)) > eps) {
                    x = x - (F3(x) / DF3(x));
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

#endif // NEWTON_METHOD_H
