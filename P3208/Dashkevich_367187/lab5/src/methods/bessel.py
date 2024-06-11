import math
from functools import reduce

from abstract_polynom import Polynomial
from utils5 import *


class Bessel_polynom(Polynomial):
    h = 0
    tree = []

    def __init__(self, points):
        super().__init__(points, list(zeros(len(points))), "stirling")
        self.h = points[1][0] - points[0][0]

        self.tree.append([y for x, y in points])
        self.tree.append([])

        for i in range(1, len(points)):
            self.tree[-1].append(points[i][1] - points[i - 1][1])

        for depth in range(1, len(points) - 1):
            self.tree.append([])
            for i in range(1, len(self.tree[-2])):
                self.tree[-1].append(self.tree[-2][i] - self.tree[-2][i - 1])

    def calc(self, x):
        xs = [i[0] for i in self.points]
        n = len(self.points) - 1
        alpha_ind = n // 2


        dts1 = [0, -1, 1, -2, 2, -3, 3, -4, 4, -5, 5, -6, 6, -7, 7]
        f = lambda x: (self.tree[0][alpha_ind] + self.tree[0][alpha_ind]) / 2 + sum([
            reduce(lambda a, b: a * b,
                   [(x - xs[alpha_ind]) / self.h + dts1[j] for j in range(k)])
            * self.tree[k][len(self.tree[k]) // 2] / math.factorial(2 * k) +
            ((x - xs[alpha_ind]) / self.h - 1 / 2) *
            reduce(lambda a, b: a * b,
                   [(x - xs[alpha_ind]) / self.h + dts1[j] for j in range(k)])
            * self.tree[k][len(self.tree[k]) // 2] / math.factorial(2 * k + 1)
            for k in range(1, n + 1)])
        return f(x)
