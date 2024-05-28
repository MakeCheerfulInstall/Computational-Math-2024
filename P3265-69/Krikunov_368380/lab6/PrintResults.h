#ifndef COMPUTATIONAL_MATHEMATICS_PRINTRESULTS_H
#define COMPUTATIONAL_MATHEMATICS_PRINTRESULTS_H

void PrintResults(const std::vector<double> &results, double x0, double h) {
    for (size_t i = 0; i < results.size(); i++) {
        std::cout << std::fixed << std::setprecision(6) << "x = " << x0 + i * h << ", y = " << results[i] << std::endl;
    }
}

#endif