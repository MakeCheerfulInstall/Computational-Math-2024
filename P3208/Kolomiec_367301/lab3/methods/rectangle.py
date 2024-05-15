from lab3.function import *
from lab3.utils.form import *
from .method import Method


class Rectangle(Method):

    def __init__(self, name):
        super().__init__(name)

    def calculate(self, func: Function, epsilon):
        way = input_variant(
            ["Левые прямоугольники", "Средние прямоугольники", "Правые прямоугольники"],
            "Введите номер метода: ") - 1
        a, b = input_limits()
        n = input_n()
        h = abs(a - b) / n

        result = 0

        if way == 1:
            a = a + h / 2
        if way == 2:
            a += h

        x, y = [a], [func.get_ordinate(a)]
        a_copy, copy = a, n
        while n > 0:
            result += abs(func.get_ordinate(a) * h)
            a += h

            x.append(round(a, epsilon))
            y.append(round(func.get_ordinate(a), epsilon))

            n -= 1

        ddf = Function(func.diff(2))
        r = abs(ddf.get_max_ordinate((a, b))) * ((b - a_copy) ** 3) / (24 * copy ** 2)
        print("Ответ:", round(result, epsilon))
        print("Погрешность: <", round(r, epsilon))
        self.draw_method(x, y)
