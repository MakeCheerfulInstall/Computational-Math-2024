from Equations import calculate_y
from Lagrange import lagrange
from Newton import newton
from Gauss import gauss
from Stirling import stirling
from Bessel import bessel
import matplotlib.pyplot as plt
import numpy as np

datamode = int(input("Выберите способ ввода данных:\n1. С клавиатуры.\n2. Из файла.\n3. На основе функции.\n> "))


def diff_table(arr_y, k):
    res = [arr_y]
    c = 0
    while True:
        tmp = []
        for q in range(k):
            tmp.append(res[c][q + 1] - res[c][q])
        if sum(tmp) == 0:
            return res
        else:
            c += 1
            k -= 1
            res.append(tmp)


if datamode == 1:
    n = int(input("Введите n: "))
    xarr = list(map(float, input("Введите строку X: ").split()))
    yarr = list(map(float, input("Введите строку Y: ").split()))
    if len(xarr) != n + 1 or len(yarr) != n + 1:
        raise Exception("Неверная длина массива.")
    for i in range(len(xarr)):
        if xarr.count(xarr[i]) > 1:
            xarr[i] += 0.001

elif datamode == 2:
    table_number = int(input(
        "Выберите таблицу:\n1. X: 0.1, 0.2, 0.3, 0.4, 0.5; Y: 1.25, 2.38, 3.79, 5.44, 7.14\n2. X: 1, 2, 3, 4; Y: 0, "
        "3, 5, 7\n3. X: 100, 121, 144; Y: 10, 11, 12\n4. X: 0.15, 0.2, 0.33, 0.47; Y: 1.25, 2.38, 3.79, 5.44\n> "))
    if table_number == 1:
        xarr, yarr = [0.1, 0.2, 0.3, 0.4, 0.5], [1.25, 2.38, 3.79, 5.44, 7.14]
        n = 4
    elif table_number == 2:
        xarr, yarr = [1, 2, 3, 4], [0, 3, 5, 7]
        n = 3
    elif table_number == 3:
        xarr, yarr = [100, 121, 144], [10, 11, 12]
        n = 2
    elif table_number == 4:
        xarr, yarr = [0.15, 0.2, 0.33, 0.47], [1.25, 2.38, 3.79, 5.44]
        n = 3
    else:
        raise Exception("Таблица не найдена.")
elif datamode == 3:
    function_number = int(input("Выберите функцию:\n1. sin(x)\n2. cos(x)\n> "))
    a, b = map(int, input("Введите интервал [A, B]: ").split())
    n = int(input("Введите количество точек на интервале: "))
    step = (b - a) / n  # шаг
    n -= 1
    xarr, yarr = [], []
    for i in np.arange(a, b, step):
        xarr.append(i)
        yarr.append(calculate_y(i, function_number))
else:
    raise Exception("Неизвестный режим.")

diff_table = diff_table(yarr, n)
print("Таблица разностей: ", end="")
for i in range(len(diff_table)):
    print(f"\ny{i}: ", end="")
    for j in diff_table[i]:
        print(round(j, 4), end=" ")
print()

arg = float(input("Введите значение аргумента: "))

print(
    f"Многочлен Лагранжа: {lagrange(arg, xarr, yarr, n)}\nМногочлен Ньютона с разделенными разностями:"
    f" {newton(arg, xarr, yarr, n)}\nМногочлен Гаусса: {gauss(arg, xarr, n, diff_table)}")

if len(xarr) % 2 == 0:
    print(f"Многочлен Бесселя: {bessel(arg, xarr, n, diff_table)}")
else:
    print(f"Многочлен Стирлинга: {stirling(arg, xarr, n, diff_table)}")

plt.plot(xarr, yarr)
if datamode == 3:
    x = np.linspace(-np.pi, np.pi, 200)  # Диапазон значений x
    if function_number == 1:
        y = np.sin(x)
    elif function_number == 2:
        y = np.cos(x)
    else:
        raise Exception("Неизвестная функция")
    plt.plot(x, y)
plt.scatter(xarr, yarr, marker='o', s=50, color='red')
plt.show()
