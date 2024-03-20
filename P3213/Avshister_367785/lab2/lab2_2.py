import math
import matplotlib.pyplot as plt
import sympy
from sympy import *
import numpy

while True:
    try:
        print("Выберите уравнение (цифра от 1 до 5): ")
        print("1. y = x^2-2x-5")
        print("2. y = 2/x-3x")
        print("3. y = 5x-3")
        print("4. y = 2.74x^3-1.93x^2-15.28x-3.72")
        print("5. y = e^(3x)-2")
        num = int(input())
    except ValueError:
        print("Пожалуйста, введите число")
        continue
    if num not in [1, 2, 3, 4, 5]:
        print('Пожалуйста, введите цифру от 1 до 5')
        continue
    else:
        break


def f(x):
    if num == 1:
        y = x ** 2 - 2 * x - 5
    elif num == 2:
        y = 2 / x - 3 * x
    elif num == 3:
        y = 5 * x - 3
    elif num == 4:
        y = 2.74 * x ** 3 - 1.93 * x ** 2 - 15.28 * x - 3.72
    else:
        y = math.e ** (3 * x) - 2
    return y


def check_d(num, a, b):
    ans = {
        1: [-1.449, 3.449],
        2: [-0.816, 0.816],
        3: [0.6],
        4: [2.838, -1.885, -0.259],
        5: [0.231]
    }
    if num == 1:
        if a < ans.get(num)[0] < b and a < ans.get(num)[1] < b:
            return 2
        elif (a > ans.get(num)[0] or ans.get(num)[0] > b) \
                and (ans.get(num)[1] > b or ans.get(num)[1] < a):
            return 0
        else:
            return 1
    elif num == 2:
        if a < ans.get(num)[0] and ans.get(num)[1] < b:
            return 2
        elif (a > ans.get(num)[0] or ans.get(num)[0] > b) \
                and (ans.get(num)[1] > b or ans.get(num)[1] < a):
            return 0
        else:
            return 1
    elif num == 3:
        if a > ans.get(num)[0] or ans.get(num)[0] > b:
            return 0
        else:
            return 1
    elif num == 4:
        if (a < ans.get(num)[0] < b and a < ans.get(num)[1] < b) or (
                a < ans.get(num)[0] < b and a < ans.get(num)[2] < b) or (
                a < ans.get(num)[1] < b and a < ans.get(num)[2] < b):
            return 2
        elif (b < ans.get(num)[0] or a > ans.get(num)[0]) and (
                a > ans.get(num)[1] or b < ans.get(num)[1]) and (
                a > ans.get(num)[2] or b < ans.get(num)[2]):
            return 0
        else:
            return 1
    elif num == 5:
        if a > ans.get(num)[0] or ans.get(num)[0] > b:
            return 0
        else:
            return 1


def input_from():
    while True:
        try:
            n = int(input("Введите 1 для ввода данных из файла, 2 - с клавиатуры: "))
        except ValueError:
            print("Пожалуйста, введите число")
            continue
        if n != 1 and n != 2:
            print("Пожалуйста, введите 1 или 2")
            continue
        else:
            return n


def output_to():
    while True:
        try:
            n = int(input("Введите 1, если хотите сохранить результат в файл. 2, если нет "))
        except ValueError:
            print("Пожалуйста, введите число")
            continue
        if n != 1 and n != 2:
            print("Пожалуйста, введите 1 или 2")
            continue
        else:
            return n


def get_data():
    n = input_from()
    while True:
        try:
            print("Введите границы интервала и погрешность вычисления (все числа через пробел)")
            if n == 1:
                with open('input.txt', 'r') as f:
                    lines = f.readline()
                f.close()
                if len(lines) != 3:
                    print("В файле должно быть три числа")
                    exit(0)
            else:
                lines = input()
            lines = lines.split(" ")
            return lines
        except IndexError:
            raise IndexError("Невозможно прочитать данные из пустого файла")


try:
    lines = get_data()
    a = float(lines[0])
    b = float(lines[1])
    e = float(lines[2].replace(',', '.'))
except ValueError:
    raise ValueError("Пожалуйста, введите три числа")
ch = check_d(num, a, b)
if ch == 0:
    raise ValueError("На данном промежутке нет корней, попробуйте другой")
if ch == 2:
    raise ValueError("На данном промежутке несколько корней, попробуйте другой")
else:
    x = numpy.arange(-10, 10, 0.01)
    plt.xlabel('$x$')
    plt.ylabel('$y$')
    plt.grid(True)
    plt.xlim(-5, 5)
    plt.ylim(-10, 10)
    if num == 1:
        plt.title('$y=x^2-2x-5$')
        plt.plot(x, x ** 2 - 2 * x - 5, [-1.449, 3.449], [0, 0], 'ro')
    elif num == 2:
        plt.title('$y=2/x-3x$')
        plt.plot(x, 2 / x - 3 * x, [-0.816, 0.816], [0, 0], 'ro')
    elif num == 3:
        plt.title('$y=5x-3$')
        plt.plot(x, 5 * x - 3, 0.6, 0, 'ro')
    elif num == 4:
        plt.title('$y=2.74x^3-1.93x^2-15.28x-3.72$')
        plt.plot(x, 2.74 * x ** 3 - 1.93 * x ** 2 - 15.28 * x - 3.72, [2.838, -1.885, -0.259], [0, 0, 0], 'ro')
    else:
        plt.title('$y=e^(3x)-2$')
        plt.plot(x, math.e ** (3 * x) - 2, 0.231, 0, 'ro')
    plt.show()


