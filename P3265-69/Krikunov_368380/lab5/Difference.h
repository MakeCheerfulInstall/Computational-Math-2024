#ifndef COMPUTATIONAL_MATHEMATICS_DIFFERENCE_H
#define COMPUTATIONAL_MATHEMATICS_DIFFERENCE_H

void Difference(const std::vector<double> &y) {
    std::vector<std::vector<double>> dif(y.size(), std::vector<double>(y.size()));

    for (int i = 0; i < y.size(); i++) {
        dif[i][0] = y[i];
    }

    for (size_t i = 1; i < y.size(); i++) {
        for (size_t j = 0; j < y.size() - i; j++) {
            dif[j][i] = dif[j + 1][i - 1] - dif[j][i - 1];
        }
    }

    std::cout << std::endl << "Таблица конечных разностей:" << std::endl;
    for (int i = 0; i < y.size(); i ++) {
        if (i == 0) {std::cout << "y" << " ";}
        else {std::cout << "Δ^" << i + 1 << "y" << " ";}
    }
    std::cout << std::endl;

    for (size_t i = 0; i < y.size(); i++) {
        for (size_t j = 0; j < y.size() - i; j++) {
            std::cout << dif[i][j] << "\t";
        }
        std::cout << "\n";
    }
    std::cout << "\n";
}

#endif