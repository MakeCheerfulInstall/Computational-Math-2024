import math
import matplotlib.pyplot as plt
import numpy as np


def checkForLn(a):
    for i in range(len(a)):
        if a[i] <= 0:
            return False
    return True


def checkR(r):
    if r == 1 or r == -1:
        print("Строгая линейная связь")
    elif r == 0:
        print("Связь отсутствует")
    elif r < 0.3:
        print("Связь слабая")
    elif r < 0.5:
        print("Связь умеренная")
    elif r < 0.7:
        print("Связь заметная")
    elif r < 0.9:
        print("Связь высокая")
    elif r > 0.9:
        print("Связь весьма высокая")


def mathFunc(userChoice):
    answer = []
    print("Линейная аппроксимация")
    sx = 0
    sxx = 0
    sy = 0
    sxy = 0
    sxxx = 0
    sxxxx = 0
    sxxxxx = 0
    sxxxxxx = 0
    sxxy = 0
    sxxxy = 0
    n = userChoice[3]
    for i in range(n):
        sx += userChoice[2][i]
        sxx += (userChoice[2][i] ** 2)
        sy += userChoice[4][i]
        sxy += (userChoice[2][i] * userChoice[4][i])
        sxxy += ((userChoice[2][i] ** 2) * userChoice[4][i])
        sxxx += (userChoice[2][i] ** 3)
        sxxxx += (userChoice[2][i] ** 4)
        sxxxxx += (userChoice[2][i] ** 5)
        sxxxxxx += (userChoice[2][i] ** 6)
        sxxxy += ((userChoice[2][i] ** 3) * userChoice[4][i])
    coeff = np.array([[sxx, sx], [sx, n]])
    const = np.array([sxy, sy])
    solution = np.linalg.solve(coeff, const)
    a = solution[0]
    b = solution[1]
    p1 = "(" + str(a) + ")" + "*x+" + "(" + str(b) + ")"
    print("P1=" + p1)
    se = 0
    print("x -> y -> P1 -> e")
    s1 = 0
    s2 = 0
    for i in range(n):
        func = eval(p1.replace("x", "(" + str(userChoice[2][i]) + ")"))
        e = abs(func - userChoice[4][i])
        se += (e ** 2)
        s1 += func ** 2
        s2 += func
        print(userChoice[2][i], "->", userChoice[4][i], "->", func, "->", e)
    print("S =", se)
    R2 = 1 - (se) / (s1 - ((s2) ** 2) / n)
    print("R2 =", R2)
    xAv = sx / n
    yAv = sy / n
    s1 = 0
    s2 = 0
    s3 = 0
    for i in range(n):
        s1 += ((userChoice[2][i] - xAv) * (userChoice[4][i] - yAv))
        s2 += ((userChoice[2][i] - xAv) ** 2)
        s3 += ((userChoice[4][i] - yAv) ** 2)
    r = s1 / ((s2 * s3) ** (0.5))
    print("r =", r)
    checkR(r)
    print("СКО =", (se / n) ** (0.5))
    lin = [p1, se, R2, "линейная аппроксимация", a, b]
    answer.append(lin)

    print("Квадратичная аппроксимация")
    coeff = np.array([[n, sx, sxx], [sx, sxx, sxxx], [sxx, sxxx, sxxxx]])
    const = np.array([sy, sxy, sxxy])
    solution = np.linalg.solve(coeff, const)
    a0 = solution[0]
    a1 = solution[1]
    a2 = solution[2]
    p2 = "(" + str(a0) + ")+x*(" + str(a1) + ")+" + "(" + str(a2) + ")*(x**2)"
    print("P2=" + p2)
    se = 0
    print("x -> y -> P2 -> e")
    s1 = 0
    s2 = 0
    for i in range(n):
        func = eval(p2.replace("x", "(" + str(userChoice[2][i]) + ")"))
        e = abs(func - userChoice[4][i])
        se += (e ** 2)
        s1 += func ** 2
        s2 += func
        print(userChoice[2][i], "->", userChoice[4][i], "->", func, "->", e)
    print("S =", se)
    R2 = 1 - (se) / (s1 - ((s2) ** 2) / n)
    print("R2 =", R2)
    print("СКО =", (se / n) ** (0.5))
    cvadr = [p2, se, R2, "квадратичная аппроксимация", a0, a1, a2]
    answer.append(cvadr)

    print("Аппроксимация полинома 3 степени")
    coeff = np.array(
        [[n, sx, sxx, sxxx], [sx, sxx, sxxx, sxxxx], [sxx, sxxx, sxxxx, sxxxxx], [sxxx, sxxxx, sxxxxx, sxxxxxx]])
    const = np.array([sy, sxy, sxxy, sxxxy])
    solution = np.linalg.solve(coeff, const)
    a0 = solution[0]
    a1 = solution[1]
    a2 = solution[2]
    a3 = solution[3]
    p3 = "(" + str(a0) + ")+x*(" + str(a1) + ")+" + "(" + str(a2) + ")*(x**2)+(" + str(a3) + ")*(x**3)"
    print("P3=" + p3)
    se = 0
    print("x -> y -> P3 -> e")
    s1 = 0
    s2 = 0
    for i in range(n):
        func = eval(p3.replace("x", "(" + str(userChoice[2][i]) + ")"))
        e = abs(func - userChoice[4][i])
        se += (e ** 2)
        s1 += func ** 2
        s2 += func
        print(userChoice[2][i], "->", userChoice[4][i], "->", func, "->", e)
    print("S =", se)
    R2 = 1 - (se) / (s1 - ((s2) ** 2) / n)
    print("R2 =", R2)
    print("СКО =", (se / n) ** (0.5))
    thirdSt = [p3, se, R2, "аппроксимация полинома 3 степени", a0, a1, a2, a3]
    answer.append(thirdSt)

    print("Аппроксимация экспоненциальной функции")
    if checkForLn(userChoice[4]) == True:
        lnsy = 0
        sxlny = 0
        for i in range(n):
            lnsy += math.log(userChoice[4][i])
            sxlny += (userChoice[2][i] * math.log(userChoice[4][i]))

        coeff = np.array([[sxx, sx], [sx, n]])
        const = np.array([sxlny, lnsy])
        solution = np.linalg.solve(coeff, const)
        B = solution[0]
        A = solution[1]
        b = B
        a = math.exp(A)
        p4 = "(" + str(a) + ")" + "*" + str(math.e) + "**(x*(" + str(b) + "))"
        print("P4=" + p4)
        se = 0
        print("x -> y -> P4 -> e")
        for i in range(n):
            s1 = 0
            s2 = 0
            func = eval(p4.replace("x", "(" + str(userChoice[2][i]) + ")"))
            e = abs(func - userChoice[4][i])
            se += (e ** 2)
            s1 += func ** 2
            s2 += func
            print(userChoice[2][i], "->", userChoice[4][i], "->", func, "->", e)
        print("S =", se)
        R2 = 1 - (se) / (s1 - ((s2) ** 2) / n)
        print("R2 =", R2)
        print("СКО =", (se / n) ** (0.5))
        apprExp = [p4, se, R2, "аппроксимация экспоненциальной функции", a, b]
        answer.append(apprExp)
    else:
        print("Нельзя взять логарифм")

    print("Аппроксимация степенной функции")
    if checkForLn(userChoice[4]) == True and checkForLn(userChoice[2]) == True:
        slnxx = 0
        slnx = 0
        lnsy = 0
        sxlny = 0
        for i in range(n):
            slnxx += ((math.log(userChoice[2][i])) ** 2)
            slnx += (math.log(userChoice[2][i]))
            lnsy += math.log(userChoice[4][i])
            sxlny += (math.log(userChoice[2][i]) * math.log(userChoice[4][i]))
        coeff = np.array([[slnxx, slnx], [slnx, n]])
        const = np.array([sxlny, lnsy])
        solution = np.linalg.solve(coeff, const)
        B = solution[0]
        A = solution[1]
        b = B
        a = math.exp(A)
        p5 = "(" + str(a) + ")" + "*" + "(x**(" + str(b) + "))"
        print("P5=" + p5)
        se = 0
        print("x -> y -> P5 -> e")
        s1 = 0
        s2 = 0
        for i in range(n):
            func = eval(p5.replace("x", "(" + str(userChoice[2][i]) + ")"))
            e = abs(func - userChoice[4][i])
            se += (e ** 2)
            s1 += func ** 2
            s2 += func
            print(userChoice[2][i], "->", userChoice[4][i], "->", func, "->", e)
        print("S =", se)
        R2 = 1 - (se) / (s1 - ((s2) ** 2) / n)
        print("R2 =", R2)
        print("СКО =", (se / n) ** (0.5))
        apprSt = [p5, se, R2, "аппроксимация степенной функции", a, b]
        answer.append(apprSt)
    else:
        print("Нельзя взять логарифм")

    print("Аппроксимация логарифмической функции")
    if checkForLn(userChoice[2]) == True:
        slnxx = 0
        slnx = 0
        sxlny = 0
        for i in range(n):
            slnxx += ((math.log(userChoice[2][i])) ** 2)
            slnx += (math.log(userChoice[2][i]))
            sxlny += (math.log(userChoice[2][i]) * userChoice[4][i])
        coeff = np.array([[slnxx, slnx], [slnx, n]])
        const = np.array([sxlny, sy])
        solution = np.linalg.solve(coeff, const)
        A = solution[0]
        B = solution[1]
        b = B
        a = A
        p6 = "(" + str(a) + ")" + "*" + "math.log(x)+(" + str(b) + ")"
        print("P6=" + p6)
        se = 0
        print("x -> y -> P6 -> e")
        s1 = 0
        s2 = 0
        for i in range(n):
            func = eval(p6.replace("x", "(" + str(userChoice[2][i]) + ")"))
            e = abs(func - userChoice[4][i])
            se += (e ** 2)
            s1 += func ** 2
            s2 += func
            print(userChoice[2][i], "->", userChoice[4][i], "->", func, "->", e)
        print("S =", se)
        R2 = 1 - (se) / (s1 - ((s2) ** 2) / n)
        print("R2 =", R2)
        print("СКО =", (se / n) ** (0.5))
        apprLog = [p6, se, R2, "аппроксимация логарифмической функции", a, b]
        answer.append(apprLog)
    else:
        print("Нельзя взять логарифм")
    return answer


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


