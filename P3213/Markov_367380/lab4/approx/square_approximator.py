from P3213.Markov_367380.lab4.approx.approximator import Approximator
from P3213.Markov_367380.lab4.dto.request import Request
from P3213.Markov_367380.lab4.dto.response import Response
from P3213.Markov_367380.lab4.dto.matrix import SquareMatrix, Matrix
from P3213.Markov_367380.lab4.type.response_type import ResponseType
from P3213.Markov_367380.lab4.utils.utils_functions import lambda_to_string
from typing import Callable


class SquareApproximator(Approximator):
    def get_coefs(self, request: Request) -> tuple:
        x2_sum: float = request.get_xn_sum(2)
        x_sum: float = request.get_x_sum()
        y_sum: float = request.get_y_sum()
        x3_sum: float = request.get_xn_sum(3)
        x4_sum: float = request.get_xn_sum(4)
        xy_sum: float = request.get_xnyn_sum(1, 1)
        x2y_sum: float = request.get_xnyn_sum(2, 1)
        matrix: SquareMatrix = SquareMatrix(3, [
            [request.n, x_sum, x2_sum],
            [x_sum, x2_sum, x3_sum],
            [x2_sum, x3_sum, x4_sum]
        ])
        print(request.n, x_sum, x2_sum, x3_sum, x4_sum, y_sum, xy_sum, x2y_sum)
        result: list[list[float]] = matrix.solve_cramer(Matrix(3, 1, [[y_sum], [xy_sum], [x2y_sum]])).get_data()
        return result[0][0], result[1][0], result[2][0]

    def approximate(self, request: Request) -> Response:
        coefs: tuple = self.get_coefs(request)
        a0, a1, a2 = coefs
        phi: Callable = lambda x: a0 + a1 * x + a2 * x ** 2

        return Response(
            ResponseType.SQUARE_APPROXIMATOR,
            Approximator.get_sd(request, phi),
            lambda_to_string(phi, coefs),
            request.get_xs(),
            request.get_ys(),
            Approximator.get_phi_values(request, phi),
            Approximator.get_diff(request, phi),
            Approximator.get_det(request, phi)
        )
