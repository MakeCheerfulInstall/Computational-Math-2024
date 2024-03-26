//
// Created by stass on 13.03.2024.
//

#ifndef LAB2_FUNCTION_H
#define LAB2_FUNCTION_H

#include <string>
#include <vector>

class UserInput {
public:
    std::pair<long double, long double> segment; //пользовательский отрезок для 1го корня
    long double x0_secant; //пользовательский x0 для метода секущей
    long double x0_iter; //пользовательский x0 для простых итераций
};


class Function {
private:
    const long double EPS = 1e-6;
    const long double STEP = 0.0001;
    const long double LEFT_CORN = -20;
    const long double RIGHT_CORN = 20;
    std::string _str_func; //функция в виде строки
    long double (*_func)(long double){}; //указатель на функцию-реализацию данной
    long double (*_x_func)(long double){}; //указатель на функцию-реализацию выражения от x
    std::vector <std::pair<long double, long double>> _segments; //отрезки, в которых находятся корни (вычисляет программа)
    long double _x1_secant;
    UserInput _user;
public:
    Function() = default;
    Function(std::string  _str_func,
             long double (*_func)(long double),
             long double (*_x_func)(long double));
    Function& operator=(const Function& f);

    [[nodiscard]] std::string get_str_func() const;
    std::vector <std::pair <long double, long double>> get_vector_segments_with_root();
    void setUserInput(UserInput user);
    [[nodiscard]] long double left_root() const;
    [[nodiscard]] long double right_root() const;
    [[nodiscard]] long double middle_root() const;

    friend bool is_correct_segment(const Function& f,
                                   std::pair <long double, long double> segment);
    friend bool is_correct_x0_iter(const Function &f,
                                   long double x0_iter);
    friend bool is_correct_x0_secant(const Function &f,
                                     long double x0_secant);

};

#endif //LAB2_FUNCTION_H
