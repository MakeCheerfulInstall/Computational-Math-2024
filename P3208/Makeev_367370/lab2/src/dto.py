class MethodData:
    def __init__(self, e: float, a: float, b: float, a_y: float=None, b_y:float=None) -> None:
        self.a = a
        self.b = b
        self.e = e
        self.a_y = a_y
        self.b_y = b_y


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


class PhiData:
    def __init__(self, phi, der_x, der_y) -> None:
        self.phi = phi
        self.der_x = der_x
        self.der_y = der_y
