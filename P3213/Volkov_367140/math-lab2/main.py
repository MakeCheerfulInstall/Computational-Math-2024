# Лабораторная работа #2 
import numpy as np
import matplotlib.pyplot as plt

FILE_IN = "iofiles/input.txt"


def d(n, x, f, h=0.00000001):
    """ Найти значение производной функции """
    if n <= 0:
        return None
    elif n == 1:
        return (f(x + h) - f(x)) / h

    return (d(n - 1, x + h, f) - d(n - 1, x, f)) / h


def chord_method(a, b, f, e, maxitr=100):
    """ Метод хорд """
    log = [['a', 'b', 'x', 'f(a)', 'f(b)', 'f(x)', '|x - x0|']]

    if f(a) * d(2, a, f) < 0:
        x = a
        fix_x = b
    elif f(b) * d(2, a, f) < 0:
        x = b
        fix_x = a
    else:
        x = a - (b - a) / (f(b) - f(a)) * f(a)
        fix_x = None

    x0 = x + 2 * e
    log.append([a, b, x, f(a), f(b), f(x), abs(x - x0)])

    itr = 0
    while abs(x - x0) > e and itr < maxitr:
        if fix_x is None:
            if f(a) * f(x) < 0:
                b = x
            else:
                a = x
            x, x0 = a - (b - a) / (f(b) - f(a)) * f(a), x
            log.append([a, b, x, f(a), f(b), f(x), abs(x - x0)])
        else:
            x, x0 = x - (fix_x - x) / (f(fix_x) - f(x)) * f(x), x
            if fix_x == a:
                log.append([fix_x, x, x, f(fix_x), f(x), f(x), abs(x - x0)])
            else:
                log.append([x, fix_x, x, f(x), f(fix_x), f(x), abs(x - x0)])
        itr += 1

    return x, f(x), itr, log


def secant_method(a, b, f, e, maxitr=100):
    """ Метод секущих """
    log = [['x0', 'f(x)', 'x', 'f(x)', 'x1', 'f(x1)', '|x - x1|']]

    if f(a) * d(2, a, f) > 0:
        x0 = a
    elif f(b) * d(2, a, f) > 0:
        x0 = b
    else:
        return None
    x1 = x0 + e
    x = x1 + 2 * e

    itr = 0
    while abs(x - x1) > e and itr < maxitr:
        x1, x, x0 = x1 - (x1 - x0) / (f(x1) - f(x0)) * f(x1), x1, x
        log.append([x0, f(x0), x, f(x), x1, f(x1), abs(x - x1)])
        itr += 1

    return x1, f(x1), itr, log


def iteration_method(x0, f, e, maxitr=100):
    """ Метод простой итерации """
    log = [['x0', 'f(x0)', 'x', 'g(x0)', '|x - x0|']]

    def g(g_x):
        return g_x + (-1 / d(1, g_x, f)) * f(g_x)

    x = g(x0)
    log.append([x0, f(x0), x, g(x0), abs(x - x0)])

    itr = 0
    while abs(x - x0) > e and itr < maxitr:
        if d(1, x, g) >= 1:
            return None
        x0, x = x, g(x)
        log.append([x0, f(x0), x, g(x0), abs(x - x0)])
        itr += 1

    return x, f(x), itr, log


def plot(x, y):
    """ Отрисовать график по заданным x и y """
    # Настраиваем всплывающее окно
    # plt.rcParams['toolbar'] = 'None'
    plt.gcf().canvas.set_window_title("График функции")
    # Настриваем оси
    ax = plt.gca()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.plot(1, 0, marker=">", ms=5, color='k',
            transform=ax.get_yaxis_transform(), clip_on=False)
    ax.plot(0, 1, marker="^", ms=5, color='k',
            transform=ax.get_xaxis_transform(), clip_on=False)

    # Отрисовываем график
    plt.plot(x, y)
    plt.show(block=False)


def getfunc(function_num):
    """ Получить выбранную функцию """
    if function_num == '1':
        return np.linspace(-1, 3, 200), \
               lambda x: x ** 3 - 2.92 * (x ** 2) + 1.435 * x + 0.791
    elif function_num == '2':
        return np.linspace(-3, 2, 200), \
               lambda x: x ** 3 - x + 4
    elif function_num == '3':
        return np.linspace(-20, 20, 200), \
               lambda x: np.sin(x) + 0.1
    else:
        return None


