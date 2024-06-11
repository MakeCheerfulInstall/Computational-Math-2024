from utils import *
import sys

def aproximate(points, degree):
    if degree < 2 or degree > 8:
        print("Incorrect degree entered", file=sys.stderr)
        sys.exit(-1)

    A = [[0 for i in range(degree + 1)] for j in range(degree + 1)]
    B = []

    A[0][0] = len(points)
    for i in range(1, degree*2+1):
        _val = 0
        for p in points:
            _val += p[0]**i
        fill_rev_diag(list(A), _val, i)

    for i in range(degree+1):
        _val = 0
        for p in points:
            _val += (p[0] ** i) * p[1]
        B.append(_val)

    _koofs = solve_slau(A, B)

    return Function(_koofs, FunctionType.polynomial)