import math

def evaluate_function(func, x):
    return func(x)

def left_rect_rule(func, a, b, n):
    h = (b - a) / n
    integral = 0
    for i in range(n):
        x_i = a + i * h
        integral += evaluate_function(func, x_i)
    integral *= h
    return integral

def right_rect_rule(func, a, b, n):
    h = (b - a) / n
    integral = 0
    for i in range(1, n + 1):
        x_i = a + i * h
        integral += evaluate_function(func, x_i)
    integral *= h
    return integral


def midpoint_rule(func, a, b, n):
    h = (b - a) / n
    integral = 0
    for i in range(n):
        x_i = a + (i + 0.5) * h
        integral += evaluate_function(func, x_i)
    integral *= h
    return integral

def trapezoidal_rule(func, a, b, n):
    h = (b - a) / n
    integral = 0.5 * (evaluate_function(func, a) + evaluate_function(func, b))
    for i in range(1, n):
        x_i = a + i * h
        integral += evaluate_function(func, x_i)
    integral *= h
    return integral

def simpsons_rule(func, a, b, n):
    h = (b - a) / n
    sum_odd = sum_even = 0
    for i in range(1, n):
        x_i = a + i * h
        if i % 2 == 0:
            sum_even += evaluate_function(func, x_i)
        else:
            sum_odd += evaluate_function(func, x_i)
    integral = h / 3 * (evaluate_function(func, a) + evaluate_function(func, b) + 4 * sum_odd + 2 * sum_even)
    return integral

def runge_rule(old_integral, new_integral, order):
    if new_integral == old_integral:
        return 0
    return abs((new_integral - old_integral) / (2 ** order - 1))

def print_results(method, integral, n, tolerance):
    print("Метод:", method)
    print("Значение интеграла:", integral)
    print("Число разбиений интервала:", n)
    print("Точность:", tolerance)

def function_1(x):
    return math.sin(x)

def function_2(x):
    return x ** 2

def function_3(x):
    return math.exp(x)

# Дополнительные функции для выбора пользователя
def function_4(x):
    return x ** 3 + 2 * x ** 2 - 3 * x - 12

# Ввод пользовательских данных
print("Выберите функцию для интегрирования:")
print("1. sin(x)")
print("2. x^2")
print("3. e^x")
print("4. x^3 + 2x^2 - 3x - 12")
choice = int(input())

if choice == 1:
    func = function_1
elif choice == 2:
    func = function_2
elif choice == 3:
    func = function_3
elif choice == 4:
    func = function_4
else:
    print("Неправильный выбор.")
    exit()

# Выбор метода вычисления интеграла
method = input("Выберите метод (прямоугольники, трапеции, симпсон): ")

# Ввод границ интегрирования
a = float(input("Введите нижний предел интегрирования: "))
b = float(input("Введите верхний предел интегрирования: "))
tolerance = float(input("Введите требуемую точность: "))

# Выбор модификации метода прямоугольников
if method == "п":
    print("Выберите модификацию метода прямоугольников:")
    print("1. Левые прямоугольники")
    print("2. Правые прямоугольники")
    print("3. Средние прямоугольники")
    rect_method = int(input())
    if rect_method == 1:
        integrate_func = left_rect_rule
    elif rect_method == 2:
        integrate_func = right_rect_rule
    elif rect_method == 3:
        integrate_func = midpoint_rule
    else:
        print("Неправильный выбор.")
        exit()
elif method == "т":
    integrate_func = trapezoidal_rule
elif method == "с":
    integrate_func = simpsons_rule
else:
    print("Неправильно выбран метод.")
    exit()

# Вычисление интеграла с заданной точностью
n = 4  # начальное значение числа разбиений
old_integral = 0
order = 2  # порядок метода для оценки погрешности
while True:
    integral = integrate_func(func, a, b, n)
    error = runge_rule(old_integral, integral, order)
    if error < tolerance:
        print_results(method, integral, n, tolerance)
        break
    old_integral = integral
    n *= 2  # удваиваем число разбиений для следующей итерации
