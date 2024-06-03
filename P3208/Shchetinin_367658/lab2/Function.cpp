//
// Created by stass on 13.03.2024.
//

#include "Function.h"

#include <utility>
#include <iostream>

Function::Function(std::string  _str_func,
                   long double (*const _func)(long double),
                   long double (*const _x_func)(long double))
        : _str_func(std::move(_str_func))
        , _func(_func)
        , _x_func(_x_func){

    std::vector <std::pair <long double, long double>> v_root =
            get_vector_segments_with_root();
    long double left = LEFT_CORN;

    for (size_t i = 0; i < v_root.size(); ++i) {
        if (i != v_root.size() - 1) {
            _segments.emplace_back(left, v_root[i + 1].first);
        } else {
            _segments.emplace_back(left, RIGHT_CORN);
        }
        left = v_root[i].second;
    }

}

Function& Function::operator=(const Function& f){
    if (this == &f) return *this;
    _str_func = f._str_func;
    _func = f._func;
    _x_func = f._x_func;
    _segments = f._segments;
    _x1_secant = f._x1_secant;
    _user = f._user;
    return *this;
}

std::string Function::get_str_func() const{
    return _str_func;
}

std::vector <std::pair <long double, long double>> Function::get_vector_segments_with_root() {
    std::vector <std::pair <long double, long double>> v_root;
    bool is_plus_pred = _func(LEFT_CORN) > 0;
    long double pred = LEFT_CORN;
    for (long double cur = LEFT_CORN; cur <= RIGHT_CORN; cur += STEP) {
        long double val = _func(cur);
        bool is_plus = val > 0;
        if (std::abs(val) < EPS) {
            //v_root.emplace_back(pred, cur + STEP);
        } else if (is_plus_pred != is_plus) {
            v_root.emplace_back(pred, cur);
        }
        is_plus_pred = is_plus;
        pred = cur;
    }
    return v_root;
}

void Function::setUserInput(UserInput user) {
    this->_user = user;
    _x1_secant = user.x0_secant + 0.1;
}

long double Function::left_root() const{
    long double cur_x = _x1_secant;
    long double pred_x = _user.x0_secant;
    long double pred_pred_x;
    long double cur_val = _func(cur_x);
    int cnt = 0;
    while (std::abs(cur_val) > EPS) {
        ++cnt;
        pred_pred_x = pred_x;
        pred_x = cur_x;
        long double value_pred_x = _func(pred_x);
        cur_x =
                pred_x - (pred_x - pred_pred_x) /
                         (value_pred_x - _func(pred_pred_x)) * value_pred_x;
        cur_val = _func(cur_x);
        /*std::cout << "\\hline " << cnt << " & " << pred_pred_x << " & "
        << pred_x << " & " << cur_x << " & " << cur_val << " & " << std::abs(cur_x - pred_x) << std::endl;*/
    }
    return cur_x;
}

long double Function::right_root() const{
    long double cur_x = _user.x0_iter;
    long double pred_x = RIGHT_CORN + 1;
    int cnt = 0;
    while (std::abs(cur_x - pred_x) > EPS) {
        ++cnt;
        pred_x = cur_x;
        cur_x = _x_func(cur_x);
//        std::cout << "\\hline " << cnt << "&" << pred_x << "&" <<
//                  cur_x << "&" <<
//                  _x_func(cur_x) << "&" << std::abs(cur_x - pred_x) << "\\\\" << std::endl;
    }

    return cur_x;
}

long double Function::middle_root() const{

    long double l = _user.segment.first;
    long double r = _user.segment.second;
    bool is = false;
    if (_func(l) > _func(r)) {
        is = true;
    }
    int cnt = 0;
    while (r - l > EPS) {
        ++cnt;
        long double m = (r + l)/2;
        if (!is) {
            (_func(m) > 0) ? r = m : l = m;
        } else {
            (_func(m) > 0) ? l = m : r = m;
        }

    }

    return l;

}

bool is_correct_segment(const Function &f,
                        std::pair<long double, long double> segment) {
    return f._segments[1].first <= segment.first && f._segments[1].second >= segment.second;
}

bool is_correct_x0_iter(const Function &f,
                        long double x0_iter) {
    return f._segments[2].first <= x0_iter && f._segments[2].second >= x0_iter;
}

bool is_correct_x0_secant(const Function &f,
                          long double x0_secant) {
    return f._segments[0].first <= x0_secant && f._segments[0].second >= x0_secant;
}