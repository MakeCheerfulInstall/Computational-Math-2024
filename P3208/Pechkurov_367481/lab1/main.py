import random

from _decimal import getcontext


def dot_product(a, b):
    # Функция для вычисления скалярного произведения двух векторов
    result = 0
    for i in range(len(a)):
        result += a[i] * b[i]
    return result


def norm(vector):
    # Функция для вычисления нормы вектора
    result = 0
    for val in vector:
        result += val ** 2
    return result ** 0.5


def gauss_seidel(A, b, x0, tol, max_iter):
    # Функция для решения СЛАУ методом Гаусса-Зейделя
    n = len(b)
    x = x0.copy()
    iterations = 0
    residual = float('inf')

    while residual > tol and iterations < max_iter:
        # Итеративный процесс метода Гаусса-Зейделя
        x_new = x.copy()
        for i in range(n):
            x_new[i] = (b[i] - dot_product(A[i][:i], x_new[:i]) - dot_product(A[i][i + 1:], x[i + 1:])) / A[i][i]

        residual = norm([x_new[i] - x[i] for i in range(n)])
        x = x_new.copy()
        iterations += 1

    return x, iterations


def check_diagonal_dominance(A):
    # Функция для проверки диагонального преобладания матрицы
    n = len(A)
    for i in range(n):
        diagonal_value = abs(A[i][i])
        sum_other_elements = sum(abs(A[i][j]) for j in range(n) if j != i)
        if diagonal_value <= sum_other_elements:
            return False
    return True


def rearrange_for_diagonal_dominance(A, b):
    # Функция для перестановки строк/столбцов с целью достижения диагонального преобладания
    n = len(b)
    row_order = sorted(range(n), key=lambda i: -abs(A[i][i]))
    A_reordered = [A[i] for i in row_order]
    b_reordered = [b[i] for i in row_order]
    return A_reordered, b_reordered



def load_matrix_from_file(filename):
    # Функция для загрузки матрицы и точности из файла
    try:
        with open(filename, 'r') as file:
            n = int(file.readline())  # Размерность матрицы
            tol = float(file.readline().replace(',', '.'))  # Точность
            A = []
            b = []
            for _ in range(n):
                row = list(map(lambda x: float(x.replace(',', '.')),
                               file.readline().strip().split()))  # Коэффициенты строк матрицы
                A.append(row[:-1])
                b.append(row[-1])
            return A, b, tol
    except FileNotFoundError:
        print("Ошибка: Файл не найден.")
        return None, None, None


def main():
    getcontext().prec = 28  # Установка точности вычислений
    # Основная функция программы
    method = input("Выберите способ загрузки матрицы (клавиатура/файл/случайная): ").lower()

    if method == "клавиатура":
        # Ввод матрицы с клавиатуры
        try:
            n = int(input("Введите размерность матрицы: "))
            if n > 20:
                print("Ошибка: Размерность матрицы должна быть не более 20.")
                return
        except ValueError:
            print("Ошибка: Введите корректное целое число для размерности матрицы.")
            return

        A = [[0] * n for _ in range(n)]
        b = [0] * n
        try:
            for i in range(n):
                row = list(map(lambda x: float(x.replace(',', '.')),
                               input(f"Введите коэффициенты {i + 1} строки через пробел: ").split()))
                A[i] = row[:-1]
                b[i] = row[-1]

        except ValueError:
            print(f"Ошибка: Некорректный ввод коэффициентов в строке {i + 1}. Проверьте введенные данные.")
            return
        try:
            tol = float(input("Введите точность: ").replace(',', '.'))
        except ValueError:
            print(f"Ошибка: Некорректный ввод точности. Проверьте введенные данные.")
            return
    elif method == "файл":
        # Загрузка матрицы и точности из файла
        filename = input("Введите имя файла с матрицей: ")
        try:
            A, b, tol = load_matrix_from_file(filename)
            if A is None or b is None or tol is None:
                return
            n = len(A)
            if n > 20:
                print("Ошибка: Размерность матрицы должна быть не более 20.")
                return
        except ValueError:
            print("Ошибка: Некорректный ввод коэффициентов или точности в файле. Проверьте введенные данные.")
            return
    elif method == "случайная":
        try:
            n = int(input("Введите размерность матрицы: "))
            if n > 20:
                print("Ошибка: Размерность матрицы должна быть не более 20.")
                return
        except ValueError:
            print("Ошибка: Введите корректное целое число для размерности матрицы.")
            return

        A = generate_random_matrix(n)
        b = [random.uniform(-100, 100) for _ in range(n)]
        try:
            tol = float(input("Введите точность: ").replace(',', '.'))
        except ValueError:
            print(f"Ошибка: Некорректный ввод точности. Проверьте введенные данные.")
            return

        print("Сгенерированная матрица A:")
        print_matrix(A)
        print("Сгенерированный вектор b:")
        print(b)
    else:
        print("Некорректный выбор способа загрузки матрицы.")
        return

    try:
        max_iter = int(input("Введите максимальное количество итераций: "))
    except ValueError:
        print(f"Ошиибка: Некорректный ввод кол-ва итераций. Проверьте введенные данные.")
        return


    if not check_diagonal_dominance(A):
        # Проверка и перестановка строк/столбцов для достижения диагонального преобладания
        print("В исходной матрице отсутствует диагональное преобладание. Переставляем строки/столбцы.")
        A, b = rearrange_for_diagonal_dominance(A, b)
        if not check_diagonal_dominance(A):
            print("Ошибка: Невозможно достичь диагонального преобладания.")
            return

    x0 = [0] * len(b)

    x, iteration, = gauss_seidel(A, b, x0, tol, max_iter)

    print("Решение:")
    for i, sol in enumerate(x, start=1):
        print(f"x{i} = {sol}")

    print(f"Количество итераций: {iteration}")
def generate_random_matrix(n):
    """
    Генерирует случайную матрицу размером n x n.

    :param n: Размерность матрицы
    :type n: int
    :return: Случайная матрица размером n x n
    :rtype: list[list[float]]
    """
    return [[random.uniform(-100, 100) for _ in range(n)] for _ in range(n)]


def print_matrix(matrix):
    """
    Выводит матрицу на экран.

    :param matrix: Матрица
    :type matrix: list[list[float]]
    """
    for row in matrix:
        print(' '.join(map(str, row)))


if __name__ == "__main__":
    main()