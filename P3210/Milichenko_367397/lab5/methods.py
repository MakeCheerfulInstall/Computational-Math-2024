import matplotlib.pyplot as plt
from functools import reduce
from math import factorial

def newton_method(x_arr, y_arr, x):
    diff = [y_arr]
    for i in range(len(x_arr)):
        tmp_dif = []
        for j in range(len(x_arr) - i - 1):
            tmp_dif.append((diff[-1][j + 1] - diff[-1][j])/(x_arr[j + i + 1] - x_arr[j]))
        diff.append(tmp_dif)
    mul = 1
    answer = y_arr[0]
    for i in range(len(x_arr)-1):
        mul *= (x - x_arr[i])
        answer += diff[i + 1][0] * mul
    return answer

#конечные разности
def final_differences(y_values):
    final_diff = [y_values]
    for i in range(len(y_values)-1):
        temp_fin_dif = []
        for j in range(len(final_diff[i]) - 1):
            diff = final_diff[i][j + 1] - final_diff[i][j]
            temp_fin_dif.append(diff)
        final_diff.append(temp_fin_dif)
    return final_diff


def lagrange_method(x_arr, y_arr, x):
    l = 0
    for j in range(len(y_arr)):
        numerator = 1
        denominator = 1
        for i in range(len(x_arr)):
            if i != j:
                numerator = numerator * (x - x_arr[i])
                denominator = denominator * (x_arr[j] - x_arr[i])
        l = l + y_arr[j] * numerator / denominator
    return l


def get_points_from_func(f, a, b, step):
    x = []
    y = []
    x_now = a
    while x_now <= b:
        x.append(x_now)
        y.append(f(x_now))
        x_now += step
    return x, y

def create_factorial(n):
    result = []
    for i in range(n+1):
        fact = 1
        for j in range(1, i+1):
            fact *= j
        result.append(fact)
    return result


def stirling_method(x_arr, y_arr, x):
    if len(y_arr) % 2 == 0:
        return
    h = x_arr[1] - x_arr[0]
    #проверка, что все остальные разности одинаковые
    for i in range(len(y_arr) - 1):
        if round(x_arr[i + 1] - x_arr[i], 8) != round(h, 8):
            return
    fin_diff = final_differences(y_arr)
    fact = create_factorial(len(y_arr))
    mid = len(y_arr)//2
    t = (x - x_arr[mid]) / h
    result = y_arr[mid]

    for n in range(1, mid + 1):
        mul = 1
        for j in range(1, n):
            mul *= (t * t - j * j)
        result += 1 / fact[2 * n - 1] * t * mul * (fin_diff[2 * n - 1][-(n - 1) + mid] + fin_diff[2 * n - 1][-n + mid]) / 2
        result += 1 / fact[2 * n] * (t ** 2) * mul * (fin_diff[2 * n][-n + mid])
    return result

def bessel_method(x_arr, y_arr, x):
    if len(y_arr) % 2 == 1:
        return
    h = x_arr[1] - x_arr[0]
    for i in range(len(y_arr)-1):
        if round(x_arr[i+1] - x_arr[i], 8) != round(h, 8):
            return
    fin_diff = final_differences(y_arr)
    fact = create_factorial(len(y_arr))
    mid = (len(y_arr) - 2) // 2
    t = (x - x_arr[mid]) / h
    result = 0

    for n in range(0, mid + 1):
        mul = 1
        for j in range(1, n + 1):
            mul *= (t - j)*(t + j - 1)
        result += (1 / fact[2 * n]) * mul * (fin_diff[2 * n][-n + mid] + fin_diff[2 * n][-(n - 1) + mid]) / 2
        result += (1 / fact[2 * n + 1]) * (t - (1 / 2)) * mul * (fin_diff[2 * n + 1][-n + mid])
    return result


def gauss_polynomial(xs, ys, x):
    n = len(xs) - 1
    alpha_ind = n // 2
    fin_difs = [ys[:]]

    # Вычисление конечных разностей
    for k in range(1, n + 1):
        last = fin_difs[-1]
        fin_difs.append([last[i + 1] - last[i] for i in range(n - k + 1)])

    h = xs[1] - xs[0]
    dts1 = [0, -1, 1, -2, 2, -3, 3, -4, 4]

    # Функция для вычисления суммы
    def calc_sum(x, dts, alpha_ind, h, fin_difs):
        return sum([
            reduce(lambda a, b: a * b,
                   [(x - xs[alpha_ind]) / h + dts[j] for j in range(k)])
            * fin_difs[k][len(fin_difs[k]) // 2] / factorial(k)
            for k in range(1, n + 1)
        ])

    # Выбор корректной формулы в зависимости от значения x
    if x > xs[alpha_ind]:
        return ys[alpha_ind] + calc_sum(x, dts1, alpha_ind, h, fin_difs)
    else:
        return ys[alpha_ind] + calc_sum(x, [-dt for dt in dts1], alpha_ind, h, fin_difs)


plot_area = 2

def plot_lag_newton(x, f_y, lag_y, newton_y, stirling_y, bessel_y, point_x, point_y):
    if f_y != None:
        plt.plot(x, f_y(x), linewidth=2.0, label="function")
    plt.plot(x, lag_y, linewidth=2.0, label="lagrange")
    plt.plot(x, newton_y, linewidth=2.0, label="newton")
    plt.plot(x, stirling_y, linewidth=2.0, label="stirling")
    plt.plot(x, bessel_y, linewidth=2.0, label="bessel")
    plt.plot(point_x, point_y, '*', linewidth=0, label="points")
    plt.legend()
    plt.grid(True)
    minimum_x = min(point_x)
    minimum_y = min(point_y)
    maximum_x = max(point_x)
    maximum_y = max(point_y)
    plt.xlim(minimum_x - plot_area, maximum_x + plot_area)
    plt.ylim(minimum_y - plot_area, maximum_y + plot_area)
    plt.show()


INPUT = "./input.txt"


def get_points_file():
    # Получение точек из файла
    with open(INPUT, 'rt') as file:
        try:
            x = []
            y = []
            for line in file:
                new_row = list(map(float, line.strip().split()))
                if len(new_row) != 2:
                    raise ValueError
                x.append(new_row[0])
                y.append(new_row[1])
        except ValueError:
            print("Неверный формат файла")
            exit()
    return x, y


def ask_input_data():
    mode = 0
    while mode != 1 and mode != 2:
        try:
            mode = int(input("Ведите источник точек. Для файла - 1, из функции - 2: "))
        except Exception:
            print("Введите число")
    return mode


def ask_num():
    a = 0
    while True:
        try:
            a = float(input("Введите число: "))
            return a
        except Exception:
            print("Нужно вводить число!")
