from math import *
import numpy as np
import matplotlib.pyplot as plt


def checkConvergenceForSystem(userChoice):
    x = int(userChoice[9])
    y = int(userChoice[10])
    dfdx = eval(userChoice[3].replace("y", "(" + str(y) + ")").replace("x", "(" + str(x) + ")"))
    dfdy = eval(userChoice[4].replace("y", "(" + str(y) + ")").replace("x", "(" + str(x) + ")"))
    dgdx = eval(userChoice[5].replace("y", "(" + str(y) + ")").replace("x", "(" + str(x) + ")"))
    dgdy = eval(userChoice[6].replace("y", "(" + str(y) + ")").replace("x", "(" + str(x) + ")"))
    if max(abs(dfdx) + abs(dfdy), abs(dgdx) + abs(dgdy)) >= 1:
        print("Проверка на сходимость не прошла!")
        return False
    else:
        return True


def simpleIterationMethod(userChoice):
    M = 30
    iteration = 0
    if (checkConvergenceForSystem(userChoice) == False):
        M = 10
    e = float(userChoice[11])
    x0 = int(userChoice[9])
    y0 = int(userChoice[10])
    x = 0
    y = 0
    curEx = 100
    curEy = 100
    print("№ -> x -> y -> |xn+1 - x| -> |yn+1 - y|")
    while (iteration < M and (curEx > e or curEy > e)):
        iteration += 1
        x = eval(userChoice[7].replace("y", "(" + str(y0) + ")").replace("x", "(" + str(x0) + ")"))
        y = eval(userChoice[8].replace("y", "(" + str(y0) + ")").replace("x", "(" + str(x0) + ")"))
        curEx = abs(x - x0)
        curEy = abs(y - y0)
        x0 = x
        y0 = y
        print(iteration, x, "->", y, "->", curEx, "->", curEy)
    answer = []
    answer.append(x)
    answer.append(curEx)
    answer.append(y)
    answer.append(curEy)
    answer.append(iteration)
    return answer


def chooseSystem(userChoice):
    checkerForSystem = True
    while checkerForSystem:
        print(
            "Выберите систему нелинейных уравнений: \n \"1\" -> 0.1x^2+x+0.2y-0.3=0 \n        0.2x^2+y+0.1xy-0.7=0\n  "
            "      0<x<1, 0<y<1\n \"2\" -> x^2+y^2+x=0.25\n          0.234x^2+0.23yx+0.2y=0 \n        -1<x<0, -1<y<0")
        selection = input()
        if selection == "1":
            userChoice.append("-0.2*x")
            userChoice.append("-0.4*y")
            userChoice.append("-0.4*x-0.1*y")
            userChoice.append("-0.1*x")
            userChoice.append("0.3-0.1*x**2-0.2*y**2")
            userChoice.append("0.7-0.2*x**2-0.1*x*y")
            userChoice.append("1")
            userChoice.append("1")
            checkerForSystem = False
            chooseE(userChoice)

        elif selection == "2":
            userChoice.append("-2*x")
            userChoice.append("-2*y")
            userChoice.append("-2.34*x-1.15*y")
            userChoice.append("-1.15*x")
            userChoice.append("0.25-x**2-y**2")
            userChoice.append("(-0.234*x**2)/(0.23*x+0.2)")
            userChoice.append("0")
            userChoice.append("0")
            checkerForSystem = False
            chooseE(userChoice)

        else:
            print("Я Вас не понимаю :(")


