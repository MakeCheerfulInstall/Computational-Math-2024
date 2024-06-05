from dto import *
import matplotlib.pyplot as plt


def to_float(val: str) -> float:
    return float(val.replace(',', '.'))


def draw_graph(ans: AnswerDto) -> None:
    plt.plot(ans.x_list, ans.y_list, 'go')
    plt.plot(ans.x_list, ans.acc_y_list)

    plt.title(ans.method_name)
    plt.grid(True)
    plt.show()
