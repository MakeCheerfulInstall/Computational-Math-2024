import inspect
from math import sqrt, exp, log

import matplotlib.pyplot as plt


def calc_det2(A):
    return A[0][0] * A[1][1] - A[0][1] * A[1][0]


def solve2(A, B):
    n = 2
    det = calc_det2(A)
    det1 = calc_det2([[B[r], A[r][1]] for r in range(n)])
    det2 = calc_det2([[A[r][0], B[r]] for r in range(n)])
    x1 = det1 / det
    x2 = det2 / det
    return x1, x2


def calc_det3(A):
    pos = A[0][0] * A[1][1] * A[2][2] + \
          A[0][1] * A[1][2] * A[2][0] + \
          A[0][2] * A[1][0] * A[2][1]
    neg = A[0][2] * A[1][1] * A[2][0] + \
          A[0][1] * A[1][0] * A[2][2] + \
          A[0][0] * A[1][2] * A[2][1]
    return pos - neg


def solve3(A, B):
    n = 3
    det = calc_det3(A)
    det1 = calc_det3([[B[r], A[r][1], A[r][2]] for r in range(n)])
    det2 = calc_det3([[A[r][0], B[r], A[r][2]] for r in range(n)])
    det3 = calc_det3([[A[r][0], A[r][1], B[r]] for r in range(n)])
    x1 = det1 / det
    x2 = det2 / det
    x3 = det3 / det
    return x1, x2, x3


def calc_det4(A):
    n = 4
    sign = 1
    r = 0
    res = 0
    for c in range(n):
        A_ = [[A[r_][c_] for c_ in range(n) if c_ != c]
              for r_ in range(n) if r_ != r]
        res += sign * A[r][c] * calc_det3(A_)
        sign *= -1
    return res


def solve4(A, B):
    n = 4
    det = calc_det4(A)
    det1 = calc_det4([[B[r], A[r][1], A[r][2], A[r][3]] for r in range(n)])
    det2 = calc_det4([[A[r][0], B[r], A[r][2], A[r][3]] for r in range(n)])
    det3 = calc_det4([[A[r][0], A[r][1], B[r], A[r][3]] for r in range(n)])
    det4 = calc_det4([[A[r][0], A[r][1], A[r][2], B[r]] for r in range(n)])
    x1 = det1 / det
    x2 = det2 / det
    x3 = det3 / det
    x4 = det4 / det
    return x1, x2, x3, x4


def linear_approximation(xs, ys, n):
    sx = sum(xs)
    sxx = sum(x ** 2 for x in xs)
    sy = sum(ys)
    sxy = sum(x * y for x, y in zip(xs, ys))
    a, b = solve2(
        [
            [n, sx],
            [sx, sxx]
        ],
        [sy, sxy])
    return lambda x: a + b * x, a, b


def quadratic_approximation(xs, ys, n):
    sx = sum(xs)
    sxx = sum(x ** 2 for x in xs)
    sxxx = sum(x ** 3 for x in xs)
    sxxxx = sum(x ** 4 for x in xs)
    sy = sum(ys)
    sxy = sum(x * y for x, y in zip(xs, ys))
    sxxy = sum(x * x * y for x, y in zip(xs, ys))
    a, b, c = solve3(
        [
            [n, sx, sxx],
            [sx, sxx, sxxx],
            [sxx, sxxx, sxxxx]
        ],
        [sy, sxy, sxxy]
    )
    return lambda x: a + b * x + c * x ** 2, a, b, c


def cubic_approximation(xs, ys, n):
    sx = sum(xs)
    sxx = sum(x ** 2 for x in xs)
    sxxx = sum(x ** 3 for x in xs)
    sxxxx = sum(x ** 4 for x in xs)
    sxxxxx = sum(x ** 5 for x in xs)
    sxxxxxx = sum(x ** 6 for x in xs)
    sy = sum(ys)
    sxy = sum(x * y for x, y in zip(xs, ys))
    sxxy = sum(x * x * y for x, y in zip(xs, ys))
    sxxxy = sum(x * x * x * y for x, y in zip(xs, ys))
    a, b, c, d = solve4(
        [
            [n, sx, sxx, sxxx],
            [sx, sxx, sxxx, sxxxx],
            [sxx, sxxx, sxxxx, sxxxxx],
            [sxxx, sxxxx, sxxxxx, sxxxxxx]
        ],
        [sy, sxy, sxxy, sxxxy]
    )
    return lambda x: a + b * x + c * x ** 2 + d * x ** 3, \
        a, b, c, d


def exponential_approximation(xs, ys, n):
    ys_ = list(map(log, ys))
    _, a_, b_ = linear_approximation(xs, ys_, n)
    a = exp(a_)
    b = b_
    return lambda x: a * exp(b * x), a, b


