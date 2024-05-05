#ifndef MATPLOTLIB_H_RECTANGLEMETHOD_H
#define MATPLOTLIB_H_RECTANGLEMETHOD_H

#include "Functions.h"

class RectangleMethod {
public:
    static double LeftRectangleSolving(double a, double b, double n, unsigned short functionChoice) {
        double h = (b - a) / n;
        double integral = 0;

        switch (functionChoice) {
            case 1:
                for (int i = 0; i < n; ++i) {
                    integral += F1(a + i * h);
                }
                return integral * h;
            case 2:
                for (int i = 0; i < n; ++i) {
                    integral += F2(a + i * h);
                }
                return integral * h;
            case 3:
                for (int i = 0; i < n; ++i) {
                    integral += F3(a + i * h);
                }
                return integral * h;
        }
    }

    static double RightRectangleSolving(double a, double b, double n, unsigned short functionChoice) {
        double h = (b - a) / n;
        double integral = 0;

        switch (functionChoice) {
            case 1:
                for (int i = 1; i <= n; ++i) {
                    integral += F1(a + i * h);
                }
                return integral * h;
            case 2:
                for (int i = 1; i <= n; ++i) {
                    integral += F2(a + i * h);
                }
                return integral * h;
            case 3:
                for (int i = 1; i <= n; ++i) {
                    integral += F3(a + i * h);
                }
                return integral * h;
        }
    }

    static double CentralRectangleSolving(double a, double b, double n, unsigned short functionChoice) {
        double h = (b - a) / n;
        double integral = 0;

        switch (functionChoice) {
            case 1:
                for (int i = 0; i < n; ++i) {
                    integral += F1(a + (i + 0.5) * h);
                }
                return integral * h;
            case 2:
                for (int i = 0; i < n; ++i) {
                    integral += F2(a + (i + 0.5) * h);
                }
                return integral * h;
            case 3:
                for (int i = 0; i < n; ++i) {
                    integral += F3(a + (i + 0.5) * h);
                }
                return integral * h;
        }
    }

    static double RectangleIntegral(double a, double b, double n, double EPS, unsigned short functionChoice) {
        std::cout << "Введите модификацию метода: \n" << "1) Метод левых \n" << "2) Метод правых \n"
                  << "3) Метод средних \n" << "4) Вывести решение всеми методами \n";
        unsigned short methodChoice;
        std::cin >> methodChoice;

        switch (methodChoice) {
            case 1: {
                double I0 = LeftRectangleSolving(a, b, n, functionChoice);
                double n1 = n * 2;
                double I1 = LeftRectangleSolving(a, b, n1, functionChoice);

                while (std::abs(I1 - I0) > EPS) {
                    I0 = I1;
                    n = n1;
                    n1 *= 2;
                    I1 = LeftRectangleSolving(a, b, n1, functionChoice);
                }
                std::cout << "Значение определенного интеграла = " << I0 << "\n"
                          << "Количество разбиений = " << n;

                return I0;
            }
            case 2: {
                double I0 = RightRectangleSolving(a, b, n, functionChoice);
                double n1 = n * 2;
                double I1 = RightRectangleSolving(a, b, n1, functionChoice);

                while (std::abs(I1 - I0) > EPS) {
                    I0 = I1;
                    n = n1;
                    n1 *= 2;
                    I1 = RightRectangleSolving(a, b, n1, functionChoice);
                }
                std::cout << "Значение определенного интеграла = " << I0 << "\n"
                          << "Количество разбиений = " << n;

                return I0;
            }
            case 3: {
                double I0 = CentralRectangleSolving(a, b, n, functionChoice);
                double n1 = n * 2;
                double I1 = CentralRectangleSolving(a, b, n1, functionChoice);

                while (std::abs(I1 - I0) > EPS) {
                    I0 = I1;
                    n = n1;
                    n1 *= 2;
                    I1 = CentralRectangleSolving(a, b, n1, functionChoice);
                }
                std::cout << "Значение определенного интеграла = " << I0 << "\n"
                          << "Количество разбиений = " << n;

                return I0;
            }
            case 4:
                double I0 = LeftRectangleSolving(a, b, n, functionChoice);
                double n1 = n * 2;
                double I1 = LeftRectangleSolving(a, b, n1, functionChoice);
                while (std::abs(I1 - I0) > EPS) {
                    I0 = I1;
                    n = n1;
                    n1 *= 2;
                    I1 = LeftRectangleSolving(a, b, n1, functionChoice);
                }
                std::cout << "Значение определенного интеграла методом левых прямоугольников = " << I0 << "\n"
                          << "Количество разбиений = " << n << "\n";

                I0 = RightRectangleSolving(a, b, n, functionChoice);
                n1 = n * 2;
                I1 = RightRectangleSolving(a, b, n1, functionChoice);
                while (std::abs(I1 - I0) > EPS) {
                    I0 = I1;
                    n = n1;
                    n1 *= 2;
                    I1 = RightRectangleSolving(a, b, n1, functionChoice);
                }
                std::cout << "Значение определенного интеграла методом правых прямоугольников = " << I0 << "\n"
                          << "Количество разбиений = " << n << "\n";

                I0 = CentralRectangleSolving(a, b, n, functionChoice);
                n1 = n * 2;
                I1 = CentralRectangleSolving(a, b, n1, functionChoice);
                while (std::abs(I1 - I0) > EPS) {
                    I0 = I1;
                    n = n1;
                    n1 *= 2;
                    I1 = CentralRectangleSolving(a, b, n1, functionChoice);
                }
                std::cout << "Значение определенного интеграла методом средних = " << I0 << "\n"
                          << "Количество разбиений = " << n << "\n";
        }
    }
};

#endif //MATPLOTLIB_H_RECTANGLEMETHOD_H