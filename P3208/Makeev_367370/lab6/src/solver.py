import math
from dataclasses import dataclass
from dto import *


@dataclass
class Solver:
    diff_ur: DiffUrDto
    params: ParamsDto

    def solve(self) -> list[AnswerDto]:
        return [modificated_ailer(self.diff_ur, self.params),
               runge_kutt4(self.diff_ur, self.params),
               adams(self.diff_ur, self.params)]


def modificated_ailer(diff_ur: DiffUrDto, params: ParamsDto) -> AnswerDto:
    h: float = params.h * 2
    e: float = math.inf
    accuracy: int = int((params.interval[1] - params.interval[0]) / h)
    x_list: list[float] = [i * h + params.interval[0] for i in range(accuracy + 1)]
    acc_y_list: list[float] = []
    y_list: list[float] = modificated_ailer_by_x_list(diff_ur, params.y_0, x_list)
    while e > params.e:
        if h < 1E-3:
            break
        h /= 2
        accuracy = int((params.interval[1] - params.interval[0]) / h)
        x_list = [i * h + params.interval[0] for i in range(accuracy+1)]
        acc_y_list = [diff_ur.find_y(x, Point(x_list[0], params.y_0)) for x in x_list]
        next_y_list: list[float] = modificated_ailer_by_x_list(diff_ur, params.y_0, x_list)
        e = calc_runge_e(y_list, next_y_list, 4)
        y_list = next_y_list

    return AnswerDto(
        method_name='Эйлер',
        x_list=x_list,
        y_list=y_list,
        acc_y_list=acc_y_list,
        e=e
    )


def modificated_ailer_by_x_list(diff_ur: DiffUrDto, y_0: float, x_list: list[float]) -> list[float]:
    h: float = x_list[1] - x_list[0]
    y_list: list[float] = [y_0]
    for i in range(len(x_list)-1):
        y_i = y_list[i]
        x_i = x_list[i]
        f_i = diff_ur.f_x_y(x_i, y_i)
        y_list.append(y_i + (h / 2) * (f_i + diff_ur.f_x_y(x_list[i+1], y_i + h * f_i)))

    return y_list


def runge_kutt4(diff_ur: DiffUrDto, params: ParamsDto) -> AnswerDto:
    h: float = params.h * 2
    e: float = math.inf
    accuracy: int = int((params.interval[1] - params.interval[0]) / h)
    x_list: list[float] = [i * h + params.interval[0] for i in range(accuracy + 1)]
    acc_y_list: list[float] = []
    y_list: list[float] = runge_kutt4_by_x_list(diff_ur, params.y_0, x_list)
    while e > params.e:
        if h < 1E-3:
            break
        h /= 2
        accuracy = int((params.interval[1] - params.interval[0]) / h)
        x_list = [i * h + params.interval[0] for i in range(accuracy+1)]
        acc_y_list = [diff_ur.find_y(x, Point(x_list[0], params.y_0)) for x in x_list]
        next_y_list: list[float] = runge_kutt4_by_x_list(diff_ur, params.y_0, x_list)
        e = calc_runge_e(y_list, next_y_list, 4)
        y_list = next_y_list

    return AnswerDto(
        method_name='Рунге-Кутт 4',
        x_list=x_list,
        y_list=y_list,
        acc_y_list=acc_y_list,
        e=e
    )


def runge_kutt4_by_x_list(diff_ur: DiffUrDto, y_0: float, x_list: list[float]) -> list[float]:
    h: float = x_list[1] - x_list[0]
    y_list: list[float] = [y_0]
    for i in range(len(x_list)-1):
        y_i = y_list[i]
        x_i = x_list[i]
        k1 = h * diff_ur.f_x_y(x_i, y_i)
        k2 = h * diff_ur.f_x_y(x_i + h/2, y_i + k1/2)
        k3 = h * diff_ur.f_x_y(x_i + h/2, y_i + k2/2)
        k4 = h * diff_ur.f_x_y(x_i + h, y_i + k3)
        y_list.append(y_i + (k1 + 2*k2 + 2*k3 + k4) / 6)

    return y_list


def adams(diff_ur: DiffUrDto, params: ParamsDto) -> AnswerDto:
    h: float = params.h
    e: float = math.inf
    x_list: list[float] = []
    y_list: list[float] = []
    acc_y_list: list[float] = []
    while e > params.e:
        if h < 1E-3:
            break
        accuracy: int = int((params.interval[1] - params.interval[0]) / h)
        x_list = [i * h + params.interval[0] for i in range(accuracy+1)]
        acc_y_list = [diff_ur.find_y(x, Point(x_list[0], params.y_0)) for x in x_list]
        y_list = adams_by_x_list(diff_ur, params.y_0, x_list)
        e = calc_e(acc_y_list, y_list)
        h /= 2

    return AnswerDto(
        method_name='Адамс',
        x_list=x_list,
        y_list=y_list,
        acc_y_list=acc_y_list,
        e=e
    )


def adams_by_x_list(diff_ur: DiffUrDto, y_0: float, x_list: list[float]) -> list[float]:
    h: float = x_list[1] - x_list[0]
    y_list: list[float] = runge_kutt4_by_x_list(diff_ur, y_0, x_list[:4])
    f_list: list[float] = []
    for i in range(len(y_list) - 1):
        f_list.append(diff_ur.f_x_y(x_list[i], y_list[i]))

    for i in range(len(y_list) - 1, len(x_list) - 1):
        f_list.append(diff_ur.f_x_y(x_list[i], y_list[i]))
        delta_f = f_list[i] - f_list[i - 1]
        delta2_f = f_list[i] - 2 * f_list[i - 1] + f_list[i - 2]
        delta3_f = f_list[i] - 3 * f_list[i - 1] + 3 * f_list[i - 2] - f_list[i - 3]
        y_list.append(y_list[i] + h * f_list[i] + (h ** 2) * delta_f / 2
                      + 5 * (h ** 3) * delta2_f / 12 +3 * (h ** 4) * delta3_f / 8)

    return y_list


def calc_e(list1: list[float], list2: list[float]) -> float:
    max_e: float = 0
    for i in range(len(list1)):
        e: float = abs(list1[i] - list2[i])
        if e > max_e:
            max_e = e
    return max_e


def calc_runge_e(list1: list[float], list2: list[float], p: int) -> float:
    max_e: float = 0
    for i in range(len(list1)):
        e: float = abs(list1[i] - list2[i*2]) / (2**p - 1)
        if e > max_e:
            max_e = e
    return max_e
