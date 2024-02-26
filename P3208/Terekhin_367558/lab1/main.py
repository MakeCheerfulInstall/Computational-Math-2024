from typing import List
from exceptions import DiagonalDominatingError


def swap_rows_sort(mat: List[List[int]]) -> List[List[int]]:
    n: int = len(mat)
    sorted_mat: List[List[int]] = [[]] * n
    for i in range(n):
        abs_row: List[int] = [abs(num) for num in mat[i][:-1]]
        max_num: int = max(abs_row)
        max_ind: int = abs_row.index(max_num)
        if sorted_mat[max_ind]:
            raise DiagonalDominatingError("Matrix isn't diagonally dominated")
        row_sum: int = sum(abs_row)
        if row_sum > 2 * max_num:
            raise DiagonalDominatingError(f"Diagonal dominating is broken in row {i + 1} of your equation")
        sorted_mat[max_ind] = mat[i]
    return sorted_mat


def select_vector_equation(mat: List[List[int]]):
    vector_mat: List[List[float]] = []
    for i in range(len(mat)):
        vector_mat.append(list(map(lambda num: num / mat[i][i] if num != mat[i][i] else 0, mat[i])))
        # vector_mat[i] = lambda x: mat[i][-1] / mat[i][i] - sum([x[j] * mat[i][j] / mat[i][i] for j in range(len(x))])
    return vector_mat


def get_next_approx(mat: List[List[float]], x: List[float], precision: int) -> List[float]:
    next_approx: List[float] = []
    for i in range(len(x)):
        next_approx.append(round(mat[i][-1] - sum([mat[i][j] * x[j] for j in range(len(x))]), precision))
    return next_approx


def get_precision(eps: float) -> int:
    precision: int = 2
    eps_iter: float = eps
    while eps_iter < 1:
        eps_iter *= 10
        precision += 1
    return precision


def do_simple_iteration(mat: List[List[int]], approx: List[float], eps: float) -> List[float]:
    k: int = len(approx)
    next_approx: List[float] = get_next_approx(select_vector_equation(mat), approx, get_precision(eps))
    if max([abs(approx[i] - next_approx[i]) for i in range(k)]) < eps:
        return next_approx
    return do_simple_iteration(mat, next_approx, eps)


A: List[List[int]] = [[2, 2, 10, 14], [10, 1, 1, 12], [2, 10, 1, 13]]

try:
    m = swap_rows_sort(A)
    print(do_simple_iteration(m, [0] * len(m), 0.01))
except DiagonalDominatingError as e:
    print(e)

