#include "SimpsonMethod.h"
#include "TrapeziumMethod.h"
#include "RectangleMethod.h"

int main() {
    std::cout << "Введите номер функции, которую хотите проинтегрировать:" << "\n" <<
              "1) F1(x) = x^3 - 3x^2 + 7x - 10" << "\n" <<
              "2) F2(x) = 2x^3 - 3x^2 + 5x - 9" << "\n" <<
              "3) F3(x) = 2x^3 - 9x^2 - 7x + 11" << "\n";
    unsigned short functionChoice;
    std::cin >> functionChoice;

    std::cout << "Выберите способ решения:" << "\n" << "1) Метод прямоугольников" << "\n" << "2) Метод трапеций" << "\n"
              << "3) Метод Симпсона" << "\n";
    unsigned short methodChoice;
    std::cin >> methodChoice;

    std::cout << "Введите пределы интегрированя:" << "\n";
    double a, b;
    std::cin >> a >> b;

    std::cout << "Введите точность интегрированя:" << "\n";
    double EPS;
    std::cin >> EPS;

    if (methodChoice == 1) {
        RectangleMethod::RectangleIntegral(a, b, 4, EPS, functionChoice);
    } else if (methodChoice == 2) {
        TrapeziumMethod::CalculateIntegral(a, b, 4, EPS, functionChoice);
    } else if (methodChoice == 3) {
        SimpsonMethod::CalculateIntegral(a, b, 4, EPS, functionChoice);
    }
}