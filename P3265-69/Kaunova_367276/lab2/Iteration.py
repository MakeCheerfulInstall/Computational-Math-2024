from sys import stderr

import numpy
import scipy.misc
from prettytable import PrettyTable

from methods.Method import Method


class Iteration(Method):
    name = "простых итераций"

    def solve(self) -> None:
        table = PrettyTable(["N", "x", "x(i-1)", "f(x(i+1))", "|x(i+1) - x|"])
        f = self.equation.function
        
        fpr_left = scipy.misc.derivative(f, self.left, dx=1e-6)
        fpr_right = scipy.misc.derivative(f, self.right, dx=1e-6)
        
#параметр для преобразования ур-ия
        lambdaa = 1 / max(fpr_left, fpr_right)
        if fpr_right > 0 and fpr_left > 0:
            lambdaa = - 1 * lambdaa
            
        phi = lambda x: x + lambdaa * f(x)
        phipr = lambda x: scipy.misc.derivative(phi, x, dx=1e-6)
        
#условие сходимость -> производные на концах интервала <1
        if phipr(self.left) >= 1 or phipr(self.right) >= 1:
            print("Условие сходимости не выполнено(", file=stderr)
            return
        print(f"{phipr(self.left)=} \n{phipr(self.right)=}")

        x_prev = self.left
        x = phi(x_prev)
        count = 1
        while numpy.abs(f(x)) > self.accuracy:
            table.add_row(list(map(lambda i: round(i, self.symbols_after_dot,),
                              [count, x_prev, x, f(x), numpy.abs(x_prev - x)])))
            x_prev = x
            x = phi(x_prev)
            count += 1
        table.add_row(list(map(lambda i: round(i, self.symbols_after_dot),
                               [count, x_prev, x, f(x), numpy.abs(x_prev - x)])))
        print(table)
        print(f"Найденный корень: {round(x, self.symbols_after_dot)}")
        print(f"Значение функции: {f(x)}")
    
