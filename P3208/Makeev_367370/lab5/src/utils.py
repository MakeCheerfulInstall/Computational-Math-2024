from dto import *
import matplotlib.pyplot as plt


def to_float(val: str) -> float:
    return float(val.replace(',', '.'))


def draw_graph(raw_data: PointTable, res: Result) -> None:
    plt.plot(raw_data.x_list(), raw_data.y_list(), 'go')
    plt.plot(res.func_points.x_list(), res.func_points.y_list())
    plt.plot(res.answer.x, res.answer.y, 'ro')
    plt.title(res.title)
    plt.grid(True)
    plt.show()
