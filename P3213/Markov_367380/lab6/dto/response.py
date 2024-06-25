from P3213.Markov_367380.lab6.type.response_type import ResponseType


class Response:
    def __init__(self,
                 type: ResponseType,
                 xs: list[float] | None = None,
                 ys: list[float] | None = None,
                 e: float | None = None,
                 status_code: float = 0,
                 error_message: str = ""
                 ):
        self.type: ResponseType = type
        self.xs = xs
        self.ys = ys
        self.e = e
        self.status_code: float = status_code
        self.error_message: str = error_message
