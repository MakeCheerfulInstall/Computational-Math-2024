import math
import matplotlib.pyplot as plt


def SA(arr):
    res = 0
    for i in arr:
        res += i
    return res


def SA2(arr):
    res = 0
    for i in arr:
        res += i ** 2
    return res


def SAB(arr1, arr2):
    res = 0
    for i in range(len(arr1)):
        res += arr1[i] * arr2[i]
    return res


def SKO(parr, yarr, n):
    numerator = 0
    for i in range(n):
        numerator += (parr[i] - yarr[i]) ** 2
    return round(math.sqrt(numerator / n), 4)


def determination(parr, yarr, n):
    numerator, denominator = 0, 0
    pavg = sum(parr) / n
    for i in range(n):
        numerator += (yarr[i] - parr[i]) ** 2
        denominator += (yarr[i] - pavg) ** 2
    return round(1 - numerator / denominator, 4)


def det_msg(r):
    if r >= 0.95:
        return "Высокая точность аппроксимации"
    elif 0.75 <= r < 0.95:
        return "Удовлетворительная аппроксимация"
    elif 0.5 <= r < 0.75:
        return "Слабая аппроксимация"
    elif r < 0.5:
        return "Недостаточная точность аппроксимации"


def create_graph(xarr, yarr, parr):
    fig, ax = plt.subplots()
    ax.plot(xarr, yarr, color="red")
    ax.plot(xarr, parr, color="blue")
    plt.scatter(xarr, yarr, marker='o', s=50, color='green')
    ax.set_xlabel("Значения X")
    ax.set_ylabel("Значения Y")
    plt.show()
