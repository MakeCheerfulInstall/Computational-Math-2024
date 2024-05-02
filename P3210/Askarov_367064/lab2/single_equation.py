import matplotlib.pyplot as plt
from math import sin, e, cos


def bisection_method(func, a, b, eps):
    while abs(a - b) > eps:
        x = (a + b) / 2
        if func(x) < eps:
            return x
        elif func(a) * func(x) > 0:
            a = x
        else:
            b = x
    return (a + b) / 2


# def secant_method(func, x0, eps):
#     x1 = x0 - func(x0) / get_derivative_at_point(func, x0)
#     while abs(x1 - x0) > eps:
#         x2 = x1 - (x1 - x0) * f(x1) / (f(x1) - f(x0))
#         x0, x1 = x1, x2
#     return x1


# def secant_method_manual(func, x0, eps):
#     x1 = x0 + eps * 2
#     # x1 = x0 - func(x0) / get_derivative_at_point(func, x0)
#     x2 = x1 - (x1 - x0) * f(x1) / (f(x1) - f(x0))
#     print(f"x0={x0}, x1={x1}, x2={x2}, f(x2)={f(x2)}, |x2-x1|={abs(x2 - x1)}")
#
#     while abs(x2 - x1) > eps:
#         x0, x1 = x1, x2
#         x2 = x1 - (x1 - x0) * f(x1) / (f(x1) - f(x0))
#         print(f"x0={x0}, x1={x1}, x2={x2}, f(x2)={f(x2)}, |x2-x1|={abs(x2-x1)}")
#     return x2


def newton_method(f, a, b, eps):
    if f(a) * second_derivative(f, a) > 0:
        x0 = a
    else:
        x0 = b
    # print(f"f(a)={f(a)}, f_2(a)={f_2(a)}, f(a)*f\"(a)={f(a) * f_2(a)}")
    # print(f"f(b)={f(b)}, f_2(b)={f_2(b)},  f(b)*f\"(b)={f(b) * f_2(b)}")

    x1 = x0 - f(x0) / derivative(f, x0)
    # print(f"xk={x0}, f(xk)={f(x0)}, f'(xk)={f_1(x0)}, xk+1={x1}, |xk+1-xk|={abs(x1 - x0)}")
    while abs(x1 - x0) > eps:
        x0 = x1
        x1 = x0 - f(x0) / derivative(f, x0)
        # print(f"xk={x0}, f(xk)={f(x0)}, f'(xk)={f_1(x0)}, xk+1={x1}, |xk+1-xk|={abs(x1 - x0)}")
    return x1


def simple_iteration_method(func, a, b, eps):
    max_func_ = 0
    x = a
    while x < b:
        max_func_ = max(max_func_, abs(derivative(func, x)))
        x += eps
    if derivative(func, a) > 0:
        h = -1 / max_func_
    else:
        h = 1 / max_func_
    fi = lambda x: x + h * func(x)

    x0 = a
    x1 = fi(x0)
    while abs(x1 - x0) > eps:
        x1, x0 = fi(x1), x1
    return x1
# def simple_iteration_method_2(func, x0, a, b, eps):
#     max_func_ = 0
#     x = a
#     while x < b:
#         max_func_ = max(max_func_, abs(derivative(func, x)))
#         x += eps
#     if derivative(func, a) > 0:
#         h = -1 / max_func_
#     else:
#         h = 1 / max_func_
#     fi = lambda x: x + h * func(x)
#     fi_ = lambda x: 1 + h * derivative(func, x)
#
#     x = fi(x0)
#     while abs(x - x0) > eps:
#         x, x0 = fi(x), x
#     return x


# def simple_iteration_method_manual(f, x0, eps):
#     x1 = (2.74 * x0 ** 3 - 1.93 * x0 ** 2 - 3.72) / 15.28
#     print(f"x0={x0}, x1={x1}, f(x1)={f(x1)}, |x0-x1|={abs(x0 - x1)}")
#     while abs(x1 - x0) > eps:
#         x0 = x1
#         x1 = (2.74 * x0 ** 3 - 1.93 * x0 ** 2 - 3.72) / 15.28
#         print(f"x0={x0}, x1={x1}, f(x1)={f(x1)}, |x0-x1|={abs(x0 - x1)}")
#     return x1


def verify(func, a, b, eps=0.00001):
    # true - if there is only one root, false - else
    if func(a) * func(b) < 0 and derivative(func, a) * derivative(func, b) > 0:
        x = a
        while x < b:
            if derivative(func, a) * derivative(func, x) <= 0:
                return False
            x += eps
        return True
    return False


def derivative(func, x0, dx=0.000001):
    return (func(x0 + dx) - func(x0)) / dx


def second_derivative(func, x0, dx=0.000001):
    return ((func(x0 + 2 * dx) - func(x0 + dx)) / dx - (func(x0 + dx) - func(x0)) / dx) / dx


def draw_plot(func, a, b, root, eps):
    xs = []
    ys = []
    x = a
    while x < b:
        xs.append(x)
        ys.append(func(x))
        x += eps
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.plot(xs, ys, 'g')
    #  plt.plot([root], [func(root)], 'r')
    plt.annotate('*', xy=(root, func(root)))
    plt.plot([a, b], [0, 0], 'b')
    plt.show()


# f = lambda x: 2.74 * x ** 3 - 1.93 * x ** 2 - 15.28 * x - 3.72
# f_1 = lambda x: 2.74 * 3 * x ** 2 - 1.93 * 2 * x - 15.28
# f_2 = lambda x: 2.74 * 3 * 2 * x - 1.93 * 2
# print(secant_method_manual(f, -2, 10**-2))
print('Выберите функцию:')
print('1. f(x) = 2.74 * x ** 3 - 1.93 * x ** 2 - 15.28 * x - 3.72')
print('2. f(x) = cos(x)')
print('3. f(x) = x ** 2 - 1')
case_number = input('Введите номер функции: ')
while case_number not in {'1', '2', '3'}:
    case_number = input('Введите номер функции: ')

case_number = int(case_number)
eps = 0.0001
if case_number == 1:
    f = lambda x: 2.74 * x ** 3 - 1.93 * x ** 2 - 15.28 * x - 3.72
    f_1 = lambda x: 2.74 * 3 * x ** 2 - 1.93 * 2 * x - 15.28
    f_2 = lambda x: 2.74 * 3 * 2 * x - 1.93 * 2
    isolation_intervals = [(-2, -1.8), (-0.4, -0.2), (2.8, 3)]
    a, b = isolation_intervals[2]
elif case_number == 2:
    f = lambda x: cos(x)
    a, b = -2, -1
elif case_number == 3:
    f = lambda x: x ** 2 - 1
    a, b = -2, 0
fl = input('Напишите "да", если хотите задать [a; b]: ')
if fl == 'да':
    while True:
        try:
            a, b = map(float, input('Введите a и b через пробел:').split())
            # if not verify(f, a, b):
            #     print('Функция на данном отрезке не имеет корней или имеет множество корней')
            #     continue
        except Exception:
            print('Неверный ввод. Повторите')
            continue
        break
print(a, b)
x0 = (a + b) / 2
print("Метод деления пополам:", (root := bisection_method(f, a, b, eps)))
print("Метод Ньютона:", newton_method(f, a, b, eps))
print("Метод простой итерации:", simple_iteration_method(f, a, b, eps))
draw_plot(f, a - 2, b + 2, root, 0.0001)
