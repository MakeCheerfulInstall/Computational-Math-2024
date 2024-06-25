import numpy as np

def init_choice():
    print("Выберите функцию для интегрирования:")
    print("1. x^2 - 4x + 4")
    print("2. sin(x)")
    print("3. e^x")
    choice = int(input("Введите номер функции: "))
    equations = {1: equation1, 2: equation2, 3: equation3}
    return equations.get(choice, None)

def equation1(x):
    return x**2 - 4*x + 4

def equation2(x):
    return np.sin(x)

def equation3(x):
    return np.exp(x)

def chose_rec_method():
    return input("Введите название для метода прямоугольников [left/middle/right]: ")

# Метод прямоугольников (левые, правые, средние)
def rectangle_method(a, b, n, method):
    h = (b - a) / n
    integral = 0
    for i in range(n):
        if method == "left":
            x = a + i * h
        elif method == "right":
            x = a + (i + 1) * h
        elif method == "middle":
            x = a + (i + 0.5) * h
        integral += func(x)
    return integral * h

# Метод трапеций
def trapezoidal_method(a, b, n):
    h = (b - a) / n
    integral = (func(a) + func(b)) / 2
    for i in range(1, n):
        integral += func(a + i * h)
    return integral * h

# Метод Симпсона
def simpson_method(a, b, n):
    h = (b - a) / n
    integral = func(a) + func(b)
    for i in range(1, n):
        x = a + i * h
        if i % 2 == 0:
            integral += 2 * func(x)
        else:
            integral += 4 * func(x)
    return integral * h / 3

# Функция для оценки погрешности метода
def runge_rule(prev_integral, integral, order):
    return abs(integral - prev_integral) / (2 ** order - 1)

# Функция для вычисления интеграла с заданной точностью
def compute_integral_with_precision(func, a, b, precision, method):
    n = 4
    prev_integral = 0
    if method == "rectangle":
        choice = chose_rec_method()
    while True:
        if method == "rectangle":
            integral = rectangle_method(a, b, n, choice)
            order = 2
        elif method == "trapezoidal":
            integral = trapezoidal_method(a, b, n)
            order = 2
        elif method == "simpson":
            integral = simpson_method(a, b, n)
            order = 4
        if prev_integral != 0:
            error = runge_rule(prev_integral, integral, order)
            if error < precision:
                return integral, n
        prev_integral = integral
        n *= 2

# Основная программа
if __name__ == "__main__":
    # Ввод функции
    func = init_choice()

    # Ввод пределов интегрирования
    a = float(input("Введите нижний предел интегрирования: "))
    b = float(input("Введите верхний предел интегрирования: "))

    # Ввод требуемой точности
    precision = float(input("Введите требуемую точность: "))

    # Выбор метода
    print("Выберите метод интегрирования:")
    print("1. Метод прямоугольников")
    print("2. Метод трапеций")
    print("3. Метод Симпсона")
    method_choice = int(input("Введите номер метода: "))

    methods = {
        1: "rectangle",
        2: "trapezoidal",
        3: "simpson"
    }
    method = methods[method_choice]

    # Вычисление интеграла с заданной точностью
    integral, n = compute_integral_with_precision(func, a, b, precision, method)
    print(f"Приближенное значение интеграла: {integral}")
    print(f"Число разбиений интервала: {n}")
