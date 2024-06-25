import math


def calculate_fx(num, x):
    if num == 1:
        return x ** 2
    elif num == 2:
        return -3 * (x ** 3) - 5 * (x ** 2) + 4 * x - 2
    elif num == 3:
        return -x ** 3 - x ** 2 + x + 3
    elif num == 4:
        return 1 / math.log(x)
