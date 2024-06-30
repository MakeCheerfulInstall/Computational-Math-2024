from methods import *
from utils import *
from functions import *

import matplotlib.pyplot as plt
import numpy as np

file_name = "points1"
input_methods = ["Файл данных", "Функция", "Ручной ввод"]
input_method = choose("метод ввода", input_methods)

match input_method:
    case "Ручной ввод":
        i = 1
        points = []
        while True:
            try:
                temp = (
                    input(f"Enter pair {i} x and y. For stop enter end:\n")
                    .strip()
                    .replace(",", ".")
                    .split(" ")
                )
                if temp == ["end"] and i >= 2:
                    i += 1
                    break
                elif temp == ["end"] and i < 2:
                    print("Minimum 2 pairs, please add more")
                    i += 1
                    continue
                x, y = map(float, temp)
                i += 1

            except ValueError:
                print("Incorrect value entered")
                continue
            pair = [x, y]
            points.append(pair)
        print(f"Entered {i-1} pairs")
        fun = None
    case "Файл данных":
        points = read_points(file_name)
        fun = None
        if len(points) < 2:
            print(f"{len(points)} точек. Должно быть минимум 2")
            exit(1)
    case "Функция":
        fun = choose("функцию", functions)
        a = read_number("Левая граница: ")
        b = read_number("Правая граница: ", lambda x: x > a)
        n = read_number("Количество точек: ", lambda x: x > 1, integer=True)

        stp = (b - a) / (n - 1)
        points = [(a + i * stp, fun(a + i * stp)) for i in range(n)]
    case _:
        raise ValueError("Неверный метод ввода")

print_points(points)

x = read_number("Введите X: ", lambda x: points[0][0] <= x <= points[-1][0])

plt.scatter(
    [point[0] for point in points], [point[1] for point in points], label="Точки"
)
x_array = np.linspace(points[0][0], points[-1][0], 1000)
if fun is not None:
    print(f"\nНастоящее значение f({x}) = {fun(x)}")
    y_array_original = [fun(x) for x in x_array]
    plt.plot(x_array, y_array_original, label=fun)
# -----------------------------------------------------------------------------------------------------------------------
print("\nМетод Лагранжа")
print(lagrange(points, x), end="\n\n")
plt.plot(x_array, [lagrange(points, x) for x in x_array], label="Лагранж")
# -----------------------------------------------------------------------------------------------------------------------
print("Метод Ньютона")
print(newton(points, x), end="\n\n")
plt.plot(x_array, [newton(points, x) for x in x_array], label="Ньютон")


def print_combined_answer(points, x, method):
    answer, table = method(points, x)
    print("Ответ:", answer)
    print("Таблица конечных разностей:")
    print(make_finite_difference_table(table).to_string(), end="\n\n")


# -----------------------------------------------------------------------------------------------------------------------
print("Метод Ньютона c разделёнными разностями")
if not is_equidistant(points):
    print("Точки не равноотстоящие, метод не применим", end="\n\n")
else:
    # print_combined_answer(points, x, fixed_combined_newton)

    answer, table = fixed_combined_newton(points, x)
    print("Ответ:", answer, end="\n\n")

    plt.plot(
        x_array,
        [fixed_combined_newton(points, x)[0] for x in x_array],
        label="Ньютон c разделёнными разностями",
    )

# -----------------------------------------------------------------------------------------------------------------------
print("Метод Ньютона c конечными разностями")
if not is_equidistant(points):
    print("Точки не равноотстоящие, метод не применим", end="\n\n")
else:
    # print_combined_answer(points, x, fixed_combined_newton)

    answer, table = fixed_combined_newton2(points, x)
    print("Ответ:", answer, end="\n\n")

    plt.plot(
        x_array,
        [fixed_combined_newton(points, x)[0] for x in x_array],
        label="Ньютон c конечными разностями",
    )

# -----------------------------------------------------------------------------------------------------------------------
print("Метод Стирлинга")
if not is_equidistant(points):
    print("Точки не равноотстоящие, метод не применим", end="\n\n")
elif len(points) % 2 == 0:
    print("Четное количество точек, метод не применим", end="\n\n")
else:
    # print_combined_answer(points, x, stirling)

    answer, table = stirling(points, x)
    print("Ответ:", answer, end="\n\n")

    plt.plot(x_array, [stirling(points, x)[0] for x in x_array], label="Стирлинг")
# -----------------------------------------------------------------------------------------------------------------------
print("Метод Бесселя")
if not is_equidistant(points):
    print("Точки не равноотстоящие, метод не применим", end="\n\n")
elif len(points) % 2 != 0:
    print("Нечетное количество точек, метод не применим", end="\n\n")
else:
    # print_combined_answer(points, x, bessel)

    answer, table = bessel(points, x)
    print("Ответ:", answer)

    plt.plot(x_array, [bessel(points, x)[0] for x in x_array], label="Бессель")

plt.legend()
plt.axhline(y=0, color="k")
plt.axvline(x=0, color="k")
plt.grid()
plt.show()
