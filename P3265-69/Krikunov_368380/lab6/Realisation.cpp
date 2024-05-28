#include <iostream>
#include <vector>
#include <functional>
#include <iomanip>

#include "matplotlibcpp.h"
#include "Functions.h"
#include "EulerMethod.h"
#include "RungeKuttaMethod.h"
#include "MilneMethod.h"
#include "RungeRule.h"
#include "ErrorCalculation.h"
#include "PrintResults.h"

namespace plt = matplotlibcpp;

int main() {

    std::cout << "Выберите уравнение:" << std::endl;
    std::cout << "1) y' = y + (1 + x) * y^2" << std::endl;
    std::cout << "2) y' = y * log(x + 1)" << std::endl;
    int equationChoice;
    std::cin >> equationChoice;
    if (equationChoice < 1 || equationChoice > 2) {
        std::cerr << "Неверный выбор уравнения";
        return 1;
    }

    std::cout << "Выберите метод решения уравнения" << std::endl;
    std::cout << "1) Метод Эйлера" << std::endl;
    std::cout << "2) Метод Рунге-Кутта" << std::endl;
    std::cout << "3) Метод Милне" << std::endl;
    int methodChoice;
    std::cin >> methodChoice;
    if (methodChoice < 1 || methodChoice > 3) {
        std::cerr << "Неверный выбор способа";
        return 1;
    }

    ODEFunction f;
    switch (equationChoice) {
        case 1:
            f = F1;
            break;
        case 2:
            f = F2;
            break;
        default:
            std::cerr << "Некорректный выбор" << std::endl;
            return 1;
    }

    double x0, y0, xN, h;
    std::cout << "Введите начальное условие y0: ";
    std::cin >> y0;
    std::cout << "Введите начальное значение x0: ";
    std::cin >> x0;
    std::cout << "Введите конечное значение xN: ";
    std::cin >> xN;
    std::cout << "Введите шаг h: ";
    std::cin >> h;
    if (h == 0 || h < 0) {
        std::cerr << "Некорректный выбор";
        return 1;
    }

    int steps = static_cast<int>((xN - x0) / h);

    if (methodChoice == 1) {
        //метод Эйлера
        std::cout << "\nМетод Эйлера:" << std::endl;
        std::vector<double> EulerResults = EulerMethod(f, x0, y0, h, steps);
        PrintResults(EulerResults, x0, h);
        std::vector<double> EulerResultsHalfH = EulerMethod(f, x0, y0, h / 2, steps * 2);
        double EulerError = RungeRule(EulerResults, EulerResultsHalfH);
        std::cout << "Оценка точности методом Рунге для метода Эйлера: " << EulerError << std::endl;

        //графики
        std::vector<double> x_values(steps + 1);
        std::vector<double> exact_values(steps + 1);
        for (int i = 0; i <= steps; ++i) {
            double x = x0 + i * h;
            x_values[i] = x;
            exact_values[i] = SolutionF1(x);
        }

        plt::figure_size(1200, 780);
        plt::plot(x_values, exact_values, "b-");
        plt::plot(x_values, EulerResults, "r--");
        plt::xlabel("x");
        plt::ylabel("y");
        plt::title("Решения дифференциального уравнения");
        plt::show();
    } else if (methodChoice == 2) {
        //метод Рунге-Кутта
        std::cout << "\nМетод Рунге-Кутта 4-го порядка:" << std::endl;
        std::vector<double> RungeKuttaResults = RungeKuttaMethod(f, x0, y0, h, steps);
        PrintResults(RungeKuttaResults, x0, h);
        std::vector<double> RungeKuttaResultsHalfH = RungeKuttaMethod(f, x0, y0, h / 2, steps * 2);
        double RungeKuttaError = RungeRule(RungeKuttaResults, RungeKuttaResultsHalfH);
        std::cout << "Оценка точности методом Рунге для метода Рунге-Кутта: " << RungeKuttaError << std::endl;

        //графики
        std::vector<double> x_values(steps + 1);
        std::vector<double> exact_values(steps + 1);
        for (int i = 0; i <= steps; ++i) {
            double x = x0 + i * h;
            x_values[i] = x;
            exact_values[i] = SolutionF1(x);
        }

        plt::figure_size(1200, 780);
        plt::plot(x_values, exact_values, "b-");
        plt::plot(x_values, RungeKuttaResults, "r--");
        plt::xlabel("x");
        plt::ylabel("y");
        plt::title("Решения дифференциального уравнения");
        plt::show();
    } else if (methodChoice == 3) {
        //метод Милна
        std::cout << "\nМетод Милна:" << std::endl;
        std::vector<double> MilneResults = MilneMethod(f, x0, y0, h, steps);
        PrintResults(MilneResults, x0, h);
        double error = CalculateError(MilneResults, SolutionF2, x0, h);
        std::cout << "Максимальная ошибка: " << error << std::endl;

        //графики
        std::vector<double> x_values(steps + 1);
        std::vector<double> exact_values(steps + 1);
        for (int i = 0; i <= steps; ++i) {
            double x = x0 + i * h;
            x_values[i] = x;
            exact_values[i] = SolutionF1(x);
        }

        plt::figure_size(1200, 780);
        plt::plot(x_values, exact_values, "b-");
        plt::plot(x_values, MilneResults, "m:");
        plt::xlabel("x");
        plt::ylabel("y");
        plt::title("Решения дифференциального уравнения");
        plt::show();
    }

    return 0;
}


