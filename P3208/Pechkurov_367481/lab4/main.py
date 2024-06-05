import math
import matplotlib.pyplot as plt
import csv


def read_data_from_file(filename):
    x = []
    y = []
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            x.append(float(row[0]))
            y.append(float(row[1]))
    return x, y

def write_results_to_file(filename, results):
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for key, value in results.items():
            writer.writerow([key, value])

def read_data():
    choice = input("Хотите ли вы ввести данные из файла? (y/n): ").strip().lower()
    if choice == 'y':
        filename = input("Введите имя файла: ")
        return read_data_from_file(filename)
    elif choice == 'n':
        n = int(input("Введите количество точек (от 8 до 12): "))
        assert 8 <= n <= 12, "Количество точек должно быть от 8 до 12"

        x = []
        y = []
        for i in range(n):
            xi, yi = map(float, input(f"Введите координаты {i + 1}-й точки (x y): ").split())
            x.append(xi)
            y.append(yi)

        return x, y
    else:
        print("Некорректный ввод. Повторите попытку.")
        return read_data()


def linear_least_squares(x, y):
    n = len(x)
    sum_x = sum(x)
    sum_y = sum(y)
    sum_xx = sum(xi ** 2 for xi in x)
    sum_xy = sum(xi * yi for xi, yi in zip(x, y))

    a = (n * sum_xy - sum_x * sum_y) / (n * sum_xx - sum_x ** 2)
    b = (sum_y - a * sum_x) / n

    return a, b


def polynomial_least_squares(x, y, degree):
    n = len(x)
    X = [[xi ** j for j in range(degree, -1, -1)] for xi in x]
    XT = [[row[i] for row in X] for i in range(degree + 1)]

    A = [[sum(XT[i][k] * XT[j][k] for k in range(n)) for j in range(degree + 1)] for i in range(degree + 1)]

    B = [sum(yi * XT[i][k] for k, yi in enumerate(y)) for i in range(degree + 1)]

    for i in range(degree + 1):
        div = A[i][i]
        A[i] = [a_i / div for a_i in A[i]]
        B[i] /= div
        for j in range(i + 1, degree + 1):
            mult = A[j][i]
            A[j] = [a_j - mult * a_i for a_i, a_j in zip(A[i], A[j])]
            B[j] -= mult * B[i]

    coeffs = [0] * (degree + 1)
    for i in range(degree, -1, -1):
        coeffs[i] = B[i]
        for j in range(i + 1, degree + 1):
            coeffs[i] -= A[i][j] * coeffs[j]

    return coeffs


def exponential_least_squares(x, y):
    if any(yi <= 0 for yi in y):
        raise ValueError("Отрицательные или нулевые значения y недопустимы для экспоненциальной аппроксимации")

    y_log = [math.log(yi) for yi in y]
    a, b = linear_least_squares(x, y_log)
    A = math.exp(b)
    B = a

    return A, B


def logarithmic_least_squares(x, y):
    if any(yi <= 0 for yi in y):
        raise ValueError("Отрицательные или нулевые значения y недопустимы для логарифмической аппроксимации")

    x_log = [math.log(xi) for xi in x]
    a, b = linear_least_squares(x_log, y)

    return a, b


def power_least_squares(x, y):
    if any(yi <= 0 for yi in y):
        raise ValueError("Отрицательные или нулевые значения y недопустимы для степенной аппроксимации")

    x_log = [math.log(xi) for xi in x]
    y_log = [math.log(yi) for yi in y]
    a, b = linear_least_squares(x_log, y_log)
    A = math.exp(b)
    B = a

    return A, B

def calculate_deviation(y_true, y_pred):
    n = len(y_true)
    sse = sum((yi - ypi) ** 2 for yi, ypi in zip(y_true, y_pred))
    mse = sse / n
    rmse = math.sqrt(mse)

    return rmse


def pearson_correlation(x, y):
    n = len(x)
    sum_x = sum(x)
    sum_y = sum(y)
    sum_xx = sum(xi ** 2 for xi in x)
    sum_yy = sum(yi ** 2 for yi in y)
    sum_xy = sum(xi * yi for xi, yi in zip(x, y))

    numerator = n * sum_xy - sum_x * sum_y
    denominator = math.sqrt((n * sum_xx - sum_x ** 2) * (n * sum_yy - sum_y ** 2))

    return numerator / denominator


