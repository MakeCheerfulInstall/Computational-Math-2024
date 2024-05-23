import differentialMethod
import numpy as np
import math 
import matplotlib.pyplot as plt
from prettytable import PrettyTable

def getFunction(func):
    if func == '1':
        return lambda x, y: y + (1 + x) * (y ** 2), lambda x: -1 / x, -2, -0.5, 1/2
    elif func == '2':
        return lambda x, y: (x ** 2) - 2 * y, lambda x: 0.75 * math.exp(-2 * x) + 0.5 * (x ** 2) - 0.5 * x + 0.25, 0, 1, 1
    else: return None
def InputFunction():
    print("Выберите ОДУ")
    print(" 1) y' = y + (1 + x)y²    на [-2 ; -0.5]    при y(-2) = 1/2")
    print(" 2) y' = x² - 2y          на [0; 1]         при y(0) = 1")
    function = input("ОДУ: ")
    while(function != '1' and function != '2'):
        print("ОДУ нет в списке!")
        function = input("ОДУ: ")
    return function
def getdata_input():
    data = {}
    function = InputFunction()
    f, solution, a, b, y0 = getFunction(function)
    data['f'] = f
    data['solution'] = solution
    data['a'] = a
    data['b'] = b
    data['y0'] = y0

    h = float(input("Введите шаг точе h: "))
    while (h < 0):
        print("Шаг точек должен быть положительным числом")
        h = float(input("Шаг точек: "))
    data['h'] = h

    h = float(input("Введите точность: "))
    while (h < 0):
        print("Точность должен быть положительным числом")
        h = float(input("Точность: "))
    data['epsilon'] = h
    return data
def plot(x1, y1, x2, y2, x3, y3,_x, _y):
    plt.title("Лабораторная работа 6")
    OFFSET = 0.7
    plt.grid(True)
    plt.xlim((min(0, min(_x)) - OFFSET, max(_x) + OFFSET))
    plt.ylim((min(0, min(_y)) - OFFSET, max(_y) + OFFSET))
    ax = plt.gca()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.plot(1, 0, marker=">", ms=5, color='k', transform=ax.get_yaxis_transform(), clip_on=False)
    ax.plot(0, 1, marker="^", ms=5, color='k', transform=ax.get_xaxis_transform(), clip_on=False)

    plt.plot(x1, y1, label="Метод Эйлера")
    plt.plot(x2, y2, label="Метод Рунге-Кутта")
    plt.plot(x3, y3, label="Метод Милна")
    plt.plot(_x, _y, label="Точное решение")

    plt.legend()
    plt.show()
def creatTable(answer, method):
    Table = PrettyTable()
    Table.field_names = ['x', method, 'Точное решение']
    for i in range(0, len(answer)):
        Table.add_row([round(answer[i][0], 5), round(answer[i][1], 5), round(data['solution'](answer[i][0]), 5)]) 
    print(Table)

if __name__ == '__main__':
    print("ЛАБОРАТОРНАЯ РАБОТА")
    print("--------------------------------------------")
    data = getdata_input()
    answer1 = differentialMethod.EulerMethod(data['f'], data['a'], data['b'], data['y0'], data['h'], data['epsilon'])
    answer2 = differentialMethod.RungeKuttaMethod(data['f'], data['a'], data['b'], data['y0'], data['h'], data['epsilon'])
    answer3 = differentialMethod.MilnaMethod(data['f'], data['a'], data['b'], data['y0'], data['h'], data['epsilon'])
    # Отображать результаты
    print("Результаты вычисления.")
    creatTable(answer1, 'Метод Эйлера')
    creatTable(answer2, 'Метод Рунге-Кутта')
    creatTable(answer3, 'Метод Милна')

    # Рисовать графику
    x1 = np.array([dot[0] for dot in answer1])
    y1 = np.array([dot[1] for dot in answer1])
    x2 = np.array([dot[0] for dot in answer2])
    y2 = np.array([dot[1] for dot in answer2])
    x3 = np.array([dot[0] for dot in answer3])
    y3 = np.array([dot[1] for dot in answer3])
    _x = np.linspace(np.min(x1), np.max(x1), 100)
    _y = [data['solution'](i) for i in _x]
    plot(x1, y1, x2, y2, x3, y3, _x, _y)