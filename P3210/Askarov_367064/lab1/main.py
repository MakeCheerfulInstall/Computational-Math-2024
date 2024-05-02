from random import randint
#
# print(20)
# for i in range(20):
#     print(" ".join(str(randint(1, 100)) for j in range(20)))
#
# print(" ".join(str(randint(1, 100)) for j in range(20)))
#
# quit()
# n = int(input())


mode = int(input("Введите режим работы (0 - файл, 1 - консоль):\n"))


if mode:
    n = int(input("Введите размерность: "))
    print("Введите матрицу:")
    initial_matrix = [list(map(int, input().split())) for _ in range(n)]
    # initial_matrix = [[randint(1, 100) for j in range(n)] for _ in range(n)]
    print("Введите вектор справа от равно:")
    res = list(map(int, input().split()))
    # res = [randint(1, 100) for _ in range(n)]
else:
    with open("in.txt") as file:
        n = int(file.readline())
        initial_matrix = [list(map(int, file.readline().strip().split())) for _ in range(n)]
        # initial_matrix = [[randint(1, 100) for j in range(n)] for _ in range(n)]

        res = list(map(int, file.readline().strip().split()))
        # res = [randint(1, 100) for _ in range(n)]


def add_col(m, col):
    for k, row in enumerate(m):
        row.append(col[k])
    return m


def remove_last_col(m):
    for k, row in enumerate(m):
        row.pop()
    return m


def plus(src, ind, m):
    for i in range(src + 1, len(m)):
        _plus(src, i, m, -m[i][ind] / m[src][ind])


def _plus(src, dest, m, mul: float = 1):
    for i in range(len(m[0])):
        m[dest][i] += m[src][i] * mul


def swap(src, dest, m):
    m[src], m[dest] = m[dest], m[src]


def rang(m):
    return sum(any(row) for row in m)


def determinant(m, k):
    p = 1
    for i in range(len(m)):
        p *= m[i][i]
    return (-1) ** k * p


def solve(m):
    xs = []
    for i in range(len(m)):
        x = m[len(m) - i - 1][-1]
        for j in range(1, i + 2):
            if j == i + 1:
                x /= m[len(m) - i - 1][-j - 1]
            else:
                x -= m[len(m) - i - 1][-j - 1] * xs[j - 1]
        xs.append(x)
    return xs[::-1]


def get_r(m, v):
    return [sum(a * b for a, b in zip(v, row)) - res[row_ind] for row_ind, row in enumerate(m)]


ext_matrix = add_col([row[:] for row in initial_matrix], res)

k = 0
row = 0
col = 0
while col < n:
    for j in range(row, n):
        if ext_matrix[j][col]:
            swap(j, row, ext_matrix)
            k += row != j
            plus(row, col, ext_matrix)
            row += 1
            break
    col += 1

matrix = remove_last_col([row[:] for row in ext_matrix])

matrix_rang = rang(matrix)
ext_matrix_rang = rang(ext_matrix)

if matrix_rang != ext_matrix_rang:
    print("Определитель: 0")
    print("Решения СЛАУ нет")
elif matrix_rang < n:
    print("Определитель: 0")
    print("Решений СЛАУ бесконечное множество")
else:
    d = determinant(matrix, k)
    print("\n".join("\t\t\t\t".join(map(str, i)) for i in ext_matrix))
    print("Определитель: ", d)
    solution = solve(ext_matrix)
    print("Вектор решения: ", solution)
    print("Вектор невязок: ", get_r(initial_matrix, solution))

# from pprint import pprint
#
# print(k)
# print(rang(ext_matrix))
# print(rang(matrix))
# pprint(ext_matrix)