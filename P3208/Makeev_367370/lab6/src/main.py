from dto import *
from parser import Parser
from solver import Solver
from utils import draw_graph


def main():
    diff_ur: DiffUrDto = Parser.choose_diff_ur()
    params: ParamsDto = Parser.parse_params()
    if params is None:
        return

    solver: Solver = Solver(diff_ur, params)
    try:
        answers: list[AnswerDto] = solver.solve()
    except Exception:
        print('No solutions found')
        return

    for ans in answers:
        print(ans)
        draw_graph(ans)


if __name__ == '__main__':
    main()
