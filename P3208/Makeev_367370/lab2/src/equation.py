from typing import Callable
from utils import find_func_max_abs, check_func_abs_smaller_one


class Equation:
    def __init__(self, expr, ex_der, roots, view) -> None:
        self.expr = expr
        self.ex_der = ex_der
        self.roots = roots
        self.view = view

    def __str__(self) -> str:
        return self.view

    def has_one_root(self, segment) -> bool:
        counter = 0
        for root in self.roots:
            if min(segment) < root < max(segment):
                counter += 1

        return counter == 1

    def get_res(self, x) -> float:
        return self.expr(x)

    def create_phi_func(self, a: float, b: float) -> Callable | None:
        der_max_abs: float = find_func_max_abs(self.ex_der, a, b)
        lam: float = -1 / der_max_abs
        der_phi = lambda x: 1 + lam * self.ex_der(x)
        if check_func_abs_smaller_one(der_phi, a, b):
            return lambda x: x + lam * self.expr(x)
        else:
            return None


class EquationSystem:
    def __init__(self, expressions, phi1_data, phi2_data, roots, views) -> None:
        self.expressions = expressions
        self.phi1_data = phi1_data
        self.phi2_data = phi2_data
        self.roots = roots
        self.views = views

    def __str__(self) -> str:
        return ('\n\t\t +--\n' +
                  '\t\t | ' + self.views[0] + '\n' +
                  '\t\t-+\n' +
                  '\t\t | ' + self.views[1] + '\n' +
                  '\t\t +--\n')

    def has_one_root(self, segment) -> bool:
        counter = 0
        for root_pair in self.roots:
            if (min(segment[0]) < root_pair[0] < max(segment[0]) and
                    min(segment[1]) < root_pair[1] < max(segment[1])):
                counter += 1

        return counter == 1
