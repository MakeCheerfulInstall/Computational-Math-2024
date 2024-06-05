from parser import Parser
from utils import *
from dto import *
import os
from interpolation import Solver

DEF_ACCURACY: int = 100


def main() -> None:
    os.chdir('/' + '/'.join(__file__.split('/')[:-2]) + '/resources')
    inp_type: int = Parser.choose_input_type()
    points: PointTable | None = None
    if inp_type == InputType.CMD:
        print('Type all x in a row and all y in next row')
        points = Parser.parse_table_from_cmd()
    elif inp_type == InputType.FILE:
        filename: str = input('Type filename -> ')
        points = Parser.parse_table_from_file(filename)
    else:
        points = Parser.get_def_func_points()

    if points is None:
        return

    x: float = to_float(input('Input x -> '))
    solver: Solver = Solver(points, x, max(DEF_ACCURACY, len(points) * 5))
    res: list[Result] = solver.solve_all()
    for r in res:
        if r.answer.y is None:
            print('Не удалось найти решение методом "', r.title, '"', sep='')
            continue

        draw_graph(points, r)
        print('Ответ методом "', r.title, '": ', f'{r.answer.y:.5g}', sep='')


if __name__ == "__main__":
    main()
