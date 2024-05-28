#ifndef COMPUTATIONAL_MATHEMATICS_POWERAPPROXIMATION_H
#define COMPUTATIONAL_MATHEMATICS_POWERAPPROXIMATION_H

class PowerApproximation {
private:
    size_t n = 0;
    double S = 0, SX = 0, SXX = 0, SY = 0, SXY = 0;
    double delta = 0, delta1 = 0, delta2 = 0;
    double a = 0, b = 0;
    double epsilon = 0;
    double variance = 0;
    std::vector<double> result;
    std::vector<double> x;
    std::vector<double> y;

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
    PowerApproximation(const std::vector<double> &x_arguments, const std::vector<double> &y_arguments) {
        if (x.size() != y.size()) {
            std::cerr << "Векторы должны иметь одинаковую длину";
        }

        x.resize(x_arguments.size());
        y.resize(y_arguments.size());

        for (int i = 0; i < x_arguments.size(); i++) {
            x[i] = log(x_arguments[i]);
            y[i] = log(y_arguments[i]);
        }

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
            SXX += x[i] * x[i];
            SY += y[i];
            SXY += x[i] * y[i];
        }

        delta = SXX * n - SX * SX;
        delta1 = SXY * n - SX * SY;
        delta2 = SXX * SY - SX * SXY;

        a = delta1 / delta;
        b = delta2 / delta;

        std::cout << "||Результат степенной аппрокисимации:||" << std::endl;
        for (int i = 0; i < n; i++) {
            result[i] = a * pow(x[i], b);
            std::cout << "𝑷" << i + 1 << "(𝒙) = a * x ^ b -> " << result[i] << std::endl;
            epsilon = (a * pow(x[i], b)) - y[i];
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