def hordaMethod(userChoice):
    iteration = 1
    e = float(userChoice[8])
    a = float(userChoice[6])
    b0 = float(userChoice[7])
    fa = eval(userChoice[3].replace("x", "(" + str(a) + ")"))
    fb0 = eval(userChoice[3].replace("x", "(" + str(b0) + ")"))
    fapp = eval(userChoice[5].replace("x", "(" + str(a) + ")"))
    fb0pp = eval(userChoice[5].replace("x", "(" + str(b0) + ")"))
    x = a
    if (fa * fapp < 0 and fb0 * fb0pp > 0):
        a = b0
        b0 = x
    fa = eval(userChoice[3].replace("x", "(" + str(a) + ")"))
    fb0 = eval(userChoice[3].replace("x", "(" + str(b0) + ")"))
    b = b0 - (a - b0) / (fa - fb0) * fb0
    curE = abs(b - b0)
    fb = eval(userChoice[3].replace("x", "(" + str(b) + ")"))
    print("№ -> a -> b -> f(a) -> f(b) -> |bn+1 - b|")
    print(iteration, "->", a, "->", b, "->", fa, "->", fb, "->", curE)
    while (curE > e):
        iteration += 1
        b0 = b
        fb0 = eval(userChoice[3].replace("x", "(" + str(b0) + ")"))
        b = b0 - (a - b0) / (fa - fb0) * fb0
        curE = abs(b - b0)
        print(iteration, "->", a, "->", b, "->", fa, "->", fb, "->", curE)
    answer = []
    answer.append(b)
    fb = eval(userChoice[3].replace("x", "(" + str(b) + ")"))
    answer.append(fb)
    answer.append(iteration)
    return answer


def checkConvergence(userChoice):
    a = float(userChoice[6])
    b = float(userChoice[7])
    fa = eval(userChoice[3].replace("x", "(" + str(a) + ")"))
    fb = eval(userChoice[3].replace("x", "(" + str(b) + ")"))
    fap = eval(userChoice[4].replace("x", "(" + str(a) + ")"))
    fbp = eval(userChoice[4].replace("x", "(" + str(b) + ")"))
    fapp = eval(userChoice[5].replace("x", "(" + str(a) + ")"))
    fbpp = eval(userChoice[5].replace("x", "(" + str(b) + ")"))
    if (fa * fb >= 0):
        print("Проверка на сходимость не прошла. Значения функции на концах интервала имеют одинаковый знак!")
        return False
    elif ((fap >= 0 and fbp >= 0 or fap <= 0 and fbp <= 0) == False):
        print("Проверка на сходимость не прошла. Производная функции не сохраняет знак на отрезке!")
        return False
    elif ((fapp > 0 and fbpp > 0 or fapp < 0 and fbpp < 0) == False):
        print("Проверка на сходимость не прошла. Вторая производная функции не сохраняет знак на отрезке!")
        return False
    else:
        return True


def methodNewton(userChoice):
    M = 30
    iteration = 0
    e = float(userChoice[8])
    a = float(userChoice[6])
    b = float(userChoice[7])
    fa = eval(userChoice[3].replace("x", "(" + str(a) + ")"))
    fb = eval(userChoice[3].replace("x", "(" + str(a) + ")"))
    fapp = eval(userChoice[5].replace("x", "(" + str(b) + ")"))
    fbpp = eval(userChoice[5].replace("x", "(" + str(b) + ")"))
    x0 = a
    if (fa * fapp > 0):
        x0 = a
    elif (fb * fbpp > 0):
        x0 = b
    if (checkConvergence(userChoice) == False):
        M = 10
    fx0 = eval(userChoice[3].replace("x", "(" + str(x0) + ")"))
    fx0p = eval(userChoice[4].replace("x", "(" + str(x0) + ")"))
    x = x0 - fx0 / fx0p
    curE = abs(x - x0)
    print("№ -> xn -> f(xn) -> f'(xn) -> xn+1 -> |xn+1 - x|")
    print(iteration, "->", x0, "->", fx0, "->", fx0p, "->", x, "->", curE)
    while (iteration < M and curE > e):
        iteration += 1
        x0 = x
        fx0 = eval(userChoice[3].replace("x", "(" + str(x0) + ")"))
        fx0p = eval(userChoice[4].replace("x", "(" + str(x0) + ")"))
        x = x0 - fx0 / fx0p
        curE = abs(x - x0)
        print(iteration, "->", x0, "->", fx0, "->", fx0p, "->", x, "->", curE)
        if (iteration == M):
            return False
    answer = []
    answer.append(x)
    fx = eval(userChoice[3].replace("x", "(" + str(x) + ")"))
    answer.append(fx)
    answer.append(iteration)
    return answer


