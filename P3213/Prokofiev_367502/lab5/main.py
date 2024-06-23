import math
import numpy as np
from matplotlib import pyplot as plt


def inp():
    while True:
        try:
            num = int(input("Введите 1, если ввод данных будет происходить из файла. Введите 2, если с клавиатуры. "
                            "Введите 3 для выбора уравнения "))
            if num != 1 and num != 2 and num != 3:
                print('Пожалуйста, введите 1, 2 или 3')
                continue
            else:
                return num
        except ValueError:
            print("Пожалуйста, введите число")
            continue


def get_data():
    num = inp()
    while True:
        try:
            x = []
            y = []
            if num == 1:
                with open('input2.txt', 'r') as f:
                    while (line := f.readline()) != '':
                        x.append(float(line.split(' ')[0]))
                        y.append(float(line.split(' ')[1]))
                    return [x, y]
            elif num == 2:
                print('Введите координаты')
                while (line := input()) != '':
                    x.append(float(line.split(' ')[0]))
                    y.append(float(line.split(' ')[1]))
                return [x, y]
            else:
                print('1. sin(x)')
                print('2. x ** 2')
                print('Выберите уравнение (1 или 2)')
                n = int(input())
                if n not in [1, 2]:
                    print("Вы ввели неверную цифру")
                    exit(0)
                print('Введите исследуемый интервал')
                temp = input().split(' ')
                a, b = int(temp[0]), int(temp[1])
                print('Введите количество точек на интервале')
                amount = int(input())
                for i in range(amount):
                    x_i = a + (b - a) * i / (amount-1)
                    x.append(x_i)
                    if n == 1:
                        y.append(math.sin(x_i))
                    elif n == 2:
                        y.append(x_i ** 2)
                    else:
                        print("Вы ввели неверную цифру")
                print("x", x)
                print("y", y)
                return [x, y]
        except TypeError:
            print("Вы ввели неверные данные")
            exit(0)
        except ValueError:
            print("Вы ввели неверные данные")
            exit(0)
        except IndexError:
            print("Вы ввели неверные данные")
            exit(0)


def lagrange_polynomial(x, y, x_cur):
    res = 0
    for i in range(len(x)):
        p = 1
        for j in range(len(y)):
            if i != j:
                p *= (x_cur - x[j]) / (x[i] - x[j])
        res += p * y[i]
    return res


def newton_coefficient(x, y):
    m = len(x)
    x = np.copy(x)
    y = np.copy(y)
    for k in range(1, m):
        y[k:m] = (y[k:m] - y[k - 1]) / (x[k:m] - x[k - 1])
    return y


def newton_polynomial(x, y, x_cur):
    n = len(x)
    is_not_equally_spaced = True
    h = x[1] - x[0]
    for i in range(1, n - 1):
        if x[i + 1] - x[i] != h:
            is_not_equally_spaced = False
            break
    if is_not_equally_spaced:
        return 'Узлы являются равноотстоящими'
    a = newton_coefficient(x, y)
    n = len(x) - 1
    p = a[n]
    for k in range(1, n + 1):
        p = a[n - k] + (x_cur - x[n - k]) * p
    return p


def t_calc(t, n, forward=True):
    result = t
    for i in range(1, n):
        if forward:
            result *= t - i
        else:
            result *= t + i
    return result

