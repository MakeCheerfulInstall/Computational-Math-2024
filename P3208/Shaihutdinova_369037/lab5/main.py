import copy

import numpy as np
from prettytable import PrettyTable
import matplotlib.pyplot as plt


def getPolynomialNewton(xm, xa, ym, table, n):
    h = (xm[1] - xm[0])
    fact = 1
    xp = xa - xm[0]
    sum = ym[0] + (table[0][1]/h)*xp
    for i in range(n - 2):
        xp *= (xa-xm[i+1])
        fact *= (i + 2)
        h*=(xm[1] - xm[0])
        sum += ((xp / (fact*h)) * table[i + 1][1])
    return sum


def f(xm, ym, x):
    if len(x) == 2:
        return (ym[x[1]] - ym[x[0]]) / (xm[x[1]] - xm[x[0]])
    else:
        x1 = copy.deepcopy(x)
        x2 = copy.deepcopy(x)
        x1.pop(-1)
        x2.pop(0)
        fx1 = f(xm, ym, x1)
        fx2 = f(xm, ym, x2)
        return (fx2 - fx1) / (xm[x[-1]] - xm[x[0]])


def newtonMethod1(ym, n, xa, xm):
    sum = ym[0]
    xi = []
    xi.append(0)
    xp = 1
    for i in range(n - 1):
        xi.append(i + 1)
        xp *= (xa - xm[i])
        fx = f(xm, ym, xi)
        sum += (fx * xp)
    return sum


def newtonMethod2(xm, xa, ym, table, n):
    h = (xm[1] - xm[0])
    if xa <= (xm[-1] - xm[0]) / 2:
        t = (xa - xm[0]) / h
        fact = 1
        sum = ym[0] + t * table[0][1]
        tp = t
        for i in range(n - 2):
            tp *= (t - i - 1)
            fact *= (i + 2)
            sum += ((tp / fact) * table[i + 1][1])
    else:
        t = (xa - xm[-1]) / h
        fact = 1
        sum = ym[n - 1] + t * table[0][n - 1]
        tp = t
        for i in range(n - 2):
            tp *= (t + i + 1)
            fact *= (i + 2)
            sum += ((tp / fact) * table[i + 1][n - 2 - i])
    return sum


def getTable(n, y):
    table = []
    checker = True
    it = n
    counter = 1
    while checker:
        a = []
        last = []
        s = "d^" + str(counter) + "y"
        a.append(s)
        for i in range(it - 1):
            a.append(y[i + 1] - y[i])
            last.append(y[i + 1] - y[i])
        table.append(a)
        for i in range(len(table[-1]), n + 1):
            table[-1].append("-")
        y = last
        if it == 2:
            checker = False
        it -= 1
        counter += 1
    return table


def genFunc(userchoice):
    answer = []
    print("Таблица конечных разностей:")
    n = userchoice[2]
    y = userchoice[3]
    table = getTable(n, y)
    x = PrettyTable()
    fields = []
    fields.append("i")
    x_row = []
    y_row = []
    x_row.append("x")
    y_row.append("y")
    for i in range(n):
        fields.append(i)
        x_row.append(userchoice[1][i])
        y_row.append(userchoice[3][i])
    x.field_names = fields
    x.add_row(x_row)
    x.add_row(y_row)
    for i in range(len(table)):
        x.add_row(table[i])
    x.border = True
    x.header = True
    x.padding_width = 1
    print(x)

    print("Метод Лагранжа:")
    xm = userchoice[1]
    ym = userchoice[3]
    xa = userchoice[4]
    sum = 0
    for i in range(n):
        s1 = 1
        s2 = 1
        for j in range(n):
            if (i != j):
                s1 *= (xa - xm[j])
                s2 *= (xm[i] - xm[j])
        sum += (ym[i] * s1 / s2)
    print("y =", sum)
    answer.append(sum)

    print("Метод Ньютона с разделёнными разностями:")
    sum = newtonMethod1(ym, n, xa, xm)
    print("y =", sum)
    answer.append(sum)

    print("Метод Ньютона с конечными разностями:")
    sum = newtonMethod2(xm, xa, ym, table, n)
    print("y =", sum)
    answer.append(sum)
    return answer


def checkX(number, numbers):
    try:
        number = float(number)
        if number >= numbers[0] and number <= numbers[-1]:
            return True
        else:
            print("Значение аргумента не входит в интервал")
    except Exception:
        print("Значения x должны быть числами!")

def func1(x):
    return 2*x+3

def func2(x):
    return 3*(x**2)

def chooseAnswerX(userChoice):
    checkerForX = True
    while checkerForX:
        print("Введите значение аргумента, приближённое значение функции для которого вы хотите вычислить:")
        numberX = input()
        if (checkX(numberX, userChoice[1]) == True):
            checkerForX = False
            numberX = float(numberX)
    userChoice.append(numberX)
    answer = genFunc(userChoice)
    x = np.linspace(userChoice[1][0], userChoice[1][-1], 100)
    y1 = newtonMethod1(userChoice[3], userChoice[2], x, userChoice[1])
    y2 = getPolynomialNewton(userChoice[1], x, userChoice[3], getTable(userChoice[2], userChoice[3]), userChoice[2])
    plt.plot(x, y1, color='green', label="Newton1")
    plt.plot(x, y2, color='blue', label="Newton2")
    if userChoice[0]=="choose":
        y3 = func1(x)
        y4 = func2(x)
        plt.plot(x, y3, color='red', label="y=2*x+3")
        plt.plot(x, y4, color='orange', label="y=3*x^2")
    points = []
    for i in range(len(userChoice[1])):
        x = userChoice[1][i]
        y = userChoice[3][i]
        points.append((x, y))
    for point in points:
        x, y = point
        plt.scatter(x, y, color='red', label=f'({x},{y})')
    plt.legend()
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('Графики')
    plt.grid(True)
    plt.show()


