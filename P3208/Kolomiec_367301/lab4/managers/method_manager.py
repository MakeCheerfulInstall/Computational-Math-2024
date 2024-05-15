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

    def draw_all_results(self, dots: tuple):
        results = self.start_all(dots)
        t = PrettyTable()
        t.field_names = ["Вид функции", "a", "b", "c", "d", "S", "δ", "R²"]
        for r in results:
            t.add_row(r)
        print(t)
