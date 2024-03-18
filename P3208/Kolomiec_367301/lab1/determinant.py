def minor(matrix, i, j) -> list[list[float]]:
    tmp = matrix
    tmp = tmp[:i] + tmp[i + 1:]
    for k in range(len(tmp)):
        tmp[k] = tmp[k][:j] + tmp[k][j + 1:]
    return tmp


def calculate(matrix, n) -> float:
    if n == 1: return matrix[0][0]
    if n == 2: return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    det = 0
    for i in range(n):
        tmp = minor(matrix, 0, i)
        det += (-1) ** i * matrix[0][i] * calculate(tmp, n - 1)
    return det
