import Hordes
import Newton
import SimpleIterations
import SystemSimpleIterations
from Equations import graph

e = 0.01  # Точность

equation_type = int(input("Выберите задачу:\n1. Нелинейное уравнение.\n2. Система нелинейных уравнений.\n> "))
if equation_type == 1:
    equation = int(
        input(
            "Выберите уравнение:\n1. -1,38x^3 - 1,93x^2 - 15,28x - 3,72 = 0\n2. - 10sin(x) = 0\n3. 4x^3 - "
            "7x + 5 = 0\n4. x^3 - x + 4 = 0\n5. -1,38x^3 - 5,42x^2 + 2,57x + 10,95 = 0\n> "))
    if equation not in [1, 2, 3, 4, 5]:
        raise ValueError("Неизвестное уравнение.")

    lower_b = -2  # Нижняя граница
    upper_b = -1  # Верхняя граница

    datamode = int(input("Выберите режим ввода данных:\n1. С клавиатуры.\n2. Из файла.\n> "))
    if datamode == 1:
        lower_b = int(input("Введите нижнюю границу интервала: "))
        upper_b = int(input("Введите верхнюю границу интервала: "))
        e = float(input("Введите точность: "))
    elif datamode != 2:
        raise ValueError("Неверный режим ввода данных.")

    method = int(input("Выберите метод:\n1. Метод хорд.\n2. Метод Ньютона.\n3. Метод простой итерации.\n> "))
    if method == 1:
        result = Hordes.hordes(lower_b, upper_b, equation, e)
    elif method == 2:
        result = Newton.newton(lower_b, upper_b, equation, e)
    elif method == 3:
        result = SimpleIterations.simpleIterations(lower_b, upper_b, equation, e)
    else:
        raise ValueError("Неизвестный метод.")
    print(f"Количество итераций: {result[0]}")
    print(round(result[1], 5))
    graph(-20.0, 20.0, 200, equation)
elif equation_type == 2:
    equation = int(
        input(
            "Выберите систему уравнений:\n1. 0.1x(1)^2 + x(1) + 0.2x(2)^2 - 0.3 = 0; 0.2x(1)^2 + x(2) + 0.1x(1)x(2) - "
            "0.7 = 0\n2. cos(x(2)) + x(1) - 1,5 = 0; 2x(2) - sin(x(1) - 0,5) - 1 = 0\n> "))

    datamode = int(input("Выберите режим ввода данных:\n1. С клавиатуры.\n2. Из файла.\n> "))
    if datamode == 1:
        e = float(input("Введите точность: "))
    elif datamode != 2:
        raise ValueError("Неверный режим ввода данных.")
    prev_x1 = int(input("Введите начальное приближение x1-0: "))
    prev_x2 = int(input("Введите начальное приближение x2-0: "))
    result = SystemSimpleIterations.systemSimpleIterations(prev_x1, prev_x2, equation, e)
    print(f"Количество итераций: {result[0]}\nx1: {round(result[1], 5)}\nx2: {round(result[2], 5)}")
    graph(-20.0, 20.0, 200, equation, True)
