#include <iostream>
#include "SolvingMethods.h"
#include "matplotlibcpp.h"
#include "StirlingPolynomial.h"

namespace plt = matplotlibcpp;

int main() {
    std::cout << "Какой полином вы хотите использовать?\n1) Полином Лагранжа\n"
                 "2) Полином Ньютона с разделенными разностями\n3) Полином Ньютона с конечными разностями\n"
                 "4) Полином Стирлинга\n5) Полином Бесселяn\n";

    size_t methodChoice;
    std::cin >> methodChoice;

    std::cout << "Как бы вы хотели получить данные?\n1)Ввести вручную\n"
                 "2)Считать с файла\n3)Получить из предоставленных\n";

    size_t inputChoice;
    std::cin >> inputChoice;

    if (inputChoice == 1) {
        std::cout << "Сколько значений у функции?\n";

        size_t n;
        std::cin >> n;

        std::vector<double> x_axis(n);
        std::vector<double> y_axis(n);

        std::cout << "Введите все значения x и y через пробел:\n";
        for (int i = 0; i < n; i++) {
            std::cin >> x_axis[i];
            std::cin >> y_axis[i];
        }

        std::cout << "Введите точку, в которой хотите найти значение функции: ";
        double x;
        std::cin >> x;

        double result;
        switch (methodChoice) {
            case 1:
                result = Interpolate(x_axis, y_axis, x, LagrangeInterpolation);
                break;
            case 2:
                result = Interpolate(x_axis, y_axis, x, NewtonInterpolationDD);
                break;
            case 3:
                result = NewtonInterpolationFD(x_axis, y_axis, x);
                break;
            case 4:
                result = StirlingInterpolation(x_axis, y_axis, x);
                plt::plot(x_axis, y_axis, "-o");
                plt::show();
                break;
            default:
                std::cerr << "Неверный выбор" << std::endl;
                return 1;
        }

        Difference(y_axis);
        std::cout << "y(x) = " << result;
    } else if (inputChoice == 2) {
        std::string filename = "t1.txt";

        std::ifstream file(filename);
        if (!file.is_open()) {
            std::cerr << "Ошибка открытия файла!" << std::endl;
            return 1;
        }

        size_t n;
        file >> n;

        std::vector<double> x_axis(n);
        std::vector<double> y_axis(n);

        for (int i = 0; i < n; i++) {
            file >> x_axis[i] >> y_axis[i];
        }

        std::cout << "Введите точку, в которой хотите найти значение функции: ";
        double x;
        std::cin >> x;

        double result;
        switch (methodChoice) {
            case 1:
                result = Interpolate(x_axis, y_axis, x, LagrangeInterpolation);
                break;
            case 2:
                result = Interpolate(x_axis, y_axis, x, NewtonInterpolationDD);
                break;
            case 3:
                result = NewtonInterpolationFD(x_axis, y_axis, x);
                break;
            case 4 :
                result = StirlingInterpolation(x_axis, y_axis, x);
                break;
            default:
                std::cerr << "Неверный выбор" << std::endl;
                return 1;
        }

        Difference(y_axis);
        std::cout << "y(x) = " << result;
    } else if (inputChoice == 3) {
        std::cout << "Выберите функцию для исследования:\n"
                     "1) sin(x)\n"
                     "2) cos(x)\n"
                     "3) x^2\n";

        size_t functionChoice;
        std::cin >> functionChoice;

        double start, end;
        size_t n;
        std::cout << "Введите начало и конец интервала: ";
        std::cin >> start >> end;
        std::cout << "Введите количество точек на интервале: ";
        std::cin >> n;

        std::vector<double> x_axis(n);
        std::vector<double> y_axis(n);
        double step = (end - start) / (n - 1);
        for (size_t i = 0; i < n; i++) {
            x_axis[i] = start + i * step;
            y_axis[i] = CustomFunction(x_axis[i], functionChoice);
        }

        std::cout << "Введите точку, в которой хотите найти значение функции: ";
        double x;
        std::cin >> x;

        double result;
        switch (methodChoice) {
            case 1:
                result = Interpolate(x_axis, y_axis, x, LagrangeInterpolation);
                break;
            case 2:
                result = Interpolate(x_axis, y_axis, x, NewtonInterpolationDD);
                break;
            case 3:
                result = NewtonInterpolationFD(x_axis, y_axis, x);
                break;
            case 4:
                result = StirlingInterpolation(x_axis, y_axis, x);
                break;
            default:
                std::cerr << "Неверный выбор" << std::endl;
                return 1;
        }

        Difference(y_axis);
        std::cout << "y(x) = " << result;
    }
}