def gauss_forward_interpolation(x, y, x_cur):
    coef = [[0] * len(x) for i in range(len(x))]
    t=(x_cur-x[(len(x)-1)//2])/((x[-1]-x[0])/(len(x)-1))
    for i in range(len(x)):
        coef[i][0] = y[i]
    for i in range(1, len(x)):
        for j in range(0, len(x) - i):
            coef[j][i] = (coef[j + 1][i - 1] - coef[j][i - 1])

    i = (len(x) - 1) // 2 -1
    res = y[i+1]
    p = 1
    f = 1
    j = 1
    for k in range(1, (len(x) - 1) // 2 + 1):
        p *= (t - (k - 1))
        res += p * coef[i][j] / math.factorial(f)
        j += 1
        f += 1
        p *= (t + k)
        res += p * coef[i][j] / math.factorial(f)
        j += 1
        i+=1
        f+=1
    return (res)

def gauss_backward_interpolation(x, y, x_cur):
    coef = [[0] * len(x) for i in range(len(x))]
    t=(x_cur-x[(len(x)-1)//2])/((x[-1]-x[0])/(len(x)-1))
    for i in range(len(x)):
        coef[i][0]=y[i]
    for i in range(1, len(x)):
        for j in range(0, len(x)-i):
            coef[j][i]=(coef[j+1][i-1]-coef[j][i-1])

    i=(len(x)-1)//2
    res=y[i]
    p=1
    f=1
    j=1
    for k in range(1, (len(x)-1)//2+1):
        p*=(t+(k-1))
        res+=p*coef[i][j]/math.factorial(f)
        i-=1
        j+=1
        f+=1
        p *= (t - k)
        res += p * coef[i][j] / math.factorial(f)
        j += 1
        f+=1
    return(res)


def interpolate_gauss(x, y, x_cur):
    if x_cur > x[(len(x)-1)//2]:
        return gauss_backward_interpolation(x, y, x_cur)
    else:
        return gauss_forward_interpolation(x, y, x_cur)


def newton_interpolation(x, y, x_cur):
    n = len(x)
    is_equally_spaced = True
    h = x[1] - x[0]
    for i in range(1, n - 1):
        if x[i + 1] - x[i] != h:
            is_equally_spaced = False
            break
    if not is_equally_spaced:
        return 'Узлы не являются равноотстоящими'
    a = [[0] * n for _ in range(n)]
    for i in range(n):
        a[i][0] = y[i]
    for i in range(1, n):
        for j in range(n - i):
            a[j][i] = a[j + 1][i - 1] - a[j][i - 1]
    if x_cur <= x[n // 2]:
        x0 = n - 1
        for i in range(n):
            if x_cur <= x[i]:
                x0 = i - 1
                break
        if x0 < 0:
            x0 = 0
        t = (x_cur - x[x0]) / h
        result = a[x0][0]
        for i in range(1, n):
            result += (t_calc(t, i) * a[x0][i]) / math.factorial(i)
    else:
        t = (x_cur - x[n - 1]) / h
        result = a[n - 1][0]
        for i in range(1, n):
            result += (t_calc(t, i, False) * a[n - i - 1][i]) / math.factorial(i)
    return result


def finite_diff(data, y):
    temp = []
    if len(y) <= 1:
        return data
    for i in range(len(y) - 1):
        temp.append(y[i + 1] - y[i])
    data.append(temp)
    return finite_diff(data, temp)


def main_run():
    data = get_data()
    print('Конечные разности:', finite_diff([], data[1]))
    x = data[0]
    y = data[1]
    if len(x) != len(set(x)):
        temp = []
        for i in range(len(x)):
            if x[i] not in temp:
                temp.append(x[i])
            else:
                temp.append(x[i] + 0.01)
        x = temp
    print('Введите значение аргумента')
    x_cur = float(input())
    answer1 = lagrange_polynomial(x, y, x_cur)
    answer2 = newton_polynomial(x, y, x_cur)
    print('Полином Ньютона с разделенными разностями дал ответ: ', answer2)
    answer3 = newton_interpolation(x, y, x_cur)
    print('Полином Ньютона с конечными разностями дал ответ: ', answer3)
    print('Полином Лагранжа дал ответ: ', answer1)
    #plt.plot(x, y)
    plt.scatter(x, y, label="Вводные точки")
    plt.grid(True)
    plot_x = np.linspace(np.min(x), np.max(x), 100)
    if answer2 != 'Узлы являются равноотстоящими':
        plot_y = [newton_polynomial(x, y, x_c) for x_c in plot_x]
        plt.plot(plot_x, plot_y, color='g', label='Newton_first')

    if answer3 != 'Узлы не являются равноотстоящими':
        plot_y = [newton_interpolation(x, y, x_c) for x_c in plot_x]
        plt.plot(plot_x, plot_y, color='r', label='Newton_second')
    plt.legend()
    plt.show()


def run():
    try:
        main_run()
    except KeyboardInterrupt:
        return


if __name__ == "__main__":
    run()