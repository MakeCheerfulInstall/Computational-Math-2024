import matplotlib.pyplot as plt
from math import sin, e


def bisection_method(func, a, b, eps):
    while abs(a - b) > eps:
        x = (a + b) / 2
        if func(a) * func(x) > 0:
            a = x
        else:
            b = x
    return (a + b) / 2
    
    
def secant_method(func, x0, eps):
    x1 = x0 - func(x0) / get_derivative_at_point(func, x0)
    while abs(x1 - x0) >  eps:
        x2 = x1 - (x1 - x0) * f(x1) / (f(x1) - f(x0))
        x0, x1 = x1, x2
    return x1


def simple_iteration_method(func, x0, a, b, eps):
    max_func_ = 0
    x = a
    while x < b:
        max_func_ = max(max_func_, abs(get_derivative_at_point(func, x)))
        x += eps
    if get_derivative_at_point(func, a) > 0:
        h = -1 / max_func_
    else:
        h = 1 / max_func_
    fi = lambda x: x + h * func(x)
    fi_ = lambda x: 1 + h * get_derivative_at_point(func, x)

    x = fi(x0)
    while abs(x - x0) > eps:
        x, x0 = fi(x), x
    return x


def verify(func, a, b, eps=0.00001):
    # true - if there is only one root, false - else
    if func(a) * func(b) < 0 and get_derivative_at_point(func, a) * get_derivative_at_point(func, b) > 0:
        x = a
        while x < b:
            if get_derivative_at_point(func, a) * get_derivative_at_point(func, x) <= 0:
                return False
            x += eps
        return True
    return False


def get_derivative_at_point(func, x0, dx=0.000001):
    return (func(x0 + dx) - func(x0)) / dx    


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
    plt.annotate('x', xy=(root, func(root)))
    plt.plot([a, b], [0, 0], 'b')
    plt.show()


print('variants of functions:')
print('1. x ** 3 + 2.84 * x ** 2 - 5.606 * x - 14.766')
print('2. x ** 3 - x + 4')
print('3. sin(x ** 2) + x + 2')
print('4. e ** sin(x) + x ** 7 - 3')
print('input the number of function (1 or 2 or 3 or 4): ')
case_number = input()
while case_number not in {'1', '2', '3', '4'}:
    print('input the number of function (1 or 2 or 3 or 4):')
    case_number = input()
    
case_number = int(case_number)
eps = 0.0001
if case_number == 1:
    f = lambda x: x ** 3 + 2.84 * x ** 2 - 5.606 * x - 14.766
    isolation_intervals = [(-3.2, -3), (-2.2, -2), (2.2, 2.4)]
    a, b = isolation_intervals[2]
elif case_number == 2:
    f = lambda x: x ** 3 - x + 4
    a, b = -2, -1
elif case_number == 3:
    f = lambda x: sin(x ** 2) + x + 2
    a, b = -2, -1.6
else:
    f = lambda x: e ** sin(x) + x ** 7 - 3
    a, b = 0.6, 1
fl = input('input "Y" if you want to set [a; b]: ')
if fl == 'Y':
    while 1:
        print('input a and b with endline:')
        try:
            a, b = float(input()), float(input())
        except Exception:
            continue
        break
x0 = (a + b) / 2    
print("Проверка:", verify(f, a, b))
if not verify(f, a, b):
    exit(0)
print("Метод деления пополам:", bisection_method(f, a, b, eps))
print("Метод секущих:", secant_method(f, x0, eps))
print("Метод простой итерации:", simple_iteration_method(f, x0, a, b, eps))
root = bisection_method(f, a, b, eps)
draw_plot(f, -4, 3, root, 0.001)
