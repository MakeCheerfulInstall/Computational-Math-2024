import matplotlib.pyplot as plt
from math import cos, sin


def simple_iteration_method(func1, func2, x01, x02, a1, b1, a2, b2, eps):
    x1 = func1(x01)
    x2 = func2(x02)
    while abs(x1 - x01) > eps or abs(x2 - x02) > eps:
        x2, x02 = func1(x1), x2
        x1, x01 = func2(x2), x1
    return x1, x2


def get_derivative_at_point(func, x0, dx=0.000001):
    return (func(x0 + dx) - func(x0)) / dx


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


print('variants of systems:')
print('1. sin(x + 1) - y == 0 and 2x + cosy = 2')
print('2. cos (x – 1) + y == 0.5 and x – cos (y) == 3')
print('input the number of system (1 or 2):')
case_number = input()
while case_number not in {'1', '2'}:
    print('input the number of function (1 or 2):')
    case_number = input()
    
case_number = int(case_number)
if case_number == 1:
    f1 = lambda x: sin(x + 1)
    f2 = lambda y: 1 - cos(y) / 2
    a1, b1 = 0.6, 0.8
    a2, b2 = 0.8, 1
else:
    f1 = lambda x: -cos(x - 1) + 0.5    # y(x)
    f2 = lambda y: cos(y) + 3           # x(y)
    a1, b1 = 3.2, 3.4
    a2, b2 = 1.1, 1.3

x01 = (a1 + b1) / 2
x02 = (a2 + b2) / 2
x01 = 0.72
x02 = 0.98
eps = 0.00000001
print(simple_iteration_method(f1, f2, x01, x02, a1, b1, a2, b2, eps))
root = simple_iteration_method(f1, f2, x01, x02, a1, b1, a2, b2, eps)[0]
draw_plots(f1, f2, -2, 4, root, 0.1)

