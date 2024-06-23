import matplotlib.pyplot as plt
import numpy as np
import math

# НЕОБЯЗАТЕЛЬНАЯ ЧАСТЬ

def stirling(x, xarr, n, diff_table):
    a_index = len(xarr) // 2  # y0
    h = (xarr[-1] - xarr[0]) / n
    t = (x - xarr[a_index]) / h
    res = 0
    for i in range(len(diff_table)):
        if i == 0:
            res += diff_table[i][a_index]
        elif i == 1:
            res += t * (diff_table[i][a_index - 1] + diff_table[i][a_index]) / 2
        elif i == 2:
            res += diff_table[i][a_index - 1] / 2 * (t ** 2)
        else:
            if i % 2 != 0:
                tempn = (i + 1) // 2
                tmpres = t * (diff_table[i][a_index - tempn] + diff_table[i][a_index - (tempn - 1)]) / (
                        2 * math.factorial(i))
                for j in range(1, tempn):
                    tmpres *= (t ** 2 - j ** 2)
                res += tmpres
            else:
                tempn = i // 2
                tmpres = (t ** 2) * diff_table[i][a_index - tempn] / math.factorial(i)
                for j in range(1, tempn):
                    tmpres *= (t ** 2 - j ** 2)
                res += tmpres
    return res

def bessel(x, xarr, n, diff_table):
    a_index = len(xarr) // 2-1  # y0
    h = (xarr[-1] - xarr[0]) / n
    t = (x - xarr[a_index]) / h
    res = 0
    for i in range(len(diff_table)):
        if i == 0:
            res += (diff_table[i][a_index] + diff_table[i][a_index + 1]) / 2
        elif i == 1:
            res += diff_table[i][a_index] * (t - 0.5)
        else:
            if i % 2 == 0:
                tempn = i // 2
                tmpres = (diff_table[i][a_index - tempn] + diff_table[i][a_index]) / (2 * math.factorial(i))
                for j in range(1, i):
                    tmpres *= (t - j) * (t + j - 1)
                res += tmpres
            else:
                tempn = (i - 1) // 2
                tmpres = (t - 0.5) * diff_table[i][a_index - tempn]/math.factorial(i)
                for j in range(1, tempn):
                    tmpres *= (t - j) * (t + j - 1)
                res += tmpres
    return res

# ОБЯЗАТЕЛЬНАЯ ЧАСТЬ

# Многочлен Ньютона с конечными разностями
def newton_k(z, x, y, n):
  n+=1
  f_list = []
  p = np.zeros([n, n+1])
  for m in z:

    for i in range(n):
      p[i, 0] = x[i]
      p[i, 1] = y[i]

    for i in range(2, n+1):
      for j in range(n+1-i):
        p[j,i] = ( p[j+1, i-1] - p[j, i-1] ) / ( x[j+i-1] - x[j] )

    np.set_printoptions(suppress=True)

    # Кэффициенты
    b = p[0][1:]

    lst = []

    t = 1
    for i in range(len(x)):
      t *= (m-x[i])
      lst.append(t)

    f = b[0]
    for k in range(1,len(b)):
      f += b[k] * lst[k-1]
    f_list.append(f)
  return f_list

# Многочлен Ньютона с разделёнными разностями
def newton_r(arg, x, y, n):
    res = 0
    for i in range(n + 1):
        if i == 0:
            res += y[i]
        else:
            arr = []
            for j in range(i + 1):
                arr.append(j)
            r = recurrent(x, y, i + 1, arr)
            for j in range(i):
                r *= (arg - x[j])
            res += r
    return res


def recurrent(x, y, k, arr):
    if k == 2:
        return (y[arr[-1]] - y[arr[0]]) / (x[arr[-1]] - x[arr[0]])
    else:
        return (recurrent(x, y, k - 1, rem(arr, 0)) - recurrent(x, y, k - 1, rem(arr, -1))) / (x[arr[-1]] - x[arr[0]])

def rem(arr, i):
    new_array = arr.copy()
    new_array.pop(i)
    return new_array