def halfMethod(userChoice):
    a = float(userChoice[6])
    b = float(userChoice[7])
    x = (a + b) / 2
    e = float(userChoice[8])
    fa = eval(userChoice[3].replace("x", "(" + str(a) + ")"))
    fb = eval(userChoice[3].replace("x", "(" + str(b) + ")"))
    fx = eval(userChoice[3].replace("x", "(" + str(x) + ")"))
    curE = abs(a - b)
    iteration = 0
    print("№ -> a -> b -> x -> f(a) -> f(b) -> f(x) -> |a - b|")
    print(iteration, "->", a, "->", b, "->", x, "->", fa, "->", fb, "->", fx, "->", curE)
    if (fx == 0):
        answer = []
        answer.append(x)
        answer.append(fx)
        answer.append(1)
        return answer
    while (curE > e):
        iteration += 1
        if (fa > 0 and fx > 0 or fa < 0 and fx < 0):
            a = x
        if (fb > 0 and fx > 0 or fb < 0 and fx < 0):
            b = x
        x = (a + b) / 2
        fa = eval(userChoice[3].replace("x", "(" + str(a) + ")"))
        fb = eval(userChoice[3].replace("x", "(" + str(b) + ")"))
        fx = eval(userChoice[3].replace("x", "(" + str(x) + ")"))
        curE = abs(a - b)
        print(iteration, "->", a, "->", b, "->", x, "->", fa, "->", fb, "->", fx, "->", curE)
    answer = []
    answer.append(x)
    answer.append(fx)
    answer.append(iteration)
    return answer


def chooseMethod(userChoice):
    checkerForMethod = True
    while checkerForMethod:
        print(
            "Выберите метод: \n \"1\" -> метод половинного деления\n \"2\" -> метод Ньютона\n \"3\" -> метод хорд:")
        selection = input()
        if selection == "1":
            userChoice.append("half")
            checkerForMethod = False
            answer = halfMethod(userChoice)
            print("Ответ:")
            print(answer[0], "->", answer[1], "->", answer[2])
            x = np.arange(-3, 3.01, 0.01)
            plt.ylim(-3, 3)
            plt.plot(x, eval(userChoice[3]))
            plt.scatter(answer[0], answer[1], color='red')
            plt.xlabel(r'$x$')
            plt.ylabel(r'$f(x)$')
            plt.title(r'$f(x)=5.74x^3-2.95x^2-10.28x+4.23$')
            plt.grid(True)
            plt.show()

        elif selection == "2":
            userChoice.append("Newton")
            checkerForMethod = False
            answer = methodNewton(userChoice)
            if answer != False:
                print("Ответ:")
                print(answer[0], "->", answer[1], "->", answer[2])
                x = np.arange(-3, 3.01, 0.01)
                plt.ylim(-3, 3)
                plt.plot(x, eval(userChoice[3]))
                plt.scatter(answer[0], answer[1], color='red')
                plt.xlabel(r'$x$')
                plt.ylabel(r'$f(x)$')
                plt.title(r'$f(x)=5.74x^3-2.95x^2-10.28x+4.23$')
                plt.grid(True)
                plt.show()
            else:
                print("Не удалось найти решение")

        elif selection == "3":
            userChoice.append("horda")
            checkerForMethod = False
            answer = hordaMethod(userChoice)
            print("Ответ:")
            print(answer[0], "->", answer[1], "->", answer[2])
            x = np.arange(-3, 3.01, 0.01)
            plt.ylim(-3, 3)
            plt.plot(x, eval(userChoice[3]))
            plt.scatter(answer[0], answer[1], color='red')
            plt.xlabel(r'$x$')
            plt.ylabel(r'$f(x)$')
            plt.title(r'$f(x)=5.74x^3-2.95x^2-10.28x+4.23$')
            plt.grid(True)
            plt.show()

        else:
            print("Я Вас не понимаю :(")


def checkE(e):
    try:
        e = float(e)
        if e > 0:
            checkerForE = True
            return checkerForE
        else:
            print("Погрешность вычислений должна быть больше нуля!")
    except Exception:
        print("Погрешность вычислений должна быть числом!")


def chooseE(userChoice):
    checkerForE = False
    while checkerForE == False:
        print("Введите погрешность вычислений:")
        e = input()
        if checkE(e) == True:
            checkerForE = True
            e = float(e)
            userChoice.append(e)
            if userChoice[2] == "one":
                chooseMethod(userChoice)
            elif userChoice[2] == "system":
                answer = simpleIterationMethod(userChoice)
                print("Ответ:")
                print(answer[0], "->", answer[1])
                print(answer[2], "->", answer[3])
                print("Количество итераций:", answer[4])