def coefficient_of_determination(r):
    return r ** 2


def plot_results(x, y, coeffs):
    plt.scatter(x, y, color='blue', label='Исходные данные')

    x_range = [min(x) + i * (max(x) - min(x)) / 1000 for i in range(1001)]

    y_linear = [coeffs['linear'][0] * xi + coeffs['linear'][1] for xi in x_range]
    plt.plot(x_range, y_linear, label='Линейная', color='red')

    y_poly2 = [sum(c * (xi ** i) for i, c in enumerate(coeffs['poly2'])) for xi in x_range]
    plt.plot(x_range, y_poly2, label='Полиномиальная (2)', color='green')

    y_poly3 = [sum(c * (xi ** i) for i, c in enumerate(coeffs['poly3'])) for xi in x_range]
    plt.plot(x_range, y_poly3, label='Полиномиальная (3)', color='purple')

    if coeffs['exp'] is not None:
        y_exp = [coeffs['exp'][0] * math.exp(coeffs['exp'][1] * xi) for xi in x_range]
        plt.plot(x_range, y_exp, label='Экспоненциальная', color='orange')

    if coeffs['log'] is not None:
        y_log = [coeffs['log'][0] * math.log(xi) + coeffs['log'][1] for xi in x_range]
        plt.plot(x_range, y_log, label='Логарифмическая', color='brown')

    if coeffs['power'] is not None:
        y_power = [coeffs['power'][0] * (xi ** coeffs['power'][1]) for xi in x_range]
        plt.plot(x_range, y_power, label='Степенная', color='pink')

    plt.xlabel('x')
    plt.ylabel('y')
    plt.legend()
    plt.show()




