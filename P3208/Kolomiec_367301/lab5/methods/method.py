from abc import abstractmethod, ABC
from typing import Final

from prettytable import PrettyTable


class Method(ABC):
    EPSILON: Final = 4

    def __init__(self, name):
        self.name = name

    @abstractmethod
    def calculate(self, dots: tuple, arg: float, is_drawing: bool):
        pass

    def round_value(self, value: float):
        return round(value, self.EPSILON)


class Lagranzh(Method):

    def __init__(self, name):
        super().__init__(name)

    def calculate(self, dots: tuple, arg: float, is_drawing: bool):
        x, y = dots
        l = []

        for i, x_i in enumerate(x):
            values_x = x[:i] + x[i + 1:]

            up, down = 1, 1
            for x_j in values_x:
                up *= (arg - x_j)
                down *= (x_i - x_j)

            l.append(y[i] * up / down)

        if not is_drawing:
            print(f"{self.name} : при x={arg} -> y={self.round_value(sum(l))}")
        return sum(l)


class NewtonSeparated(Method):

    def __init__(self, name):
        super().__init__(name)

    def search_value(self, points: list[list[float]], k: int, index: int):
        pass

    def calculate(self, dots: tuple, arg: float, is_drawing: bool):
        x, y = dots
        n = len(x)

        table = [y]
        table += [["-"] * n for _ in range(n - 1)]

        result = y[0]

        for i in range(1, n):
            for j in range(n - i):
                table[i][j] = self.round_value((table[i - 1][j + 1] - table[i - 1][j]) / (x[j + i] - x[j]))

            up = 1
            for j in range(i):
                up *= (arg - x[j])

            result += table[i][0] * up

        if not is_drawing:
            print(f"{self.name} : при x={arg} -> y={self.round_value(result)}")
            self.draw_table(table, x)
        return result

    def draw_table(self, table, x):
        table_size = len(table)
        t = PrettyTable()
        t.title = self.name
        t.field_names = ["№", "x_i", "y_i", f"Δy_i"] + [f"Δ{i}y_i" for i in range(2, table_size)]

        for column_index in range(table_size):
            line = []
            for line_index in range(table_size): line.append(table[line_index][column_index])
            t.add_row([column_index, x[column_index]] + line)
        print(t)


class NewtonFinal(Method):

    def __init__(self, name):
        super().__init__(name)

    def check_intervals(self, x) -> bool:
        return len(set([self.round_value(x[i + 1] - x[i]) for i in range(len(x) - 1)])) == 1

    def calculate(self, dots: tuple, arg: float, is_drawing: bool):
        x, y = dots
        n = len(x)

        table = [y]
        table += [["-"] * n for _ in range(n - 1)]

        if self.check_intervals(x):

            where_is = "1"

            if arg <= min(x):
                where_is = "1"
            if arg >= max(x):
                where_is = "2"

            for i in range(1, n):
                if x[i - 1] <= arg <= x[i]:
                    if i >= n // 2:
                        where_is = "2"

            if where_is == "1":
                result = y[0]

                for i in range(1, n):
                    for j in range(n - i):
                        table[i][j] = self.round_value(table[i - 1][j + 1] - table[i - 1][j])

                    up = 1
                    for j in range(i):
                        up *= (arg - x[j])

                    result += table[i][0] * up
            else:
                result = y[n - 1]
                h = self.round_value(x[1] - x[0])
                t = self.round_value((arg - x[-1]) / h)
                for i in range(1, n):
                    for j in range(n - i):
                        table[i][j] = self.round_value(table[i - 1][j + 1] - table[i - 1][j])

                    up = 1
                    for j in range(i):
                        up *= ((t + j) / (j + 1))

                    result += table[i][-i - 1] * up

            if not is_drawing:
                print(f"{self.name} : при x={arg} -> y={self.round_value(result)}")
                self.draw_table(table, x)
            return result

        else:
            if not is_drawing:
                print("Интервалы разной длины. Попробуйте ввести другие координаты!")
            return None

    def draw_table(self, table, x):
        table_size = len(table)
        t = PrettyTable()
        t.title = self.name
        t.field_names = ["№", "x_i", "y_i", f"Δy_i"] + [f"Δ{i}y_i" for i in range(2, table_size)]

        for column_index in range(table_size):
            line = []
            for line_index in range(table_size): line.append(table[line_index][column_index])
            t.add_row([column_index, x[column_index]] + line)
        print(t)
