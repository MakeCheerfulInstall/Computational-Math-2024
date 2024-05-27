from tools import *


def solve(f, a, b, accuracy):
    iter = 0
    eps = 10**(-9)
    print_table_header(["#", "a", "b", "x_i", "f(a)", "f(b)", "f(x_i)", "|a-b|"])
    while abs(a-b) > accuracy:
        iter += 1
        x = mid(a, b)
        if abs(f(x)) < eps:
            return x
        if f(a)*f(x) > 0: a = x
        else: b = x
        print_table_row([iter, a, b, x, f(a), f(b), f(x), abs(a-b)])
    x = mid(a, b)
    return x