def getdata_file():
    """ Получить данные из файла """
    with open(FILE_IN, 'rt') as fin:
        try:
            data = {}

            function_data = getfunc(fin.readline().strip())
            if function_data is None:
                raise ValueError
            x, function = function_data
            plot(x, function(x))
            data['function'] = function

            method = fin.readline().strip()
            if (method != '1') and (method != '2') and (method != '3'):
                raise ValueError
            data["method"] = method

            if method == '1' or method == '2':
                a, b = map(float, fin.readline().strip().split())
                if a > b:
                    a, b = b, a
                elif a == b:
                    raise ArithmeticError
                elif function(a) * function(b) > 0:
                    raise AttributeError
                data['a'] = a
                data['b'] = b
            elif method == '3':
                x0 = float(fin.readline().strip())
                data['x0'] = x0

            error = float(fin.readline().strip())
            if error < 0:
                raise ArithmeticError
            data['error'] = error

            return data
        except (ValueError, ArithmeticError, AttributeError):
            return None


def getdata_input():
    """ Получить данные с клавиатуры """
    data = {}

    print("\nВыберите функцию.")
    print(" 1 - x³ - 2.92x² + 4.435x + 0.791")
    print(" 2 - x³ - x + 4")
    print(" 3 - sin(x) + 0.1")
    function_data = getfunc(input("Функция: "))
    while function_data is None:
        print("Выберите функцию из списка.")
        function_data = getfunc(input("Функция: "))
    x, function = function_data
    plot(x, function(x))
    data['function'] = function

    print("\nВыберите метод решения.")
    print(" 1 - Метод хорд")
    print(" 2 - Метод секущих")
    print(" 3 - Метод простой итерации")
    method = input("Метод решения: ")
    while (method != '1') and (method != '2') and (method != '3'):
        print("Выберите метод решения из списка.")
        method = input("Метод решения: ")
    data['method'] = method

    if method == '1' or method == '2':
        print("\nВыберите границы интервала.")
        while True:
            try:
                a, b = map(float, input("Границы интервала: ").split())
                if a > b:
                    a, b = b, a
                elif a == b:
                    raise ArithmeticError
                elif function(a) * function(b) > 0:
                    raise AttributeError
                break
            except ValueError:
                print("Границы интервала должны быть числами, введенными через пробел.")
            except ArithmeticError:
                print("Границы интервала не могут быть равны.")
            except AttributeError:
                print("Интервал содержит ноль или несколько корней.")
        data['a'] = a
        data['b'] = b
    elif method == '3':
        print("\nВыберите начальное приближение.")
        while True:
            try:
                x0 = float(input("Начальное приближение: "))
                break
            except ValueError:
                print("Начальное приближение должно быть числом.")
        data['x0'] = x0

    print("\nВыберите погрешность вычисления.")
    while True:
        try:
            error = float(input("Погрешность вычисления: "))
            if error <= 0:
                raise ArithmeticError
            break
        except (ValueError, ArithmeticError):
            print("Погрешность вычисления должна быть положительным числом.")
    data['error'] = error

    return data


def main():
    print("\t\tЛабораторная работа #2 (19)")
    print("Численное решение нелинейных уравнений")

    print("\nВзять исходные данные из файла (+) или ввести с клавиатуры (-)?")
    inchoice = input("Режим ввода: ")
    while (inchoice != '+') and (inchoice != '-'):
        print("Введите '+' или '-' для выбора способа ввода.")
        inchoice = input("Режим ввода: ")

    if inchoice == '+':
        data = getdata_file()
        if data is None:
            print("\nПри считывании данных из файла произошла ошибка!")
            print("Режим ввода переключен на ручной.")
            data = getdata_input()
    else:
        data = getdata_input()

    try:
        answer = None
        if data['method'] == '1':
            answer = chord_method(data['a'], data['b'], data['function'], data['error'])
        elif data['method'] == '2':
            answer = secant_method(data['a'], data['b'], data['function'], data['error'])
            if answer is None:
                print("Знаки функций и вторых производных не равны ни в 'a', ни в 'b'.")
                raise ValueError
        elif data['method'] == '3':
            answer = iteration_method(data['x0'], data['function'], data['error'])
            if answer is None:
                print("Не выполняется условие сходимости.")
                raise ValueError

        print(f"\nКорень уравнения: {answer[0]}")
        print(f"Значение функции в корне: {answer[1]}")
        print(f"Число итераций: {answer[2]}")

        print("\nВывести таблицу трассировки? (+ / -)")
        logchoice = input("Таблица трассировки: ")
        while (inchoice != '+') and (inchoice != '-'):
            print("Введите '+' или '-' для выбора, выводить ли таблицу трассировки.")
            logchoice = input("Таблица трассировки: ")

        if logchoice == '+':
            for j in range(len(answer[3][0])):
                print('%12s' % answer[3][0][j], end = '')
            print()
            for i in range(1, len(answer[3])):
                for j in range(len(answer[3][i])):
                    print('%12.3f' % answer[3][i][j], end='')
                print()
    except ValueError:
        pass

    input("\n\nНажмите Enter, чтобы выйти.")


main()
