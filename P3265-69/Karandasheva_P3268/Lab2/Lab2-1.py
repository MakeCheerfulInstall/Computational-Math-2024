import random
import math
from sympy import diff, symbols, cos, sin
import matplotlib.pyplot as plt
import numpy as np

def draw_plot(f):
   fig, ax = plt.subplots()
   ax.set_title('График функции')
   # Название оси X:
   ax.set_xlabel('x')
   # Название оси Y:
   ax.set_ylabel('y')
   # Начало и конец изменения значения X, разбитое на 100 точек
   x = np.linspace(-10, 10, 100) # X от 0 до 5
   y = f(x)
   ax.plot(x, y)
   plt.show()

def hord_method(f, a, b, accuracy):
    solution = 0
    value = 0
    iteration = 0
    x = a - ((b - a) * f(a)) / (f(b) - f(a))
    if f(a) * f(x) < 0:
        b = x
    if f(x) * f(b) < 0:
        a = x

    while True:
        x1 = a - ((b - a) * f(a)) / (f(b) - f(a))
        iteration +=1
        if abs(x1 - x) <= accuracy or abs(a - b) <= accuracy or abs(f(x1)) <= accuracy:
            solution = x1
            value = f(x1)
            break
        x = x1
        if f(a) * f(x) < 0:
            b = x
        if f(x) * f(b) < 0:
            a = x
    return solution, value, iteration

def secan_method(f, a, b, accuracy):
    if f(a) * diff(diff(f(a))) > 0:
      x0 = a
    else:
      x0 = b
    num_iterations = 0
    while abs(f(x0) - f(a)) >= accuracy:
        x1 = x0 - (f(x0) * (x0 - a)) / (f(x0) - f(a))
        a = x0
        x0 = x1
        num_iterations += 1
    root = x0
    value_at_root = f(x0)
    return root, value_at_root, num_iterations

def simple_iteration_method(f, a, b, accuracy):
    x0 = a

    x = symbols('x')
    q = -1 / max(abs(diff(f(x), x).subs(x, i)) for i in np.linspace(a, b, 1000))
    phi = x + q * f(x)
    phiprime = diff(phi, x)

    if abs(phiprime.subs(x, x0)) >= 1:
        print("Условие сходимости не выполняется. Нельзя применить метод.")
        return None, None, None

    iterations = 0
    while True:
        xnew = phi.subs(x, x0)
        if abs(xnew - x0) < accuracy:
            break
        x0 = xnew
        iterations += 1

    root = xnew
    valueatroot = f(x).subs(x, root)
    return root, valueatroot, iterations

def f1(x):
    return x**3 - x + 4

def f2(x):
    return math.cos(x) + 0.5

def f3(x):
    return 2 * x**3 + 3.41*x**2 - 23.74*x + 2.95

functions = {1: f1, 2: f2, 3: f3}

def main():
    filename = "file.txt"

    f = 0
    method = 0
    a = 0
    b = 0
    accuracy = 0

    ask_input = input(
        "Введите f, чтобы взять исходные данные из файла \"" + filename + "\" или k, чтобы ввести с клавиатуры\n")
    if ask_input == "k":
        print("Выберите функцию из списка: \n 1) x^3 - x + 4 \n 2) cosx + 0.5 \n 3) 2x^3 + 3.41x^2 - 23.74x + 2.95")
        f = int(input())

        print("Выберите метод решения:\n 1) Метод хорд \n 2) Метод секущих \n 3) Метод простой итерации ")
        method = int(input())

        print("Введите границы интервала:")
        a = float(input())
        b = float(input())

        print("Введите точность:")
        accuracy = float(input())

    elif ask_input == "f":
        from google.colab import files

        # создаем объект этого класса, применяем метод .upload()
        uploaded = files.upload()
        file = open("3.txt", "r")

        f = int(file.readline())
        method = int(file.readline())
        a = float(file.readline())
        b = float(file.readline())
        accuracy = float(file.readline())

        file.close()

    else:
        raise ValueError("Введено неверное значение")

    if (f<1)or(f>3):
          raise ValueError("Номер функции введён неверно")
    if a > b:
          raise ValueError("Границы интервала выбраны неверно")

    if (method == 1):
          solution = hord_method(functions[f], a, b, accuracy)
    elif (method == 2):
          solution = secan_method(functions[f], a, b, accuracy)
    elif (method == 3):
          solution = simple_iteration_method(functions[f], a, b, accuracy)
    else:
          raise ValueError("Введён неверный номер метода.")

    print("Корень уравнения: ", solution[0])
    print("Значение функции в точке: ", solution[1])
    print("Количество итераций: ", solution[2])
    draw_plot(functions[f])

try:
    main()
except ValueError as e:
    print("Ошибка: ", e)
except KeyboardInterrupt as e:
    print(e)
except ZeroDivisionError as e:
    print("Вычисление корня невозможно")
