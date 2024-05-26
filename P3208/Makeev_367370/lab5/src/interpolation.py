from dto import *
from dataclasses import dataclass
from enum import Enum


@dataclass
class Solver:
    table: PointTable
    x: float
    accuracy: int

    def solve_all(self) -> list[Result]:
        answers: list[Result] = []
        h: float = (self.table.points[-1].x - self.table.points[0].x) / self.accuracy
        x_list: list[float] = [i * h + self.table.points[0].x for i in range(self.accuracy + 1)]
        for method in SolveMethods:
            y_list: list[float] = [method.value.solve(self.table, x) for x in x_list]
            answer_y: float = method.value.solve(self.table, self.x)
            answers.append(Result(
                title=method.value.name,
                answer=Point(self.x, answer_y),
                func_points=PointTable.init(x_list, y_list)
            ))

        return answers


def lagrange_polynom(data: PointTable, x: float) -> float:
    result: float = 0

    n: int = len(data)
    for i in range(n):
        l: float = data[i].y
        for j in range(n):
            if i == j:
                continue

            l *= (x - data[j].x)
            l /= (data[i].x - data[j].x)
        result += l

    return result


nuton_diffs: list[list[float]] = []


def fill_nuton_diffs(data: PointTable) -> None:
    for i in range(len(data)):
        curr_diffs: list[float] = []
        for j in range(len(data) - i):
            if i == 0:
                curr_diffs.append(data[j].y)
            else:
                curr_diffs.append((nuton_diffs[-1][j+1] - nuton_diffs[-1][j]) / (data[j+i].x - data[j].x))

        nuton_diffs.append(curr_diffs)


def nuton_polinom1(data: PointTable, x: float) -> float:
    if len(nuton_diffs) == 0:
        fill_nuton_diffs(data)

    result: float = nuton_diffs[0][0]

    for i in range(1, len(data)):
        n: float = nuton_diffs[i][0]
        for j in range(i):
            n *= (x - data[j].x)
        result += n

    return result


nuton_diffs2: list[list[float]] = []


def fill_nuton_diffs2(data: PointTable) -> None:
    for i in range(len(data)):
        curr_diffs: list[float] = []
        for j in range(len(data) - i):
            if i == 0:
                curr_diffs.append(data[j].y)
            else:
                curr_diffs.append(nuton_diffs2[-1][j+1] - nuton_diffs2[-1][j])

        nuton_diffs2.append(curr_diffs)


def nuton_polinom2(data: PointTable, x: float) -> float:
    h: float = (data[-1].x - data[0].x) / (len(data) - 1)
    for i in range(1, len(data)):
        if round(data[i].x - data[i - 1].x, 5) != round(h, 5):
            return None

    if len(nuton_diffs2) == 0:
        fill_nuton_diffs2(data)

    result: float = nuton_diffs2[0][0]
    factorials: list[int] = [1, 1]
    for i in range(1, len(data)):
        factorials.append(i * factorials[-1])
        n: float = nuton_diffs2[i][0] / (factorials[-1] * h ** i)
        for j in range(i):
            n *= (x - data[j].x)

        result += n

    return result


class SolveMethods(Enum):
    LAGRANGE = MethodDto(name='Лагранж', solve=lagrange_polynom)
    NUTON1 = MethodDto(name='Ньютон с разделенными разностями', solve=nuton_polinom1)
    NUTON2 = MethodDto(name='Ньютон с конечными разностями', solve=nuton_polinom2)
