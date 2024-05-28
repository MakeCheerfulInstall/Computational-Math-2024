#ifndef COMPUTATIONAL_MATHEMATICS_QUADRATICAPPROXIMATION_H
#define COMPUTATIONAL_MATHEMATICS_QUADRATICAPPROXIMATION_H

#include <iostream>
#include <vector>
#include <cmath>

class QuadraticApproximation {
private:
    double n = 0;
    double S = 0, SX = 0, SX2 = 0, SY = 0, SXY = 0, SX3 = 0, SX4 = 0, SX2Y = 0;
    double a0 = 0, a1 = 0, a2 = 0;
    double epsilon = 0;
    double variance = 0;
    std::vector<double> result;
    std::vector<double> x;
    std::vector<double> y;

    std::vector<double> GaussSolve(std::vector<std::vector<double>> &a, std::vector<double> &y, int n) {
        std::vector<double> answers(n);
        int k, index;
        for (k = 0; k < n; k++) {
            double maxVal = abs(a[k][k]);
            index = k;
            for (int i = k + 1; i < n; i++) {
                if (abs(a[i][k]) > maxVal) {
                    maxVal = abs(a[i][k]);
                    index = i;
                }
            }

            for (int j = 0; j < n; ++j)
                std::swap(a[k][j], a[index][j]);
            std::swap(y[k], y[index]);

            for (int i = k; i < n; ++i) {
                double temp = a[i][k];
                if (abs(temp) < 0.0001) continue;
                for (int j = k; j < n; j++)
                    a[i][j] = a[i][j] / temp;
                y[i] = y[i] / temp;
                if (i == k) continue;
                for (int j = 0; j < n; j++)
                    a[i][j] = a[i][j] - a[k][j];
                y[i] = y[i] - y[k];
            }
        }

        for (k = n - 1; k >= 0; k--) {
            answers[k] = y[k];
            for (int i = 0; i < k; i++)
                y[i] = y[i] - a[i][k] * answers[k];
        }
        return answers;
    }

    void CalculateR2(const std::vector<double> &y_true, const std::vector<double> &y_pred) {
        if (y_true.size() != y_pred.size()) {
            throw std::invalid_argument("y_true и y_pred должны быть одного размера");
        }

        double mean_y_true = 0.0;
        double R = 0;
        for (double y: y_true) {
            mean_y_true += y;
        }
        mean_y_true /= y_true.size();

        double sum_squared_residuals = 0.0;
        for (int i = 0; i < y_true.size(); ++i) {
            double residual = y_true[i] - y_pred[i];
            sum_squared_residuals += residual * residual;
        }

        double total_variance = 0.0;
        for (double y: y_true) {
            double squared_deviation = (y - mean_y_true) * (y - mean_y_true);
            total_variance += squared_deviation;
        }

        R = 1.0 - sum_squared_residuals / total_variance;

        if (R >= 0.95) {
            std::cout << "➤ R^2 = " << R << " - высокая точность аппроксимации" << std::endl;
        } else if (R >= 0.75 || R < 0.95) {
            std::cout << "➤ R^2 = " << R << " - удовлетворительная аппроксимация" << std::endl;
        } else if (R >= 0.5 || R < 0.75) {
            std::cout << "➤ R^2 = " << R << " - слабая аппроксимация" << std::endl;
        } else if (R < 0.5) {
            std::cout << "➤ R^2 = " << R << " - недостаточная точность аппроксимации" << std::endl;
        }

        std::cout << std::endl;
    }

    void SetVariance(double var) {
        variance = var;
    }

public:
    QuadraticApproximation(const std::vector<double> &x_arguments, const std::vector<double> &y_arguments) {
        if (x_arguments.size() != y_arguments.size()) {
            std::cerr << "Векторы должны быть одной длины";
            return;
        }

        x = x_arguments;
        y = y_arguments;

        n = x.size();
        result.resize(n);
    }

    void Approximation() {
        if (n == 0) {
            std::cerr << "Пустые вектора";
            return;
        }

        for (int i = 0; i < n; i++) {
            SX += x[i];
            SX2 += x[i] * x[i];
            SY += y[i];
            SXY += x[i] * y[i];
            SX3 += x[i] * x[i] * x[i];
            SX4 += x[i] * x[i] * x[i] * x[i];
            SX2Y += x[i] * x[i] * y[i];
        }

        std::vector<std::vector<double>> leftSide{{n,   SX,  SX2},
                                                  {SX,  SX2, SX3},
                                                  {SX2, SX3, SX4}};
        std::vector<double> rightSide{SY, SXY, SX2Y};

        std::vector<double> coefficients = GaussSolve(leftSide, rightSide, 3);
        a0 = coefficients[0];
        a1 = coefficients[1];
        a2 = coefficients[2];

        std::cout << "||Результат квадратичной аппроксимации:||" << std::endl;
        for (int i = 0; i < n; i++) {
            result[i] = a2 * x[i] * x[i] + a1 * x[i] + a0;
            std::cout << "P" << i + 1 << "(x) = " << a2 << " * x^2 + " << a1 << " * x + " << a0 << " -> " << result[i]
                      << std::endl;
            epsilon = (a2 * x[i] * x[i] + a1 * x[i] + a0) - y[i];
            std::cout << "ε" << i + 1 << " = " << epsilon << std::endl;
            S += epsilon * epsilon;
        }

        std::cout << "➤ S = " << S << std::endl;
        CalculateR2(y, result);
        variance = sqrt(S / n);
        SetVariance(variance);
    }

    double GetVariance() const {
        return variance;
    }

    std::vector<double> GetX() {
        return result;
    }

    std::vector<double> GetY() {
        return y;
    }
};

#endif
