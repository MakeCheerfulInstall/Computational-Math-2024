from math import cos, sin

import numpy as np

def quation_solution(quation,x):
    if quation == 1:
        return x**2 - 3*x + 2
    elif quation == 2:
        return x**3 + 2*(x**2) - 5
    elif quation == 3:
        return np.cos(x) - x**2
    else:
        print("Unknown quation")
        exit()

def quation_df_solution(quation, x):
    if quation == 1:
        return 2*x - 3
    elif quation == 2:
        return 3*(x**2) + 4*x
    elif quation == 3:
        return -2*x - sin(x)
    else:
        print("Unknown quation")
        exit()

def quation_df2_solution(quation, x):
    if quation == 1:
        return 2
    elif quation == 2:
        return 6*x +4
    elif quation == 3:
        return -2 - cos(x)
    else:
        print("Unknown quation")
        exit()

def converted_quation(quation,x):
    if quation == 1:
        return x + (x**2 - 3*x + 2)*0.1
    elif quation == 2:
        return x + (x**3 + 2*x**2 - 5)*0.1
    elif quation == 3:
        return x + (cos(x)- (x**2))*(0.1)
    else:
        print("Unknown quation")
        exit()

def convertred_df_quation(quation, x):
    if quation == 1:
        return x/5 + 0.7
    elif quation == 2:
        return (3*x**2)/(10)+(2*x)/5+1
    elif quation == 3:
        return -(sin(x))/(10)-x/5+1
    else:
        print("Unknown quation")
        exit()

def check_convergencecondition(quation, a, b):
    print("q(a) = ", convertred_df_quation(quation, a), "q(b) = ", convertred_df_quation(quation, b))
    return max(abs(convertred_df_quation(quation, a)), abs(convertred_df_quation(quation, b)))

def count_roots_on_interval(quation, a, b, inaccuracy):
    b1 = b
    num_roots = 0
    x = a
    while x < b1:
        if quation_solution(quation, x) * quation_solution(quation, x + inaccuracy) < 0:
            num_roots += 1
        x += inaccuracy
    return num_roots
def validate_roots(num_roots):
    num_roots = int(num_roots)
    if num_roots <1:
        print("No roots")
        return False
    elif num_roots > 1:
        print("More than one root (", num_roots, ")")
        return False
    return True