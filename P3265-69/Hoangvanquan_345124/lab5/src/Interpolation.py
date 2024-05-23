import math
import numpy as np
from prettytable import PrettyTable

def FiniteDifferenceTable(x, y):
    table = [[0 for _ in range(len(y) + 1)] for _ in range(len(y))]
    for i in range(len(y)):
        table[i][0] = f"{float(x[i]):.2f}"
        table[i][1] = f"{float(y[i]):.4f}"

    for j in range(2, len(y) + 1):
        for i in range(len(y) - j + 1):
            table[i][j] = float(table[i+1][j-1]) - float(table[i][j-1])
            table[i][j] = f"{table[i][j]:.5f}"

    if(len(y) < 3):_FiniteDifferenceTable = PrettyTable(["x", "y", *[f"∆{chr(184+j)}y" for j in range(1, len(y))]])
    elif(len(y) < 5):_FiniteDifferenceTable = PrettyTable(["x", "y", *[f"∆{chr(184+j)}y" for j in range(1, 2)], *[f"∆{chr(176+j)}y" for j in range(2, len(y))]])
    else:_FiniteDifferenceTable = PrettyTable(["x", "y", *[f"∆{chr(184+j)}y" for j in range(1, 2)], *[f"∆{chr(176+j)}y" for j in range(2, 4)], *[f"∆{chr(8304+j)}y" for j in range(4, len(y))]])
    for row in table:
        _FiniteDifferenceTable.add_row(row)
    print(_FiniteDifferenceTable)

def Lagrange(x, y, value):
    result = 0
    for i in range(len(x)):
        c1 = c2 = 1
        for j in range(len(x)):
            if i != j:
                c1 *= value - x[j]
                c2 *= x[i] - x[j]
        result += y[i] * c1 / c2

    return round(result,5)

def NewtonSeparatedDifferences(x, y, value):
    f = subNewtonSeparatedDifferences_createrTable(x, y)
    result = y[0]
    for j in range(1, len(f[0])):
        temp = f[0][j]
        for i in range(0, j): temp *= (value - x[i])
        result += temp
    return round(result,5)

def subNewtonSeparatedDifferences_createrTable(x, y):
    f = [[0 for _ in range(len(y) )] for _ in range(len(y))]
    for i in range(len(y)):
        f[i][0] = y[i]
    for j in range(1, len(y)):
        for i in range(len(y) - j): 
            f[i][j] = (f[i+1][j-1] - f[i][j-1])/(x[i + j] - x[i])
    return f

def subNewton_createrTable(y):
    table = [[0 for _ in range(len(y))] for _ in range(len(y))]
    for i in range(len(y)):table[i][0] = y[i]
    for j in range(1, len(y)):
        for i in range(len(y) - j): 
            table[i][j] = table[i+1][j-1] - table[i][j-1]
    return table

def NewtonFiniteDifferences(x, y, value):
    table = subNewton_createrTable(y)
    if value <= x[len(x) - 1]:
        x0 = 0
        for i in range(len(x) - 1, -1, -1):
            if value >= x[i]:
                x0 = i
                break
        t = (value - x[x0]) / (x[1] - x[0])
        result = table[x0][0]
        for i in range(1, len(table[x0])):
            temp = t
            for yi in range(1, i): temp *= (t - yi) 
            result += (temp * table[x0][i]) / math.factorial(i)
    else:
        t = (value - x[len(x) - 1]) / (x[1] - x[0])
        result = table[len(x) - 1][0]
        for i in range(1, len(x)):
            temp = t
            for yi in range(1, i): temp *= (temp + yi) 
            result += (temp * table[len(x) - i - 1][i]) / math.factorial(i)

    return round(result,5)

def createrTable_Guass(y):
    result = [y]
    for i in range(len(y) - 1):
        div_dif = []
        for j in range (len(result[i]) - 1):
            diff = result[i][j+1] - result[i][j]
            div_dif.append(diff)
        result.append(div_dif)

    return result

def Stirling(x, y, value):
    if(len(y) % 2 == 0):
        print("Четное число узло. Формула Стирлинга не применяется")
        return
    table = createrTable_Guass(y)
    mid = len(y)//2
    h = x[1] - x[0]
    t = (value - x[mid])/h
    if(abs(t) > 0.25): print("Результат по формуле Стирлинга содержит большую погрешность")
    result = y[mid]
    for i in range(1, mid + 1):
        mul = 1
        for j in range(1, i):
            mul *= (t * t - j * j)
        result += t * mul * (table[2*i-1][-(i-1) + mid] + table[2 * i - 1][-i + mid]) / (2 * math.factorial(2*i-1)) 
        result += t * t * mul * (table[2 * i][-i + mid]) / math.factorial(2*i) 
    return result

def Bessel(x, y, value):
    if(len(y) % 2 != 0):
        print("Нечетное число узло. Формула Бесселя не применяется")
        return
    table = createrTable_Guass(y)
    mid = len(y)//2
    h = x[1] - x[0]
    t = (value - x[mid])/h
    if(abs(t) < 0.25 or abs(t) > 0.75): print("Результат по формуле Бесселя содержит большую погрешность")
    result = (y[mid] + y[mid+1])/2 + (t - 0.5)*table[1][mid]
    for i in range(2, mid):
        mul = 1
        for j in range(0, i):
            mul *= (t + math.pow(-1, j)*j)
        n = i - 1
        result += mul * (table[2*n][-n + mid] + table[2*i - 2][-(n-1) + mid]) / (2 * math.factorial(2*n)) 
        result += (t - 0.5) * mul * (table[2*n + 1][-n + mid]) / math.factorial(2*n + 1) 
    return result