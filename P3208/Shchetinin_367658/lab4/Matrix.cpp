//
// Created by stass on 11.02.2024.
//

#include <ostream>
#include <iomanip>
#include <iostream>
#include <algorithm>
#include <map>
#include "Matrix.h"

using namespace std;

Matrix::Matrix(vector <vector<long double>> &new_mat, const vector <long double> &res) {
    this->setMat(new_mat);
    this->setRes(res);
    this->setImmutRes(res);
    this->size_ = new_mat.size();
}

void Matrix::setMat(const vector<vector<long double>> &new_mat) {
    this->mat_ = new_mat;
    this->t_mat_ = new_mat;
}

void Matrix::setRes(const vector<long double> &res) {
    this->res_ = res;
}

void Matrix::setImmutRes(const vector<long double> &res) {
    this->res_immut_ = res;
}

vector<vector<long double>> Matrix::getMat() const {
    return this->mat_;
}

vector<long double> Matrix::getRes() const {
    return this->res_;
}

size_t Matrix::getSize() const {
    return this->size_;
}


size_t Matrix::getPosMaxFromColumn(size_t num_column) {
    size_t pos_max = num_column;
    for (size_t i = num_column; i < this->size_; ++i){
        if (abs(this->t_mat_[pos_max][num_column]) < abs(this->t_mat_[i][num_column])) {
            pos_max = i;
        }
    }
    return pos_max;
}

void Matrix::swapRow(size_t first_row, size_t second_row) {
    swap(t_mat_[first_row], t_mat_[second_row]);
    swap(res_[first_row], res_[second_row]);
}

void Matrix::setZeroColumn(int column) {
    if (this->t_mat_[column][column] == 0) return;
    for (int i = column + 1; i < this->size_; ++i){
        if (this->t_mat_[i][i] == 0) continue;
        long double cf =  - this->t_mat_[i][column] / this->t_mat_[column][column];
        for (int j = column; j < this->size_; ++j){
            this->t_mat_[i][j] += this->t_mat_[column][j] * cf;
        }
        this->res_[i] += this->res_[column] * cf;
    }
}

//only for triangle form
long double Matrix::getDetTrianMatrix() {
    long double res = (-1) * (cnt_in_ % 2 != 0) + (cnt_in_ % 2 == 0);
    for (int i = 0; i < this->size_; ++i){
        res *= this->t_mat_[i][i];
    }
    return res;
}

void Matrix::generateTrianMat() {
    for (int j = 0; j < this->size_ - 1; ++j){
        size_t pos_max = this->getPosMaxFromColumn(j);
        this->swapRow(pos_max, j);
        this->cnt_in_ += pos_max != j;
        this->setZeroColumn(j);
    }
}

void Matrix::generateXandR() {
    for (int i = (int)this->size_ - 1; i >= 0; --i){
        long double left_sum = 0;
        for (size_t j = i + 1; j < this->size_; ++j){
            left_sum += this->t_mat_[i][j] * this->x_[this->x_.size() - (j - i - 1) - 1];
        }
        long double right_part = this->res_[i] - left_sum;
        this->x_.push_back(right_part/this->t_mat_[i][i]);
        this->r_.push_back(this->x_.back() * this->t_mat_[i][i] + left_sum - this->res_[i]);
    }
    reverse(this->x_.begin(), this->x_.end());
}

vector<long double> Matrix::getX() {
    if (this->x_.empty()) generateXandR();
    return this->x_;
}

vector<long double> Matrix::getR() {
    if (this->r_.empty()) generateXandR();
    return this->r_;
}

vector <vector <long double>> Matrix::getTrianMat() const {
    return this->t_mat_;
}

vector <long double> Matrix::methodGauss() {

    this->generateTrianMat();
    long double det = this->getDetTrianMatrix();

    if (abs(det) < EPS){
        map <vector <long double>, long double> mp;
        for (size_t i = 0; i < size_; ++i) {
            vector <long double> v = this->mat_[i];
            long double cf = *v.begin();
            for (auto &x : v){
                x /= cf;
            }
            long double res = res_immut_[i] / cf;
            if (mp.find(v) == mp.end()) {
                mp[v] = res;
            } else if (res != mp[v]) {
                return {};
            }
        }
        return {};
    }
    return this->getX();
}


std::ostream& operator << (std::ostream &os, const Matrix& matrix)
{
    int num_res = 0;
    os << fixed; os.precision(3);
    for (const vector<long double> &i : matrix.getTrianMat()){
        for (long double j : i) {
            os << " | " << setw(10) << j << " ";
        }
        os << " | ";
        os << " = " << setw(4) << matrix.getRes()[num_res];
        os << endl;
        ++num_res;
    }
    return os;
}

std::ostream& operator << (std::ostream &os, const vector <long double> &v)
{
    os << "[" << endl;
    for (size_t i = 0; i < v.size(); ++i){
        os << "   v" << i + 1 << ": " << v[i] << "," << endl;
    }
    os << "]" << endl;
    return os;
}
