#include <iostream>
#include <vector>
#include <cmath>
#include <functional>

using flink_type = long double (*) (long double);
using clink_type = long double (*)(long double, long double, int, flink_type);
const long double delta = 1e-1;

std::pair <std::string, flink_type> pair_f1() {
    return {"1. 3x^3 - 4x^2 + 7x - 17\n", [](long double x) { return 3*x*x*x - 4*x*x + 7*x - 17;}};
}

std::pair <std::string, flink_type> pair_f2() {
    return {"2. 2^x + 3^(x - 2)\n",
            [](long double x) { return std::pow(2, x) + std::pow(3, x - 2);}};
}

std::pair <std::string, flink_type> pair_f3() {
    return {"3. sin(x) + 2ln(x)\n",
            [](long double x) { return std::sin(x) + 2 * std::log(x);}};
}

auto select_func() {
    std::cout << "Input number of function:\n";
    std::vector <std::pair <std::string, flink_type >> v = {
            pair_f1(), pair_f2(), pair_f3()
    };
    for (auto &p : v) {
        std::cout << p.first;
    }
    size_t n;
    std::cin >> n;
    return v.at(n - 1).second;
}

long double count_func(long double x, long double d, long double a, long double b, flink_type func) {
    long double res = func(x);
    if (std::isinf(res)) {
        if (x - d < a)
            return func(x + d);
        if (x + d > b)
            return func(x - d);
        return (func(x - d) + func(x + d)) / 2;
    }
    return res;
}

long double count_integral_simpson (long double l,
                            long double r,
                            int n,
                            flink_type func) {
    long double cur_x = l;
    long double h = (r - l) / n;
    auto count_func_fb = std::bind(count_func, std::placeholders::_1, delta, l, r, func);
    long double res = count_func_fb(l);
    for (int i = 1; i < n; ++i) {
        cur_x += h;
        res += 4 * (i % 2) * count_func_fb(cur_x) + 2 * (1 - (i % 2)) * count_func_fb(cur_x);
    }
    cur_x += h;
    res += count_func_fb(cur_x);
    res *= h/3;
    return res;
}

long double count_integral_trapeze(long double l,
                                   long double r,
                                   int n,
                                   flink_type func) {
    auto count_func_fb = std::bind(count_func, std::placeholders::_1, delta, l, r, func);
    long double h = (r - l) / n;
    long double res = 0;
    long double xp = l, xc = l + h;

    while (xp < r) {
        long double res_xp = count_func_fb(xp), res_xc = count_func_fb(xc);
        res += (res_xp + res_xc) / 2 * h;
        xp += h;
        xc += h;
    }

    return res;
}

long double count_integral_left(long double l,
                                long double r,
                                int n,
                                flink_type func) {
    long double h = (r - l) / n;    
    long double res = 0;
    long double xp = l, xc = l + h;
    auto count_func_fb = std::bind(count_func, std::placeholders::_1, delta, l, r, func);
    while (xp < r) {
        res += count_func_fb(xp) * h;
        xp += h;
        xc += h;
    }

    return res;
}

long double count_integral_middle(long double l,
                                  long double r,
                                  int n,
                                  flink_type func) {
    long double h = (r - l) / n;
    return count_integral_left(l + h/2, r + h/2, n, func);
}

long double count_integral_right(long double l,
                                  long double r,
                                  int n,
                                  flink_type func) {
    long double h = (r - l) / n;
    return count_integral_left(l + h, r + h, n, func);
}

std::pair <std::string, clink_type> pair_count1() {
    return {"1. Метод прямоугольников (левый)\n", count_integral_left};
}

std::pair <std::string, clink_type> pair_count2() {
    return {"2. Метод прямоугольников (средний)\n", count_integral_middle};
}

std::pair <std::string, clink_type> pair_count3() {
    return {"3. Метод прямоугольников (правый)\n", count_integral_right};
}

std::pair <std::string, clink_type> pair_count4() {
    return {"4. Метод трапеций\n", count_integral_trapeze};
}

std::pair <std::string, clink_type> pair_count5() {
    return {"5. Метод Симпсона\n", count_integral_simpson};
}

auto select_count() {
    std::cout << "Input number of the method of counting:\n";
    std::vector <std::pair <std::string, clink_type >> v = {
            pair_count1(), pair_count2(), pair_count3(), pair_count4(), pair_count5()
    };
    for (auto &p : v) {
        std::cout << p.first;
    }
    size_t n;
    std::cin >> n;
    return v.at(n - 1).second;
}

int main() {
    auto func = select_func();
    auto count = select_count();

    long double l, r, e;
    std::cout << "Input left: \n";
    std::cin >> l;
    std::cout << "Input right: \n";
    std::cin >> r;
    std::cout << "Input epsilon: \n";
    std::cin >> e;
    auto count_fb = std::bind(count, l, r, std::placeholders::_1, func);


   long double cur_e = e + 1, cur_n = 4;
    long double res;

    while (cur_e > e) {
        long double ih1 = count_fb(cur_n/2);
        long double ih2 = count_fb(cur_n);
        res = ih2;
        cur_e = std::abs(ih1 - ih2);
        cur_n *= 2;
    }

    std::cout << "Result: " << res << std::endl;

    return 0;
}
