from P3213.Markov_367380.lab5.type.response_type import ResponseType


class Response:
    def __init__(self,
                 type: ResponseType,
                 ans: float | None = None,
                 status_code: float = 0,
                 error_message: str = ""
                 ):
        self.type: ResponseType = type
        self.ans: float | None = ans
        self.status_code: float = status_code
        self.error_message: str = error_message
