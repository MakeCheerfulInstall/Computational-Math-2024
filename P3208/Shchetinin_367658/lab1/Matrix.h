//
// Created by stass on 11.02.2024.
//

#ifndef LAB1_MATRIX_H
#define LAB1_MATRIX_H

#include <cstddef>
#include <vector>

using namespace std;

class Matrix
{
private:
    vector <vector <long double>> mat_;
    vector <vector <long double>> t_mat_;
    vector <long double> res_;
    vector <long double> res_immut_;
    vector <long double> x_;
    vector <long double> r_;
    size_t size_;
    int cnt_in_{};
    void setMat(const vector <vector<long double>>&);
    void setRes(const vector <long double>&);
    void setImmutRes(const vector <long double>&);
    void generateXandR();
    size_t getPosMaxFromColumn(size_t num_column);
    void swapRow(size_t first_row, size_t second_row);
    void setZeroColumn(int column);
    static inline long double EPS = 1e-8;
public:
    explicit Matrix(vector <vector<long double>>&, const vector <long double>&);
    [[nodiscard]] vector<vector<long double>> getMat() const;
    [[nodiscard]] vector<long double> getRes() const;
    [[nodiscard]] size_t getSize() const;
    void generateTrianMat();
    [[nodiscard]] vector <vector <long double>> getTrianMat() const;
    vector <long double> getX();
    vector <long double> getR();
    long double getDetTrianMatrix();
    void methodGauss();
};

std::ostream& operator << (std::ostream &os, const Matrix& matrix);
std::ostream& operator << (std::ostream &os, const vector <long double> &v);

#endif //LAB1_MATRIX_H
