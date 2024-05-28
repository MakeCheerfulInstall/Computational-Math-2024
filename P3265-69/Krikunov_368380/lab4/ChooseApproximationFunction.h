#ifndef COMPUTATIONAL_MATHEMATICS_CHOOSEAPPROXIMATIONFUNCTION_H
#define COMPUTATIONAL_MATHEMATICS_CHOOSEAPPROXIMATIONFUNCTION_H

#include <utility>
#include "LinearApproximation.h"
#include "QuadraticApproximation.h"
#include "ExponentialApproximation.h"
#include "LogarithmApproximation.h"
#include "PowerApproximation.h"
#include "matplotlibcpp.h"

namespace plt = matplotlibcpp;

class ChooseApproximationFunction {
private:

public:
    static void BestApproximation(LinearApproximation &linearApproximation,
                                  QuadraticApproximation &quadraticApproximation,
                                  ExponentialApproximation &exponentialApproximation,
                                  PowerApproximation &powerApproximation,
                                  LogarithmApproximation &logarithmApproximation) {

        std::vector<std::pair<std::string, double>> variances(5);

        variances[0].first = "Линейной аппроксимации ";
        variances[1].first = "Квадратичной аппроксимации ";
        variances[2].first = "Экспоненциальной аппроксимации ";
        variances[3].first = "Степенной аппроксимации ";
        variances[4].first = "Логарифмической аппроксимации ";

        variances[0].second = linearApproximation.GetVariance();
        variances[1].second = quadraticApproximation.GetVariance();
        variances[2].second = exponentialApproximation.GetVariance();
        variances[3].second = powerApproximation.GetVariance();
        variances[4].second = logarithmApproximation.GetVariance();

        double min = INT32_MAX;
        size_t index = INT32_MAX;
        for (int i = 0; i < variances.size(); i++) {
            if (variances[i].second < min) {
                min = variances[i].second;
                index = i;
            }
        }

        std::cout << "Лучший показатель аппроксимации у " << variances[index].first << variances[index].second;
        ShowGraphics(linearApproximation, quadraticApproximation, exponentialApproximation, powerApproximation,
                     logarithmApproximation);
    }

private:
    static void ShowGraphics(LinearApproximation &linearApproximation,
                             QuadraticApproximation &quadraticApproximation,
                             ExponentialApproximation &exponentialApproximation,
                             PowerApproximation &powerApproximation,
                             LogarithmApproximation &logarithmApproximation) {

        plt::figure();
        plt::title("Графики аппроксимационных функций");
        plt::xlabel("X");
        plt::ylabel("Y");

        plt::Plot linear_plot("Линейная аппроксимация", linearApproximation.GetX(), linearApproximation.GetY(), "-");
        plt::Plot quadratic_plot("Квадратичная аппроксимация", quadraticApproximation.GetX(),quadraticApproximation.GetY(), "-");
        plt::Plot exponential_plot("exp", exponentialApproximation.GetX(), exponentialApproximation.GetY(), "-");
        plt::Plot power_plot("pow", powerApproximation.GetX(), powerApproximation.GetY(), "-");
        plt::Plot log_plot("log", logarithmApproximation.GetX(), logarithmApproximation.GetY(), "-");


        plt::legend();
        plt::show();
    }
};

#endif