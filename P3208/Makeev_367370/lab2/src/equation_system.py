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
