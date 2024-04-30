import inspect
from math import sqrt, exp, log

import matplotlib.pyplot as plt


def add_col(m, col):
    for k, row in enumerate(m):
        row.append(col[k])
    return m


def remove_last_col(m):
    for k, row in enumerate(m):
        row.pop()
    return m


def plus(src, ind, m):
    for i in range(src + 1, len(m)):
        _plus(src, i, m, -m[i][ind] / m[src][ind])


def _plus(src, dest, m, mul: float = 1):
    for i in range(len(m[0])):
        m[dest][i] += m[src][i] * mul


def swap(src, dest, m):
    m[src], m[dest] = m[dest], m[src]


def rang(m):
    return sum(any(row) for row in m)


def determinant(m, k):
    p = 1
    for i in range(len(m)):
        p *= m[i][i]
    return (-1) ** k * p


def solve(m):
    k = 0
    row = 0
    col = 0
    n = len(m)
    while col < n:
        for j in range(row, n):
            if m[j][col]:
                swap(j, row, m)
                k += row != j
                plus(row, col, m)
                row += 1
                break
        col += 1
    xs = []
    for i in range(len(m)):
        x = m[len(m) - i - 1][-1]
        for j in range(1, i + 2):
            if j == i + 1:
                x /= m[len(m) - i - 1][-j - 1]
            else:
                x -= m[len(m) - i - 1][-j - 1] * xs[j - 1]
        xs.append(x)
    return xs[::-1]


def linear_approximation(xs, ys, n):
    sx = sum(xs)
    sxx = sum(x ** 2 for x in xs)
    sy = sum(ys)
    sxy = sum(x * y for x, y in zip(xs, ys))
    ext_matrix = add_col(
        [
            [n, sx],
            [sx, sxx]
        ],
        [sy, sxy]
    )

    a, b = solve(ext_matrix)
    return lambda x: a + b * x, a, b


def quadratic_approximation(xs, ys, n):
    sx = sum(xs)
    sxx = sum(x ** 2 for x in xs)
    sxxx = sum(x ** 3 for x in xs)
    sxxxx = sum(x ** 4 for x in xs)
    sy = sum(ys)
    sxy = sum(x * y for x, y in zip(xs, ys))
    sxxy = sum(x * x * y for x, y in zip(xs, ys))
    ext_matrix = add_col(
        [
            [n, sx, sxx],
            [sx, sxx, sxxx],
            [sxx, sxxx, sxxxx]
        ],
        [sy, sxy, sxxy]
    )

    a, b, c = solve(ext_matrix)
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
    ext_matrix = add_col(
        [
            [n, sx, sxx, sxxx],
            [sx, sxx, sxxx, sxxxx],
            [sxx, sxxx, sxxxx, sxxxxx],
            [sxxx, sxxxx, sxxxxx, sxxxxxx]
        ],
        [sy, sxy, sxxy, sxxxy]
    )

    a, b, c, d = solve(ext_matrix)
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


def calc_deviation(xs, ys, fi, n):
    return sum((eps ** 2 for eps in [fi(x) - y for x, y in zip(xs, ys)]))


def calc_standard_deviation(xs, ys, fi, n):
    return sqrt(sum(((fi(x) - y) ** 2 for x, y in zip(xs, ys))) / n)


def calc_pearson_correlation_coefficient(xs, ys, n):
    av_x = sum(xs) / n
    av_y = sum(ys) / n
    return sum((x - av_x) * (y - av_y) for x, y in zip(xs, ys)) / \
           sqrt(sum((x - av_x) ** 2 for x in xs) *
                sum((y - av_y) ** 2 for y in ys))


def calc_coefficient_of_determination(xs, ys, fi, n):
    return 1 - sum((y - fi(x)) ** 2 for x, y in zip(xs, ys)) / (sum(fi(x) ** 2 for x in xs) - sum(fi(x) for x in xs) ** 2 / n)


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


def read_number(s: str):
    while True:
        try:
            return float(input(s))
        except Exception:
            continue


if __name__ == '__main__':
    read_number("Введите количество точек: ")
    xs = list(map(float, input().split()))
    ys = list(map(float, input().split()))
    if len(xs) != len(ys):
        print("Некорректные данные")

    n = len(xs)

    names = {
        linear_approximation: "Линейная",
        power_approximation: "Степенная",
        exponential_approximation: "Экспоненциальная",
        logarithmic_approximation: "Логарифмическая",
        quadratic_approximation: "Квадратичная",
        cubic_approximation: "Кубическая"
    }

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
        print(names[apprxmt_f], ": ")
        fi, *coeffs = apprxmt_f(xs, ys, n)
        s = calc_deviation(xs, ys, fi, n)
        sigma = calc_standard_deviation(xs, ys, fi, n)
        if sigma < best_sigma:
            best_sigma = sigma
            best_apprxmt_f = apprxmt_f
        r2 = calc_coefficient_of_determination(xs, ys, fi, n)
        print('fi(x) =', get_str_content_of_func(fi))
        print(f'coeffs:', list(map(lambda cf: round(cf, 4), coeffs)))
        print(f'S = {s:.5f}, σ = {sigma:.5f}, R2 = {r2:.5f}')
        if apprxmt_f is linear_approximation:
            print('r =', calc_pearson_correlation_coefficient(xs, ys, n))
        plt.title(names[apprxmt_f])

        draw_plot(xs[0], xs[-1], fi)
        for i in range(n):
            plt.scatter(xs[i], ys[i], c='r')
        plt.xlabel("X")
        plt.ylabel("Y")
        plt.show()
        print('-' * 50)
    print(f'best_func: {names[best_apprxmt_f]}')

