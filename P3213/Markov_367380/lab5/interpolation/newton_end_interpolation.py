from P3213.Markov_367380.lab5.dto.request import Request
from P3213.Markov_367380.lab5.dto.response import Response
from P3213.Markov_367380.lab5.interpolation.interpolation import Interpolation
from P3213.Markov_367380.lab5.type.response_type import ResponseType


class NewtonEndInterpolation(Interpolation):
    @staticmethod
    def get_end_f(ys: list[float], i, k, diffs: list[list[float | None]]):
        if k == 1:
            diffs[i][k] = ys[i + 1] - ys[i]
            return diffs[i][k]

        if diffs[i + 1][k - 1] is None:
            diffs[i + 1][k - 1] = NewtonEndInterpolation.get_end_f(ys, i + 1, k - 1, diffs)
        if diffs[i][k - 1] is None:
            diffs[i][k - 1] = NewtonEndInterpolation.get_end_f(ys, i, k - 1, diffs)
        return diffs[i + 1][k - 1] - diffs[i][k - 1]

    def interpolate(self, request: Request, x: float) -> Response:
        xs: list[float] = request.get_xs()
        ys: list[float] = request.get_ys()
        diffs: list[list[float | None]] = [[None for i in range(request.n)] for j in range(request.n)]
        y: float = ys[-1]
        h: float = round(xs[1] - xs[0], 3)
        fact: float = 1
        h_pow: float = 1

        for i in range(1, request.n - 1):
            if round(xs[i + 1] - xs[i], 3) != h:
                print(xs[i + 1] - xs[i], h)
                return Response(
                    type=ResponseType.NEWTON_END_INTERPOLATION,
                    status_code=1,
                    error_message='Узлы не являются равноотстоящими',
                )
        for i in range(1, request.n):
            mult: float = 1
            fact *= i
            h_pow *= h
            for j in range(i):
                mult *= (x - xs[j])
            y += (NewtonEndInterpolation.get_end_f(ys, 0, i, diffs) * mult) / (fact * h_pow)

        return Response(ResponseType.NEWTON_END_INTERPOLATION, y)
