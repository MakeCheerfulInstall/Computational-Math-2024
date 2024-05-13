from Kolomiec_367301.lab3.function import *
from Kolomiec_367301.lab3.methods.method import Method
from Kolomiec_367301.lab3.utils.form import *


class Trapezoid(Method):
    def __init__(self, name):
        super().__init__(name)

    def calculate(self, func: Function, epsilon):
        a, b = input_limits()
        n = input_n()
        h = abs(a - b) / n

        result = 0

        x, y = [], []
        copy, a_copy = n, a
        while n >= 0:

            if n == 0 or n == copy:
                result += abs(func.get_ordinate(a)) / 2
            else:
                result += abs(func.get_ordinate(a))

            x.append(round(a, epsilon))
            y.append(round(func.get_ordinate(a), epsilon))

            a += h
            n -= 1

        ddf = Function(func.diff(2))
        r = abs(ddf.get_max_ordinate((a, b))) * ((b - a_copy) ** 3) / (12 * copy ** 2)
        print("Ответ:", round(result, epsilon))
        print("Погрешность: <", round(r, epsilon))
        self.draw_method(x, y)
