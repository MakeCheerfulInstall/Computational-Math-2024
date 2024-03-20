import math
import sympy

while True:
    try:
        print("Выберите функцию: ")
        print("1. 3-2x-x^2")
        print("2. (2x+1)^5")
        print("3. 1/(4x+5)")
        print("4. sqrt(x)")
        num = int(input())
    except ValueError:
        print("Пожалуйста, введите число")
        continue
    if num not in [1, 2, 3, 4]:
        print('Пожалуйста, введите цифру от 1 до 4')
        continue
    else:
        break

while True:
    try:
        print("Выберите метод: ")
        print("1. Метод прямоугольников")
        print("2. Метод трапеций")
        print("3. Метод Симпсона")
        meth = int(input())
    except ValueError:
        print("Пожалуйста, введите число")
        continue
    if meth not in [1, 2, 3]:
        print('Пожалуйста, введите цифру от 1 до 3')
        continue
    else:
        break


def f(x):
    if num == 1:
        y = abs(3 - 2 * x - x ** 2)
    elif num == 2:
        y = abs((2 * x + 1) ** 5)
    elif num == 3:
        y = abs(1 / (4 * x + 5))
    else:
        y = abs(math.sqrt(x))
    return y


print("Введите пределы интегрирования и точность вычисления в одну строку через пробел")
lines = input()
lines = lines.split(" ")
if len(lines) != 3:
    raise Exception("Вы ввели не три числа")
a = float(lines[0].replace(',', '.'))
b = float(lines[1].replace(',', '.'))
e = float(lines[2].replace(',', '.'))
sec_b = b

x = sympy.symbols('x')
if sympy.limit(f(x), x, a) == sympy.oo:
    a += 0.01
    print("a - точка разрыва")
if sympy.limit(f(x), x, b) == sympy.oo:
    b -= 0.01
    print("b - точка разрыва")

tick = 0
c = round(a+0.01, 2)
while c < b:
    if sympy.limit(f(x), x, c) == sympy.oo:
        tick = 1
        b = c - 0.01
        print(c, " - точка разрыва")
    else:
        c = round(c + 0.01, 2)

if num == 4:
    if a < 0 or b < 0:
        raise Exception("Нельзя извлечь корень из отрицательного числа в действительных числах")


def meth_left_rect():
    n = 2
    cur_s = 10 ** 8
    r = 10 ** 8
    while r > e:
        n *= 2
        h = (b - a) / n
        s = 0
        x = a
        for i in range(n):
            s += f(x)
            x += h
        s *= h
        r = abs(s - cur_s)
        cur_s = s
    return cur_s, n


def meth_right_rect():
    n = 2
    cur_s = 10 ** 8
    r = 10 ** 8
    while r > e:
        n *= 2
        h = (b - a) / n
        s = 0
        x = a + h
        for i in range(n):
            s += f(x)
            x += h
        s *= h
        r = abs(s - cur_s)
        cur_s = s
    return cur_s, n


def meth_mid_rect():
    n = 2
    cur_s = 10 ** 8
    r = 10 ** 8
    while r > e:
        n *= 2
        h = (b - a) / n
        s = 0
        x = a + h / 2
        for i in range(n):
            s += f(x)
            x += h
        s *= h
        r = abs(s - cur_s)
        cur_s = s
    return cur_s, n


def meth_trap():
    n = 2
    cur_int = 10 ** 8
    r = 10 ** 8
    while r > e:
        n *= 2
        h = (b - a) / n
        integ = 0.5 * (f(a) + f(b))
        for i in range(1, n):
            integ += f(a + i * h)
        integ *= h
        r = abs(integ - cur_int)
        cur_int = integ
    return cur_int, n


def meth_Sim():
    n = 2
    cur_int = 10 ** 8
    r = 10 ** 8
    while r > e:
        n *= 2
        h = (b - a) / n
        integ = f(a) + f(b)
        for i in range(1, n):
            x = a + i * h
            if i % 2 == 0:
                integ += 2 * f(x)
            else:
                integ += 4 * f(x)
        integ *= h / 3
        r = abs(integ - cur_int)
        cur_int = integ
    return cur_int, n

if meth == 1:
    s1 = meth_left_rect()
    s2 = meth_right_rect()
    s3 = meth_mid_rect()
    if tick == 1:
        a = c + 0.01
        b = sec_b
        mlr = meth_left_rect()
        mrr = meth_right_rect()
        mmr = meth_mid_rect()
        list(s1)[0] += mlr[0]
        list(s1)[1] += mlr[1]
        list(s2)[0] += mrr[0]
        list(s2)[1] += mrr[1]
        list(s3)[0] += mmr[0]
        list(s3)[1] += mmr[1]
    print("Метод левых прямоугольников (результат и число разбиения): ", s1)
    print("Метод правый прямоугольников: (результат и число разбиения)", s2)
    print("Метод средних прямоугольников: (результат и число разбиения)",s3)
elif meth == 2:
    s = meth_trap()
    if tick == 1:
        a = c + 0.01
        b = sec_b
        list(s)[0] += meth_trap()[0]
        list(s)[1] += meth_trap()[1]
    print("Метод трапеций: (результат и число разбиения)", s)
else:
    s = meth_Sim()
    if tick == 1:
        a = c + 0.01
        b = sec_b
        list(s)[0] += meth_Sim()[0]
        list(s)[1] += meth_Sim()[1]
    print("Метод Симпсона: (результат и число разбиения)", s)
