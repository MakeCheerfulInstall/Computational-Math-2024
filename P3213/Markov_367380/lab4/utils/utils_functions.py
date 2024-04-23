import inspect
from typing import Callable

from P3213.Markov_367380.lab4.dto.response import Response
import matplotlib.pyplot as plt
from matplotlib.axes import Axes


def lambda_to_string(func: Callable, coefs: tuple) -> str:
    coefs_str: str = str(tuple(f'a{i}' for i in range(len(coefs)))) + f' = {str(coefs)}'
    return f"{inspect.getsource(func).split(':')[-1].strip()}, {coefs_str}"


def show(data: Response):
    plt.figure(figsize=(5, 5))
    plt.plot(data.xs, data.ys, 'ro', linewidth=3)
    plt.plot(data.xs, data.phi_values, linewidth=1)
    plt.grid(True)
    plt.title(data.type.value)
    plt.show()


def out(data: Response):
    print()
    print(f'{data.type.value}:')
    if not data.status_code:
        print('\txi:')
        print(f'\t\t{list(map(lambda x: round(x, 5), data.xs))}')
        print('\tyi:')
        print(f'\t\t{list(map(lambda x: round(x, 5), data.ys))}')
        print('\tСКО:')
        print(f'\t\t{data.sd:.5g}')
        print('\tphi:')
        print(f'\t\t{data.func}')
        print(f'\t\t{list(map(lambda x: round(x, 5), data.phi_values))}')
        print('\teps:')
        print(f'\t\t{list(map(lambda x: round(x, 5), data.diff))}')
        print('\tdet:')
        print(f'\t\t{data.det:.5g}')
        if data.pirson:
            print('\tpirson:')
            print(f'\t\t{data.pirson:.5g}')
    else:
        print('\tstatus:')
        print(f'\t\t{data.status_code}')
        print('\terror:')
        print(f'\t\t{data.error_message}')
    print('-' * 30)