def chooseInterval(userChoice):
    if (userChoice[3] == "5.74*x**3-2.95*x**2-10.28*x+4.23"):
        checker = True
        while checker:
            print(
                "Выберите интервал: \n \"1\" -> [-2;-1] \n \"2\" -> [0;1] \n \"3\" -> [1;2]")
            selection = input()
            if selection == "1":
                userChoice.append("-2")
                userChoice.append("-1")
                checker = False
                chooseE(userChoice)
            elif selection == "2":
                userChoice.append("0")
                userChoice.append("1")
                checker = False
                chooseE(userChoice)
            elif selection == "3":
                userChoice.append("1")
                userChoice.append("2")
                checker = False
                chooseE(userChoice)
            else:
                print("Я Вас не понимаю :(")

    elif (userChoice[3] == "x**3-x+4"):
        userChoice.append("-2")
        userChoice.append("-1")
        chooseE(userChoice)

    else:
        checker = True
        while checker:
            print(
                "Выберите интервал: \n \"1\" -> [-0.2;0.2] \n \"2\" -> [0.4;1]")
            selection = input()
            if selection == "1":
                userChoice.append("-0.2")
                userChoice.append("0.2")
                checker = False
                chooseE(userChoice)
            elif selection == "2":
                userChoice.append("0.4")
                userChoice.append("1")
                checker = False
                chooseE(userChoice)

            else:
                print("Я Вас не понимаю :(")


def chooseEquation(userChoice):
    checkerForEquation = True
    while checkerForEquation:
        print(
            "Выберите нелинейное уравнение: \n \"1\" -> 5.74x^3-2.95x^2-10.28x+4.23=0\n \"2\" -> x^3-x+4=0\n \"3\" -> sin(2x)-3x^2=0:")
        selection = input()
        if selection == "1":
            userChoice.append("5.74*x**3-2.95*x**2-10.28*x+4.23")
            userChoice.append("17.22*x**2-5.9*x-10.28")
            userChoice.append("34.44*x-5.9")
            x = np.arange(-3, 3.01, 0.01)
            plt.ylim(-3, 3)
            plt.plot(x, 5.74 * x ** 3 - 2.95 * x ** 2 - 10.28 * x + 4.23)
            plt.xlabel(r'$x$')
            plt.ylabel(r'$f(x)$')
            plt.title(r'$f(x)=5.74x^3-2.95x^2-10.28x+4.23$')
            plt.grid(True)
            plt.show()
            checkerForEquation = False
            chooseInterval(userChoice)
        elif selection == "2":
            userChoice.append("x**3-x+4")
            userChoice.append("3*x**2-1")
            userChoice.append("6*x")
            x = np.arange(-3, 3.01, 0.01)
            plt.ylim(-3, 3)
            plt.plot(x, x ** 3 - x + 4)
            plt.xlabel(r'$x$')
            plt.ylabel(r'$f(x)$')
            plt.title(r'$f(x)=x^3-x+4$')
            plt.grid(True)
            plt.show()
            checkerForEquation = False
            chooseInterval(userChoice)
        elif selection == "3":
            userChoice.append("sin(2*x)-3*x**2")
            userChoice.append("2*cos(2*x)-6*x")
            userChoice.append("-4*sin(2*x)-6")
            x = np.arange(-3, 3.01, 0.01)
            plt.ylim(-3, 3)
            plt.plot(x, np.sin(2 * x) - 3 * x ** 2)
            plt.xlabel(r'$x$')
            plt.ylabel(r'$f(x)$')
            plt.title(r'$f(x)=sin(2x)-3x^2$')
            plt.grid(True)
            plt.show()
            checkerForEquation = False
            chooseInterval(userChoice)
        else:
            print("Я Вас не понимаю :(")


def chooseSystemOrNot(userChoice):
    checkerForSelection = True
    while checkerForSelection:
        print(
            "Введите \"1\" для решения нелинейного уравнения, \"2\" для решения системы нелинейных уравнений:")
        selection = input()
        if selection == "1":
            userChoice.append("one")
            checkerForSelection = False
            chooseEquation(userChoice)

        elif selection == "2":
            userChoice.append("system")
            checkerForSelection = False
            chooseSystem(userChoice)
        else:
            print("Я Вас не понимаю :(")


