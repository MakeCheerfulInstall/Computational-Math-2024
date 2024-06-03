from random import randint, random


# Вычисление знака числа
def sign(num):
    if num < 0:
        return "-"
    else:
        return "+"


# Вывод матрицы
def print_matrix(m, v, num):
    for a in range(num):
        for b in range(num):
            if b == num - 1:
                print(f"{sign(m[a][b])} {round(m[a][b], 3)}x({num}) = {round(v[a], 3)}")
            else:
                print(f"{sign(m[a][b])} {abs(round(m[a][b], 3))}x({b + 1}) ", end="")


# Проверка отклонения
def checker(appr, vect, ee):
    m = 0
    for a in range(n):
        if m < abs(appr[a] - vect[a]):
            m = abs(appr[a] - vect[a])
    return [m < ee, round(m, 3)]


# Проверка условия преобладания диагональных элементов
def check_diagonal_dominance(m):
    diagonal_sum = 0
    off_diagonal_sum = 0
    for a in range(len(m)):
        diagonal_sum += abs(m[a][a])
        off_diagonal_sum += sum([abs(m[a][b]) for b in range(len(m)) if b != a])

    return all(diagonal_sum > off_diagonal_sum - abs(m[a][a]) for a in range(len(m)))


# Генерация случайной матрицы
def random_matrix(num):
    matr = [[randint(-100, 100) for _ in range(num)] for _ in range(num)]
    diagonal_sum = sum(abs(matr[a][a]) for a in range(num))
    for a in range(num):
        for j in range(num):
            if a != j:
                matr[a][j] = randint(-100, 100) * 0.01
    for a in range(num):
        matr[a][a] = randint(-100, 100) * (diagonal_sum + 0.01)
    return matr


e = 0.01  # Погрешность
n = 3  # n <= 20, размер матрицы
matrix = [[2, 2, 10], [10, 1, 1], [2, 10, 1]]  # Матрица
vector = [14, 12, 13]  # Вектор свободных членов
datamode = int(
    input("Выберите режим ввода данных:\n1. С клавиатуры\n2. Из файла\n3. Сгенерировать случайную матрицу\n> "))

# Ввод данных
if datamode == 1 or datamode == 3:
    e = float(input("Введите погрешность: "))
    n = int(input("Введите размерность матрицы: "))
    if n > 20:
        raise ValueError("Недопустимое значение.")
if datamode == 1:
    print("Введите коэффициенты матрицы в строку через пробел:")
    for i in range(n):
        print(f"Строка {i + 1}: ")
        matrix[i] = list(map(int, input().split()))
    print("Введите значения вектора свободных членов в строку через пробел:")
    vector = list(map(int, input().split()))
elif datamode == 3:
    matrix = random_matrix(n)
    vector = [randint(-100, 100) for _ in range(n)]
elif datamode != 2:
    raise Exception("Недопустимое значение.")

# Исходная матрица
print("Исходная матрица:")
print_matrix(matrix, vector, n)

# Перестановка строк/столбцов до достижения диагонального преобладания
diagonal_check = False
for i in range(n):
    if check_diagonal_dominance(matrix):
        diagonal_check = True
        break
    else:
        # Перестановка строк
        matrix = matrix[1:] + [matrix[0]]
        vector = vector[1:] + [vector[0]]

if diagonal_check:
    print("Диагональное преобладание достигнуто:")
    print_matrix(matrix, vector, n)
else:
    raise Exception("Невозможно достичь диагонального преобладания путем перестановок.")

# Преобразование матрицы
newmatrix = [[float(0) for j in range(n)] for i in range(n)]
newvector = [float(0) for i in range(n)]
for i in range(n):
    for j in range(n):
        if i == j:
            newmatrix[i][j] = 0
        else:
            newmatrix[i][j] = -matrix[i][j] / matrix[i][i]
    newvector[i] = vector[i] / matrix[i][i]

print("Преобразованная матрица:")
print_matrix(newmatrix, newvector, n)

# Выражение диагональных иксов
print("Выражаем диагональные иксы...")
for i in range(n):
    print(f"x({i + 1}) = ", end="")
    for j in range(n):
        if i != j:
            print(f"{sign(newmatrix[i][j])} {abs(round(newmatrix[i][j], 3))}x({j + 1}) ", end="")
    print(f"{sign(newvector[i])} {abs(round(newvector[i], 3))}")

# Норма матрицы
c = 0
for row in newmatrix:
    row_sum = sum(abs(element) for element in row)
    c = max(c, row_sum)
print(f"Норма преобразованной матрицы C: {round(c, 3)}")
if c < 1:
    print("Условие сходимости выполнено!")
else:
    raise Exception("Не выполнено условие сходимости.")

approx = [float(0) for i in range(n)]
oldapprox = [float(0) for i in range(n)]
counter = 0

while True:
    approx = [float(0) for i in range(n)]
    counter += 1

    for i in range(n):
        for j in range(n):
            if i != j:
                approx[i] += newmatrix[i][j] * oldapprox[j]
        approx[i] += newvector[i]
    if checker(approx, oldapprox, e)[0]:
        break
    else:
        oldapprox = approx

print(f"Количество итераций: {counter}")
print("Приближённое решение задачи:")
for i in range(n):
    print(f"x({i}) = {round(approx[i], 3)}")
print(f"Абсолютное отклонение: {checker(approx, oldapprox, e)[1]} < e")
