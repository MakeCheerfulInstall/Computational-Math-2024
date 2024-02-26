from typing import List
import re
from exceptions import DiagonalDominatingError


def swap_rows_sort(mat: List[List[float]]) -> List[List[float]]:
    m: int = len(mat)
    sort_mat: List[List[float]] = [[]] * m
    for i in range(m):
        abs_row: List[float] = [abs(num) for num in mat[i][:-1]]
        max_num: float = max(abs_row)
        max_ind: int = abs_row.index(max_num)
        if sort_mat[max_ind]:
            raise DiagonalDominatingError("Matrix isn't diagonally dominated")
        row_sum: int = sum(abs_row)
        if row_sum > 2 * max_num:
            raise DiagonalDominatingError(f"Diagonal dominating is broken in row {i + 1} of your equation")
        sort_mat[max_ind] = mat[i]
    return sort_mat


def select_vector_equation(mat: List[List[float]]):
    vector_mat: List[List[float]] = []
    for i in range(len(mat)):
        vector_mat.append(list(map(lambda num: num / mat[i][i] if num != mat[i][i] else 0, mat[i])))
    return vector_mat


def get_next_approx(mat: List[List[float]], x: List[float], precision: int) -> List[float]:
    next_approx: List[float] = []
    for i in range(len(x)):
        next_approx.append(round(mat[i][-1] - sum([mat[i][j] * x[j] for j in range(len(x))]), precision))
    return next_approx


def get_precision(epsilon: float) -> int:
    precision: int = 2
    eps_iter: float = epsilon
    while eps_iter < 1:
        eps_iter *= 10
        precision += 1
    return precision


def do_simple_iteration(mat: List[List[float]], approx: List[float], precision: float) -> List[float]:
    k: int = len(approx)
    next_approx: List[float] = get_next_approx(select_vector_equation(mat), approx, get_precision(precision))
    if max([abs(approx[i] - next_approx[i]) for i in range(k)]) < precision:
        return next_approx
    return do_simple_iteration(mat, next_approx, precision)


def read_matrix_from_console(lines: int) -> List[List[float]]:
    print('Input your equation as an extended matrix line by line',
          'The format is: ' + ' '.join([f'a[{i + 1}]' for i in range(lines)]) + ' | b', sep='\n')
    while True:
        mat: List[List[float]] = []
        for i in range(lines):
            mat.append(read_row_from_console(i + 1))
        try:
            return swap_rows_sort(mat)
        except DiagonalDominatingError as e:
            print(e)


def read_row_from_console(step: int) -> List[float]:
    while True:
        s: List[str] = list(filter(lambda a: a, re.split("[ |]+", input(f'Line [{step} / {n}]: '))))
        if len(s) != n + 1:
            print('Dimension is incorrect')
            continue
        try:
            return list(map(float, s))
        except ValueError as ex:
            print(ex)


def read_dimension_and_precision() -> tuple[int, float]:
    dim: int = 0
    precision: float = 0
    while True:
        try:
            dim = int(input('Enter matrix dimension: '))
        except ValueError:
            print("Can't parse integer value")
            continue
        if dim > 20 or dim < 0:
            print('It should be less than or equal to 20. Try again')
            continue
        break

    while True:
        try:
            precision = float(input('Input precision number: '))
        except ValueError:
            print("Can't parse float value")
            continue
        if precision >= 1 or precision <= 1e-10:
            print('Precision is a number less than 1. Please try again')
            continue
        break
    return dim, precision


def choose_input_descriptor():
    variants: List[str] = ['From file', 'Using console']
    var: int = 0
    print('How do you want to read your equation?', '\n'.join([f'{ind + 1}. {v}' for ind, v in enumerate(variants)]), sep='\n')
    while True:
        try:
            var = int(input())
        except ValueError:
            print('No such variant. Try again')
            continue
        if var < 1 or var > len(variants):
            print('No such variant. Try again')
            continue
        return var


n, eps = read_dimension_and_precision()
inp: int = choose_input_descriptor()
matrix: List[List[float]] = read_matrix_from_console(n)
sorted_mat: List[List[float]] = swap_rows_sort(matrix)

print(do_simple_iteration(sorted_mat, [0] * n, eps))


