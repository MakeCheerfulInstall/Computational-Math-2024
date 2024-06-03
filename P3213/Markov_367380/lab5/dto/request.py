from P3213.Markov_367380.lab5.dto.point import Point


class Request:
    def __init__(self, points=None, xs=None, ys=None):
        if points:
            self.points = points
            self.n = len(points)
        else:
            self.n = len(xs)
            self.points = [Point(xs[i], ys[i]) for i in range(self.n)]

    def get_xs(self) -> list[float]:
        return [point.x for point in self.points]

    def get_ys(self) -> list[float]:
        return [point.y for point in self.points]

    def get_x_sum(self) -> float:
        return sum([point.x for point in self.points])

    def get_y_sum(self) -> float:
        return sum([point.y for point in self.points])

    def get_xn_sum(self, power: int) -> float:
        return sum([point.x ** power for point in self.points])

    def get_yn_sum(self, power: int) -> float:
        return sum([point.y ** power for point in self.points])

    def get_xnyn_sum(self, power_x: int, power_y: int) -> float:
        return sum([point.x ** power_x * point.y ** power_y for point in self.points])
