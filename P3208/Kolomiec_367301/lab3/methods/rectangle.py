from Kolomiec_367301.lab3.utils.form import *
from .method import Method


class Rectangle(Method):

    def __init__(self, name):
        super().__init__(name)

    def calculate(self, func, epsilon):
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
        while n > 0:
            result += abs(func.get_ordinate(a) * h)
            a += h

            x.append(round(a, epsilon))
            y.append(round(func.get_ordinate(a), epsilon))

            n -= 1
        print("Ответ:", round(result, epsilon))
        self.draw_method(x, y)
