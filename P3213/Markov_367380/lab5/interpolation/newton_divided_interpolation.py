from P3213.Markov_367380.lab5.dto.request import Request
from P3213.Markov_367380.lab5.dto.response import Response
from P3213.Markov_367380.lab5.interpolation.interpolation import Interpolation
from P3213.Markov_367380.lab5.type.response_type import ResponseType


class NewtonDividedInterpolation(Interpolation):
    @staticmethod
    def get_divided_f(xs: list[float], ys: list[float], i, k, diffs: list[list[float | None]]):
        if k == 0:
            diffs[i][k] = ys[i]
            return diffs[i][k]

        if k == 1:
            diffs[i][k] = (ys[i + k] - ys[i]) / (xs[i + k] - xs[i])
            return diffs[i][k]

        if diffs[i + 1][k - 1] is None:
            diffs[i + 1][k - 1] = NewtonDividedInterpolation.get_divided_f(xs, ys, i + 1, k - 1, diffs)
        if diffs[i][k - 1] is None:
            diffs[i][k - 1] = NewtonDividedInterpolation.get_divided_f(xs, ys, i, k - 1, diffs)
        return (diffs[i + 1][k - 1] - diffs[i][k - 1]) / (xs[i + k] - xs[i])

    def interpolate(self, request: Request, x: float) -> Response:
        xs: list[float] = request.get_xs()
        ys: list[float] = request.get_ys()
        diffs: list[list[float | None]] = [[None for i in range(request.n)] for j in range(request.n)]
        y: float = ys[0]

        for i in range(1, request.n):
            mult: float = 1
            for j in range(i):
                mult *= (x - xs[j])
            y += NewtonDividedInterpolation.get_divided_f(xs, ys, 0, i, diffs) * mult

        return Response(ResponseType.NEWTON_DIVIDED_INTERPOLATION, y)
