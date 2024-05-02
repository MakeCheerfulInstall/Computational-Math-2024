from P3213.Markov_367380.lab5.dto.request import Request
from P3213.Markov_367380.lab5.dto.response import Response
from P3213.Markov_367380.lab5.interpolation.interpolation import Interpolation
from P3213.Markov_367380.lab5.type.response_type import ResponseType


class LagrangeInterpolation(Interpolation):
    def interpolate(self, request: Request, x: float) -> Response:
        y: float = 0
        xs: list[float] = request.get_xs()
        ys: list[float] = request.get_ys()

        for i in range(request.n):
            numerator: float = 1
            denominator: float = 1
            for j in range(request.n):
                if i == j:
                    continue
                numerator *= (x - xs[j])
                denominator *= (xs[i] - xs[j])
            y += ys[i] * (numerator / denominator)

        return Response(ResponseType.LAGRANGE_INTERPOLATION, y)