def f1(x, answer):
    return answer[0][4] * x + answer[0][5]


def f2(x, answer):
    return answer[1][6] * x * x + answer[1][5] * x + answer[1][4]


def f3(x, answer):
    return answer[2][7] * x * x * x + answer[2][6] * x * x + answer[2][5] * x + answer[2][4]


def f4(x, answer):
    return answer[3][4] * 2.718281 ** (x * answer[3][5])


def f5(x, answer):
    return answer[4][4] * x ** answer[4][5]


# def f6(x, answer):
#     return answer[5][4]*math.log(x)+answer[5][5]

def chooseY(userChoice):
    checkerForY = True
    while checkerForY:
        print("Введите от 8 до 12 значений y через пробел")
        numbersY = input()
        if (checkNumbersY(numbersY, userChoice[3]) == True):
            checkerForY = False
            numbersY = numbersY.split()
            numbersY = list(map(float, numbersY))
    userChoice.append(numbersY)
    answer = mathFunc(userChoice)
    print("\n")
    print("P -> S -> R2 -> аппроксимация")
    maxR = 0
    counter = 0
    for i in range(len(answer)):
        print("P =", answer[i][0], "->", answer[i][1], "->", answer[i][2], "->", answer[i][3])
        if maxR < answer[i][2]:
            maxR = answer[i][2]
            counter = i
    print("\n")
    print("Ответ:", "P =", answer[counter][0], "->", answer[counter][1], "->", answer[counter][2], "->",
          answer[counter][3])
    x = np.linspace(-1.0, 8, 100)
    y1 = f1(x, answer)
    y2 = f2(x, answer)
    y3 = f3(x, answer)
    y4 = f4(x, answer)
    y5 = f5(x, answer)
    # y6 = f6(x,answer)
    plt.figure(figsize=(12, 8))

    plt.plot(x, y1, color='red', label=answer[1][3])
    plt.plot(x, y2, color='blue', label=answer[2][3])
    plt.plot(x, y3, color='green', label=answer[3][3])
    plt.plot(x, y4, color='purple', label=answer[4][3])
    plt.plot(x, y5, color='orange', label=answer[5][3])
    points = []
    for i in range(len(userChoice[2])):
        x = userChoice[2][i]
        y = userChoice[4][i]
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