# Многочлен Лагранжа
def lagrange(arg, x, y, n):
    res = 0
    for i in range(n + 1):
        numerator = 1
        denominator = 1
        for j in range(n + 1):
            if j != i:
                denominator *= (x[i] - x[j])
                numerator *= (arg - x[j])
        res += (numerator / denominator) * y[i]
    return res

# Построение таблицы конечных разностей
def build_table(y, k):
    res = [y]
    c = 0
    while True:
        arr = []
        for i in range(k):
            arr.append(res[c][i + 1] - res[c][i])
        if sum(arr) == 0:
            return res
        else:
            c += 1
            k -= 1
            res.append(arr)


# Выбор способа ввода данных
var = int(input("Выберите способ задания исходных данных:\n1. С клавиатуры\n2. Из файла\n3. На основе функции\n> "))
if var == 1:
    h = int(input("Введите шаг: "))
    x = list(map(float, input("Введите строку X: ").split()))
    y = list(map(float, input("Введите строку Y: ").split()))
    if len(x) != h + 1 or len(y) != h + 1:
        raise Exception("Неверная длина массива.")
    for i in range(len(x)):
        if x.count(x[i]) > 1:
            x[i] += 0.001

elif var == 2:
    test_var = int(input(
        "Выберите набор данных:\n1. X: 0.1, 0.2, 0.3, 0.4, 0.5; Y: 1.25, 2.38, 3.79, 5.44, 7.14\n2. X: 1, 2, 3, 4; Y: 0, "
        "3, 5, 7\n3. X: 0.15, 0.2, 0.33, 0.47; Y: 1.25, 2.38, 3.79, 5.44\n> "))
    if test_var == 1:
        x, y = [0.1, 0.2, 0.3, 0.4, 0.5], [1.25, 2.38, 3.79, 5.44, 7.14]
        h = 4
    elif test_var == 2:
        x, y = [1, 2, 3, 4], [0, 3, 5, 7]
        h = 3
    elif test_var == 3:
        x, y = [0.15, 0.2, 0.33, 0.47], [1.25, 2.38, 3.79, 5.44]
        h = 3
    else:
        raise Exception("Таблица не найдена.")

elif var == 3:
    f = int(input("Выберите функцию:\n1. sin(x)\n2. cos(x)\n> "))
    a = int(input("Введите левую границу интервала: "))
    b = int(input("Введите правую границу интервала: "))
    h = int(input("Введите количество точек на интервале: "))
    step = (b - a) / h  # шаг
    h -= 1
    x, y = [], []
    for i in np.arange(a, b, step):
        x.append(i)
        y.append(calculate_y(i, f))
else:
    raise Exception("Ошибка ввода данных.")

# Построение таблицы конечных разностей
table = build_table(y, h)
print("Таблица конечных разностей: ", end="")
for i in range(len(table)):
    print(f"\ny{i}: ", end="")
    for j in table[i]:
        print(round(j, 4), end=" ")
print()

# Ввод аргумента, для которого ищется значение функции
arg = float(input("Введите значение аргумента: "))

print(
    f"Многочлен Лагранжа: {lagrange(arg, x, y, h)}\nМногочлен Ньютона с разделенными разностями:"
    f" {newton_r(arg, x, y, h)}\nМногочлен Ньютона с конечными разностями: {newton_k([arg], x, y, h)[0]}")

# Построение многочленов Бесселя и Стирлинга (доп)
if len(x) % 2 == 0:
    print(f"Многочлен Бесселя: {bessel(arg, x, h, table)}")
else:
    print(f"Многочлен Стирлинга: {stirling(arg, x, h, table)}")

# Вывод графика
plt.plot(x, y)
if var == 3:
    x = np.linspace(-np.pi, np.pi, 200)  # Диапазон значений x
    if f == 1:
        y = np.sin(x)
    elif f == 2:
        y = np.cos(x)
    else:
        raise Exception("Неизвестная функция")
    plt.plot(x, y)
plt.scatter(x, y, marker='o', s=50, color='Skyblue')
plt.show()
