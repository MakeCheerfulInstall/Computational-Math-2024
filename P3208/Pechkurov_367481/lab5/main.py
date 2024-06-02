# -*- coding: utf-8 -*-
from functools import reduce
import math
import matplotlib.pyplot as plt

def input_data():
    n = int(input("Введите количество точек: "))
    x = []
    y = []
    for i in range(n):
        x.append(float(input(f"x[{i}]: ")))
        y.append(float(input(f"y[{i}]: ")))
    return x, y

def read_data_from_file(filename):
    with open(filename, 'r') as f:
        data = f.readlines()
    x = []
    y = []
    for line in data:
        xi, yi = map(float, line.split())
        x.append(xi)
        y.append(yi)
    return x, y

def generate_function_data(func, a, b, n):
    x = [a + i * (b - a) / (n - 1) for i in range(n)]
    y = [func(xi) for xi in x]
    return x, y



def lagrange_interpolation(x, y, xi):
    n = len(x)
    result = 0
    for i in range(n):
        term = y[i]
        for j in range(n):
            if i != j:
                term *= (xi - x[j]) / (x[i] - x[j])
        result += term
    return result

def newton_divided_differences(x, y, xi):
    n = len(x)
    def calc_newton_divided_difference_polynomial(xs, ys):
        div_difs = []
        div_difs.append(ys[:])
        for k in range(1, n):
            new = []
            last = div_difs[-1][:]
            for i in range(n - k):
                new.append((last[i + 1] - last[i]) / (xs[i + k] - xs[i]))
            div_difs.append(new[:])
        f = lambda x: ys[0] + sum([
            div_difs[k][0] * reduce(lambda a, b: a * b, [x - xs[j] for j in range(k)])
            for k in range(1, n)])
        return f
    return calc_newton_divided_difference_polynomial(x, y)(xi)


def gauss_interpolation(x, y, xi):
    n = len(x)
    def calc_gauss_polynomial(xs, ys):
        alpha_ind = n // 2
        fin_difs = []
        fin_difs.append(ys[:])

        for k in range(1, n):
            last = fin_difs[-1][:]
            fin_difs.append(
                [last[i + 1] - last[i] for i in range(n - k)])

        h = xs[1] - xs[0]
        dts1 = [0, -1, 1, -2, 2, -3, 3, -4, 4]
        f1 = lambda x: ys[alpha_ind] + sum([
            reduce(lambda a, b: a * b,
                   [(x - xs[alpha_ind]) / h + dts1[j] for j in range(k)])
            * fin_difs[k][len(fin_difs[k]) // 2] / math.factorial(k)
            for k in range(1, n)])
        f2 = lambda x: ys[alpha_ind] + sum([
            reduce(lambda a, b: a * b,
                   [(x - xs[alpha_ind]) / h - dts1[j] for j in range(k)])
            * fin_difs[k][len(fin_difs[k]) // 2 - (1 - len(fin_difs[k]) % 2)] / math.factorial(k)
            for k in range(1, n)])
        return f1 if xi > xs[alpha_ind] else f2
    return calc_gauss_polynomial(x, y)(xi)


def finite_differences(x, y):
    n = len(y)
    difference_table = [y.copy()]
    for i in range(1, n):
        differences = []
        for j in range(n - i):
            differences.append(difference_table[i - 1][j + 1] - difference_table[i - 1][j])
        difference_table.append(differences)
    return difference_table




def plot_graphs(x, y, func, xi, yi_lagrange, yi_newton, yi_gauss):
    plt.scatter(x, y, color='red', label='Узлы интерполяции')

    x_line = [min(x) + i * (max(x) - min(x)) / 1000 for i in range(1001)]
    if func:
        y_func = [func(xi) for xi in x_line]
        plt.plot(x_line, y_func, label='Исходная функция', color='black')

    y_lagrange = [lagrange_interpolation(x, y, xi) for xi in x_line]
    y_newton = [newton_divided_differences(x, y, xi) for xi in x_line]
    y_gauss = [gauss_interpolation(x, y, xi) for xi in x_line]

    plt.plot(x_line, y_lagrange, label='Интерполяция Лагранжа', color='blue')
    plt.plot(x_line, y_newton, label='Интерполяция Ньютона', color='green')
    plt.plot(x_line, y_gauss, label='Интерполяция Гаусса', color='orange')

    plt.scatter([xi], [yi_lagrange], color='blue', marker='x', label='Значение Лагранжа')
    plt.scatter([xi], [yi_newton], color='green', marker='x', label='Значение Ньютона')
    plt.scatter([xi], [yi_gauss], color='orange', marker='x', label='Значение Гаусса')

    plt.legend()
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('Интерполяция многочленами')
    plt.show()

def main():
    print("Выберите способ ввода данных:")
    print("1. Ввести данные с клавиатуры")
    print("2. Прочитать данные из файла")
    print("3. Сгенерировать данные на основе функции")

    choice = int(input("Введите номер выбора: "))

    func = None

    if choice == 1:
        x, y = input_data()
    elif choice == 2:
        filename = input("Введите имя файла: ")
        x, y = read_data_from_file(filename)
    elif choice == 3:
        print("Выберите функцию:")
        print("1. sin(x)")
        print("2. cos(x)")
        func_choice = int(input("Введите номер функции: "))
        if func_choice == 1:
            func = math.sin
        elif func_choice == 2:
            func = math.cos
        else:
            print("Неверный выбор функции")
            return
        a = float(input("Введите начало интервала: "))
        b = float(input("Введите конец интервала: "))
        n = int(input("Введите количество точек: "))
        x, y = generate_function_data(func, a, b, n)
    else:
        print("Неверный выбор способа ввода данных")
        return

    print("\nТаблица конечных разностей:")
    diff_table = finite_differences(x, y)
    for row in diff_table:
        print(row)

    xi = float(input("Введите значение аргумента для интерполяции: "))

    yi_lagrange = round(lagrange_interpolation(x, y, xi), 4)
    yi_newton = round(newton_divided_differences(x, y, xi), 4)
    yi_gauss = round(gauss_interpolation(x, y, xi), 4)

    print(f"\nПриближенные значения функции для x = {xi}:")
    print(f"Многочлен Лагранжа: {yi_lagrange}")
    print(f"Многочлен Ньютона: {yi_newton}")
    print(f"Многочлен Гаусса: {yi_gauss}")

    plot_graphs(x, y, func, xi, yi_lagrange, yi_newton, yi_gauss)

if __name__ == "__main__":
    main()
