from P3213.Markov_367380.lab6.dto.request import Request
from P3213.Markov_367380.lab6.dto.response import Response
from P3213.Markov_367380.lab6.solver.one_step_solver import OneStepSolver
from P3213.Markov_367380.lab6.type.response_type import ResponseType


class EulerSolver(OneStepSolver):
    def solve_for_h(self, request: Request, h: float) -> tuple:
        n: int = int((request.xn - request.x0) / h) + 1
        xs: list[float] = [request.x0 + i * h for i in range(n)]
        ys: list[float] = [request.y0 for i in range(n)]
        for i in range(1, n):
            ys[i] = ys[i - 1] + h * request.func(xs[i - 1], ys[i - 1])
        return xs, ys

    def solve(self, request: Request, h: float, ys_init: list[float] = None) -> Response:
        cur_xs, cur_ys = self.solve_for_h(request, h)
        while True:
            new_xs, new_ys = self.solve_for_h(request, h / 2)
            if abs(new_ys[-1] - cur_ys[-1]) < request.e:
                return Response(
                    ResponseType.EULER_METHOD,
                    cur_xs,
                    cur_ys,
                    abs(new_ys[-1] - cur_ys[-1])
                )
            cur_xs, cur_ys = new_xs, new_ys
            h /= 2
