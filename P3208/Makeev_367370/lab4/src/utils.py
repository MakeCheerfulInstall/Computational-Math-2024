from dto import ApproxRes, PointTable, Point
import matplotlib.pyplot as plt


def to_float(val: str) -> float:
    return float(val.replace(',', '.'))


def avg(data: list[float]) -> float:
    return sum(data) / len(data)


def draw_graph(data: ApproxRes) -> None:
    ax: Axes = plt.gca()
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.set_aspect('equal', adjustable='box')
    plt.plot(data.x_list, data.y_list, 'ro', linewidth=3)
    plt.plot(data.x_list, data.phi_x, linewidth=1, label=data.func_view)
    plt.grid(True)
    plt.legend()
    plt.title(data.type)
    plt.show()
