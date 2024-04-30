import matplotlib.pyplot as plt
from math import cos, sin


def simple_iteration_method_manual(func1, func2, x0, y0, eps):
    x1 = func1(x0, y0)
    y1 = func2(x0, y0)
    print(f"x0={x0}, y0={y0}")
    print(f"x1=f1(x0, y0)=1-0.5cos({y0})={x1}, |x1-x0|={abs(x1 - x0)}")
    print(f"y1=f2(x0, y0)=sin({x0}+1)-1.2={y1}, |y1-y0|={abs(y1 - y0)}")
    print("=============")
    while abs(x1 - x0) > eps or abs(y1 - y0) > eps:
        x1, x0 = func1(x1, y1), x1
        y1, y0 = func2(x1, y1), y1
        print(f"x1=f1(x0, y0)=1-0.5cos({y0})={x1}, |x1-x0|={abs(x1 - x0)}")
        print(f"y1=f2(x0, y0)=sin({x0}+1)-1.2={y1}, |y1-y0|={abs(y1 - y0)}")
        print("=============")
    return x1, y1


# def check_convergence(func1, func2, x1, x2, y1, y2, dx=0.00001):
#     i = 0
#     while True:
#         break
#         dfdx1 = get_derivative_of_x_at_point(func1, x1 + dx * i, y1 + dx * i, dx)
#         dfdy1 = get_derivative_of_x_at_point(func1, x1 + dx * i, y1 + dx * i, dx)
#
#         if abs(dfdy1) + abs(dfdx1) > 1:
#             return False
#
#         dfdx2 = get_derivative_of_x_at_point(func2, x1 + dx * i, y1 + dx * i, dx)
#         dfdy2 = get_derivative_of_x_at_point(func2, x1 + dx * i, y1 + dx * i, dx)
#
#         if abs(dfdy2) + abs(dfdx2) > 1:
#             return False
#
#         if x1 >= x2 or y1 >= y2:
#             break
#         i += 1
#     return True


def simple_iteration_method(func1, func2, x01, x02, a1, b1, a2, b2, eps):
    x1 = func1(x01)
    x2 = func2(x02)
    i = 0
    print(f"x0={x01}, y0={x02}")
    print(f"x1=f1(x0, y0)=1-0.5cos({x02})={x1}, |x1-x0|={abs(x1 - x01)}")
    print(f"y1=f2(x0, y0)=sin({x01}+1)-1.2={x2}, |y1-y0|={abs(x2 - x02)}")
    while abs(x1 - x01) > eps or abs(x2 - x02) > eps:
        i += 1
        x2, x02 = func1(x1), x2
        x1, x01 = func2(x2), x1
        print(f"x{i}={x01}, y{i}={x01}")
        print(f"x{i + 1}=f1(x{i}, y{i})=1-0.5cos({x02})={x1}, |x{i + 1}-x{i}|={abs(x1 - x01)}")
        print(f"y{i + 1}=f2(x{i}, y{i})=sin({x01}+1)-1.2={x2}, |y{i + 1}-y{i}|={abs(x2 - x02)}")
    return x1, x2


def get_derivative_of_x_at_point(func, x0, y0, dx=0.001):
    return (func(x0 + dx, y0) - func(x0, y0)) / dx


def get_derivative_of_y_at_point(func, x0, y0, dy=0.001):
    return (func(x0, y0 + dy) - func(x0, y0)) / dy


def draw_plots(func1, func2, a, b, root, eps):
    xs1, xs2 = [], []
    ys1, ys2 = [], []
    x = a
    while x < b:
        xs1.append(x)
        ys1.append(func1(x))

        xs2.append(func2(x))
        ys2.append(x)
        x += eps

    plt.xlabel("X")
    plt.ylabel("Y")
    plt.annotate('x', xy=(root, func1(root)))
    plt.plot(xs1, ys1, 'r')
    plt.plot(xs2, ys2, 'g')
    plt.plot([a, b], [0, 0], 'b')

    plt.show()


# simple_iteration_method_manual(lambda x, y: 1 - 0.5 * cos(y), lambda x, y: sin(x + 1) - 1.2, 0.2, 0, 10 ** -2)
print('Варианты систем:')
print('1. sin(x+1) - y = 1.2 and 2x + cos(y) = 2')
print('2. sin(x) + 2y = 2 and x + cos(y-1) = 0.7')
case_number = input('Введите номер системы: ')
while case_number not in {'1', '2'}:
    case_number = input('Введите номер системы: ')

case_number = int(case_number)
if case_number == 1:
    f1 = lambda x: sin(x + 1) - 1.2
    f2 = lambda y: 1 - 0.5 * cos(y)
    a1, b1 = 0.2, 0.6
    a2, b2 = -0.4, 0
else:
    f1 = lambda x: 1 - 0.5 * sin(x)  # x(y)
    f2 = lambda y: 0.7 - cos(y - 1)  # y(x)
    a1, b1 = -0.2, 0
    a2, b2 = 0.4, 0.6

x01 = (a1 + b1) / 2
x02 = (a2 + b2) / 2
while True:
    try:
        s = input("Введите нач приближения через пробел: ")
        if s.strip():
            x01, x02 = map(float, s.split())
            break
        else:
            break
    except Exception:
        print("Неверный ввод")
        continue

if not a1 <= x01 <= b1:
    x01 = a1
if not a2 <= x02 <= b2:
    x02 = a2

print(x01, x02)
eps = 0.00000001
ans = simple_iteration_method(f1, f2, x01, x02, a1, b1, a2, b2, eps)
print(ans)
root = ans[0]
draw_plots(f1, f2, -10, 10, root, 0.01)

