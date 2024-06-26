#include <iostream>
#include <complex>
#include <algorithm>
#include <fstream>
#include "Matrix.h"

#define DRAW_GRAPH "python ..\\script\\plot.py"

struct Point {
    long double x;
    long double y;
};

std::vector <Point> input_points() {

    int n;
    std::cout << "Input count of points:\n";
    std::cin >> n;

    std::cout << "Input " << n << " x's:\n";

    std::vector <Point> res;

    for (int i = 0; i < n; ++i) {
        long double x;
        std::cin >> x;
        res.push_back(Point{x});
    }

    std::cout << "Input " << n << " y's:\n";

    for (int i = 0; i < n; ++i) {
        long double y;
        std::cin >> y;
        res[i].y = y;
    }

    return res;

}

std::vector <long double> get_pol(const std::vector <Point> &points, int m) {
    size_t n = points.size();
    std::vector <long double> sum_x(2 * m + 1);
    for (int i = 0; i <= 2*m; ++i) {
        long double res = 0;
        for (int j = 0; j < n; ++j) {
            res += std::pow(points[j].x, i);
        }
        sum_x[i] = res;
    }

    std::vector <std::vector <long double>> mx(m + 1, std::vector <long double>(m + 1));
    std::vector <long double> my(m + 1);

    for (int i = 0; i < m + 1; ++i) {
        long double res = 0;
        for (int j = 0; j < n; ++j) {
            res += points[j].y * std::pow(points[j].x, i);
        }
        my[i] = res;
    }

    for (int i = 0; i < m + 1; ++i) {
        for (int j = 0; j < m + 1; ++j) {

            mx[i][j] = sum_x[i + j];

        }
    }

    Matrix matrix(mx, my);
    return matrix.methodGauss();

}



std::vector <long double> get_deg_ap(const std::vector <Point>& points) {
    std::vector <Point> lnp;
    for (auto &p : points) {
        lnp.push_back({std::log(p.x), std::log(p.y)});
    }
    std::vector <long double> pol_1 = get_pol(lnp, 1);
    if (pol_1.empty()) return {};
    return {std::exp(pol_1[0]), pol_1[1]};
}

std::vector <long double> get_exp_ap(const std::vector <Point>& points) {
    std::vector <Point> lnp;
    for (auto &p : points) {
        lnp.push_back({p.x, std::log(p.y)});
    }
    std::vector <long double> pol_1 = get_pol(lnp, 1);
    if (pol_1.empty()) return {};
    return {std::exp(pol_1[0]), pol_1[1]};
}

std::vector <long double> get_ln_ap(const std::vector <Point>& points) {
    std::vector <Point> lnp;
    for (auto &p : points) {
        lnp.push_back({std::log(p.x), p.y});
    }
    std::vector <long double> pol_1 = get_pol(lnp, 1);
    if (pol_1.empty()) return {};
    return {std::exp(pol_1[0]), pol_1[1]};
}

void print_pol_1(std::vector <long double> pol_1) {
    if (pol_1.empty()) return;
    std::cout << "Approximation by a polynomial of the 1th degree:\n";
    std::reverse(pol_1.begin(), pol_1.end());
    std::cout << pol_1[0] << "x + (" << pol_1[1] << ")\n";
}

void print_pol_2(std::vector <long double> pol_2) {
    if (pol_2.empty()) return;
    std::cout << "Approximation by a polynomial of the 2th degree:\n";
    std::reverse(pol_2.begin(), pol_2.end());
    std::cout << pol_2[0] << "x^2 + (" << pol_2[1] << "x) + (" << pol_2[2] << ")\n";
}

void print_pol_3(std::vector <long double> pol_3) {
    if (pol_3.empty()) return;
    std::cout << "Approximation by a polynomial of the 3th degree:\n";
    std::reverse(pol_3.begin(), pol_3.end());
    std::cout << pol_3[0] << "x^3 + (" << pol_3[1] << "x^2) + ("
    << pol_3[2] << "x) + (" << pol_3[3] << ")\n";
}

void print_deg_ap (const std::vector <long double> &deg_ap) {
    if (deg_ap.empty()) return;
    std::cout << "Approximation by a power function:\n";
    std::cout << deg_ap[0] << "x^(" << deg_ap[1] << ")\n";
}

void print_exp_ap (const std::vector <long double> &exp_ap) {
    if (exp_ap.empty()) return;
    std::cout << "Approximation by a exp function:\n";
    std::cout << exp_ap[0] << "e^(" << exp_ap[1] << "x" << ")\n";
}

void print_ln_ap (const std::vector <long double> &ln_ap) {
    if (ln_ap.empty()) return;
    std::cout << "Approximation by a ln function:\n";
    std::cout << ln_ap[1] << "ln(x) + (" << ln_ap[0] << ")\n";
}

    void write_file(const std::vector <long double>& v, std::ofstream& out) {
        for (auto &x : v) {
            out << x << " ";
        }
        out << "\n";
    }

    void write_points(const std::vector <Point> &v, std::ofstream & out) {
        for (auto &p : v) {
            out << p.x << " ";
        }
        out << "\n";
        for (auto &p : v) {
            out << p.y << " ";
        }
        out << "\n";
    }

int main() {

    std::vector <Point> points = input_points();
    std::vector <long double> pol_1 = get_pol(points, 1);
    long double s = 0;
    for (auto [x, y]: points) {
        long double e2 = (pol_1[1] * x + pol_1[0] - y)*(pol_1[1] * x + pol_1[0] - y);
        std::cout << "l(x): " << pol_1[1] * x + pol_1[0] << " y: " << y << " e^2: " << e2
        << std::endl;
        s += e2;
    }
    std::cout << "sigma^2: " << s/points.size() << std::endl;

    std::vector <long double> pol_2 = get_pol(points, 2);
    std::vector <long double> pol_3 = get_pol(points, 3);
    std::vector <long double> deg_ap = get_deg_ap(points);
    std::vector <long double> exp_ap = get_exp_ap(points);
    std::vector <long double> ln_ap = get_ln_ap(points);

    print_pol_1(pol_1);
    print_pol_2(pol_2);
    print_pol_3(pol_3);
    print_deg_ap(deg_ap);
    print_exp_ap(exp_ap);
    print_ln_ap(ln_ap);

    std::ofstream out("../script/df");

    write_points(points, out);
    write_file(pol_1, out);
    write_file(pol_2, out);
    write_file(pol_3, out);
    write_file(deg_ap, out);
    write_file(exp_ap, out);
    write_file(ln_ap, out);
    out.close();

    system(DRAW_GRAPH);

    return 0;
}
