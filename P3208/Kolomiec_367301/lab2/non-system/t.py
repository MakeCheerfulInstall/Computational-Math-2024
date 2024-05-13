import numpy as np
import matplotlib.pyplot as plt

# Получить коэффициенты уравнения и диапазон
coefficients = [float(x) for x in input("Введите коэффициенты уравнения, разделенные пробелами: ").split()]
x_range = [float(x) for x in input("Введите диапазон значений x, разделенных пробелами: ").split()]

# Создать функцию из коэффициентов
def func(x):
    y = 0
    for i, coefficient in enumerate(coefficients):
        y += coefficient * x*i
    return y

# Создать массив значений x в указанном диапазоне
x = np.linspace(x_range[0], x_range[1], 100)

# Вычислить соответствующие значения y
y = func(x)

# Нарисовать график
plt.plot(x, y)
plt.xlabel("x")
plt.ylabel("y")
plt.title("График уравнения")
plt.show()
