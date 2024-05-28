#include <iostream>
#include <vector>
#include <cmath>
#include <fstream>

#include "ChooseApproximationFunction.h"
#include "LinearApproximation.h"
#include "QuadraticApproximation.h"
#include "ExponentialApproximation.h"
#include "LogarithmApproximation.h"
#include "PowerApproximation.h"

int main() {
    char choice;
    std::cout << "Хотите ввести значения вручную (1) или из файла (2)? ";
    std::cin >> choice;

    std::vector<double> x;
    std::vector<double> y;

    if (choice == '1') {
        size_t numberOfXY;
        std::cout << "Сколько значений x и y вы введете? (от 7 до 12): ";
        std::cin >> numberOfXY;
        if (numberOfXY > 12 || numberOfXY < 7) {
            std::cerr << "Вы ввели некорректное количество пар X и Y";
            return 1;
        }

        std::cout << "Введите значения x и y через пробел:\n";
        double x_val, y_val;
        for (size_t i = 0; i < numberOfXY; ++i) {
            std::cin >> x_val >> y_val;
            x.push_back(x_val);
            y.push_back(y_val);
        }
    } else if (choice == '2') {
        std::ifstream inputFile("t.txt");
        if (!inputFile.is_open()) {
            std::cerr << "Не удалось открыть файл t.txt\n";
            return 1;
        }

        double x_val, y_val;
        while (inputFile >> x_val >> y_val) {
            x.push_back(x_val);
            y.push_back(y_val);
        }
        inputFile.close();
    } else {
        std::cerr << "Неверный выбор. Пожалуйста, выберите '1' или '2'.\n";
        return 1;
    }

    size_t numberOfXY = x.size();
    if (numberOfXY > 12 || numberOfXY < 7) {
        std::cerr << "В файле недопустимое число пар X и Y";
    }

    LinearApproximation linearApproximation = LinearApproximation(x, y);
    linearApproximation.Approximation();

    QuadraticApproximation quadraticApproximation = QuadraticApproximation(x, y);
    quadraticApproximation.Approximation();

    PowerApproximation powerApproximation = PowerApproximation(x, y);
    powerApproximation.Approximation();

    ExponentialApproximation exponentialApproximation = ExponentialApproximation(x, y);
    exponentialApproximation.Approximation();

    LogarithmApproximation logarithmApproximation = LogarithmApproximation(x, y);
    logarithmApproximation.Approximation();

    ChooseApproximationFunction::BestApproximation(linearApproximation, quadraticApproximation,
                                                   exponentialApproximation, powerApproximation,
                                                   logarithmApproximation);

    return 0;
}
