from lab3.utils.form import *
from lab3.methods.method import Method
from lab3.function import *


class Simpson(Method):
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
                result += abs(func.get_ordinate(a))
            elif n % 2 == 0:
                result += abs(func.get_ordinate(a)) * 2
            else:
                result += abs(func.get_ordinate(a)) * 4

            x.append(round(a, epsilon))
            y.append(round(func.get_ordinate(a), epsilon))

            a += h
            n -= 1

        ddddf = Function(func.diff(4))
        r = abs(ddddf.get_max_ordinate((a, b))) * ((b - a_copy) ** 5) / (180 * copy ** 4)
        print("Ответ:", round(result, epsilon))
        print("Погрешность: <", round(r, epsilon))
        self.draw_method(x, y)
