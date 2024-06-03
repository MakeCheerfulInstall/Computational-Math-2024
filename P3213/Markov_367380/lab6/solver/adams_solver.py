from P3213.Markov_367380.lab6.dto.request import Request
from P3213.Markov_367380.lab6.dto.response import Response
from P3213.Markov_367380.lab6.solver.several_step_solver import SeveralStepSolver
from P3213.Markov_367380.lab6.type.response_type import ResponseType


class AdamsSolver(SeveralStepSolver):
    def solve_for_h(self, request: Request, h: float) -> tuple:
        n: int = int((request.xn - request.x0) / h) + 1
        xs: list[float] = [request.x0 + i * h for i in range(n)]
        ys: list[float] = [request.y0 for i in range(n)]
        for i in range(1, 4):
            ys[i] = ys[i - 1] + h * request.func(xs[i - 1], ys[i - 1])
        for i in range(4, n):
            df: float = request.func(xs[i - 1], ys[i - 1]) - request.func(xs[i - 2], ys[i - 2])
            df2: float = request.func(xs[i - 1], ys[i - 1]) - 2 * request.func(xs[i - 2], ys[i - 2]) + request.func(
                xs[i - 3], ys[i - 3])
            df3: float = request.func(xs[i - 1], ys[i - 1]) - 3 * request.func(xs[i - 2], ys[i - 2]) + 3 * request.func(
                xs[i - 3], ys[i - 3]) - request.func(xs[i - 4], ys[i - 4])
            ys[i] = ys[i - 1] + h * request.func(xs[i - 1], ys[
                i - 1]) + h ** 2 / 2 * df + 5 * h ** 3 / 12 * df2 + 3 * h ** 4 / 8 * df3
        return xs, ys

    def solve(self, request: Request, h: float, ys_init: list[float] = None) -> Response:
        cur_xs, cur_ys = self.solve_for_h(request, h)
        return Response(
            ResponseType.ADAMS_METHOD,
            cur_xs,
            cur_ys,
            max([abs(ys_init[i] - cur_ys[i]) for i in range(len(ys_init))])
        )
