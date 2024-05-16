#ifndef MATPLOTLIB_H_SYSTEMNEWTONMETHOD_H
#define MATPLOTLIB_H_SYSTEMNEWTONMETHOD_H

#include <vector>
#include "Functions.h"

class SystemNewtonMethod {
public:
    static std::vector<double> SolveEquation(double x0, double y0, double eps) {
        double x = x0;
        double y = y0;
        double dx, dy;

        short iterations = 0;
        std::vector<double> errors;

        dx = (-F4(x, y) * DF5_DY() + F5(x, y) * DF4_DY(y)) / (DF4_DX(x) * DF5_DY() - DF5_DX(x) * DF4_DY(y));
        dy = (-F5(x, y) * DF4_DX(x) + F4(x, y) * DF5_DX(x)) / (DF4_DX(x) * DF5_DY() - DF5_DX(x) * DF4_DY(y));
        x += dx;
        y += dy;
        iterations++;

        while (abs(dx) > eps && abs(dy) > eps) {
            dx = (-F4(x, y) * DF5_DY() + F5(x, y) * DF4_DY(y)) / (DF4_DX(x) * DF5_DY() - DF5_DX(x) * DF4_DY(y));
            dy = (-F5(x, y) * DF4_DX(x) + F4(x, y) * DF5_DX(x)) / (DF4_DX(x) * DF5_DY() - DF5_DX(x) * DF4_DY(y));
            x += dx;
            y += dy;
            iterations++;
            errors.push_back(sqrt(dx * dx + dy * dy));
        }

        std::cout << "Количество итераций: " << iterations << std::endl;
        std::cout << "Вектор погрешностей: ";
        for (double &error: errors) {
            std::cout << error << " ";
        }
        std::cout << std::endl;

        std::vector<double> result = {x, y};

        std::vector<double> x_plot(100), y1_plot(100), y2_plot(100);
        double minX = -5.0, maxX = 5.0;
        double minY = -5.0, maxY = 5.0;
        double step = (maxX - minX) / 100;

        for (int i = 0; i < 100; i++) {
            x_plot[i] = minX + i * step;
            y1_plot[i] = std::sqrt(4 - std::pow(x_plot[i], 2));
        }

        for (int i = 0; i < 100; i++) {
            y2_plot[i] = 3 * std::pow(x_plot[i], 2);
        }

        plt::plot(x_plot, y1_plot, "b-");
        plt::plot(x_plot, y2_plot, "r-");

        plt::xlim(minX, maxX);
        plt::ylim(minY, maxY);

        plt::grid(true);

        plt::show();

        return result;
    }
};

#endif //MATPLOTLIB_H_SYSTEMNEWTONMETHOD_H