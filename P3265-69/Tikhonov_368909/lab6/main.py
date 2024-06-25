from Euler import euler
from RungeKutt import runge_kutt
from Adams import adams
import matplotlib.pyplot as plt
from Equations import graph

y0, x0, h, lower, upper, e = 0, 0, 0, 0, 0, 0
equation = int(input(
    "Выберите задачу Коши:\n1. y' = y + (1 + x)y^2; (y(1) = -1; h = 0.1; интервал: [1; 1.5])\n2. y' = 3x^2y + "
    "x^2e^(x^3); (y(0) = 0; h = 0.1; интервал: [0; 1])\n3. y' = -ycos(x) + sin(x)cos(x); (y(0) = 1; h= 0.2; "
    "интервал: [0; 10])\n> "))
if equation < 1 or equation > 3:
    raise ValueError("Неизвестная задача.")
datamode = int(input("Выберите метод ввода данных:\n1. Вручную\n2. Из файла\n> "))
if datamode == 1:
    y0 = float(input("Введите y0: "))
    x0 = float(input("Введите x0: "))
    h = float(input("Введите h: "))
    lower = float(input("Введите нижнюю границу интервала: "))
    upper = float(input("Введите верхнюю границу интервала: "))
    e = float(input("Введите точность: "))
elif datamode == 2:
    if equation == 1:
        y0 = -1
        x0 = 1
        h = 0.1
        lower = 1
        upper = 1.5
        e = None
    elif equation == 2:
        y0 = 0
        x0 = 0
        h = 0.1
        lower = 0
        upper = 1
        e = None
    elif equation == 3:
        y0 = 1
        x0 = 0
        h = 0.2
        lower = 0
        upper = 10
        e = None
else:
    raise ValueError("Неизвестный режим ввода данных.")

solution = euler(equation, x0, y0, lower, upper, h, e)
print(f"Метод Эйлера:\nx: {solution[0]}\ny: {solution[1]}\nf(x,y): {solution[2]}")
farr = solution[2]
graph(solution[0], solution[1], equation)

solution = runge_kutt(equation, x0, y0, lower, upper, h, e)
print(f"Метод Рунге-Кутта:\nx: {solution[0]}\ny: {solution[1]}")
graph(solution[0], solution[1], equation)

if len(solution[1]) < 4:
    raise ValueError("Использование метода Адамса невозможно.")
else:
    solution = adams(equation, x0, lower, upper, solution[1][0:4], farr, h, e)
    print(f"Метод Адамса:\nx: {solution[0]}\ny: {solution[1]}")
    graph(solution[0], solution[1], equation)
