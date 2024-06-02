import copy


def checkConvergence(userChoice, x):
    try:
        y = eval(userChoice[0].replace("x", "(" + str(x) + ")"))
        return True
    except Exception:
        print("Интеграл не существует!")
        userChoice.append(x-userChoice[2])
        userChoice.append(x+userChoice[2])
        return False


def simpsonMethod(userChoice):
    h0 = (userChoice[1][1] - userChoice[1][0]) / userChoice[3]
    n = userChoice[3]
    x0 = userChoice[1][0]
    x0 += h0
    i0 = 0
    i0 += (eval(userChoice[0].replace("x", "(" + str(userChoice[1][1]) + ")")) + eval(
        userChoice[0].replace("x", "(" + str(userChoice[1][0]) + ")")))
    while x0 < userChoice[1][1]:
        if checkConvergence(userChoice, x0):
            y0 = eval(userChoice[0].replace("x", "(" + str(x0) + ")"))
            i0 += 4 * y0
            x0 += 2 * h0
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = simpsonMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = simpsonMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(answer1[1] + answer2[1])
            return answer

    x0 = userChoice[1][0] + 2 * h0
    while x0 < userChoice[1][1]:
        if checkConvergence(userChoice, x0):
            y0 = eval(userChoice[0].replace("x", "(" + str(x0) + ")"))
            i0 += 2 * y0
            x0 += 2 * h0
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = simpsonMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = simpsonMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(answer1[1] + answer2[1])
            return answer

    i0 *= (h0 / 3)
    y0 = i0
    print("Значение интеграла ->", y0)
    print("Число разбиения интеграла ->", n)
    n *= 2
    h1 = (userChoice[1][1] - userChoice[1][0]) / n
    x1 = userChoice[1][0]
    x1 += h1
    i1 = 0
    i1 += (eval(userChoice[0].replace("x", "(" + str(userChoice[1][1]) + ")")) + eval(
        userChoice[0].replace("x", "(" + str(userChoice[1][0]) + ")")))
    while x1 < userChoice[1][1]:
        if checkConvergence(userChoice, x1):
            y1 = eval(userChoice[0].replace("x", "(" + str(x1) + ")"))
            i1 += 4 * y1
            x1 += 2 * h1
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = simpsonMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = simpsonMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(answer1[1] + answer2[1])
            return answer

    x1 = userChoice[1][0] + 2 * h1
    while x1 < userChoice[1][1]:
        if checkConvergence(userChoice, x1):
            y1 = eval(userChoice[0].replace("x", "(" + str(x1) + ")"))
            i1 += 2 * y1
            x1 += 2 * h1
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = simpsonMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = simpsonMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(answer1[1] + answer2[1])
            return answer
    i1 *= (h1 / 3)
    y1 = i1
    print("Значение интеграла ->", y1)
    print("Число разбиения интеграла ->", n)
    di = (y0 - y1) / (2 ** 4 - 1)

    while abs(di) >= userChoice[2]:
        n *= 2
        h = (userChoice[1][1] - userChoice[1][0]) / n
        x = userChoice[1][0]
        x += h
        i = 0
        i += (eval(userChoice[0].replace("x", "(" + str(userChoice[1][1]) + ")")) + eval(
            userChoice[0].replace("x", "(" + str(userChoice[1][0]) + ")")))
        while x < userChoice[1][1]:
            if checkConvergence(userChoice, x):
                y = eval(userChoice[0].replace("x", "(" + str(x) + ")"))
                i += 4 * y
                x += 2 * h
            else:
                userChoice1 = copy.deepcopy(userChoice)
                userChoice1[1][1] = userChoice[4]
                answer1 = simpsonMethod(userChoice1)
                userChoice2 = copy.deepcopy(userChoice)
                userChoice2[1][0] = userChoice[5]
                answer2 = simpsonMethod(userChoice2)
                answer = []
                answer.append(answer1[0] + answer2[0])
                answer.append(answer1[1] + answer2[1])
                return answer

        x = userChoice[1][0] + 2 * h
        while x < userChoice[1][1]:
            if checkConvergence(userChoice, x):
                y = eval(userChoice[0].replace("x", "(" + str(x) + ")"))
                i += 2 * y
                x += 2 * h
            else:
                userChoice1 = copy.deepcopy(userChoice)
                userChoice1[1][1] = userChoice[4]
                answer1 = simpsonMethod(userChoice1)
                userChoice2 = copy.deepcopy(userChoice)
                userChoice2[1][0] = userChoice[5]
                answer2 = simpsonMethod(userChoice2)
                answer = []
                answer.append(answer1[0] + answer2[0])
                answer.append(answer1[1] + answer2[1])
                return answer
        i *= (h / 3)
        y = i
        print("Значение интеграла ->", y)
        print("Число разбиения интеграла ->", n)
        di = (y1 - y) / (2 ** 4 - 1)
        y1 = y
    answer = []
    answer.append(y1)
    answer.append(n)
    return answer


