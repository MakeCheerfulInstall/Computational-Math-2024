import os
from fractions import Fraction
from matrix_input import *

# Функция для выполнения операции умножения строки матрицы
def string_multiplication(matrix, row_index, k, j):
    # Проверяем, находится ли индекс строки в допустимых пределах
    if row_index < 0 or row_index >= len(matrix):
        raise IndexError("Недопустимый индекс строки")
    # Создаем копию строки, чтобы избежать изменения оригинальной матрицы
    new_row = matrix[row_index][:]

    # Преобразуем k и j в объекты Fraction, если они не являются целыми числами
    if (not isinstance(k, int)) and (k == 0):
        try:
            k = int(k)
        except ValueError:
            k = Fraction(k)
    if (not isinstance(j, int)) and (j == 0):
        try:
            j = int(j)
        except ValueError:
            j = Fraction(j)
    k = Fraction(k)
    j = Fraction(j)
    # Выполняем операцию умножения для каждого элемента в строке
    for i in range(len(new_row)):
        new_row[i] *= Fraction(-k, j)

    return new_row

# Функция для обмена строк в матрице
def swap_rows(matrix, i, j):
    # Проверяем, находятся ли индексы строк в допустимых пределах
    if i < 0 or i >= len(matrix) or j < 0 or j >= len(matrix):
        raise IndexError("Недопустимый индекс строки")
    # Обмениваем строки
    matrix[i], matrix[j] = matrix[j], matrix[i]

# Функция для выполнения операции сложения строк матрицы
def string_addition(matrix, row_index, adding_row):
    # Проверяем, находится ли индекс строки в допустимых пределах
    if row_index < 0 or row_index >= len(matrix):
        raise IndexError("Недопустимый индекс строки")
    # Выполняем операцию сложения для каждого элемента в строке
    for i in range(len(matrix[row_index])):
        matrix[row_index][i] += adding_row[i]
    return matrix

# Функция для выполнения метода Гаусса над матрицей
def method_Gauss(matrix):
    swap_counts = 0
    for i in range(len(matrix)):
        if matrix[i][i] != 0:
            for j in range(i + 1, len(matrix)):
                # Выполняем операции над строками для исключения элементов ниже диагонали
                buffer_row = string_multiplication(
                    matrix, i, matrix[j][i], matrix[i][i]
                )
                matrix = string_addition(matrix, j, buffer_row)
        else:
            for p in range(i + 1, len(matrix)):
                if matrix[p][i] != 0:
                    # Если диагональный элемент равен нулю, меняем строки для нахождения ненулевого элемента
                    swap_counts += 1
                    swap_rows(matrix, i, p)
                    break
                else:
                    raise ValueError("Нет ненулевого диагонального элемента")
            for j in range(i + 1, len(matrix)):
                # Выполняем операции над строками для исключения элементов ниже диагонали после обмена строк
                buffer_row = string_multiplication(
                    matrix, i, matrix[j][i], matrix[i][i]
                )
                matrix = string_addition(matrix, j, buffer_row)

    return matrix, swap_counts

# Функция для проверки, является ли матрица треугольной
def is_triangular(matrix):
    n = len(matrix)
    for i in range(n):
        for j in range(i):
            # Проверяем, есть ли ненулевые элементы под диагональю
            if matrix[i][j] != 0:
                return False
    return True

# Функция для решения треугольной матрицы с использованием метода обратной подстановки
def reversal_method(matrix):
    n = len(matrix)
    x = [0] * n  # Создаем список для хранения решений

    for i in range(n - 1, -1, -1):  # Начинаем с последнего уравнения
        sum = 0
        for j in range(i + 1, n):  # Итерируем по коэффициентам выше диагонали
            sum += matrix[i][j] * x[j]
        # Вычисляем значение переменной с помощью обратной подстановки
        x[i] = (matrix[i][-1] - sum) / matrix[i][i]
    # Преобразуем решения в значения типа float
    x = list(map(float, x))
    return x

# Функция для вычисления определителя треугольной матрицы
def triangular_matrix_determinant(matrix):
    det = 1
    for i in range(len(matrix)):
        det *= matrix[i][i]
    return det

# Функция для вычисления остатков системы уравнений
def calculated_residuals(matrix, answers):
    values = []
    for row in matrix:
        sum = 0
        for j in range(len(row) - 1):
            # Вычисляем сумму произведений коэффициентов и решений
            sum += row[j] * answers[j]
        # Вычисляем остаток для каждого уравнения
        values.append(row[-1] - sum)
    return values