def chooseOutput(userChoice):
    checkerForOutputSelection = True
    while checkerForOutputSelection:
        print(
            "Введите \"1\" для вывода результата в консоль, \"2\" для записи в файл:")
        outputSelection = input()
        if outputSelection == "1":
            userChoice.append("console")
            checkerForOutputSelection = False
            chooseSystemOrNot(userChoice)
        elif outputSelection == "2":
            userChoice.append("file")
            checkerForOutputSelection = False
            chooseSystemOrNot(userChoice)


        else:
            print("Я Вас не понимаю :(")


def readFile(file, userChoice):
    try:
        while (True):
            outputSelection = file.readline().strip()
            if (outputSelection != "1" and outputSelection != "2"):
                print("Некорректный выбор типа вывода")
            else:
                if outputSelection == "1":
                    userChoice.append("console")
                elif outputSelection == "2":
                    userChoice.append("file")

            systemOrNotSelection = file.readline().strip()
            if (systemOrNotSelection != "1" and systemOrNotSelection != "2"):
                print("Некорректный выбор задачи")
            else:
                if systemOrNotSelection == "1":
                    userChoice.append("one")

                elif systemOrNotSelection == "2":
                    userChoice.append("system")

            equationSelection = file.readline().strip()
            if (equationSelection != "1" and equationSelection != "2" and equationSelection != "3"):
                print("Некорректный выбор уравнения")
            else:
                if equationSelection == "1":
                    userChoice.append("5.74*x**3-2.95*x**2-10.28*x+4.23")
                    userChoice.append("17.22*x**2-5.9*x-10.28")
                    userChoice.append("34.44*x-5.9")

                elif equationSelection == "2":
                    userChoice.append("x**3-x+4")
                    userChoice.append("3*x**2-1")
                    userChoice.append("6*x")
                elif equationSelection == "3":
                    userChoice.append("sin(2*x)-3*x**2")
                    userChoice.append("2*cos(2*x)-6*x")
                    userChoice.append("-4*sin(2*x)-6")
            intervalSelection = file.readline().strip()
            if (userChoice[3] == "5.74*x**3-2.95*x**2-10.28*x+4.23"):
                if (intervalSelection != "1" and intervalSelection != "2" and intervalSelection != "3"):
                    print("Некорректный выбор интервала")
                else:
                    if intervalSelection == "1":
                        userChoice.append("-2")
                        userChoice.append("-1")
                    elif intervalSelection == "2":
                        userChoice.append("0")
                        userChoice.append("1")
                    elif intervalSelection == "3":
                        userChoice.append("1")
                        userChoice.append("2")
            elif (userChoice[3] == "x**3-x+4"):
                userChoice.append("-2")
                userChoice.append("-1")
            else:
                if (intervalSelection != "1" and intervalSelection != "2"):
                    print("Некорректный выбор интервала")
                else:
                    if intervalSelection == "1":
                        userChoice.append("-0.2")
                        userChoice.append("0.2")
                    elif intervalSelection == "2":
                        userChoice.append("0.4")
                        userChoice.append("1")
            e = file.readline().strip()
            e = float(e)
            if (checkE(e)):
                userChoice.append(e)
                method = file.readline().strip()
                if (method != "1" and method != "2" and method != "3"):
                    print("Некорректный выбор метода")
                else:
                    if method == "1":
                        userChoice.append("half")
                        answer = halfMethod(userChoice)
                        print("Ответ:")
                        print(answer[0], "->", answer[1], "->", answer[2])

                    elif method == "2":
                        userChoice.append("Newton")
                        answer = methodNewton(userChoice)
                        if answer != False:
                            print("Ответ:")
                            print(answer[0], "->", answer[1], "->", answer[2])
                        else:
                            print("Не удалось найти решение")

                    elif method == "3":
                        userChoice.append("horda")
                        answer = hordaMethod(userChoice)
                        print("Ответ:")
                        print(answer[0], "->", answer[1], "->", answer[2])


    except Exception:
        print("Некорректные данные в файле или данных не хватает!")


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
