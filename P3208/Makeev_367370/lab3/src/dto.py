class IntegralAnswer:
    def __init__(self, value: float, intrv_count: int) -> None:
        self.value = value
        self.intrv_count = intrv_count

    def __str__(self) -> str:
        return f'Integral value: {self.value}\nInterval count: {self.intrv_count}'


class Interval:
    def __init__(self, a: float, b: float) -> None:
        self.a = a
        self.b = b
