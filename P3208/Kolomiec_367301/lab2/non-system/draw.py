import matplotlib.pyplot as plt
import numpy as np


def f(eq, x):
    y = 0
    for i in range(len(eq)):
        y += eq[i] * x ** i
    return y


# Создать функцию из коэффициентов
def draw(eq):
    x = np.linspace(-100, 100, 100)

    plt.plot(x, [f(eq, i) for i in x])
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("График уравнения")
    plt.show()
