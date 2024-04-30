#include <iostream>
#include <string>
#include <utility>
#include <vector>
#include <map>
#include <set>
#include <cmath>
#include <sstream>
#include "Function.h"


std::map<int, Function>  init_functions() {
    std::map<int, Function> num2func;
    num2func[1] = Function(
            "2x^3 - 2x^2 - x + 1",
            [](long double x) {return 2*x*x*x - 2*x*x - x + 1;},
            [](long double x){return std::cbrt((2*x*x + x - 1)/2.);}
    );

    num2func[2] = Function(
            "-0.38x^3 - 3.42x^2 + 2.51x + 8.75",
            [](long double x) {return -0.38*x*x*x - 3.42*x*x + 2.51*x + 8.75;},
            [](long double x){
                return std::sqrt((-8.75 - 2.51 * x) / (-0.38 * x - 3.42));
            }
    );

    num2func[3] = Function(
            "(2^(x) - 1)* (3^(x) - 2) * (x - 2) + 1",
            [](long double x) {
                return static_cast<long double>(
                        (pow(2, x) - 1) * (pow(3, x) - 2) * (x - 2) + 1
                );
            },
            [](long double x){
                return static_cast<long double>(
                        -1 / ((pow(2, x) - 1) * (pow(3, x) - 2)) + 2
                );
            }
    );
    return num2func;
}

Function select_func(const std::map<int, Function>& num2func) {
    std::cout << "Please, input number of function:\n";
    for (auto &x : num2func) {
        std::cout << x.first << ". " << x.second.get_str_func() << '\n';
    }

    int num;
    std::cin >> num;
    return num2func.at(num);
}

void set_x0_secant(UserInput &userInput, const Function& func) {
    std::cout << "Input x0 for left root of equation (secant):\n";
    std::cin >> userInput.x0_secant;

    if (!is_correct_x0_secant(func, userInput.x0_secant)) {
        std::cout << "Invalid value for x0 secant\n";
        exit(1);
    }
}

void set_segment(UserInput &userInput, const Function& func) {
    std::cout << "\nInput segment for middle root of equation (binary search):\nLeft:\n";
    std::cin >> userInput.segment.first;
    std::cout << "\nRight:\n";
    std::cin >> userInput.segment.second;

    if (!is_correct_segment(func, userInput.segment)) {
        std::cout << "Invalid value for segment\n";
        exit(1);
    }
}

void set_x0_iter(UserInput &userInput, const Function& func) {
    std::cout << "\nInput x0 for right root of equation (iterations):\n";
    std::cin >> userInput.x0_iter;

    if (!is_correct_x0_iter(func, userInput.x0_iter)) {
        std::cout << "Invalid value for x0 iter\n";
        exit(1);
    }
}

void set_user_data_in_func(Function &func) {
    UserInput userInput;

    set_x0_secant(userInput, func);
    set_segment(userInput, func);
    set_x0_iter(userInput, func);

    func.setUserInput(userInput);
}

void determining_root(const Function& func) {
    std::cout << "Left root:\n";
    std::cout << func.left_root() << std::endl;
    std::cout << "Middle root:\n";
    std::cout << func.middle_root() << std::endl;
    std::cout << "Right root:\n";
    std::cout << func.right_root() << std::endl;
}

class System {
private:
    const long double EPS = 1e-4;
    std::string str_system;
    long double (*x_func)(long double){};
    long double (*y_func)(long double){};
    long double x0 = 0;
    long double y0 = 0;
public:
    System() = default;
    System(std::string  str_system,
           long double (*const x_func)(long double),
           long double (*const y_func)(long double))
           : str_system(std::move(str_system))
           , x_func(x_func)
           , y_func(y_func){

    }
    System& operator=(const System& f) {
        if (this == &f) return *this;
        str_system = f.str_system;
        x_func = f.x_func;
        y_func = f.y_func;
        x0 = f.x0;
        y0 = f.y0;
        return *this;
    };
    void set_xy(long double x0, long double y0) {
        this->x0 = x0;
        this->y0 = y0;
    }
    [[nodiscard]] std::string get_str_system() const {
        return str_system;
    }
    std::pair <long double, long double> count_root() {
        long double x0p = 0, y0p = 0;
        long double x0c = x0, y0c = y0;
        do {
            x0p = x0c, y0p = y0c;
            x0c = x_func(x0p);
            y0c = y_func(y0p);
        } while ( std::abs(x0c - x0p) >  EPS && std::abs(y0c - y0p) >  EPS);
        return {x0c, y0c};
    }
};

std::map <int, System>  init_systems() {
    std::map <int, System> num2system;
    num2system[1] = System(
            "(\n_|x^2 + y^2 - 4 = 0\n |1/x = y\n (\n",
            [](long double y){
                return std::sqrt(4 - y);
            },
            [](long double x){
                return 1/x;
            }
    );
    num2system[2] = System(
            "(\n_|x^2 + y^2 - 4 = 0\n |3x^2 = y\n (\n",
            [](long double y){
                return std::sqrt(y/3);
            },
            [](long double x){
                return std::sqrt(4 - x);
            }
    );
    num2system[3] = System(
            "(\n_|1/x = y\n |3x^2 = y\n (\n",
            [](long double y){
                return std::sqrt(y/3);
            },
            [](long double x){
                return 1/x;
            }
    );
    return num2system;
}

System select_system(const std::map<int, System>& num2system) {
    std::cout << "Input number of system\n";
    for (auto &x : num2system) {
        std::cout << x.first << ".\n " << x.second.get_str_system();
    }
    int num;
    std::cin >> num;
    return num2system.at(num);
}

void set_user_data_in_system(System &system) {
    long double x0, y0;
    std::cout << "Input x0:\n";
    std::cin >> x0;
    std::cout << "Input y0:\n";
    std::cin >> y0;
    system.set_xy(x0, y0);
}

int main() {

    const std::map<int, Function> num2func = init_functions();
    Function func = select_func(num2func);
    set_user_data_in_func(func);
    determining_root(func);

    std::map<int, System> num2system = init_systems();
    System system = select_system(num2system);
    set_user_data_in_system(system);
    std::pair <long double, long double> solve = system.count_root();
    std::cout << solve.first << " " << solve.second;

    return 0;
}

