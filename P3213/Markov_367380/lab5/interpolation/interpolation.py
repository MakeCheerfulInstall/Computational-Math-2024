from abc import abstractmethod, ABC
from P3213.Markov_367380.lab5.dto.request import Request
from P3213.Markov_367380.lab5.dto.response import Response


class Interpolation(ABC):
    @abstractmethod
    def interpolate(self, request: Request, x: float) -> Response:
        pass
