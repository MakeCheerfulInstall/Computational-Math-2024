import numpy as np
import matplotlib.pyplot as plt
import sys

def solution_1(x, C):
    return -(x) - 1 + C * np.exp(x)

def solution_2(x, C):
    return np.sin(x) / 2 - np.cos(x) / 2 + C / np.exp(x)

def solution_3(x, C):
    return x ** 4 / 4 + C

def exact_solution_1(y0, x0, xn):
    C = y0 + x0 + 1
    x = np.linspace(x0, xn, 100000)
    y = solution_1(x, C)
    return x, y, lambda x: solution_1(x, C)

def exact_solution_2(y0, x0, xn):
    C = np.exp(x0)*(y0 - np.sin(x0) / 2 + np.cos(x0) / 2)
    x = np.linspace(x0, xn, 100000)
    y = solution_2(x, C)
    return x, y, lambda x: solution_2(x, C)

def exact_solution_3(y0, x0, xn):
    C = y0 - x0 ** 4 / 4
    x = np.linspace(x0, xn, 100000)
    y = solution_3(x, C)
    return x, y, lambda x: solution_3(x, C)

def cycle(f, y0, i, h, counter):
    for j in np.arange(i, i + h + 0.0000000001, h / counter):
        y2 = y0 + (h / counter) * f(j, y0)
        if abs(j-i-h) <= 0.001:
            return y0
        y0 = y2

def euler(f, y0, x0, xn, h, eps):
    integer = 0
    results = []
    popper = 0
    R = 0
    y0_prev = y0
    p = 1
    n = int((xn + 0.001 - x0) / h)
    if n > 100000:
        print("Слишком большое n, выберете другие интервалы")
        sys.exit(0)
    for i in np.arange(x0, xn + 0.001, h):
        y1 = y0 + h * f(i, y0)
        y2 = y0
        counter = 2
        if i != x0:
            y2 = cycle(f, y0, i - h, h, counter)
            R = np.abs(y2 - y0) / (2 ** p - 1)
            while R > eps:
                popper += 1
                counter = counter * 2
                y2 = cycle(f, y0, i - h, h, counter)
                R = np.abs(y2 - y0) / (2 ** counter - 1)
            y0 = y2
        results.append((i, y2))
        integer += 1
        y0 = y1
    return results, R

def getK(f, i, h, y0):
    k1 = h * f(i, y0)
    k2 = h * f(i + h / 2, y0 + k1 / 2)
    k3 = h * f(i + h / 2, y0 + k2 / 2)
    k4 = h * f(i + h, y0 + k3)
    return k1, k2, k3, k4

def runge_kutta_4(f, y0, x0, xn, h, eps):
    def cycle(y0, i, counter):
        for j in np.arange(i, i + h + 0.0000001, h / counter):
            k1, k2, k3, k4 = getK(f, j, h / counter, y0)
            y2 = y0 + 1 / 6 * (k1 + 2 * k2 + 2 * k3 + k4)
            if abs(j-i-h) <= 0.001:
                return y0
            y0 = y2

    integer = 0
    results = []
    result = []
    x0 = x0
    y0 = y0
    y0_prev = y0
    p = 1
    n = int((xn + 0.001 - x0) / h)
    if n > 100000:
        print("Слишком большое n, выберете другие интервалы")
        sys.exit(0)
    for i in np.arange(x0, xn + 0.001, h):
        k1, k2, k3, k4 = getK(f, i, h, y0)

        y1 = y0 + 1 / 6 * (k1 + 2 * k2 + 2 * k3 + k4)
        y2 = y0
        counter = 1
        if i != x0:
            counter = 2
            y2 = cycle(y0, i - h, counter)
            R = np.abs(y2 - y0) / (2 ** p - 1)
            while R > eps:
                counter = counter * 2
                y2 = cycle(y0, i - h, counter)
                R = np.abs(y2 - y0) / (2 ** counter - 1)
            y0 = y2
        p = max(p, counter)
        result.append((y2, f(i, y2)))
        results.append((i, y2))
        integer += 1
        y0 = y1
    return result, results, R

