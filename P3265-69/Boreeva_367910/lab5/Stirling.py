import math


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
