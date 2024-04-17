import math
import os
from parser import Parser
from dto import PointTable, ApproxRes
from approx import APPROXIMATORS
from utils import draw_graph


def main() -> None:
    os.chdir('/' + '/'.join(__file__.split('/')[:-2]) + '/resources')
    data: PointTable | None = Parser.parse_table_from_file(input('Print input filename -> '))
    if data is None:
        return

    Parser.clear_file()
    min_sko: float = math.inf
    min_type: str | None = None
    for approximator in APPROXIMATORS:
        res: ApproxRes = approximator(data)
        Parser.print_res(res)

        if res.data is not None:
            draw_graph(res.data, res.type)
            if res.data.sko < min_sko:
                min_sko = res.data.sko
                min_type = res.type

    if min_type is not None:
        print(f'Best approximator is {min_type} with sko = {min_sko:.4g}')


if __name__ == '__main__':
    main()
