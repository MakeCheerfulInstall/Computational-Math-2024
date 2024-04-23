from P3213.Markov_367380.lab4.type.response_type import ResponseType


class Response:
    def __init__(self,
                 type: ResponseType,
                 sd: float | None = None,
                 func: str | None = None,
                 xs: list[float] | None = None,
                 ys: list[float] | None = None,
                 phi_values: list[float] | None = None,
                 diff: list[float] | None = None,
                 det: float | None = None,
                 pirson: float | None = None,
                 status_code: float = 0,
                 error_message: str = ""
                 ):
        self.type: ResponseType = type
        self.sd: float | None = sd
        self.func: str | None = func
        self.xs: list[float] | None = xs
        self.ys: list[float] | None = ys
        self.phi_values: list[float] | None = phi_values
        self.diff: list[float] | None = diff
        self.det: float | None = det
        self.pirson: float | None = pirson
        self.status_code: float = status_code
        self.error_message: str = error_message
