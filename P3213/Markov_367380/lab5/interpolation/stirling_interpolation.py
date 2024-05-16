from P3213.Markov_367380.lab5.dto.request import Request
from P3213.Markov_367380.lab5.dto.response import Response
from P3213.Markov_367380.lab5.interpolation.interpolation import Interpolation
from P3213.Markov_367380.lab5.type.response_type import ResponseType


class StirlingInterpolation(Interpolation):
    def interpolate(self, request: Request, x: float) -> Response:
        xs: list[float] = request.get_xs()
        ys: list[float] = request.get_ys()
        diffs: list[list[float]] = [[0 for i in range(request.n)] for j in range(request.n)]
        h: float = round(xs[1] - xs[0], 3)

        for i in range(1, request.n - 1):
            if round(xs[i + 1] - xs[i], 3) != h:
                return Response(
                    type=ResponseType.STIRLING_INTERPOLATION,
                    status_code=1,
                    error_message='Узлы не являются равноотстоящими',
                )

        for i in range(request.n):
            diffs[i][0] = ys[i]

        for j in range(1, request.n):
            for i in range(request.n - j):
                diffs[i][j] = diffs[i + 1][j - 1] - diffs[i][j - 1]

        fact: float = 1
        s: int = request.n // 2
        if request.n % 2 == 0:
            s -= 1
        t: float = (x - xs[s]) / h
        y: float = diffs[s][0]
        if abs(t) > 0.25:
            print('|t| > 0.25 => результат Стирлинга может быть неточным')
        odd: int = 1
        even: int = 1
        mult_odd: float = 1
        mult_even: float = 1

        for i in range(1, request.n):
            fact *= i
            if i % 2:
                if odd != 1:
                    mult_odd *= (t ** 2 - (odd - 1) ** 2)
                else:
                    mult_odd *= t
                odd += 1
                s_h = s - ((i + 1) // 2)
                y += ((mult_odd / fact) * ((diffs[s_h][i] + diffs[s_h + 1][i]) / 2))
            else:
                mult_even *= (t ** 2 - (even - 1) ** 2)
                even += 1
                s_h = s - (i // 2)
                y += (mult_even / fact) * diffs[s_h][i]

        return Response(ResponseType.STIRLING_INTERPOLATION, y)
