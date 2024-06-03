#include <iostream>
#include <fstream>
#include "NewtonMethod.h"
#include "ChordsMethod.h"
#include "SimpleIterationsMethod.h"
#include "SystemNewtonMethod.h"
#include "matplotlibcpp.h"

int main() {
    short functionChoice;
    std::cout << "Введите номер функции, которую хотите решить:" << "\n" <<
              "1) F1(x) = x^3 - 3.125x^2 - 3.5x + 2.458" << "\n" <<
              "2) F2(x) = 2x^3 - 1.89x^2 - 5x + 2.34" << "\n" <<
              "3) F3(x) = e^x - 3" << "\n" <<
              "4) F4(x, y) = \n" <<
              "{x^2 + y^2 - 4 = 0 \n" <<
              "{-3 * x^2 + y = 0 \n";
    std::cin >> functionChoice;
    if (functionChoice > 4 or functionChoice < 1 or typeid(functionChoice) != typeid(short)) {
        throw std::invalid_argument("Неверный выбор функции!");
    }

    short choiceMethod;
    std::cout << "Выберите способ решения:" << "\n" << "1) Метод Ньютона" << "\n" << "2) Метод хорд" << "\n" <<
              "3) Метод простой итерации" << "\n" << "4) Метод Ньютона для систем" << "\n";
    std::cin >> choiceMethod;
    if (choiceMethod > 4 or choiceMethod < 1 or typeid(choiceMethod) != typeid(short)) {
        throw std::invalid_argument("Неверный выбор метода!");
    }

    short dataInput;
    std::cout << "Как бы вы хотели получить значения границы интервала, "
                 "начальное приближение к корню(в случае, где оно не высчитывается) и погрешность вычисления?" << "\n" <<
              "1 - вручную" << "\n" << "2 - из файла" << "\n";
    std::cin >> dataInput;
    if (dataInput > 2 or dataInput < 1 or typeid(dataInput) != typeid(short)) {
        throw std::invalid_argument("Неверный выбор способа получения данных!");
    }

    std::vector<double> segment(2);
    double EPS;
    if (dataInput == 1) {
        std::cout << "Введите границу отрезка через пробел:" << "\n";
        std::cin >> segment[0] >> segment[1];
        if (segment[0] >= segment[1]) {
            throw std::invalid_argument("Начальное значение интервала должно быть меньше конечного значения!");
        }
        if ((functionChoice == 1 && F1(segment[0]) * F1(segment[1]) > 0) ||
            (functionChoice == 2 && F2(segment[0]) * F2(segment[1]) > 0) ||
            (functionChoice == 3 && F3(segment[0]) * F3(segment[1]) > 0)) {
            throw std::invalid_argument("На заданном интервале нет корня!");
        }
        std::cout << "Введите погрешность:" << "\n";
        std::cin >> EPS;
        if (EPS < 0 or typeid(EPS) != typeid(double)) {
            throw std::invalid_argument("Некорректно введена погрешность!");
        }
    } else if (dataInput == 2) {
        std::ifstream inputFile("test.txt");
        if (!inputFile.is_open()) {
            throw std::runtime_error("Не удалось открыть файл!");
        }
        inputFile >> segment[0] >> segment[1] >> EPS;
        inputFile.close();
    }

    if (choiceMethod == 1) {
        auto result = NewtonMethod::SolveEquation(segment, EPS, functionChoice);
        std::cout << "Решение: x = " << result << std::endl;
    } else if (choiceMethod == 2) {
        auto result = ChordsMethod::SolveEquation(segment, EPS, functionChoice);
        std::cout << "Решение: x = " << result << std::endl;
    } else if (choiceMethod == 3) {
        auto result = SimpleIterationsMethod::SolveEquation(segment, EPS, functionChoice);
        std::cout << "Решение: x = " << result << std::endl;
    } else if (choiceMethod == 4) {
        auto result = SystemNewtonMethod::SolveEquation(segment[0], segment[1], EPS);
        std::cout << "Приближенное решение: x = " << result[0] << ", y = " << result[1] << std::endl;
    }

    return 0;
}