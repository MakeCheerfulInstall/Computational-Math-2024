import math


def separate():
    print("-------------------------")

def get_function(func_num):
    if func_num == 1:
        return lambda x: 3 * x ** 3 - 4 * x ** 2 + 5 * x - 16
    elif func_num == 2:
        return lambda x: x ** 3 - 5 * x ** 2 + 3 * x - 16
    elif func_num == 3:
        return lambda x: math.e ** (-x) + x ** 2
    elif func_num == 4:
        return lambda x: math.pi / 4 * (2 * math.cos(x) + 3 * math.sin(x))


def rectangle_mid(a, b, e, func_number, n=4):
    function = get_function(func_number)
    sum_i0 = 0
    a_new = a
    h = (b - a) / n
    for i in range(0, n):
        sum_i0 += function(a_new + h / 2)
        a_new += h
    sum_i0 = sum_i0 * h

    while True:
        a_new = a
        sum_i1 = 0
        n *= 2
        h = (b - a) / n

        for i in range(0, n):
            sum_i1 += function(a_new + h / 2)
            a_new += h
        sum_i1 = sum_i1 * h

        if (abs(sum_i1 - sum_i0) / 3) <= e:
            print(f"{sum_i1}, n = {n}")
            separate()
            break
        sum_i0 = sum_i1


def rectangle_left(a, b, e, func_number, n=4):
    function = get_function(func_number)
    a_new = a
    h = (b - a) / n
    sum_i0 = 0
    for i in range(0, n):
        sum_i0 += function(a_new)
        a_new += h
    sum_i0 = sum_i0 * h

    while True:
        a_new = a
        sum_i1 = 0
        n *= 2
        h = (b - a) / n

        for i in range(0, n):
            sum_i1 += function(a_new)
            a_new += h
        sum_i1 = sum_i1 * h

        if (abs(sum_i1 - sum_i0) / 3) <= e:
            print(f"{sum_i1}, n = {n}")
            separate()
            break
        sum_i0 = sum_i1


def rectangle_right(a, b, e, func_number, n=4):
    function = get_function(func_number)
    a_new = a
    h = (b - a) / n
    a_new += h
    sum_i0 = 0
    for i in range(0, n):
        sum_i0 += function(a_new)
        a_new += h
    sum_i0 = sum_i0 * h

    while True:
        a_new = a
        sum_i1 = 0
        n *= 2
        h = (b - a) / n
        a_new += h

        for i in range(0, n):
            sum_i1 += function(a_new)
            a_new += h
        sum_i1 = sum_i1 * h

        if (abs(sum_i1 - sum_i0) / 3) <= e:
            print(f"{sum_i1}, n = {n}")
            separate()
            break
        sum_i0 = sum_i1


def trapezoid(a, b, e, func_number, n=4):
    function = get_function(func_number)
    sum_i0 = 0
    h = (b - a) / n

    for i in range(1, n):
        sum_i0 += function(a + i * h)
    sum_i0 = (function(a) + function(b) + 2 * sum_i0) * h / 2

    while True:
        sum_i1 = 0
        n *= 2
        h = (b - a) / n

        for i in range(1, n):
            sum_i1 += function(a + i * h)
        sum_i1 = (function(a) + function(b) + 2 * sum_i1) * h / 2

        if abs(sum_i1 - sum_i0) / 3 <= e:
            print(f"{sum_i1}, n = {n}")
            separate()
            break
        sum_i0 = sum_i1


def simpson(a, b, e, func_number, n=4):
    print(a, b, e, func_number, n)
    function = get_function(func_number)
    sum_i0 = 0
    h = (b - a) / n

    for i in range(1, n, 2):
        sum_i0 += 4 * function(a + i * h)
    for i in range(2, n - 1, 2):
        sum_i0 += 2 * function(a + i * h)

    sum_i0 = (function(a) + function(b) + sum_i0) * h / 3

    while True:
        sum_i1 = 0
        n *= 2
        h = (b - a) / n

        for i in range(1, n, 2):
            sum_i1 += 4 * function(a + i * h)
        for i in range(2, n - 1, 2):
            sum_i1 += 2 * function(a + i * h)

        sum_i1 = (function(a) + function(b) + sum_i1) * h / 3

        if abs(sum_i1 - sum_i0) / 15 <= e:
            print(f"{sum_i1}, n = {n}")
            separate()
            break
        sum_i0 = sum_i1


print(f'1) 3x^3 - 4x^2 + 5x - 16')
print(f'2) x^3 - 5x^2 + 3x - 16')
print(f'3) e^-x + x^2')
print(f'4) pi/4 * (2cos(x) + 3sin(x))')

choice = int(input("Ваш выбор: "))
print("Выберите пределы интегрирования: ")
input_a = float(input("Нижний: "))
input_b = float(input("Верхний: "))
epsilon = float(input("Точность: "))
nn = int(input("Начальное значение числа разбиения: "))
while True:
    method_number = int(input("Выберите метод:\n1 - Прямоугольника (центральный)\n2 - Прямоугольника (левый)\n3 - "
                              "Прямоугольника (правый)\n4 - Tрапеции\n5 - Симпсона\nВаш выбор: "))
    separate()
    if method_number == 1:
        rectangle_mid(input_a, input_b, epsilon, choice, nn)
    elif method_number == 2:
        rectangle_left(input_a, input_b, epsilon, choice, nn)
    elif method_number == 3:
        rectangle_right(input_a, input_b, epsilon, choice, nn)
    elif method_number == 4:
        trapezoid(input_a, input_b, epsilon, choice, nn)
    elif method_number == 5:
        simpson(input_a, input_b, epsilon, choice, nn)
    else:
        print("Введите корректный номер метода!")
        continue
    break
