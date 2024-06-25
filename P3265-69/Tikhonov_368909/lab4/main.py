import math
from Linear import linear
from Exponential import exponential
from Equations import create_graph
from Logarithmic import logarithmic
from Power import power
from Polinomial2 import polynomial2
from Polinomial3 import polynomial3

datamode = int(input("Выберите способ ввода данных:\n1. С клавиатуры.\n2. Из файла.\n> "))

if datamode == 1:
    xarr = list(map(int, input("Введите количество x-ов от 8 до 12: ").strip().split()))
    yarr = list(map(int, input("Введите количество y-ов от 8 до 12: ").strip().split()))

    if len(xarr) != len(yarr) or not 8 <= len(xarr) <= 12:
        raise ValueError("Неверное значение.")

    n = len(xarr)
if datamode == 2:
    table = int(input(
        "Выберите таблицу исходных значений:\n1.\nX: [1.2, 2.9, 4.1, 5.5, 6.7, 7.8, 9.2, 10.3]\nY: [7.4, 9.5, 11.1, "
        "12.9, 14.6, 17.3, 18.2, 20.7]\n> "))
    if table == 1:
        xarr = [1.2, 2.9, 4.1, 5.5, 6.7, 7.8, 9.2, 10.3]
        yarr = [7.4, 9.5, 11.1, 12.9, 14.6, 17.3, 18.2, 20.7]
        n = 8
    else:
        raise ValueError("Неверное значение.")
else:
    raise ValueError("Неизвестный режим ввода данных.")

best_equation = ['', math.inf]

approx = linear(xarr, yarr, n)
if approx[3] < best_equation[1]:
    best_equation[0] = "Линейное"
    best_equation[1] = approx[3]

print(
    f"Линейная аппроксимация:\nP(x): {approx[0]}\ne: {approx[1]}\nr: {approx[2]}\nCKO: {approx[3]}\nR^2: {approx[4]}")
create_graph(xarr, yarr, approx[0])

approx = exponential(xarr, yarr, n)
if approx[2] < best_equation[1]:
    best_equation[0] = "Экспоненциальное"
    best_equation[1] = approx[2]
print(
    f"Экспоненциальная аппроксимация:\nP(x): {approx[0]}\ne: {approx[1]}\nCKO: {approx[2]}\nR^2: {approx[3]}")
create_graph(xarr, yarr, approx[0])

approx = logarithmic(xarr, yarr, n)
if approx[2] < best_equation[1]:
    best_equation[0] = "Логарифмическое"
    best_equation[1] = approx[2]
print(
    f"Логарифмическая аппроксимация:\nP(x): {approx[0]}\ne: {approx[1]}\nCKO: {approx[2]}\nR^2: {approx[3]}")
create_graph(xarr, yarr, approx[0])

approx = power(xarr, yarr, n)
if approx[2] < best_equation[1]:
    best_equation[0] = "Степенное"
    best_equation[1] = approx[2]
print(
    f"Степенная аппроксимация:\nP(x): {approx[0]}\ne: {approx[1]}\nCKO: {approx[2]}\nR^2: {approx[3]}")
create_graph(xarr, yarr, approx[0])

approx = polynomial2(xarr, yarr, n)
if approx[2] < best_equation[1]:
    best_equation[0] = "Полином 2-й степени"
    best_equation[1] = approx[2]
print(
    f"Квадратичная аппроксимация:\nP(x): {approx[0]}\ne: {approx[1]}\nCKO: {approx[2]}\nR^2: {approx[3]}")
create_graph(xarr, yarr, approx[0])

approx = polynomial3(xarr, yarr, n)
if approx[2] < best_equation[1]:
    best_equation[0] = "Полином 3-й степени"
    best_equation[1] = approx[2]
print(
    f"Квадратичная аппроксимация:\nP(x): {approx[0]}\ne: {approx[1]}\nCKO: {approx[2]}\nR^2: {approx[3]}")
create_graph(xarr, yarr, approx[0])

print(f"Лучшее уравнение: {best_equation[0]}")
