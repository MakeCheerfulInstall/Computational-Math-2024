from abc import abstractmethod
from P3213.Markov_367380.lab6.dto.request import Request
from P3213.Markov_367380.lab6.dto.response import Response
from P3213.Markov_367380.lab6.solver.solver import Solver


class SeveralStepSolver(Solver):
    @abstractmethod
    def solve_for_h(self, request: Request, h: float) -> Response:
        pass
