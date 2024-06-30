//
// Created by stass on 11.02.2024.
//

#ifndef LAB1_MATRIX_H
#define LAB1_MATRIX_H

#include <cstddef>
#include <vector>

class Matrix
{
private:
    std::vector <std::vector <long double>> mat_;
    std::vector <std::vector <long double>> t_mat_;
    std::vector <long double> res_;
    std::vector <long double> res_immut_;
    std::vector <long double> x_;
    std::vector <long double> r_;
    size_t size_;
    int cnt_in_{};
    void setMat(const std::vector <std::vector<long double>>&);
    void setRes(const std::vector <long double>&);
    void setImmutRes(const std::vector <long double>&);
    void generateXandR();
    size_t getPosMaxFromColumn(size_t num_column);
    void swapRow(size_t first_row, size_t second_row);
    void setZeroColumn(int column);
    static inline long double EPS = 1e-8;
public:
    explicit Matrix(std::vector <std::vector<long double>>&, const std::vector <long double>&);
    [[nodiscard]] std::vector<std::vector<long double>> getMat() const;
    [[nodiscard]] std::vector<long double> getRes() const;
    [[nodiscard]] size_t getSize() const;
    void generateTrianMat();
    [[nodiscard]] std::vector <std::vector <long double>> getTrianMat() const;
    std::vector <long double> getX();
    std::vector <long double> getR();
    long double getDetTrianMatrix();
    std::vector<long double> methodGauss();
};

std::ostream& operator << (std::ostream &os, const Matrix& matrix);
std::ostream& operator << (std::ostream &os, const std::vector <long double> &v);

#endif //LAB1_MATRIX_H
