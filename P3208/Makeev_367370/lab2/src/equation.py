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
    def __init__(self, expr1, expr2, ex1_der_x, ex1_der_y,
                 ex2_der_x, ex2_der_y, roots, view1, view2) -> None:
        self.expr1 = expr1
        self.expr2 = expr2
        self.ex1_der_x = ex1_der_x
        self.ex1_der_y = ex1_der_y
        self.ex2_der_x = ex2_der_x
        self.ex2_der_y = ex2_der_y
        self.roots = roots
        self.view1 = view1
        self.view2 = view2

    def __str__(self) -> str:
        return ('\n\t\t +--\n' +
                  '\t\t | ' + self.view1 + '\n' +
                  '\t\t-+\n' +
                  '\t\t | ' + self.view2 + '\n' +
                  '\t\t +--\n')

    def has_one_root(self, segment) -> bool:
        counter = 0
        for root_pair in self.roots:
            if min(segment) < root_pair[0] < max(segment):
                counter += 1

        return counter == 1
