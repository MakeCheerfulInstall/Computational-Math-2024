from P3213.Markov_367380.lab4.approx.approximator import Approximator
from P3213.Markov_367380.lab4.dto.request import Request
from P3213.Markov_367380.lab4.dto.response import Response
from P3213.Markov_367380.lab4.dto.matrix import SquareMatrix, Matrix
from P3213.Markov_367380.lab4.type.response_type import ResponseType
from P3213.Markov_367380.lab4.utils.utils_functions import lambda_to_string
from typing import Callable


class CubicApproximator(Approximator):
    def get_coefs(self, request: Request) -> tuple:
        x_sum: float = request.get_x_sum()
        x2_sum: float = request.get_xn_sum(2)
        x3_sum: float = request.get_xn_sum(3)
        x4_sum: float = request.get_xn_sum(4)
        x5_sum: float = request.get_xn_sum(5)
        x6_sum: float = request.get_xn_sum(6)
        y_sum: float = request.get_y_sum()
        xy_sum: float = request.get_xnyn_sum(1, 1)
        x2y_sum: float = request.get_xnyn_sum(2, 1)
        x3y_sum: float = request.get_xnyn_sum(3, 1)
        matrix: SquareMatrix = SquareMatrix(4, [
            [x3_sum, x_sum, x2_sum, x3_sum],
            [x_sum, x2_sum, x3_sum, x4_sum],
            [x2_sum, x3_sum, x4_sum, x5_sum],
            [x3_sum, x4_sum, x5_sum, x6_sum]
        ])
        result: list[list[float]] = matrix.solve_cramer(
            Matrix(4, 1, [[y_sum], [xy_sum], [x2y_sum], [x3y_sum]])).get_data()
        return result[0][0], result[1][0], result[2][0], result[3][0]

    def approximate(self, request: Request) -> Response:
        coefs: tuple = self.get_coefs(request)
        a0, a1, a2, a3 = coefs
        phi: Callable = lambda x: a0 + a1 * x + a2 * x ** 2 + a3 * x ** 3
        return Response(
            ResponseType.CUBIC_APPROXIMATOR,
            Approximator.get_sd(request, phi),
            lambda_to_string(phi, coefs),
            request.get_xs(),
            request.get_ys(),
            Approximator.get_phi_values(request, phi),
            Approximator.get_diff(request, phi),
            Approximator.get_det(request, phi)
        )
