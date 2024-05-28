from tabulate import tabulate

from P3208.Terekhin_367558.lab2.main import request_from_list
from P3208.Terekhin_367558.lab2.readers import AbstractReader, READERS

if __name__ == '__main__':
    reader: AbstractReader = request_from_list(READERS)
    points: list[tuple[float, float]] = sorted(reader.read_interpolation_data())
    argument: float = reader.read_interpolation_argument()

    n: int = len(points)
    sub_table: list[list[float]] = [[points[i][1] for i in range(n)]]
    for i in range(n - 1):
        sub_table.append([0] * n)
    x: list[float] = [points[i][0] for i in range(n)]
    headers = ['y']

    for i in range(1, n):
        headers.append(f'Δ^{i}y' if i != 1 else 'Δy')
        for j in range(n - i):
            sub_table[i][j] = (sub_table[i - 1][j + 1] - sub_table[i - 1][j]) / (x[j + 1] - x[j])
    print(tabulate(sub_table, headers, tablefmt='pretty'))


