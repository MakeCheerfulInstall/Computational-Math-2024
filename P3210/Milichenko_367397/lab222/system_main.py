import math
import matplotlib.pyplot as plt


def simple_iteration_method(func1, func2, x01, x02, a1, b1, a2, b2, eps):
    x1 = func1(x01)
    x2 = func2(x02)
    while abs(x1 - x01) > eps or abs(x2 - x02) > eps:
        x2, x02 = func1(x1), x2
        x1, x01 = func2(x2), x1
    return x1, x2


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
    plt.scatter([root], [func1(root)], color='red') 
    plt.plot(xs1, ys1, 'r')
    plt.plot(xs2, ys2, 'g')
    plt.plot([a, b], [0, 0], 'b')
    
    plt.show()


def choice_system(choice):
    if choice == 1:
        return lambda x: math.sin(x + 1), lambda y: 1 - math.cos(y) / 2, 0.6, 0.8, 0.8, 1
    
    elif choice == 2:
        return lambda x: -math.cos(x - 1) + 0.5, lambda y: math.cos(y) + 3, 3.2, 3.4, 1.1, 1.3
    
    
    
    
print('Выберите понравившуюся систему(введите ниже ее номер):')
print('1. sin(x + 1) - y == 0 и 2x + cosy = 2')
print('2. cos (x – 1) + y == 0.5 и x – cos (y) == 3')



choice = input()
while choice not in {'1', '2'}:
    print('Введите номер понравившейся системы')
    choice = input()
    
choice = int(choice)
f1, f2, a1, b1, a2, b2 = choice_system(choice)


x01 = (a1 + b1) / 2
x02 = (a2 + b2) / 2

eps = 0.000001

print(simple_iteration_method(f1, f2, x01, x02, a1, b1, a2, b2, eps))
root = simple_iteration_method(f1, f2, x01, x02, a1, b1, a2, b2, eps)[0]
draw_plots(f1, f2, -2, 4, root, 0.1)