def miln(f, result, h, x0, xn, eps):
    y = [result[i][0] for i in range(4)]
    f_vals = [result[i][1] for i in range(4)]

    results = [(x0 + h * i, y[i]) for i in range(4)]
    counter = 4
    for i in np.arange(x0 + h * 4, xn + 0.001, h):
        y_prog = y[counter - 4] + 4 * h / 3 * (2 * f_vals[counter-3] - f_vals[counter-2] + 2 * f_vals[counter - 1])
        f_prog = f(i, y_prog)
        y_corr = y[counter - 2] + h / 3 * (f_vals[counter - 2] + 4 * f_vals[counter - 1] + f_prog)
        f_corr = f(i, y_corr)
        if (i != x0 + h * 4):
            while (abs(y_corr - y_prog) >= eps):
                y_prog = y_corr
                f_prog = f(i, y_prog)
                y_corr = y[counter - 2] + h / 3 * (f_vals[counter - 2] + 4 * f_vals[counter - 1] + f_prog)
                f_corr = f(i, y_corr)
        y.append(y_corr)
        f_vals.append(f_corr)
        results.append((i, y_corr))
        counter += 1
    return results

def main():
    f1 = lambda x, y: x + y
    f2 = lambda x, y: np.sin(x) - y
    f3 = lambda x, y: x ** 3

    print("f1 = x + y")
    print("f2 = sin(x) - y")
    print("f3 = x^3")
    func_input = input("Выберите функцию f1/f2/f3 ")

    func = f1

    if func_input == "f1":
        func = f1
    elif func_input == "f2":
        func = f2
    elif func_input == "f3":
        func = f3
    else:
        main()
        return

    y0 = float(input("Введите начальное условие y0 = y(x0): "))
    x0 = float(input("Введите начало интервала дифференцирования x0: "))
    xn = float(input("Введите конец интервала дифференцирования xn: "))
    h = float(input("Введите шаг h: "))
    epsilon = float(input("Введите погрешность eps:"))

    if func == f3:
        x, y, exact_sol = exact_solution_3(y0, x0, xn)
    elif func == f2:
        x, y, exact_sol = exact_solution_2(y0, x0, xn)
    else:
        x, y, exact_sol = exact_solution_1(y0, x0, xn)

    plt.plot(x, y, label='Exact solution')

    print("Эйлер: ")
    dotsEuler, R = euler(func, y0, x0, xn, h, epsilon)
    print(f"Точность метода по правилу Рунге: {R}")
    
    print("\nТаблица для метода Эйлера: ")
    print("x \t y \t y_exact")
    for x, y in dotsEuler:
        y_exact = exact_sol(x)
        print(f"{x:.2f} \t {y:.2f} \t {y_exact:.2f}")

    print("Рунге-Кутт: ")
    result, dotsRunge_kutt, R = runge_kutta_4(func, y0, x0, xn, h, epsilon)
    print(f"Точность метода по правилу Рунге: {R}")

    print("\nТаблица для метода Рунге-Кутта: ")
    print("x \t y \t y_exact")
    for x, y in dotsRunge_kutt:
        y_exact = exact_sol(x)
        print(f"{x:.2f} \t {y:.2f} \t {y_exact:.2f}")

    dotsMiln = None
    if len(dotsRunge_kutt) >= 4:
        print("\nМилн: ")
        dotsMiln = miln(func, result, h, x0, xn, epsilon)
        
        print("\nТаблица для метода Милна: ")
        print("x \t y \t y_exact")
        for x, y in dotsMiln:
            y_exact = exact_sol(x)
            print(f"{x:.2f} \t {y:.2f} \t {y_exact:.2f}")
    else:
        print("Не хватает данных для использования метода Милна")

    plt.plot([i[0] for i in dotsEuler], [i[1] for i in dotsEuler], label="Euler")
    plt.plot([i[0] for i in dotsRunge_kutt], [i[1] for i in dotsRunge_kutt], label="Runge-Kutt")
    if dotsMiln is not None:
        plt.plot([i[0] for i in dotsMiln], [i[1] for i in dotsMiln], label="Miln")

    plt.legend()
    plt.show()

if __name__ == "__main__":
    main()