def checkNumbersY(numbers, n):
    try:
        numbers = numbers.split()
        numbers = list(map(float, numbers))
        if len(numbers) == n:
            return True
        else:
            print("Количества x и y должны быть равными")
    except Exception:
        print("Значения x должны быть числами!")


def chooseY(userChoice):
    checkerForY = True
    while checkerForY:
        print("Введите от 4 до 10 значений y через пробел")
        numbersY = input()
        if (checkNumbersY(numbersY, userChoice[2]) == True):
            checkerForY = False
            numbersY = numbersY.split()
            numbersY = list(map(float, numbersY))
    userChoice.append(numbersY)
    chooseAnswerX(userChoice)


def checkNumbersX(numbers):
    try:
        numbers = numbers.split()
        numbers = list(map(float, numbers))
        if len(numbers) >= 4 and len(numbers) <= 10:
            return True
        else:
            print("Количество введённых чисел должно быть от 4 до 10")
    except Exception:
        print("Значения x должны быть числами!")


def chooseX(userChoice):
    checkerForX = True
    while checkerForX:
        print("Введите от 4 до 10 значений x через пробел")
        numbersX = input()
        if (checkNumbersX(numbersX) == True):
            checkerForX = False
            numbersX = numbersX.split()
            numbersX = list(map(float, numbersX))
    userChoice.append(numbersX)
    userChoice.append(len(numbersX))
    chooseY(userChoice)


def readFile(file, userChoice):
    numbers = file.readline().strip()
    numbers = numbers.split()
    numbers = list(map(float, numbers))
    userChoice.append(numbers)
    userChoice.append(len(numbers))
    numbers = file.readline().strip()
    numbers = numbers.split()
    numbers = list(map(float, numbers))
    userChoice.append(numbers)
    k = file.readline().strip()
    userChoice.append(float(k))
    answer = genFunc(userChoice)


def fileInput(userChoice):
    checkerForFile = False
    while checkerForFile == False:
        print("Введите путь к файлу:")
        fileInput = input()
        try:
            file = open(fileInput, "r")
            checkerForFile = True
            readFile(file, userChoice)
        except Exception:
            print("Не получилось найти файл с таким именем!")


def chooseH(userChoice):
    checkerForSelection = True
    while checkerForSelection:
        print(
            "Выберете шаг \"1\" -> 0.1, \"2\" -> 0.2:")
        outputSelection = input()
        if outputSelection == "1":
            userChoice.append(0.1)
            checkerForSelection = False
            n = int((userChoice[3] - userChoice[2]) / userChoice[4] + 1)
            x = []
            y = []
            for i in range(n):
                x.append(userChoice[2] + i * userChoice[4])
                y.append(eval(userChoice[1].replace("x", "(" + str(userChoice[2] + i * userChoice[4]) + ")")))
            userChoice = []
            userChoice.append(x)
            userChoice.append(n)
            userChoice.append(y)
            chooseAnswerX(userChoice)
        elif outputSelection == "2":
            userChoice.append(0.2)
            checkerForSelection = False
            n = int((userChoice[3] - userChoice[2]) / userChoice[4] + 1)
            x = []
            y = []
            for i in range(n):
                x.append(userChoice[2] + i * userChoice[4])
                y.append(eval(userChoice[1].replace("x", "(" + str(userChoice[2] + i * userChoice[4]) + ")")))
            userChoice = []
            userChoice.append("choose")
            userChoice.append(x)
            userChoice.append(n)
            userChoice.append(y)
            chooseAnswerX(userChoice)
        else:
            print("Я Вас не понимаю :(")


def chooseInt(userChoice):
    checkerForSelection = True
    while checkerForSelection:
        print(
            "Выберете интервал \"1\" -> [1;1.8], \"2\" -> [0;0.4]")
        outputSelection = input()
        if outputSelection == "1":
            userChoice.append(1)
            userChoice.append(1.8)
            checkerForSelection = False
            chooseH(userChoice)
        elif outputSelection == "2":
            userChoice.append(0)
            userChoice.append(0.4)
            checkerForSelection = False
            chooseH(userChoice)
        else:
            print("Я Вас не понимаю :(")


def chooseFunc(userChoice):
    checkerForSelection = True
    while checkerForSelection:
        print(
            "Выберете функцию \"1\" -> y=2*x+3, \"2\" -> y=3*x^2:")
        outputSelection = input()
        if outputSelection == "1":
            userChoice.append("2*x+3")
            checkerForSelection = False
            chooseInt(userChoice)
        elif outputSelection == "2":
            userChoice.append("3*x^2")
            checkerForSelection = False
            chooseInt(userChoice)
        else:
            print("Я Вас не понимаю :(")


while (True):
    userChoice = []
    print(
        "Введите \"1\" для ввода входных данных через консоль, \"2\" для чтения из файла, \"3\" для выбора функции, шага и интервала из предложенных, \"4\" для выхода из программы:")
    inputSelection = input()
    if inputSelection == "1":
        userChoice.append("console")
        chooseX(userChoice)

    elif inputSelection == "2":
        userChoice.append("file")
        fileInput(userChoice)

    elif inputSelection == "3":
        userChoice.append("choose")
        chooseFunc(userChoice)

    elif inputSelection == "4":
        exit()
    else:
        print("Я Вас не понимаю :(")
