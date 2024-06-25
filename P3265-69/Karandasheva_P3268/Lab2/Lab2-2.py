import numpy as np
from scipy.optimize import fsolve
import matplotlib.pyplot as plt

def draw_plot(f, yf, vars):
   fig, ax = plt.subplots()
   ax.set_title('График функции')
   ax.set_xlabel('x')
   ax.set_ylabel('y')
   x = np.linspace(-10, 10, 100)
   y1, y2 = yf(x)
   ax.plot(x, y1, label='Function 1')
   ax.plot(x, y2, label='Function 2')
   ax.legend()
   plt.show()

def f1(vars):
    x, y = vars
    return np.sin(x) + 2*y - 2, x + np.cos(y-1) - 0.7

def yf1(x):
    return 1 - 1/2 * np.sin(x), np.arccos(0.7 - x)

def f2(vars):
    return 0, 0

def yf2(x):
    return 0, 0

def newton_method(f, initial_guess, tolerance):
    vars = initial_guess
    errors = []
    num_iterations = 0

    while True:
        num_iterations += 1
        x, y = fsolve(f, vars)
        errors.append(np.max(np.abs(np.array([x, y]) - np.array(vars))))
        if np.max(np.abs(np.array([x, y]) - np.array(vars))) < tolerance:
            return (x, y), num_iterations, errors
        vars = (x, y)

functions = {1: f1, 2: f2}
yfs = {1: yf1, 2: yf2}

def main():
    filename = "file.txt"

    f = 0
    x = 0
    y = 0
    accuracy = 0

    ask_input = input(
        "Введите f, чтобы взять исходные данные из файла \"" + filename + "\" или k, чтобы ввести с клавиатуры\n")
    if ask_input == "k":
        print("Выберите систему нелинейных уравнений из списка: \n 1){sin(x) + 2y = 2, x + cos(y-1) = 0.7) \n 2) ... \n ")
        f = int(input())

        print("Введите начальные приближения:")
        x = float(input())
        y = float(input())

        print("Введите точность:")
        accuracy = float(input())

    elif ask_input == "f":
        from google.colab import files

        uploaded = files.upload()
        file = open("file.txt", "r")

        f = int(file.readline())
        x = float(file.readline())
        y = float(file.readline())
        accuracy = float(file.readline())

        file.close()

    else:
        raise ValueError("Введено неверное значение")

    if f not in functions:
        raise ValueError("Номер системы нелинейных уравнений введён неверно")

    initial_guess = (x, y)
    solution, iterations, errors = newton_method(functions[f], initial_guess, accuracy)
    print("Решение:", solution)
    print("Количество итераций:", iterations)
    print("Вектор погрешностей:", errors)

    draw_plot(functions[f], yfs[f], initial_guess)

try:
    main()
#except ValueError as e:
    #print("Ошибка: ", e)
#except KeyboardInterrupt as e:
    #print(e)
except ZeroDivisionError as e:
    print("Вычисление корня невозможно")
