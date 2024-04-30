import sympy
from sympy import diff
import numpy
import matplotlib.pyplot as plt

while True:
    try:
        print("Выберите систему уравнений (1 или 2): ")
        print("1. 5x^2-3y=4")
        print("   7x-y=1")
        print("2. x^3-3y=5")
        print("   2x+y=1")
        num = int(input())
    except ValueError:
        print("Пожалуйста, введите число")
        continue
    if num != 1 and num != 2:
        print('Пожалуйста, введите 1 или 2')
        continue
    else:
        break

print("Введите начальное приближение для х и у: ")
pr = input().split(" ")
if len(pr) != 2:
    print("Вы ввели не два числа")
    exit(0)
x_0 = pr[0]
y_0 = pr[1]

def fun(x, y, num):
    if num == 1:
        f1 = 5 * x ** 2 - 3 * y - 4
        f2 = 7 * x - y - 1
    else:
        f1 = x ** 3 - 3 * y - 5
        f2 = 2 * x + y - 1
    return [f1, f2]


def Newton(x_0, y_0, e):
    x_0 = float(x_0)
    y_0 = float(y_0)
    x = sympy.symbols('x')
    y = sympy.symbols('y')
    m = fun(x, y, num)
    f_x = diff(m[0], x).subs(x, x_0).subs(y, y_0)
    f_y = diff(m[0], y).subs(x, x_0).subs(y, y_0)
    g_x = diff(m[1], x).subs(x, x_0).subs(y, y_0)
    g_y = diff(m[1], y).subs(x, x_0).subs(y, y_0)
    j = f_x * g_y - f_y * g_x
    f = m[0].subs(x, x_0).subs(y, y_0)
    g = m[1].subs(x, x_0).subs(y, y_0)
    x = float(x_0 - (f * g_y - f_y * g) / j)
    y = float(y_0 - (f_x * g - f * g_x) / j)
    it = 1
    while abs(x - x_0) > e or abs(y - y_0) > e:
        x_0 = x
        y_0 = y
        x = sympy.symbols('x')
        y = sympy.symbols('y')
        f_x = diff(m[0], x).subs(x, x_0).subs(y, y_0)
        f_y = diff(m[0], y).subs(x, x_0).subs(y, y_0)
        g_x = diff(m[1], x).subs(x, x_0).subs(y, y_0)
        g_y = diff(m[1], y).subs(x, x_0).subs(y, y_0)
        j = f_x * g_y - f_y * g_x
        f = m[0].subs(x, x_0).subs(y, y_0)
        g = m[1].subs(x, x_0).subs(y, y_0)
        x = x_0 - float(f * g_y - f_y * g) / j
        y = y_0 - float(f_x * g - f * g_x) / j
        it += 1
    print("Метод Ньютона дал следующий результат: x=", x, "y=", y)
    print("Количество итераций равно ", it)
    print("Погрешность равна ", x - x_0)
    return 1


Newton(x_0, y_0, 0.01)

x = numpy.arange(-10, 10, 0.01)
y = numpy.arange(-10, 10, 0.01)
plt.xlabel('$x$')
plt.ylabel('$y$')
plt.grid(True)
plt.xlim(-5, 5)
plt.ylim(-10, 35)
if num == 1:
    plt.title('$5x^2-3y=4, 7x-y=1$')
    plt.plot(x, 5 * x ** 2 / 3 - 4 / 3, x, 7 * x - 1, [-0.047, 4.247], [-1.33, 28.73], 'ro')
else:
    plt.title('$x^3-3y=5, 2x+y=1$')
    plt.plot(x, x ** 3 / 3 - 5 / 3, x, 1 - 2 * x, 1.107, -1.214, 'ro')
plt.show()
