import matplotlib.pyplot as plt
import numpy as np
from prettytable import PrettyTable

from lab4.methods.method import Method


class MethodManager:

    def __init__(self, storage: list[Method]) -> None:
        super().__init__()
        self.storage = storage

    def add_to_storage(self, method: Method):
        self.storage.append(method)

    def start_calculate(self, method_number: int, dots: tuple):
        return self.storage[method_number].calculate(dots)

    def start_all(self, dots: tuple):
        return [[self.storage[i].view] + self.start_calculate(i, dots) for i in range(len(self.storage))]

    def get_methods_names(self):
        return [method.name for method in self.storage]

    def get_approx_func(self, dots: tuple):
        x = dots[0]

        results = self.start_all(dots)
        max_r, tmp = -1, 0

        for i in range(len(results)):
            if results[i][-1] > max_r:
                max_r = results[i][-1]
                tmp = i

        coefficients = results[tmp][1:5]
        view = results[tmp][0]
        while "-" in coefficients: coefficients.remove("-")
        return tmp, view, coefficients

    def draw_all_results(self, dots: tuple):
        results = self.start_all(dots)
        t = PrettyTable()
        t.field_names = ["Вид функции", "a", "b", "c", "d", "S", "δ", "R²"]
        for r in results:
            t.add_row(r)
        print(t)

    def draw_graphic(self, dots: tuple):
        x, y = dots
        left_corner, right_corner = min(x) - 2, max(x) + 2
        index, view, coefficients = self.get_approx_func(dots)

        space = np.linspace(left_corner, right_corner, 100)
        empire_y = self.storage[index].calculate_empire_func_ordinate(list(space), coefficients)

        fig, ax = plt.subplots()

        ax.scatter(x, y, color='red')
        ax.set_xlim(left=left_corner, right=right_corner)

        plt.plot(space, empire_y)
        plt.title(f"График функции: {view}")

        ax.set_xlabel("x")
        ax.set_ylabel("y")
        plt.show()