def trapezoidMethod(userChoice):
    h0 = (userChoice[1][1] - userChoice[1][0]) / userChoice[3]
    n = userChoice[3]
    x0 = userChoice[1][0]
    x0 += h0
    i0 = 0
    while x0 < userChoice[1][1]:
        if checkConvergence(userChoice, x0):
            y0 = eval(userChoice[0].replace("x", "(" + str(x0) + ")"))
            i0 += y0
            x0 += h0
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = trapezoidMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = trapezoidMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer
    i0 += ((eval(userChoice[0].replace("x", "(" + str(userChoice[1][1]) + ")")) + eval(
        userChoice[0].replace("x", "(" + str(userChoice[1][0]) + ")"))) / 2)
    i0 *= h0
    y0 = i0
    print("Значение интеграла ->", y0)
    print("Число разбиения интеграла ->", n)
    n *= 2
    h1 = (userChoice[1][1] - userChoice[1][0]) / n
    x1 = userChoice[1][0] + h1
    i1 = 0
    while x1 < userChoice[1][1]:
        if checkConvergence(userChoice, x1):
            y1 = eval(userChoice[0].replace("x", "(" + str(x1) + ")"))
            i1 += y1
            x1 += h1
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = trapezoidMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = trapezoidMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer
    i1 += ((eval(userChoice[0].replace("x", "(" + str(userChoice[1][1]) + ")")) + eval(
        userChoice[0].replace("x", "(" + str(userChoice[1][0]) + ")"))) / 2)
    i1 *= h1
    y1 = i1
    di = (y0 - y1) / (2 ** 2 - 1)
    print("Значение интеграла ->", y1)
    print("Число разбиения интеграла ->", n)
    while abs(di) >= userChoice[2]:
        n *= 2
        h = (userChoice[1][1] - userChoice[1][0]) / n
        x = userChoice[1][0] + h
        i = 0
        while x < userChoice[1][1]:
            if checkConvergence(userChoice, x):
                y = eval(userChoice[0].replace("x", "(" + str(x) + ")"))
                i += y
                x += h
            else:
                userChoice1 = copy.deepcopy(userChoice)
                userChoice1[1][1] = userChoice[4]
                answer1 = trapezoidMethod(userChoice1)
                userChoice2 = copy.deepcopy(userChoice)
                userChoice2[1][0] = userChoice[5]
                answer2 = trapezoidMethod(userChoice2)
                answer = []
                answer.append(answer1[0] + answer2[0])
                answer.append(max(answer1[1], answer2[1]))
                return answer
        i += ((eval(userChoice[0].replace("x", "(" + str(userChoice[1][1]) + ")")) + eval(
            userChoice[0].replace("x", "(" + str(userChoice[1][0]) + ")"))) / 2)
        i *= h
        y = i
        print("Значение интеграла ->", y)
        print("Число разбиения интеграла ->", n)
        di = (y1 - y) / (2 ** 2 - 1)
        y1 = y
    answer = []
    answer.append(y1)
    answer.append(n)
    return answer


def mediumRectanglesMethod(userChoice):
    h0 = (userChoice[1][1] - userChoice[1][0]) / userChoice[3]
    n = userChoice[3]
    x0 = userChoice[1][0] + h0 / 2
    i0 = 0
    while x0 < userChoice[1][1]:
        if checkConvergence(userChoice, x0):
            y0 = eval(userChoice[0].replace("x", "(" + str(x0) + ")"))
            i0 += y0
            x0 += h0
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = mediumRectanglesMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = mediumRectanglesMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer
    i0 *= h0
    y0 = i0
    print("Значение интеграла ->", y0)
    print("Число разбиения интеграла ->", n)
    n *= 2
    h1 = (userChoice[1][1] - userChoice[1][0]) / n
    x1 = userChoice[1][0] + h1 / 2
    i1 = 0
    while x1 < userChoice[1][1]:
        if checkConvergence(userChoice, x1):
            y1 = eval(userChoice[0].replace("x", "(" + str(x1) + ")"))
            i1 += y1
            x1 += h1
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = mediumRectanglesMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = mediumRectanglesMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer
    i1 *= h1
    y1 = i1
    di = (y0 - y1) / (2 ** 2 - 1)
    print("Значение интеграла ->", y1)
    print("Число разбиения интеграла ->", n)
    while abs(di) >= userChoice[2]:
        n *= 2
        h = (userChoice[1][1] - userChoice[1][0]) / n
        x = userChoice[1][0] + h / 2
        i = 0
        while x < userChoice[1][1]:
            if checkConvergence(userChoice, x):
                y = eval(userChoice[0].replace("x", "(" + str(x) + ")"))
                i += y
                x += h
            else:
                userChoice1 = copy.deepcopy(userChoice)
                userChoice1[1][1] = userChoice[4]
                answer1 = mediumRectanglesMethod(userChoice1)
                userChoice2 = copy.deepcopy(userChoice)
                userChoice2[1][0] = userChoice[5]
                answer2 = mediumRectanglesMethod(userChoice2)
                answer = []
                answer.append(answer1[0] + answer2[0])
                answer.append(max(answer1[1], answer2[1]))
                return answer
        i *= h
        y = i
        print("Значение интеграла ->", y)
        print("Число разбиения интеграла ->", n)
        di = (y1 - y) / (2 ** 2 - 1)
        y1 = y
    answer = []
    answer.append(y1)
    answer.append(n)
    return answer


