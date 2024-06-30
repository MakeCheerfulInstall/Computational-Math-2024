import numpy as np
import math


def gauss(A: list, b: list) -> tuple:
    """
      This function applies gauss method calculation on the provided data.
    """
    n = len(b)

    s = 0
    for k in range(n):  # straight flow of the gauss method
        nonzero_row = k
        if A[k, k] == 0:  # searching for the nonzero element in a column
            for i in range(k + 1, n):
                if A[i, k] != 0:
                    nonzero_row = i
                    break
            s += 1

        # rows rearrangement
        A[[k, nonzero_row], :] = A[[nonzero_row, k], :]
        b[k], b[nonzero_row] = b[nonzero_row], b[k]

        for i in range(k + 1, n):  # convert the matrix to the upper triangular form
            c = A[i, k] / A[k, k]
            A[i, k:n] -= c * A[k, k:n]
            b[i] -= c * b[k]

    x = np.zeros(n)
    for k in range(n - 1, -1, -1):  # reversed flow of the gauss method
        x[k] = (b[k] - np.dot(A[k, k + 1:], x[k + 1:])) / A[k, k]

    r = np.dot(A, x) - b  # residual vector calculation

    det = np.prod(np.diag(A)) * math.pow(-1, s)  # matrix determinant calculation

    return x, r, det