class MethodData:
    def __init__(self, a: float, b: float, e: float) -> None:
        self.a = a
        self.b = b
        self.e = e


class Point:
    def __init__(self, x: float, y: float) -> None:
        self.x = x
        self.y = y

    def __str__(self) -> str:
        return f'Point({self.x}, {self.y})'


class MethodResult:
    def __init__(self, point: Point, iterations: int) -> None:
        self.point = point
        self.iterations = iterations

    def __str__(self) -> str:
        return f'{self.point} It={self.iterations}'
