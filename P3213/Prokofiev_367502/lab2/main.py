from math import cos, sin
import matplotlib.pyplot as plt
import hord
import iter
import newt


roundconst = 5 # точность вывода
def roundto(x:float):
    return round(x, roundconst)


def exitstr():
    print("Неверный ввод")


def tryto(x, func):
    a = 0
    try:
        a = func(x)
    except Exception:
        a = None
    return a
    

def inputf(st:str, func):
    x = tryto(input(st), func)
    while x is None:
        exitstr()
        x = tryto(input(st), func)
    return x


def get_function(choice):
    if choice == "1":
        return (lambda x: 3 * (x ** 3) + 1.7 * (x ** 2) - 15.42 * x + 6.89,
                lambda x: 9 * (x ** 2) + 3.4 * x - 15.42,
                lambda x: (3 * (x ** 3) + 1.7 * (x ** 2) - 15.42 * x + 6.89) * -0.018 + x)
    elif choice == "2":
        return (lambda x: -1.8 * (x ** 3) - 2.94 * (x ** 2) + 10.37 * x + 5.38,
                lambda x: -5.4 * (x ** 2) - 5.88 * x + 10.37,
                lambda x: (-1.8 * (x ** 3) - 2.94 * (x ** 2) + 10.37 * x + 5.38) * 0.0383 + x)
    else:
        return (lambda x: (x ** 3) - 3.125 * (x ** 2) - 3.5 * x + 2.458,
                lambda x: 3 * (x ** 2) - 6.25 * x - 3.5,
                lambda x: ((x ** 3) - 3.125 * (x ** 2) - 3.5 * x + 2.458) * -0.0512 + x)


def get_system(choice):
    if choice == "1":
        return (lambda x, y: 2 * x - sin(y - 0.5) - 1,
                lambda x, y: y + cos(x) - 1.5,
                lambda x, y: (sin(y - 0.5) + 1) / 2,
                lambda x, y: -cos(x) + 1.5)
    elif choice == "2":
        return (lambda x, y: sin(x + y) - 1.5 * x + 0.1,
                lambda x, y: (x ** 2) + 2 * (y ** 2) - 1,
                lambda x, y: (sin(x + y) + 0.1) / 1.5,
                lambda x, y: (abs(1 - x ** 2)/2) ** 0.5)
    else:
        return (lambda x, y: cos(y - 2) + x,
                lambda x, y: sin(x + 0.5) - y - 1,
                lambda x, y: -cos(y - 2),
                lambda x, y: sin(x + 0.5) - 1)


def read_values():
    input_choice = input("Введи 0 если хочешь ввести с клавиатуры входные данные, и иное от 0 если хочешь из файла:")
    if input_choice == "0":
        a = inputf('Введи левую границу = ', float)
        b = inputf('Введи правую границу = ', float)
        eps = inputf('Введи погрешность = ', float)
        return a, b, eps
    else:
        file_name = "input.txt" #input("Введите имя файла: ")
        try:
            with open(file_name, 'r') as file:
                lines = file.readlines()
                if len(lines) >= 3:
                    try:
                        a = float(lines[0].strip())
                        b = float(lines[1].strip())
                        eps = float(lines[2].strip())
                        return a, b, eps
                    except Exception:
                        print("Файл содержит не числа")
                        exit(1)
                else:
                    print("Файл не содержит достаточно строк")
                    exit(1)
        except FileNotFoundError:
            print(f"Файл {file_name} не найден")
            exit(1)


def choice_method(f, df, phi, a, b, eps):
    print("На выбор 3 метода:")
    print("1. Метод хорд")
    print("2. Метод Ньютона")
    print("3. Метод простой итерации")
    type_choice = input("Выбери один из трех методов:")
    if type_choice == "1":
        return hord.hord_method(f, a, b, eps)
    elif type_choice == "2":
        return newt.newt_method(f, df, (a+b)/2, eps)
    else:
        return iter.iter_method(f, phi, (a+b)/2, eps)


def output(x, y, i, fxy = None):
    output_choice = input("Введи 0 если хочешь вывести на экран, и иное от 0 если хочешь в файл:")
    s = "" if fxy is None else f',f(x,y) = {roundto(fxy)}'
    s1 = f"x = {roundto(x)},{'f(x)' if fxy is None else 'y'} = {roundto(y)},i = {i}{s}\n"
    if output_choice == "0":
        print(s1, end = "")
    else:
        file_name = "output.txt" #input("Введите имя файла: ")
        try:
            with open(file_name, 'w') as file:
                file.write(s1)
        except FileNotFoundError:
            print("Файл не найден")


def run():
    type_choice = input("Введи 0 если хочешь систему уравнений, и иное от 0 если хочешь уравнение:")
    if type_choice == "0":
        print('На выбор дано 3 системы:')
        print('1. 2x - sin(y - 0.5) = 1')
        print('   y + cos(x) = 1.5\n')
        print('2. sin(x+y) = 1.5x - 0.1')
        print('   x^2 + 2y^2 = 1\n')
        print('3. cos(y - 2) + x = 0')
        print('   sin(x + 0.5) - y = 1\n')
        F, G, phi1, phi2 = get_system(input("Введи номер системы, которую надо решить: "))
        plt_system(F, G)
        a, b, eps = read_values()
        x, y, i = iter.iter_method_system(phi1, phi2, (a+b)/2, 0, eps)
        output(x, y, i, F(x, y))
    else:
        print('На выбор дано 3 уравнения:')
        print('1. 3x^3 + 1.7x^2 - 15.42x + 6.89\n')
        print('2. -1.8x^3 - 2.94x^2 + 10.37x + 5.38\n')
        print('3. x^3 - 3.125x^2 - 3.5x + 2.458\n')
        F, df, phi = get_function(input("Введи номер функции, которую надо решить: "))
        plt_function(F)
        a, b, eps = read_values()
        x, y, i = choice_method(F, df, phi, a, b, eps)
        output(x, y, i)


def plt_system(f, g):
    plt.gcf().canvas.manager.set_window_title("График функции")
    ax = plt.gca()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.plot(1, 0, marker=">", ms=5, color='k',
            transform=ax.get_yaxis_transform(), clip_on=False)
    ax.plot(0, 1, marker="^", ms=5, color='k',
            transform=ax.get_xaxis_transform(), clip_on=False)
    xRange = [i * 0.01 for i in range(-500, 501)]
    yRange = [i * 0.01 for i in range(-500, 501)]
    F = [[f(X, Y) for X in xRange] for Y in yRange]
    G = [[g(X, Y) for X in xRange] for Y in yRange]
    plt.contour(xRange, yRange, F, [0])
    plt.contour(xRange, yRange, G, [0])
    plt.show()


def plt_function(f):
    plt.gcf().canvas.manager.set_window_title("График функции")
    ax = plt.gca()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.plot(1, 0, marker=">", ms=5, color='k',
            transform=ax.get_yaxis_transform(), clip_on=False)
    ax.plot(0, 1, marker="^", ms=5, color='k',
            transform=ax.get_xaxis_transform(), clip_on=False)
    xRange = [i * 0.01 for i in range(-500, 501)]
    yRange = [f(X) for X in xRange]
    plt.plot(xRange, yRange)
    plt.show()


if __name__ == "__main__":
    try:
        run()
    except KeyboardInterrupt:
        exit(-1)