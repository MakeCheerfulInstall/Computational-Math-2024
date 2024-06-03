import math

from P3213.Markov_367380.lab4.approx.approximator import Approximator
from P3213.Markov_367380.lab4.dto.request import Request
from P3213.Markov_367380.lab4.dto.response import Response
from P3213.Markov_367380.lab4.dto.matrix import SquareMatrix, Matrix
from P3213.Markov_367380.lab4.type.response_type import ResponseType
from P3213.Markov_367380.lab4.utils.utils_functions import lambda_to_string
from typing import Callable


class PowerApproximator(Approximator):
    def get_coefs(self, request: Request) -> tuple:
        ln_request: Request = Request(xs=list(map(math.log, request.get_xs())), ys=list(map(math.log, request.get_ys())))
        x2_sum: float = ln_request.get_xn_sum(2)
        x_sum: float = ln_request.get_x_sum()
        xy_sum: float = ln_request.get_xnyn_sum(1, 1)
        y_sum: float = ln_request.get_y_sum()
        matrix: SquareMatrix = SquareMatrix(2, [[x2_sum, x_sum], [x_sum, ln_request.n]])
        result: list[list[float]] = matrix.solve_cramer(Matrix(2, 1, [[xy_sum], [y_sum]])).get_data()
        return result[0][0], result[1][0]

    def approximate(self, request: Request) -> Response:
        try:
            coefs: tuple = self.get_coefs(request)
        except ValueError:
            return Response(
                type=ResponseType.POWER_APPROXIMATOR,
                status_code=3,
                error_message="Can't apply approximation: x <= 0 || y<=0"
            )
        coefs = coefs[0], math.exp(coefs[1])
        a1, a0 = coefs
        phi: Callable = lambda x: a0 * (x ** a1)
        return Response(
            ResponseType.POWER_APPROXIMATOR,
            Approximator.get_sd(request, phi),
            lambda_to_string(phi, coefs),
            request.get_xs(),
            request.get_ys(),
            Approximator.get_phi_values(request, phi),
            Approximator.get_diff(request, phi),
            Approximator.get_det(request, phi)
        )
