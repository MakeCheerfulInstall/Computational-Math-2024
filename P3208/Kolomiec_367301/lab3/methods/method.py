from abc import abstractmethod, ABC

from prettytable import PrettyTable


class Method(ABC):

    def __init__(self, name):
        self.name = name

    @abstractmethod
    def calculate(self, func, epsilon):
        pass

    def draw_method(self, x, y):
        t = PrettyTable()
        t.title = self.name
        t.field_names = ["i"] + [str(i) for i in range(len(x))]
        t.add_row(["x_i"] + x)
        t.add_row(["y_i"] + y)
        print(t)
