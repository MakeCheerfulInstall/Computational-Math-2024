from typing import Callable


class Request:
    def __init__(self, x0: float, xn: float, y0: float, e: float, func: Callable):
        self.y0 = y0
        self.x0 = x0
        self.xn = xn
        self.e = e
        self.func = func