def half(a, b, e):
    y_0 = 10 ** 8
    last = 5000
    x_0 = 0
    while abs(b - a) > e or abs(y_0) > e or abs(x_0 - last) > e:
        last = x_0
        x_0 = (a + b) / 2
        if num == 2:
            if x_0 == 0:
                y_0 = f(x_0 + 0.1)
            else:
                y_0 = f(x_0)
            if a == 0:
                y_a = f(a + 0.1)
            else:
                y_a = f(a)
            if b == 0:
                y_b = f(b - 0.1)
            else:
                y_b = f(b)
        else:
            y_0 = f(x_0)
            y_a = f(a)
            y_b = f(b)
        if y_0 * y_a < 0:
            b = x_0
        elif y_0 * y_b < 0:
            a = x_0
    x = (a + b) / 2
    y = f(x)
    return [float('%.5f' % x), float('%.5f' % y)]


def Newton(a, b, e):
    x = sympy.symbols('x')
    if num == 2:
        if a == 0:
            y_a = f(a + 0.1)
            pr2_a = diff(diff(f(x))).subs(x, a + 0.1)
        else:
            y_a = f(a)
            pr2_a = diff(diff(f(x))).subs(x, a)
        if b == 0:
            y_b = f(b - 0.1)
            pr2_b = diff(diff(f(x))).subs(x, b - 0.1)
        else:
            y_b = f(b)
            pr2_b = diff(diff(f(x))).subs(x, b)
        if y_a * pr2_a > 0:
            x_i = a + 0.1
        elif y_b * pr2_b > 0:
            x_i = b - 0.1
        else:
            return [-10000, -10000]
    else:
        y_a = f(a)
        y_b = f(b)
        pr2_a = diff(diff(f(x))).subs(x, a)
        pr2_b = diff(diff(f(x))).subs(x, b)
        if y_a * pr2_a > 0:
            x_i = a
        elif y_b * pr2_b > 0:
            x_i = b
        else:
            return [-10000, -10000]
    last = 10 ** 8
    while (abs(f(x_i)) > e or abs(f(x_i) / diff(f(x)).subs(x, x_i)) > e or abs(x_i - last) > e) and a <= x_i <= b:
        last = x_i
        x_i = x_i - f(x_i) / diff(f(x)).subs(x, x_i)
    y = f(x_i)
    return [float('%.5f' % x_i), float('%.5f' % y)]


def simp_iter(a, b, e):
    x = sympy.symbols('x')
    if num == 2:
        if a == 0:
            f_x_a = diff(f(x)).subs(x, a + 0.1)
        else:
            f_x_a = diff(f(x)).subs(x, a)
        if b == 0:
            f_x_b = diff(f(x)).subs(x, b - 0.1)
        else:
            f_x_b = diff(f(x)).subs(x, b)
    else:
        f_x_a = diff(f(x)).subs(x, a)
        f_x_b = diff(f(x)).subs(x, b)
    if f_x_a > 0 or f_x_b > 0:
        lam = - 1 / max(abs(f_x_a), abs(f_x_b))
    else:
        lam = 1 / max(abs(f_x_a), abs(f_x_b))
    phi = x + lam * f(x)
    q = max(abs(diff(phi).subs(x, a)), abs(diff(phi).subs(x, b)))
    if q > 1:
        return [-10000, -10000]
    else:
        x_n = a
        last = 10 ** 8
        while abs(x_n - last) > e or abs(f(x_n)) > e:
            last = x_n
            x_n = phi.subs(x, x_n)
        y = f(x_n)
        return [float('%.5f' % x_n), float('%.5f' % y)]


n = output_to()
if n == 1:
    with open('output.txt', 'w') as file:
        ans = half(a, b, e)
        rpd1 = ans[0]
        rpd2 = ans[1]
        file.write("Метод половинного деления дал следующие результаты: ")
        file.write(str(rpd1) + " ")
        file.write(str(rpd2))
        file.write('\n')
        ans = Newton(a, b, e)
        rn1 = ans[0]
        rn2 = ans[1]
        file.write("Метод Ньютона дал следующие результаты: ")
        file.write(str(rn1) + " ")
        file.write(str(rn2))
        file.write('\n')
        ans = simp_iter(a, b, e)
        pi1 = ans[0]
        pi2 = ans[1]
        file.write("Метод простой итерации дал следующие результаты: ")
        file.write(str(pi1) + " ")
        file.write(str(pi2))
        file.write('\n')
    file.close()
else:
    ans = half(a, b, e)
    rpd1 = ans[0]
    rpd2 = ans[1]
    print("Метод половинного деления дал следующие результаты: ", rpd1, rpd2)
    ans = Newton(a, b, e)
    rn1 = ans[0]
    rn2 = ans[1]
    if rn1 == rn2 == -10000:
        print("У метода Ньютона нет сходимости")
    else:
        print("Метод Ньютона дал следующие результаты: ", rn1, rn2)
    ans = simp_iter(a, b, e)
    pi1 = ans[0]
    pi2 = ans[1]
    if pi1 == pi2 == -10000:
        print("В методе простых итераций сходимости нет")
    else:
        print("Метод простой итерации дал следующие результаты: ", pi1, pi2)
