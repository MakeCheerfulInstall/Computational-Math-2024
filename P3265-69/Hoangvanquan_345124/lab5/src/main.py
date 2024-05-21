import Interpolation 
import matplotlib.pyplot as plt
import numpy as np
from math import sqrt, sin

FILE_IN1 = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\code\\codeC++\\python\\Math_Lab5\\input1"
FILE_IN2 = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\code\\codeC++\\python\\Math_Lab5\\input2"
FILE_IN3 = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\code\\codeC++\\python\\Math_Lab5\\input3"
def inputMode():
    print("Выберите способ ввода данных:")
    print("a) в виде набора данных (x,y)")
    print("b) в виде сформированных в файле данных")
    print("c) на основе выбранной функции")
    choice = input("Способ ввода: ").strip()
    while(choice != 'a' and choice != 'b' and choice != 'c'):
        choice = input("Введите 'a', 'b' или 'c' для выбора способа ввода: ").strip()
    return choice
def getDataInput():
    print('Введите x, y в горизонтальных строках (не менее 5 точек):')
    x = list(map(float, input('x: ').split()))
    y = list(map(float, input('y: ').split()))
    return x, y
def getDataFile():
    print("Выберите файл: input1, input2, input3")
    choice = input("Номер файла: ") 
    if(choice == '1'): FILE_IN = FILE_IN1
    elif(choice == '2'): FILE_IN = FILE_IN2
    else: FILE_IN = FILE_IN3
    with open(FILE_IN, 'r') as file:
        x = list(map(float, file.readline().split()))
        y = list(map(float, file.readline().split()))
    return x, y
def getFunction():
    FUNCTIONS = [
        lambda x: 2 * x**2 - 8 * x + 1,
        lambda x: (x**0.5) / 2,
        lambda x: sin(x)
    ]

    print("Выберите функцию")
    print("1) 2x² - 8x + 1")
    print("2) sqrt(x)/2")
    print("3) sin(x)")

    func_id = int(input('Номер функции: '))
    a, b = map(float, input('Границы отрезка через пробел: ').split())
    a, b = min(a, b), max(a, b)
    n = int(input('Количество узлов интерполяции: '))
    h = (b - a) / (n - 1)
    x = [a + i * h for i in range(n)]
    y = [FUNCTIONS[func_id - 1](x_val) for x_val in x]
    return x, y

def plot(x, y, plot_x, plot_y1, plot_y2, plot_y3, x0, answer1, answe2, answer3):
    plt.title("Лабораторная работа 5")
    OFFSET = 0.5
    plt.grid(True)
    plt.xlim((min(0, min(x)) - OFFSET, max(x) + OFFSET))
    plt.ylim((min(0, min(y)) - OFFSET, max(y) + OFFSET))
    ax = plt.gca()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.plot(1, 0, marker='>', ms=5, color='k', transform=ax.get_yaxis_transform(), clip_on=False)
    ax.plot(0, 1, marker='^', ms=5, color='k', transform=ax.get_xaxis_transform(), clip_on=False)

    plt.plot(x,y, color = 'blue')
    ax.plot(x, y, label = "Заданные значения")
    plt.plot(plot_x, plot_y1)
    ax.plot(plot_x, plot_y1, label = "Многочлен Лагранжа")
    plt.plot(plot_x, plot_y2)
    ax.plot(plot_x, plot_y2, label = "Многочлен Ньютона с разделенными разностями")
    plt.plot(plot_x, plot_y3)
    ax.plot(plot_x, plot_y3, label = "Многочлен Ньютона с конечными разностями")
    
    plt.plot([x0], [answer1], 'ro')
    plt.plot([x0], [answer2], 'ro')
    plt.plot([x0], [answer3], 'ro')

    plt.legend()
    plt.show()

if __name__ == '__main__':
    print("ЛАБОРАТОРНАЯ РАБОТА")
    print("--------------------------------------------")
    choice = inputMode()
    if choice == 'a': x, y = getDataInput()
    elif choice == 'b': x, y = getDataFile()
    elif choice == 'c': x, y = getFunction()
    Interpolation.FiniteDifferenceTable(x, y)
    value = float(input("Заданное значение аргумента: "))
    answer1 = Interpolation.Lagrange(x, y, value)
    answer2 = Interpolation.NewtonFiniteDifferences(x, y, value)
    answer3 = Interpolation.NewtonSeparatedDifferences(x, y, value)
    print("Приближенное значения функции по многочлену Лагранжа: " + str(answer1))
    print("Приближенное значения функции по многочлену Ньютона с разделенными разностями: " + str(answer2))
    print("Приближенное значения функции по многочлену Ньютона с конечными разностями: " + str(answer3))
    print("Приближенное значения функции по схеме Стирлинга: " + str(Interpolation.Stirling(x, y, value)))
    print("Приближенное значения функции по схеме Бесселя: " + str(Interpolation.Bessel(x, y, value)))

    plot_x = np.linspace(np.min(np.array(x)), np.max(np.array(x)), 100)
    plot_y1 = [Interpolation.Lagrange(x, y, value) for value in plot_x]
    plot_y2 = [Interpolation.NewtonFiniteDifferences(x, y, value) for value in plot_x]
    plot_y3 = [Interpolation.NewtonSeparatedDifferences(x, y, value) for value in plot_x]
    plot(x, y, plot_x, plot_y1, plot_y2, plot_y3, value, answer1, answer2, answer3)