from Kolomiec_367301.lab3.methods.method import Method
from Kolomiec_367301.lab3.utils.form import *


class Trapezoid(Method):
    def __init__(self, name):
        super().__init__(name)

    def calculate(self, func, epsilon):
        a, b = input_limits()
        n = input_n()
        h = abs(a - b) / n

        result = 0

        x, y = [], []
        copy = n
        while n >= 0:

            if n == 0 or n == copy:
                result += func.get_ordinate(a) / 2
            else:
                result += func.get_ordinate(a)

            x.append(round(a, epsilon))
            y.append(round(func.get_ordinate(a), epsilon))

            a += h
            n -= 1
        print("Ответ:", round(h * result, epsilon))
        self.draw_method(x, y)
