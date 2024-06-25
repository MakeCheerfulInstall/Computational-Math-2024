import eq_methods
import math
import matplotlib.pyplot as plt


def choice_interval(choice):
    if choice == 1:
        f = lambda x: x ** 3 + 2.84 * x ** 2 - 5.606 * x - 14.766
        print("Выберите понравившийся интервал(напишите одну из цифр 1, 2 или 3)")
        print("(-3.2, -3) - 1, (-2.2, -2) - 2, (2.2, 2.4) - 3")
        interval = input()
        while interval not in {'1','2','3'}:
            print("Выберите понравившийся интервал(напишите одну из цифр 1, 2 или 3)")
            interval = input()
        if interval == '1':
            a, b = -3.2, -3
            return f, a, b
        elif interval == '2':
            a, b = -2.2, -2
            return f, a, b
        elif interval == '3':
            a, b = 2.2, 2.4
            return f, a, b
        
    elif choice == 2:
        f = lambda x: x ** 3 - x + 4
        a, b = -2, -1
        return f, a, b

    elif choice == 3:
        f = lambda x: math.sin(x ** 2) + x + 2
        a, b = -2, -1.6
        return f, a, b


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
    plt.scatter([root], [func(root)], color='red')  
    plt.plot([a, b], [0, 0], 'b')
    plt.show()


def verify(f, a, b):
    return f(a) * f(b) < 0

def proiz_f(x):
    if choice == 1:
        return 3*(x**2) + 5.68*x - 5.606
    elif choice == 2:
        return 3*(x**2) - 1
    elif choice == 3:
        return 2*x*(math.cos(x**2)) + 1

print('Выберите понравившуюся функцию(в ответ напишите ее номер):')
print('1. x ** 3 + 2.84 * x ** 2 - 5.606 * x - 14.766')
print('2. x ** 3 - x + 4')
print('3. sin(x ** 2) + x + 2')



choice = input()
while choice not in ['1', '2', '3']:
    print('Введите номер понравившейся функции:')
    choice = input()


choice = int(choice)
function, a, b = choice_interval(choice)

x0 = (a+b)/2
eps =0.0001
print("Метод хорд:", eq_methods.horde_method(function, a, b))
root = eq_methods.horde_method(function, a, b)
draw_plot(function, a, b, root, eps)

print("Метод Ньютона:", eq_methods.newton_method(function, proiz_f, x0))


print("Метод простой итерации:", eq_methods.simple_iteration_method(function, x0, a, b , eps))