def checkNumbersX(numbers):
    try:
        numbers = numbers.split()
        numbers = list(map(float, numbers))
        if len(numbers) >= 5 and len(numbers) <= 12:
            return True
        else:
            print("Количество введённых чисел должно быть от 8 до 12")
    except Exception:
        print("Значения x должны быть числами!")


def chooseX(userChoice):
    checkerForX = True
    while checkerForX:
        print("Введите от 8 до 12 значений x через пробел")
        numbersX = input()
        if (checkNumbersX(numbersX) == True):
            checkerForX = False
            numbersX = numbersX.split()
            numbersX = list(map(float, numbersX))
    userChoice.append(numbersX)
    userChoice.append(len(numbersX))
    chooseY(userChoice)


def chooseOutput(userChoice):
    checkerForOutputSelection = True
    while checkerForOutputSelection:
        print(
            "Введите \"1\" для вывода результата в консоль, \"2\" для записи в файл:")
        outputSelection = input()
        if outputSelection == "1":
            userChoice.append("console")
            checkerForOutputSelection = False
            chooseX(userChoice)
        elif outputSelection == "2":
            userChoice.append("file")
            checkerForOutputSelection = False
            chooseX(userChoice)
        else:
            print("Я Вас не понимаю :(")


def readFile(file, userChoice):
    k = file.readline().strip()
    userChoice.append(k)
    numbers = file.readline().strip()
    numbers = numbers.split()
    numbers = list(map(float, numbers))
    userChoice.append(numbers)
    userChoice.append(len(numbers))
    numbers = file.readline().strip()
    numbers = numbers.split()
    numbers = list(map(float, numbers))
    userChoice.append(numbers)
    answer = mathFunc(userChoice)
    print("\n")
    print("P -> S -> R2 -> аппроксимация")
    maxR = 0
    counter = 0
    for i in range(len(answer)):
        print("P =", answer[i][0], "->", answer[i][1], "->", answer[i][2], "->", answer[i][3])
    if maxR < answer[i][2]:
        maxR = answer[i][2]
        counter = i
    print("\n")
    print("Ответ:", "P =", answer[counter][0], "->", answer[counter][1], "->", answer[counter][2], "->",
          answer[counter][3])
    x = np.linspace(-1.0, 8, 100)
    y1 = f1(x, answer)
    y2 = f2(x, answer)
    y3 = f3(x, answer)
    y4 = f4(x, answer)
    y5 = f5(x, answer)
    # y6 = f6(x,answer)
    plt.figure(figsize=(12, 8))

    plt.plot(x, y1, color='red', label=answer[1][3])
    plt.plot(x, y2, color='blue', label=answer[2][3])
    plt.plot(x, y3, color='green', label=answer[3][3])
    plt.plot(x, y4, color='purple', label=answer[4][3])
    plt.plot(x, y5, color='orange', label=answer[5][3])
    # plt.plot(x, y6, color='brown', label=answer[6][3])
    points = []
    for i in range(len(userChoice[2])):
        x = userChoice[2][i]
        y = userChoice[4][i]
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


while (True):
    userChoice = []
    print(
        "Введите \"1\" для ввода входных данных через консоль, \"2\" для чтения из файла, \"3\" для выхода из программы:")
    inputSelection = input()
    if inputSelection == "1":
        userChoice.append("console")
        chooseOutput(userChoice)

    elif inputSelection == "2":
        userChoice.append("file")
        fileInput(userChoice)

    elif inputSelection == "3":
        exit()
    else:
        print("Я Вас не понимаю :(")
