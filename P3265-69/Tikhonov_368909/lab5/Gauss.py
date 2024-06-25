import math


def gauss(x, xarr, n, diff_table):
    if len(xarr) % 2 == 0:
        a = (xarr[0] + xarr[-1]) / 2
    else:
        a = xarr[len(xarr) // 2]
    if len(xarr) % 2 == 0:
        a_index = len(xarr) // 2 - 1  # y0
    else:
        a_index = len(xarr) // 2
    h = (xarr[-1] - xarr[0]) / n
    t = (x - xarr[a_index]) / h
    res = 0
    m = 1  # additional
    f = 0  # factorial
    prev_res = 0

    if x == a:
        x += 0.001

    if x > a:
        for i in range(len(diff_table)):
            if i == 0:
                res += diff_table[i][a_index]
            elif i == 1:
                res += t * diff_table[i][a_index]
                prev_res = t
            else:
                if i % 2 == 0:
                    res += prev_res * math.factorial(f - 1) / math.factorial(f) * (t - m) * diff_table[i][a_index - m]
                    prev_res = prev_res * math.factorial(f - 1) / math.factorial(f) * (t - m)
                else:
                    res += prev_res * math.factorial(f - 1) / math.factorial(f) * (t + m) * diff_table[i][a_index - m]
                    prev_res = prev_res * math.factorial(f - 1) / math.factorial(f) * (t + m)
                    m += 1
            f += 1
    if x < a:
        for i in range(len(diff_table)):
            if i == 0:
                res += diff_table[i][a_index]
            elif i == 1:
                res += t * diff_table[i][a_index - m]
                prev_res = t
            else:
                if i % 2 == 0:
                    res += prev_res * math.factorial(f - 1) / math.factorial(f) * (t + m) * diff_table[i][a_index - m]
                    prev_res = prev_res * math.factorial(f - 1) / math.factorial(f) * (t + m)
                else:
                    res += prev_res * math.factorial(f - 1) / math.factorial(f) * (t - m) * diff_table[i][
                        a_index - m - 1]
                    prev_res = prev_res * math.factorial(f - 1) / math.factorial(f) * (t - m)
                    m += 1
            f += 1
    return res
