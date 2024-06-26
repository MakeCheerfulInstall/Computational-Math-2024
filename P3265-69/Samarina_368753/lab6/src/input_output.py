from functions import *
import matplotlib.pyplot as plt
import numpy as np


def try_to_convert_to_int(number):
    try:
        number_float = float(number)
        if number_float.is_integer():
            return int(number_float)
        else:
            return number_float
    except ValueError:
        return float(number)


def input_data():
    function, exact_y = choose_quation()
    x0 = choose_x()
    h = choose_h()
    n = choose_n()
    y0 = choose_y()
    eps = choose_eps()
    return function, exact_y, x0, eps, h, n, y0


def choose_x():
    while True:
        try:
            interval = input("Enter the x0 value: ").replace(",", ".")
            return try_to_convert_to_int(interval)

        except ValueError:
            print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_y():
    while True:
        try:
            interval = input("Enter the y0 value: ").replace(",", ".")
            return try_to_convert_to_int(interval)

        except ValueError:
            print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_h():
    while True:
        try:
            interval = input("Enter the h value(step range): ").replace(",", ".")

            interval = try_to_convert_to_int(interval)
            if interval <= 0:
                print(f"h must be greater then 0")
                continue
            return interval

        except ValueError:
            print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_n():
    while True:
        try:
            interval = int(
                input("Enter the N value(count of dots): ").replace(",", ".")
            )

            if interval < 2:
                print(f"N must be greater then 1")
                continue
            return interval

        except ValueError:
            print("h must be int")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_eps():
    while True:
        try:
            interval = input("Enter the epsilon value: ").replace(",", ".")
            interval = try_to_convert_to_int(interval)
            if interval <= 0 or interval >= 1:
                print(f"epsilon must be in (0, 1)")
                continue
            return interval

        except ValueError:
            print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_quation():
    while True:
        try:
            print("Choose the quation:")
            input_func = int(
                input("1) y + (1+x)\n2) x+y\n3) sin(x) - y\n").replace(",", ".")
            )
            if input_func == 1:
                f = f1
                exact_y = y1
                break
            elif input_func == 2:
                f = f2
                exact_y = y2
                break
            elif input_func == 3:
                f = f3
                exact_y = y3
                break
            else:
                print("Please, chose one of the available options.")
        except ValueError:
            print("Please, chose one of the available options.")
    return f, exact_y


def draw_plot(ax, xs, ys, func, x0, y0, name, dx=0.01):
    ax.set_title(name)
    xss, yss = [], []
    a = xs[0]
    b = xs[-1]
    a -= dx
    b += dx
    x = a
    while x <= b:
        xss.append(x)
        yss.append(func(x, x0, y0))
        x += dx
    # Рисуем график точного решения
    ax.plot(xss, yss, "g", label="Exact Solution")

    # Рисуем точки численного решения
    ax.scatter(xs, ys, c="r", label="Numerical Solution")

    ax.set_xlabel("X")
    ax.set_ylabel("Y")
    ax.legend()


def draw_dense_plot(ax, xs_dense, ys_dense):

    ax.plot(xs_dense, ys_dense, "b--", label="Numerical Function (Dense)")