def leftRectanglesMethod(userChoice):
    h0 = (userChoice[1][1] - userChoice[1][0]) / userChoice[3]
    n = userChoice[3]
    x0 = userChoice[1][0]
    i0 = 0
    while x0 < userChoice[1][1]:
        if checkConvergence(userChoice, x0):
            y0 = eval(userChoice[0].replace("x", "(" + str(x0) + ")"))
            i0 += y0
            x0 += h0
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = leftRectanglesMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = leftRectanglesMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer
    i0 *= h0
    y0 = i0
    print("Значение интеграла ->", y0)
    print("Число разбиения интеграла ->", n)
    n *= 2
    h1 = (userChoice[1][1] - userChoice[1][0]) / n
    x1 = userChoice[1][0]
    i1 = 0
    while x1 < userChoice[1][1]:
        if checkConvergence(userChoice, x1):
            y1 = eval(userChoice[0].replace("x", "(" + str(x1) + ")"))
            i1 += y1
            x1 += h1
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = leftRectanglesMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = leftRectanglesMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer

    i1 *= h1
    y1 = i1
    di = (y0 - y1) / (2 ** 2 - 1)
    print("Значение интеграла ->", y1)
    print("Число разбиения интеграла ->", n)
    while abs(di) >= userChoice[2]:
        n *= 2
        h = (userChoice[1][1] - userChoice[1][0]) / n
        x = userChoice[1][0]
        i = 0
        while x < userChoice[1][1]:
            if checkConvergence(userChoice, x):
                y = eval(userChoice[0].replace("x", "(" + str(x) + ")"))
                i += y
                x += h
            else:
                userChoice1 = copy.deepcopy(userChoice)
                userChoice1[1][1] = userChoice[4]
                answer1 = leftRectanglesMethod(userChoice1)
                userChoice2 = copy.deepcopy(userChoice)
                userChoice2[1][0] = userChoice[5]
                answer2 = leftRectanglesMethod(userChoice2)
                answer = []
                answer.append(answer1[0] + answer2[0])
                answer.append(max(answer1[1], answer2[1]))
                return answer

        i *= h
        y = i
        print("Значение интеграла ->", y)
        print("Число разбиения интеграла ->", n)
        di = (y1 - y) / (2 ** 2 - 1)
        y1 = y
    answer = []
    answer.append(y1)
    answer.append(n)
    return answer


