import os
from parser import Parser
from dto import PointTable
from approx import *


def main() -> None:
    os.chdir('/' + '/'.join(__file__.split('/')[:-2]) + '/resources')
    data: PointTable | None = Parser.parse_table_from_file(input('Print input filename -> '))
    if data is None:
        return

    res: ApproxRes = Approximators.LINEAR(data)
    Parser.print_res(res, 'output')

    for approximator in Approximators:
        print('lol')
        res: ApproxRes = approximator.value(data)
        Parser.print_res(res, 'output')


if __name__ == '__main__':
    main()