def logarithmic_approximation(xs, ys, n):
    xs_ = list(map(log, xs))
    _, a_, b_ = linear_approximation(xs_, ys, n)
    a = a_
    b = b_
    return lambda x: a + b * log(x), a, b


def power_approximation(xs, ys, n):
    xs_ = list(map(log, xs))
    ys_ = list(map(log, ys))
    _, a_, b_ = linear_approximation(xs_, ys_, n)
    a = exp(a_)
    b = b_
    return lambda x: a * x ** b, a, b


def calc_measure_of_deviation(xs, ys, fi, n):
    epss = [fi(x) - y for x, y in zip(xs, ys)]
    return sum((eps ** 2 for eps in epss))


def calc_standard_deviation(xs, ys, fi, n):
    return sqrt(sum(((fi(x) - y) ** 2 for x, y in zip(xs, ys))) / n)


def calc_pearson_correlation_coefficient(xs, ys, n):
    av_x = sum(xs) / n
    av_y = sum(ys) / n
    return sum((x - av_x) * (y - av_y) for x, y in zip(xs, ys)) / \
        sqrt(sum((x - av_x) ** 2 for x in xs) *
             sum((y - av_y) ** 2 for y in ys))


def calc_coefficient_of_determination(xs, ys, fi, n):
    av_fi = sum(fi(x) for x in xs) / n
    return 1 - sum((y - fi(x)) ** 2 for x, y in zip(xs, ys)) / sum((y - av_fi) ** 2 for y in ys)


def get_str_content_of_func(func):
    str_func = inspect.getsourcelines(func)[0][0]
    return str_func.split('lambda x: ')[-1].split(',')[0].strip()


def draw_plot(a, b, func, dx=0.1):
    xs, ys = [], []
    a -= dx
    b += dx
    x = a
    while x <= b:
        xs.append(x)
        ys.append(func(x))
        x += dx
    plt.plot(xs, ys, 'g')


def main(xs, ys, n):
    if all(map(lambda x: x > 0, xs)) and all(map(lambda x: x > 0, ys)):
        approximation_funcs = [
            linear_approximation,
            power_approximation,
            exponential_approximation,
            logarithmic_approximation,
            quadratic_approximation,
            cubic_approximation
        ]
    else:
        approximation_funcs = [
            linear_approximation,
            quadratic_approximation,
            cubic_approximation
        ]

    best_sigma = float('inf')
    best_apprxmt_f = None

    for apprxmt_f in approximation_funcs:
        print(apprxmt_f.__name__, ": ")
        fi, *coeffs = apprxmt_f(xs, ys, n)
        s = calc_measure_of_deviation(xs, ys, fi, n)
        sigma = calc_standard_deviation(xs, ys, fi, n)
        if sigma < best_sigma:
            best_sigma = sigma
            best_apprxmt_f = apprxmt_f
        r2 = calc_coefficient_of_determination(xs, ys, fi, n)
        print('fi(x) =', get_str_content_of_func(fi))
        tmp = '(a, b, c)' if len(coeffs) == 3 else '(a, b)'
        print(f'coeffs {tmp}:', list(map(lambda cf: round(cf, 4), coeffs)))
        print(f'S = {s:.5f}, σ = {sigma:.5f}, R2 = {r2:.5f}')
        if apprxmt_f is linear_approximation:
            print('r =', calc_pearson_correlation_coefficient(xs, ys, n))
        plt.title(apprxmt_f.__name__)

        draw_plot(xs[0], xs[-1], fi)
        for i in range(n):
            plt.scatter(xs[i], ys[i], c='r')
        plt.xlabel("X")
        plt.ylabel("Y")
        plt.show()
        print('-' * 50)
    print(f'best_func: {best_apprxmt_f.__name__}')


if __name__ == '__main__':
    case = 3
    if case == 0:
        n = int(input('input n: '))
        xs = list(map(float, input('input xs: ').split()))
        ys = list(map(float, input('input ys: ').split()))
    elif case == 1:
        xs = [1.2, 2.9, 4.1, 5.5, 6.7, 7.8, 9.2, 10.3]
        ys = [7.4, 9.5, 11.1, 12.9, 14.6, 17.3, 18.2, 20.7]
    elif case == 2:
        xs = [1.1, 2.3, 3.7, 4.5, 5.4, 6.8, 7.5]
        ys = [3.5, 4.1, 5.2, 6.9, 8.3, 14.8, 21.2]
    elif case == 3:
        xs = [1.1,  2.3,  3.7,  4.5,  5.4,   6.8,   7.5]
        ys = [2.73, 5.12, 7.74, 8.91, 10.59, 12.75, 13.43]
    else:
        h = 0.2
        x0 = -2
        n = 11
        xs = [round(x0 + i * h, 2) for i in range(n)]
        f = lambda x: 4 * x / (x ** 4 + 3)
        ys = [round(f(x), 2) for x in xs]
    n = len(xs)
    main(xs, ys, n)
