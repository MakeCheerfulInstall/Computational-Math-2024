import random


def checkDiagonalDominance(matrix, n):
    counter = 0
    sum = 0
    for i in range(n):
        for j in range(n):
            sum += abs(matrix[i][j])
        if sum - 2 * abs(matrix[i][i]) < 0:
            counter += 1
        sum = 0
    if counter == n:
        return True
    else:
        return False


def printMatrix(matrix, n):
    for i in range(n):
        for j in range(n + 1):
            print(matrix[i][j], end=" ")
        print("\n")


def calculateSum(line, n, matrix, xArray):
    sum = 0
    for i in range(n):
        sum += xArray[i] * matrix[line][i]
    sum -= xArray[line] * matrix[line][line]
    return sum


def generalMathFuntion(n, e, M, matrix):
    for i in range(n - 1):
        if checkDiagonalDominance(matrix, n) == True:
            print("Матрица прошла проверку на диаганальное преобладание :)")
            break
        else:
            firstLine = matrix[i]
            matrix.pop(i)
            matrix.append(firstLine)
            print("Проверка на диаганальное преобладание не пройдена :(")
            print("Новая матрица:")
            printMatrix(matrix, n)
    eArray = []
    counter = 0
    xArray = []
    xArrayLast = []
    eResult = False
    err = False
    for i in range(n):
        x = matrix[i][-1] / matrix[i][i]
        xArray.append(x)
        xArrayLast.append(x)
    for i in range(n):
        eArray.append(0)
    print("Итерация -> x1, x2, ... xn -> Максимальное абсолютное отклонение")
    while counter < M and eResult == False and err == False:
        for i in range(n):
            xArray[i] = (matrix[i][-1] - calculateSum(i, n, matrix, xArray)) / matrix[i][i]
        for i in range(n):
            eArray[i] = abs(xArray[i] - xArrayLast[i])
        if max(eArray) < e:
            eResult = True
        for i in range(n):
            xArrayLast[i] = xArray[i]
        counter += 1
        print(counter, "->", xArray, "->", max(eArray))
        if eArray[-1] > eArray[-2]:
            err = True
    answer = []
    answer.append(counter)
    answer.append(xArray)
    answer.append(eArray)
    answer.append(err)
    print(answer)
    return answer

def checkN(n):
    try:
        n = int(n)
        if n > 0 and n <= 20:
            checkerForN = True
            return checkerForN
        else:
            print("Порядок матрицы должен быть больше нуля и <= 20!")
    except Exception:
        print("Порядок матрицы должен быть целым числом!")


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


def checkM(M):
    try:
        M = int(M)
        if M > 0:
            checkerForM = True
            return checkerForM
        else:
            print("Максимальное число итераций должно быть больше нуля!")
    except Exception:
        print("Максимальное число итераций должно быть целым числом!")


def checkNumbers(numbers, n, i):
    try:
        numbers = numbers.split()
        numbers = list(map(float, numbers))
        if len(numbers) == n + 1:
            checkerForNumbers = True
            return checkerForNumbers
        else:
            print("Количество введённых чисел в строке", i + 1, "должно быть равно", n + 1)
    except Exception:
        print("Коэффициенты и правая часть уравнения должны быть числами!")


def consoleInput(n, matrix):
    for i in range(n):
        checkerForNumbers = False
        while checkerForNumbers == False:
            print("Введите через пробел коэффициенты и правую часть уравнения для", i + 1, "строки:")
            numbers = input()
            if checkNumbers(numbers, n, i) == True:
                checkerForNumbers = True
                numbers = numbers.split()
                numbers = list(map(float, numbers))
        matrix.append(numbers)
    return matrix


def randomInput(n, matrix):
    for i in range(n):
        numbers = []
        for i in range(n + 1):
            number = round(random.uniform(-100, 100), 5)
            numbers.append(number)
        matrix.append(numbers)
    return matrix


def consoleInputForNME(inputSelection):
    checkerForN = False
    while checkerForN == False:
        print("Введите порядок матрицы:")
        n = input()
        if checkN(n) == True:
            checkerForN = True
            n = int(n)

    checkerForE = False
    while checkerForE == False:
        print("Введите погрешность вычислений:")
        e = input()
        if checkE(e) == True:
            checkerForE = True
            e = float(e)

    checkerForM = False
    while checkerForM == False:
        print("Введите максимальное число итераций:")
        M = input()
        if checkM(M) == True:
            checkerForM = True
            M = int(M)

    matrix = []
    if inputSelection == "1":
        matrix = consoleInput(n, matrix)
    elif inputSelection == "3":
        matrix = randomInput(n, matrix)
    print("Исходная матрица:")
    printMatrix(matrix, n)
    answer = generalMathFuntion(n, e, M, matrix)
    if answer[0] >= M:
        print("Не удалось получить ответ за указанное количество итераций")
    elif answer[3] == True:
        print("Погрешность увеличивается")
    else:
        print("Ответ:")
        print(answer[0], "->", answer[1], "->", max(answer[2]))


def readFile(file):
    try:
        while (True):
            n = file.readline().strip()
            n = int(n)
            e = file.readline().strip()
            e = float(e)
            M = file.readline().strip()
            M = int(M)
            matrix = []
            for i in range(n):
                numbers = file.readline().strip()
                if checkNumbers(numbers, n, i) == True:
                    numbers = numbers.split()
                    numbers = list(map(float, numbers))
                    matrix.append(numbers)
            if checkM(M) and checkN(n) and checkE(e) and len(matrix) == n:
                print("Исходная матрица:")
                printMatrix(matrix, n)
                counter, xArray, eArray, err = generalMathFuntion(n, e, M, matrix)
                if counter >= M:
                    print("Не удалось получить ответ за указанное количество итераций")
                elif err == True:
                    print("Погрешность увеличивается")
                else:
                    print("Ответ:")
                    print(counter, "->", xArray, "->", max(eArray))
    except Exception:
        print("Некорректные данные в файле или данных не хватает!")


def fileInput():
    checkerForFile = False
    while checkerForFile == False:
        print("Введите путь к файлу:")
        fileInput = input()
        try:
            file = open(fileInput, "r")
            checkerForFile = True
            readFile(file)
        except Exception:
            print("Не получилось найти файл с таким именем!")


while (True):
    checkerForInputSelection = False
    while checkerForInputSelection == False:
        print(
            "Введите \"1\" для ввода входных данных через консоль, \"2\" для чтения из файла, \"3\" для генерации рандомной матрицы, \"4\" для выхода из программы:")
        inputSelection = input()
        if inputSelection == "1":
            checkerForInputSelection = True
            consoleInputForNME(inputSelection)
        elif inputSelection == "2":
            checkerForInputSelection = True
            fileInput()
        elif inputSelection == "3":
            checkerForInputSelection = True
            consoleInputForNME(inputSelection)
        elif inputSelection == "4":
            checkerForInputSelection = True
            exit()
        else:
            print("Я Вас не понимаю :(")
