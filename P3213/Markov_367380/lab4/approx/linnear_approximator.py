import math

from P3213.Markov_367380.lab4.approx.approximator import Approximator
from P3213.Markov_367380.lab4.dto.request import Request
from P3213.Markov_367380.lab4.dto.response import Response
from P3213.Markov_367380.lab4.dto.matrix import SquareMatrix, Matrix
from P3213.Markov_367380.lab4.type.response_type import ResponseType
from P3213.Markov_367380.lab4.utils.utils_functions import lambda_to_string
from statistics import mean
from typing import Callable


class LinnearApproximator(Approximator):
    @staticmethod
    def get_pirson(request: Request):
        xs: list[float] = request.get_xs()
        ys: list[float] = request.get_ys()
        avg_x: float = mean(xs)
        avg_y: float = mean(ys)

        return sum([(xs[i] - avg_x) * (ys[i] - avg_y) for i in range(request.n)]) / math.sqrt(
            sum([(xs[i] - avg_x) * (xs[i] - avg_x) for i in range(request.n)]) * sum(
                [(ys[i] - avg_y) * (ys[i] - avg_y) for i in range(request.n)]))

    def get_coefs(self, request: Request) -> tuple:
        x2_sum: float = request.get_xn_sum(2)
        x_sum: float = request.get_x_sum()
        xy_sum: float = request.get_xnyn_sum(1, 1)
        y_sum: float = request.get_y_sum()

        matrix: SquareMatrix = SquareMatrix(2, [[x2_sum, x_sum], [x_sum, request.n]])
        result: list[list[float]] = matrix.solve_cramer(Matrix(2, 1, [[xy_sum], [y_sum]])).get_data()
        return result[0][0], result[1][0]

    def approximate(self, request: Request) -> Response:
        coefs: tuple = self.get_coefs(request)
        a1, a0 = coefs
        phi: Callable = lambda x: a1 * x + a0
        return Response(
            ResponseType.LINNEAR_APPROXIMATOR,
            Approximator.get_sd(request, phi),
            lambda_to_string(phi, coefs),
            request.get_xs(),
            request.get_ys(),
            Approximator.get_phi_values(request, phi),
            Approximator.get_diff(request, phi),
            Approximator.get_det(request, phi),
            LinnearApproximator.get_pirson(request)
        )
