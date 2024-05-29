import numpy as np
from matplotlib import pyplot as plt
from prettytable import PrettyTable

from lab5.methods.method import Method


class MethodManager:

    def __init__(self, storage: list[Method]) -> None:
        super().__init__()
        self.storage = storage

    def add_to_storage(self, method: Method):
        self.storage.append(method)

    def start_calculate(self, method_number: int, dots: tuple, arg: float):
        return self.storage[method_number].calculate(dots, arg, False)

    def start_all(self, dots: tuple, arg: float):
        return [self.start_calculate(i, dots, arg) for i in range(len(self.storage))]

    def get_methods_names(self):
        return [method.name for method in self.storage]

    def draw_all_results(self, dots: tuple, arg: float):
        results = self.start_all(dots, arg)
        t = PrettyTable()
        t.field_names = ["Вид функции", "a", "b", "c", "d", "S", "δ", "R²"]
        for r in results:
            t.add_row(r)
        print(t)

    def draw_graphic(self, dots: tuple):
        x, y = dots

        left, right = min(x), max(x)

        abcis = np.linspace(left, right, 100)
        colors = ["blue", "green", "orange"]
        for i in range(len(self.storage)):
            plt.plot(abcis, [self.storage[i].calculate(dots, j, True) for j in abcis], color=colors[i],
                     label=self.storage[i].name)
        plt.scatter(x, y, color="red")

        # Отображение графика
        plt.show()
