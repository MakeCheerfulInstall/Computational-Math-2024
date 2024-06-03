import math


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
