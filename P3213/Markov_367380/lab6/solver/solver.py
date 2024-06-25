from abc import abstractmethod, ABC
from P3213.Markov_367380.lab6.dto.request import Request
from P3213.Markov_367380.lab6.dto.response import Response


class Solver(ABC):
    @abstractmethod
    def solve(self, request: Request, h: float, ys_init: list[float] = None) -> Response:
        pass

