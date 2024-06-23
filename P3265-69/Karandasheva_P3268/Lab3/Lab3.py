import numpy as np

# Функции для интегрирования
def f1(x):
    return 3 * x ** 3 + 5 * x ** 2 + 3 * x - 6

def f2(x):
    return np.sin(x)

def f3(x):
    return np.exp(x)

def f4(x):
    return np.cos(x)

def f5(x):
    return np.sqrt(x)

# Методы интегрирования
def left_rectangle_method(f, a, b, n):
    h = (b - a) / n
    integral = sum([f(a + i * h) for i in range(n)])
    return h * integral

def right_rectangle_method(f, a, b, n):
    h = (b - a) / n
    integral = sum([f(a + (i + 1) * h) for i in range(n)])
    return h * integral

def middle_rectangle_method(f, a, b, n):
    h = (b - a) / n
    integral = sum([f(a + (i + 0.5) * h) for i in range(n)])
    return h * integral

def trapezoidal_method(f, a, b, n):
    h = (b - a) / n
    integral = sum([(f(a + i * h) + f(a + (i + 1) * h)) for i in range(n)]) / 2
    return h * integral

def simpson_method(f, a, b, n):
    h = (b - a) / n
    x = np.linspace(a, b, n + 1)
    integral = (h / 6) * (f(a) + f(b) + 2 * sum([4 * f(x[i]) for i in range(1, n, 2)]) + 4 * sum([2 * f(x[i]) for i in range(2, n - 1, 2)]))
    return integral

# Правило рунге для проверки точности
def runge_rule(method, f, a, b, accuracy, k):
    n = 1
    prev_integral = method(f, a, b, n)
    n *= 2
    integral = method(f, a, b, n)

    while(abs(integral - prev_integral)/(2^k-1)) >= accuracy:
        prev_integral = integral
        n *= 2
        integral = method(f, a, b, n)

    return integral, n

# Функции и методы
functions = {1: f1, 2: f2, 3: f3, 4: f4, 5: f5}
methods = {
    1: left_rectangle_method,
    2: right_rectangle_method,
    3: middle_rectangle_method,
    4: trapezoidal_method,
    5: simpson_method
}

def main():
    print("Выбери функцию для интегрирования:")
    print("1. 3x^3 + 5x^2 + 3x - 6")
    print("2. sin(x)")
    print("3. e^x") # не стоит брать, кончится память
    print("4. cos(x)")
    print("5. sqrt(x)")

    choice = int(input())
    if choice not in functions:
        raise ValueError("Введён неверный номер функции")

    f = functions[choice]

    a = float(input("Введи левый предел интегрирования: "))
    b = float(input("Введи правый предел интегрирования: "))
    accuracy = float(input("Задай точность вычисления: "))
    initial_intervals = 4

    print("Выбери метод интегрирования:")
    print("1. Метод левых прямоугольников")
    print("2. Метод правых прямоугольников")
    print("3. Метод средних прямоугольников")
    print("4. Метод трапеций")
    print("5. Метод Симпсона")
    method_choice = int(input())

    if method_choice == 5:
        k = 4
    #else if ((method_choice > 0) and (method_choice < 5):
        k = 2
    else:
      k = 2
      #raise ValueError("Введён неверный номер метода")
    integral, num_intervals = runge_rule(methods[method_choice], f, a, b, accuracy, k)
    print("Значение интеграла:", integral)
    print("Число разбиения интервала:", num_intervals)

try:
    main()
except ValueError as e:
    print("Ошибка: ", e)
except KeyboardInterrupt as e:
    print(e)
