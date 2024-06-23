import numpy as np


def sum_squared_deviations(func, dots):
    return sum([(func(dot[0]) - dot[1]) ** 2 for dot in dots])


# Среднеквадратичное отклонение
def standard_deviation(sum_square_deviation, n):
    return (sum_square_deviation / n) ** 0.5


def calc_coefficient_of_determination(fi, xs, ys, n):
    av_fi = sum(fi(x) for x in xs) / n
    return 1 - sum((y - fi(x)) ** 2 for x, y in zip(xs, ys)) / sum((y - av_fi) ** 2 for y in ys)

# Получение минора матрицы
def get_minor(matrix, index_i, index_j):
    n = len(matrix)
    result = []
    for i in range(n):
        if i != index_i:
            result.append([matrix[i][j] for j in range(n) if j != index_j])
    return result


def calc_deviation(data, points, func):
    n = len(points)
    temp = sum_squared_deviations(func, points)
    data["minimization_criterion"] = temp
    data["standard_deviation"] = standard_deviation(temp, n)


# Получение определителя матрицы
def get_determinant(matrix):
    n = len(matrix)
    if n != 1:
        determinant = 0
        sign = 1
        for column in range(n):
            determinant += sign * matrix[0][column] * get_determinant(get_minor(matrix, 0, column))
            sign *= -1
        return determinant
    else:
        return matrix[0][0]


# Линейная аппроксимация
def linear_approximation(points):
    result = {}

    sx = sum([point[0] for point in points])
    sxx = sum([point[0] ** 2 for point in points])
    sy = sum([point[1] for point in points])
    sxy = sum([point[0] * point[1] for point in points])

    n = len(points)
    det = sxx * n - sx * sx
    det1 = sxy * n - sx * sy
    det2 = sxx * sy - sx * sxy

    a = det1 / det
    b = det2 / det
    """Коэффициент корреляции Пирсона"""
    mean_x = sx / n
    mean_y = sy / n
    numerator = 0
    denominator1 = 0
    denominator2 = 0
    for point in points:
        numerator += (point[0] - mean_x) * (point[1] - mean_y)
        denominator1 += (point[0] - mean_x) ** 2
        denominator2 += (point[1] - mean_y) ** 2

    coefficient = numerator / ((denominator1 * denominator2) ** 0.5)
    result["pirson_coefficient"] = coefficient
    result["name"] = "линейная"
    func = lambda x: a * x + b
    result["func"] = func
    result["a"] = a
    result["b"] = b
    result["func_string"] = "f(x) = %.2fx + (%.2f)" % (a, b)
    calc_deviation(result, points, func)
    result["coeff"] = calc_coefficient_of_determination(func, [point[0] for point in points], [point[1] for point in points], n)
    return result


# Аппроксимация по квадратичной функции
def quadratic_approximation(points):
    result = {}
    sx = sum([point[0] for point in points])
    sxx = sum([point[0] ** 2 for point in points])
    sx3 = sum([point[0] ** 3 for point in points])
    sx4 = sum([point[0] ** 4 for point in points])
    sy = sum([point[1] for point in points])
    sxy = sum([point[0] * point[1] for point in points])
    sx2y = sum([point[0] ** 2 * point[1] for point in points])
    n = len(points)

    determinant = get_determinant([[n, sx, sxx],
                                   [sx, sxx, sx3],
                                   [sxx, sx3, sx4]])
    det_a0 = get_determinant([[sy, sx, sxx],
                              [sxy, sxx, sx3],
                              [sx2y, sx3, sx4]])
    det_a1 = get_determinant([[n, sy, sxx],
                              [sx, sxy, sx3],
                              [sxx, sx2y, sx4]])
    det_a2 = get_determinant([[n, sx, sy],
                              [sx, sxx, sxy],
                              [sxx, sx3, sx2y]])
    try:
        a0, a1, a2 = det_a0 / determinant, det_a1 / determinant, det_a2 / determinant
    except:
        return None

    func = lambda x: a0 + a1 * x + a2 * x ** 2
    result["func"] = func
    result["name"] = "квадратичная"
    result["func_string"] = f"f(x) = %.2f + (%.2f)x + (%.2f)x^2" % (a0, a1, a2)
    result["a0"], result["a1"], result["a2"] = a0, a1, a2
    calc_deviation(result, points, func)
    result["coeff"] = calc_coefficient_of_determination(func, [point[0] for point in points], [point[1] for point in points], n)

    return result


