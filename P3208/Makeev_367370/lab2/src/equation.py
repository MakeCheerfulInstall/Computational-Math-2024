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

    def get_der_res(self, x) -> float:
        return self.ex_der(x)


class EquationSystem:
    def __init__(self, expr1, expr2, ex_der1, ex_der2, roots, view) -> None:
        self.expr1 = expr1
        self.expr2 = expr2
        self.ex_der1 = ex_der1
        self.ex_der2 = ex_der2
        self.roots = roots
        self.view = view

    def __str__(self) -> str:
        return self.view

    def solve(self) -> None:
        pass