def main():
    x, y = read_data()

    coeffs = {}
    coeffs['linear'] = linear_least_squares(x, y)
    coeffs['poly2'] = polynomial_least_squares(x, y, 2)
    coeffs['poly3'] = polynomial_least_squares(x, y, 3)

    try:
        coeffs['exp'] = exponential_least_squares(x, y)
    except ValueError as e:
        print(f"Ошибка: {e}")
        coeffs['exp'] = None

    try:
        coeffs['log'] = logarithmic_least_squares(x, y)
    except ValueError as e:
        print(f"Ошибка: {e}")
        coeffs['log'] = None

    try:
        coeffs['power'] = power_least_squares(x, y)
    except ValueError as e:
        print(f"Ошибка: {e}")
        coeffs['power'] = None

    y_linear_pred = [coeffs['linear'][0] * xi + coeffs['linear'][1] for xi in x]
    y_poly2_pred = [sum(c * (xi ** i) for i, c in enumerate(coeffs['poly2'])) for xi in x]
    y_poly3_pred = [sum(c * (xi ** i) for i, c in enumerate(coeffs['poly3'])) for xi in x]

    if coeffs['exp'] is not None:
        y_exp_pred = [coeffs['exp'][0] * math.exp(coeffs['exp'][1] * xi) for xi in x]
    else:
        y_exp_pred = None

    if coeffs['log'] is not None:
        y_log_pred = [coeffs['log'][0] * math.log(xi) + coeffs['log'][1] for xi in x]
    else:
        y_log_pred = None

    if coeffs['power'] is not None:
        y_power_pred = [coeffs['power'][0] * (xi ** coeffs['power'][1]) for xi in x]
    else:
        y_power_pred = None

    print(f"Коэффициенты линейной аппроксимации: a = {coeffs['linear'][0]}, b = {coeffs['linear'][1]}")
    print(f"Коэффициенты полиномиальной аппроксимации (2-й степени): {coeffs['poly2']}")
    print(f"Коэффициенты полиномиальной аппроксимации (3-й степени): {coeffs['poly3']}")
    if coeffs['exp'] is not None:
        print(f"Коэффициенты экспоненциальной аппроксимации: a = {coeffs['exp'][0]}, b = {coeffs['exp'][1]}")
    else:
        print("Экспоненциальная аппроксимация невозможна из-за отрицательных или нулевых значений y")
    if coeffs['log'] is not None:
        print(f"Коэффициенты логарифмической аппроксимации: a = {coeffs['log'][0]}, b = {coeffs['log'][1]}")
    else:
        print("Логарифмическая аппроксимация невозможна из-за отрицательных или нулевых значений y")
    if coeffs['power'] is not None:
        print(f"Коэффициенты степенной аппроксимации: a = {coeffs['power'][0]}, b = {coeffs['power'][1]}")
    else:
        print("Степенная аппроксимация невозможна из-за отрицательных или нулевых значений y")

    rmse_linear = calculate_deviation(y, y_linear_pred)
    rmse_poly2 = calculate_deviation(y, y_poly2_pred)
    rmse_poly3 = calculate_deviation(y, y_poly3_pred)

    if y_exp_pred is not None:
        rmse_exp = calculate_deviation(y, y_exp_pred)
    else:
        rmse_exp = None

    if y_log_pred is not None:
        rmse_log = calculate_deviation(y, y_log_pred)
    else:
        rmse_log = None

    if y_power_pred is not None:
        rmse_power = calculate_deviation(y, y_power_pred)
    else:
        rmse_power = None

    print(f"Среднеквадратичное отклонение (линейная): {rmse_linear}")
    print(f"Среднеквадратичное отклонение (полиномиальная 2-й степени): {rmse_poly2}")
    print(f"Среднеквадратичное отклонение (полиномиальная 3-й степени): {rmse_poly3}")
    if rmse_exp is not None:
        print(f"Среднеквадратичное отклонение (экспоненциальная): {rmse_exp}")
    else:
        print("Среднеквадратичное отклонение (экспоненциальная) не рассчитано")
    if rmse_log is not None:
        print(f"Среднеквадратичное отклонение (логарифмическая): {rmse_log}")
    else:
        print("Среднеквадратичное отклонение (логарифмическая) не рассчитано")
    if rmse_power is not None:
        print(f"Среднеквадратичное отклонение (степенная): {rmse_power}")
    else:
        print("Среднеквадратичное отклонение (степенная) не рассчитано")

    r_linear = pearson_correlation(x, y_linear_pred)
    r_poly2 = pearson_correlation(x, y_poly2_pred)
    r_poly3 = pearson_correlation(x, y_poly3_pred)

    if y_exp_pred is not None:
        r_exp = pearson_correlation(x, y_exp_pred)
    else:
        r_exp = None

    if y_log_pred is not None:
        r_log = pearson_correlation(x, y_log_pred)
    else:
        r_log = None

    if y_power_pred is not None:
        r_power = pearson_correlation(x, y_power_pred)
    else:
        r_power = None
    print(f"Коэффициент корреляции Пирсона (линейная): {r_linear}")


    r_squared_linear = coefficient_of_determination(r_linear)
    r_squared_poly2 = coefficient_of_determination(r_poly2)
    r_squared_poly3 = coefficient_of_determination(r_poly3)

    if r_exp is not None:
        r_squared_exp = coefficient_of_determination(r_exp)
    else:
        r_squared_exp = None

    if r_log is not None:
        r_squared_log = coefficient_of_determination(r_log)
    else:
        r_squared_log = None

    if r_power is not None:
        r_squared_power = coefficient_of_determination(r_power)
    else:
        r_squared_power = None

    print(f"Коэффициент детерминации (линейная): {r_squared_linear}")
    print(f"Коэффициент детерминации (полиномиальная 2-й степени): {r_squared_poly2}")
    print(f"Коэффициент детерминации (полиномиальная 3-й степени): {r_squared_poly3}")
    if r_squared_exp is not None:
        print(f"Коэффициент детерминации (экспоненциальная): {r_squared_exp}")
    else:
        print("Коэффициент детерминации (экспоненциальная) не рассчитан")
    if r_squared_log is not None:
        print(f"Коэффициент детерминации (логарифмическая): {r_squared_log}")
    else:
        print("Коэффициент детерминации (логарифмическая) не рассчитан")
    if r_squared_power is not None:
        print(f"Коэффициент детерминации (степенная): {r_squared_power}")
    else:
        print("Коэффициент детерминации (степенная) не рассчитан")

    r_squared_values = {
        'linear': r_squared_linear,
        'poly2': r_squared_poly2,
        'poly3': r_squared_poly3,
        'exp': r_squared_exp if r_squared_exp is not None else -1,
        'log': r_squared_log if r_squared_log is not None else -1,
        'power': r_squared_power if r_squared_power is not None else -1,
    }

    best_fit = max(r_squared_values, key=r_squared_values.get)

    print(f"Наилучшая аппроксимирующая функция: {best_fit}")


    plot_results(x, y, coeffs)

if __name__ == "__main__":
    main()