def rightRectanglesMethod(userChoice):
    h0 = (userChoice[1][1] - userChoice[1][0]) / userChoice[3]
    n = userChoice[3]
    x0 = userChoice[1][0]
    x0 += h0
    i0 = 0
    while x0 <= userChoice[1][1]:
        if checkConvergence(userChoice, x0):
            y0 = eval(userChoice[0].replace("x", "(" + str(x0) + ")"))
            i0 += y0
            x0 += h0
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = rightRectanglesMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = rightRectanglesMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer

    i0 *= h0
    y0 = i0
    print("Значение интеграла ->", y0)
    print("Число разбиения интеграла ->", n)
    n *= 2
    h1 = (userChoice[1][1] - userChoice[1][0]) / n
    x1 = userChoice[1][0] + h1
    i1 = 0
    while x1 <= userChoice[1][1]:
        if checkConvergence(userChoice, x1):
            y1 = eval(userChoice[0].replace("x", "(" + str(x1) + ")"))
            i1 += y1
            x1 += h1
        else:
            userChoice1 = copy.deepcopy(userChoice)
            userChoice1[1][1] = userChoice[4]
            answer1 = rightRectanglesMethod(userChoice1)
            userChoice2 = copy.deepcopy(userChoice)
            userChoice2[1][0] = userChoice[5]
            answer2 = rightRectanglesMethod(userChoice2)
            answer = []
            answer.append(answer1[0] + answer2[0])
            answer.append(max(answer1[1], answer2[1]))
            return answer

    i1 *= h1
    y1 = i1
    di = (y0 - y1) / (2 ** 2 - 1)
    print("Значение интеграла ->", y1)
    print("Число разбиения интеграла ->", n)
    while abs(di) >= userChoice[2]:
        n *= 2
        h = (userChoice[1][1] - userChoice[1][0]) / n
        x = userChoice[1][0] + h
        i = 0
        while x <= userChoice[1][1]:
            if checkConvergence(userChoice, x):
                y = eval(userChoice[0].replace("x", "(" + str(x) + ")"))
                i += y
                x += h
            else:
                userChoice1 = copy.deepcopy(userChoice)
                userChoice1[1][1] = userChoice[4]
                answer1 = rightRectanglesMethod(userChoice1)
                userChoice2 = copy.deepcopy(userChoice)
                userChoice2[1][0] = userChoice[5]
                answer2 = rightRectanglesMethod(userChoice2)
                answer = []
                answer.append(answer1[0] + answer2[0])
                answer.append(max(answer1[1], answer2[1]))
                return answer
        i *= h
        y = i
        print("Значение интеграла ->", y)
        print("Число разбиения интеграла ->", n)
        di = (y1 - y) / (2 ** 2 - 1)
        y1 = y
    answer = []
    answer.append(y1)
    answer.append(n)
    return answer


def chooseMethod(userChoice):
    userChoice.append(4)
    checkerForMethod = True
    while checkerForMethod:
        print(
            "Выберите метод: \n \"1\" -> метод левых прямоугольников\n \"2\" -> метод правых прямоугольников\n \"3\" -> метод средних прямоугольников \n \"4\" -> метод трапеций\n \"5\" -> метод Симпсона:")
        selection = input()
        if selection == "1":
            checkerForMethod = False
            answer = leftRectanglesMethod(userChoice)
            if answer != False:
                print("Ответ:")
                print("Значение интеграла ->", answer[0])
                print("Число разбиения интеграла ->", answer[1])

        elif selection == "2":
            checkerForMethod = False
            answer = rightRectanglesMethod(userChoice)
            if answer != False:
                print("Ответ:")
                print("Значение интеграла ->", answer[0])
                print("Число разбиения интеграла ->", answer[1])

        elif selection == "3":
            checkerForMethod = False
            answer = mediumRectanglesMethod(userChoice)
            if answer != False:
                print("Ответ:")
                print("Значение интеграла ->", answer[0])
                print("Число разбиения интеграла ->", answer[1])

        elif selection == "4":
            checkerForMethod = False
            answer = trapezoidMethod(userChoice)
            if answer != False:
                print("Ответ:")
                print("Значение интеграла ->", answer[0])
                print("Число разбиения интеграла ->", answer[1])

        elif selection == "5":
            checkerForMethod = False
            answer = simpsonMethod(userChoice)
            if answer != False:
                print("Ответ:")
                print("Значение интеграла ->", answer[0])
                print("Число разбиения интеграла ->", answer[1])

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
            chooseMethod(userChoice)


def checkNumbers(numbers):
    try:
        numbers = numbers.split()
        numbers = list(map(float, numbers))
        if len(numbers) == 2:
            if numbers[0] < numbers[1]:
                checkerForNumbers = True
                return checkerForNumbers
            else:
                print("Первое число в строке должно быть меньше, чем второе!")
        else:
            print("Количество введённых чисел в строке должно быть равно 2!")

    except Exception:
        print("Пределы интегрирования должны быть числами!")


def chooseInterval(userChoice):
    checker = False
    while checker == False:
        print("Введите через пробел пределы интегрирования:")
        numbers = input()
        if checkNumbers(numbers) == True:
            checker = True
            numbers = numbers.split()
            numbers = list(map(float, numbers))
            userChoice.append(numbers)
            if checkConvergence(userChoice, numbers[0]) == True and checkConvergence(userChoice, numbers[1]) == True:
                chooseE(userChoice)


while (True):
    userChoice = []
    print(
        "Выберите функцию, интеграл которой нужно вычислить: \n \"1\" -> 5.74x^3-2.95x^2-10.28x+4.23\n \"2\" -> x^3-x+4\n \"3\" -> 1/x:")
    selection = input()
    if selection == "1":
        userChoice.append("5.74*x**3-2.95*x**2-10.28*x+4.23")
        chooseInterval(userChoice)
    elif selection == "2":
        userChoice.append("x**3-x+4")
        chooseInterval(userChoice)
    elif selection == "3":
        userChoice.append("1/x")
        chooseInterval(userChoice)
    else:
        print("Я Вас не понимаю :(")
