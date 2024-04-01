import random

def g():
    while True:
        try:
            num = int(input("Введите 1, если ввод данных будет происходить из файла. Введите 2, если с клавиатуры. "
                            "Введите 3 для генерации случайной матрицы: "))
        except ValueError:
            print("Пожалуйста, введите число")
            continue
        if num != 1 and num != 2 and num != 3:
            print('Пожалуйста, введите 1, 2 или 3')
            continue
        else:
            return num


def get_data():
    num = g()
    while True:
        try:
            if num == 1:
                with open('input.txt', 'r') as f:
                    lines = f.readlines()
                    if " " in lines[0]:
                        print("В одной строке с размерностью не должно быть других символов")
                        break
                    n = int(lines[0])
                    lines.pop(0)
                with open('input.txt', 'w') as f:
                    f.writelines(lines)
            elif num == 2:
                print("Введите размерность матрицы (<=20)")
                n = int(input())
            else:
                print("Введите размерность матрицы (<=20)")
                dim = int(input())
                print("Матрица: ")
                m = [0]*dim
                for i in range(dim):
                    m[i] = [0] * (dim + 1)
                    for j in range(dim + 1):
                        m[i][j] = random.randint(0, 1000) / 100
                    print(m[i])
                return m
        except ValueError:
            print("Пожалуйста, введите целое положительное число меньше 21")
            continue
        except IndexError:
            print("Невозможно прочитать данные из пустого файла")
            return 0
        if n < 1 or n > 20:
            print("Пожалуйста, введите целое положительное число меньше 21")
            continue
        m = []
        try:
            print("Введите матрицу")
            if num == 1:
                with open('input.txt', 'r') as f:
                    for i in f:
                        row = [float("%.2f" % float(x.replace(',', '.'))) for x in i.split()]
                        if len(row) != n + 1:
                            print("В строке должно быть ", n + 1, " коэффициентов")
                            continue
                        m.append(row)
                    if len(m) != n:
                        print("В столбце должно быть ", n, " коэффициентов")
            else:
                i = 0
                while i != n:
                    s = list(map(float, input().strip().replace(',', '.').split()))
                    if len(s) != n + 1:
                        print("В строке должно быть ", n + 1, " коэффициентов")
                        continue
                    else:
                        m.append(s)
                        i += 1
        except ValueError:
            print("Коэффициенты должны быть числами")
            break
        for i in range(len(m)):
            m[i] = [float('%.2f' % float(elem)) for elem in m[i]]
        return m

m = get_data()


def det(m, p):
    d = 1
    for i in range(len(m)):
        d *= m[i][i]
    d = (-1) ** p * d
    print("Определитель равен ", d)
    return d

def tr(m):
    print("Треугольная матрица:")
    for i in m:
        print([float('%.2f' % elem) for elem in i])

def gauss(m):
    n = len(m)
    # ПРЯМОЙ
    p = 0
    for k in range(n):
        if m[k][k] == 0:
            dop = m[k]
            p += 1
            for i in range(k+1, n):
                if m[i][k] != 0:
                    m[k] = m[i]
                    m[i] = dop
                    break
        for j in range(k + 1, n):
            for i in range(n, k - 1, -1):
                m[j][i] -= m[k][i] * m[j][k] / m[k][k]
    tr(m)
    d = det(m, p)
    if d == 0:
        raise Exception("Метод Гаусса нельзя использовать для матриц с нулевым определителем")
    # ОБРАТНЫЙ
    vek = v(m)
    r(m, vek)
    return 1

def v(m):
    n = len(m)
    v = [0 for i in range(n)]
    for i in range(n):
        v[n - i - 1] = (m[n - i - 1][n] - sum(m[n - i - 1][n - i + j] * v[n - i + j] for j in range(i))) / m[n - i - 1][
            n - i - 1]
    print("Вектор неизвестных: ", [float('%.2f' % elem) for elem in v])
    return v

def r(m, v):
    n = len(m)
    r = [0 for i in range(n)]
    for i in range(n):
        for j in range(n):
            r[i] += m[i][j] * v[j]
        r[i] -= m[i][n]
    print("Вектор невязок: ", [float('%.2f' % elem) for elem in r])
    return r

if m != 0 and m != None:
    gauss(m)