# Аппроксимация по кубической функции
def cubic_approximation(points):
    result = {}
    n = len(points)

    sx = sum([point[0] for point in points])
    sxx = sum([point[0] ** 2 for point in points])
    sx3 = sum([point[0] ** 3 for point in points])
    sx4 = sum([point[0] ** 4 for point in points])
    sx5 = sum([point[0] ** 5 for point in points])
    sx6 = sum([point[0] ** 6 for point in points])
    sy = sum([point[1] for point in points])
    sxy = sum([point[0] * point[1] for point in points])
    sx2y = sum([point[0] ** 2 * point[1] for point in points])
    sx3y = sum([point[0] ** 3 * point[1] for point in points])

    determinant = get_determinant([[n, sx, sxx, sx3],
                                   [sx, sxx, sx3, sx4],
                                   [sxx, sx3, sx4, sx5],
                                   [sx3, sx4, sx5, sx6]])
    det_a0 = get_determinant([[sy, sx, sxx, sx3],
                              [sxy, sxx, sx3, sx4],
                              [sx2y, sx3, sx4, sx5],
                              [sx3y, sx4, sx5, sx6]])
    det_a1 = get_determinant([[n, sy, sxx, sx3],
                              [sx, sxy, sx3, sx4],
                              [sxx, sx2y, sx4, sx5],
                              [sx3, sx3y, sx5, sx6]])
    det_a2 = get_determinant([[n, sx, sy, sx3],
                              [sx, sxx, sxy, sx4],
                              [sxx, sx3, sx2y, sx5],
                              [sx3, sx4, sx3y, sx6]])
    det_a3 = get_determinant([[n, sx, sxx, sy],
                              [sx, sxx, sx3, sxy],
                              [sxx, sx3, sx4, sx2y],
                              [sx3, sx4, sx5, sx3y]])
    try:
        a0, a1, a2, a3 = det_a0 / determinant, det_a1 / determinant, det_a2 / determinant, det_a3 / determinant
    except:
        return None
    func = lambda x: a0 + a1 * x + a2 * (x ** 2) + a3 * (x ** 3)
    result["name"] = "кубическая"
    result["func"] = func
    result["func_string"] = "f(x) = %.2f + (%.2f)x + (%.2f)x^2 + (%.2f)x^3" % (a0, a1, a2, a3)
    result["a0"], result["a1"], result["a2"], result["a3"] = a0, a1, a2, a3
    calc_deviation(result, points, func)
    result["coeff"] = calc_coefficient_of_determination(func, [point[0] for point in points], [point[1] for point in points], n)

    return result


# Cтепенная аппроксимация
def power_approximation(points):
    result = {}

    for point in points:
        if point[1] <= 0 or point[0] <= 0:
            return None
    linear_result = linear_approximation([(np.log(point[0]), np.log(point[1])) for point in points])

    b = linear_result["a"]
    a = np.exp(linear_result["b"])
    result["a"], result["b"] = a, b

    func = lambda x: a * (x ** b)
    result["func"] = func
    result["name"] = "степенная"
    result["func_string"] = "f(x) = %.2fx^(%.2f)" % (a, b)
    calc_deviation(result, points, func)
    result["coeff"] = calc_coefficient_of_determination(func, [point[0] for point in points], [point[1] for point in points], len(points))

    return result


# Экспоненциальная аппроксимация
def exponential_approximation(points):
    result = {}

    for point in points:
        if point[1] <= 0:
            return None
    linear_result = linear_approximation([(point[0], np.log(point[1])) for point in points])
    b = linear_result["a"]
    a = np.exp(linear_result["b"])
    result["a"], result["b"] = a, b
    func = lambda x: a * np.exp(b * x)
    result["func"] = func
    result["name"] = "экспоненциальная"
    result["func_string"] = f"f(x) = %.2fe^(%.2fx)" % (a, b)
    calc_deviation(result, points, func)
    result["coeff"] = calc_coefficient_of_determination(func, [point[0] for point in points], [point[1] for point in points], len(points))

    return result


# Логарифмическая аппрокцимация
def logarithmic_approximation(points):
    result = {}

    for point in points:
        if point[0] <= 0:
            return None

    linear_result = linear_approximation([(np.log(point[0]), point[1]) for point in points])

    b = linear_result["b"]
    a = linear_result["a"]
    result["a"], result["b"] = a, b

    func = lambda x: a * np.log(x) + b
    result["func"] = func
    result["name"] = "логарифмическая"
    result["func_string"] = f"f(x) = %.2f*ln(x) + (%.2f)" % (a, b)
    calc_deviation(result, points, func)
    result["coeff"] = calc_coefficient_of_determination(func, [point[0] for point in points], [point[1] for point in points], len(points))

    return result
