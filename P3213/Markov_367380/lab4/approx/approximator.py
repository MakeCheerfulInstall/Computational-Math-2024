from abc import abstractmethod, ABC
from P3213.Markov_367380.lab4.dto.request import Request
from P3213.Markov_367380.lab4.dto.response import Response
from typing import Callable
from statistics import mean
import math


class Approximator(ABC):
    @staticmethod
    def get_phi_values(request: Request, phi: Callable) -> list[float]:
        return list(map(phi, request.get_xs()))

    @staticmethod
    def get_diff(request: Request, phi: Callable) -> list[float]:
        phi_values: list[float] = Approximator.get_phi_values(request, phi)
        y_values: list[float] = request.get_ys()
        return [phi_values[i] - y_values[i] for i in range(request.n)]

    @staticmethod
    def get_diff_square(request: Request, phi: Callable) -> list[float]:
        return [e * e for e in Approximator.get_diff(request, phi)]

    @staticmethod
    def get_sd(request: Request, phi: Callable) -> float:
        return math.sqrt(sum(Approximator.get_diff_square(request, phi)))

    @staticmethod
    def get_avg_phi(request: Request, phi: Callable) -> float:
        return mean(Approximator.get_phi_values(request, phi))

    @staticmethod
    def get_det(request: Request, phi: Callable):
        y_values: list[float] = request.get_ys()
        avg_phi: float = Approximator.get_avg_phi(request, phi)
        return 1 - sum(Approximator.get_diff_square(request, phi)) / sum(
            [(y_values[i] - avg_phi) ** 2 for i in range(request.n)])

    @abstractmethod
    def get_coefs(self, request: Request) -> tuple:
        pass
    
    @abstractmethod
    def approximate(self, request: Request) -> Response:
        pass
