import numpy as np
import matplotlib.pyplot as plt
from scipy.interpolate import interp1d

def f1(x, y):
    return x ** 2 - 2 * y  # y(0) = 1; [0, 1] h = 0.1

def f2(x, y):
    return 2 * x  # y(0) = 0, [0, 7], h=1

def f3(x, y):
    return y + (1 + x) * y**2

def select_equation():
    while True:
        print("Выберите уравнение:")
        print("1. y' + 2y = x^2")
        print("2. y' = 2x")
        print("3. y' = y + (1 + x) * y^2")

        try:
            choice = int(input("Введите номер уравнения: "))
            equations = {1: f1, 2: f2, 3: f3}
            return equations[choice]
        except KeyError:
            print("Ошибка: Неверный ввод. Пожалуйста, введите номер уравнения из списка.")
        except ValueError:
            print("Ошибка: Введите целое число.")

def exact_solution(x, equation):
    if equation == f1:
        return (1 / np.exp(2 * x)) + (x**2 / 2) - (x / 2) + (1 / 4)
    elif equation == f2:
        return x**2
    elif equation == f3:
        return -np.exp(x) / (x * np.exp(x))

def euler_method(f, y0, a, b, h):
    t = np.arange(a, b + h, h)
    y = np.zeros(len(t))
    y[0] = y0
    for i in range(1, len(t)):
        y[i] = y[i-1] + h * f(t[i-1], y[i-1])
    return t, y

def adaptive_improved_euler_method(f, y0, a, b, h, epsilon):
    t = [a]
    y = [y0]
    p = 2

    while t[-1] < b:
        t_current = t[-1]
        y_current = y[-1]

        k1_h = h * f(t_current, y_current)
        k2_h = h * f(t_current + h, y_current + k1_h)
        y_h = y_current + 0.5 * (k1_h + k2_h)

        h_half = h / 2
        k1_h_half_1 = h_half * f(t_current, y_current)
        k2_h_half_1 = h_half * f(t_current + h_half, y_current + k1_h_half_1)
        y_h_half_1 = y_current + 0.5 * (k1_h_half_1 + k2_h_half_1)

        k1_h_half_2 = h_half * f(t_current + h_half, y_h_half_1)
        k2_h_half_2 = h_half * f(t_current + h, y_h_half_1 + k1_h_half_2)
        y_h_half_2 = y_h_half_1 + 0.5 * (k1_h_half_2 + k2_h_half_2)

        error = np.abs(y_h_half_2 - y_h) / (2**p - 1)

        if error <= epsilon:
            t.append(t_current + h)
            y.append(y_h_half_2)
            if error < epsilon / 2:
                h *= 2
        else:
            h /= 2

        if t[-1] + h > b:
            h = b - t[-1]

    return np.array(t), np.array(y)

def runge_kutta_4(f, y0, a, b, h):
    t = np.arange(a, b + h, h)
    y = np.zeros(len(t))
    y[0] = y0
    for i in range(1, len(t)):
        k1 = h * f(t[i-1], y[i-1])
        k2 = h * f(t[i-1] + 0.5 * h, y[i-1] + 0.5 * k1)
        k3 = h * f(t[i-1] + 0.5 * h, y[i-1] + 0.5 * k2)
        k4 = h * f(t[i], y[i-1] + k3)
        y[i] = y[i-1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6
    return t, y

def milne_simpson(f, y0, a, b, h):
    t = np.arange(a, b + h, h)
    y = np.zeros(len(t))
    y[0] = y0

    for i in range(1, 4):
        k1 = h * f(t[i-1], y[i-1])
        k2 = h * f(t[i-1] + 0.5 * h, y[i-1] + 0.5 * k1)
        k3 = h * f(t[i-1] + 0.5 * h, y[i-1] + 0.5 * k2)
        k4 = h * f(t[i], y[i-1] + k3)
        y[i] = y[i-1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6

    for i in range(3, len(t)-1):
        y_pred = y[i-3] + 4 * h / 3 * (2 * f(t[i-2], y[i-2]) - f(t[i-1], y[i-1]) + 2 * f(t[i], y[i]))
        y_corr = y[i-1] + h / 3 * (f(t[i-1], y[i-1]) + 4 * f(t[i], y[i]) + f(t[i+1], y_pred))
        y[i+1] = y_corr
    return t, y

def plot_results(t_euler, t_adaptive_euler, y_euler, y_adaptive_euler, y_rk4, y_milne, exact_y):
    plt.plot(t_euler, y_euler, label='Метод Эйлера')
    plt.plot(t_adaptive_euler, y_adaptive_euler, label='Адаптивный метод Эйлера')
    plt.plot(t_euler, y_rk4, label='Метод Рунге-Кутта 4-го порядка')
    plt.plot(t_euler, y_milne, label='Метод Милна-Симпсона')
    plt.plot(t_euler, exact_y, label='Точное решение')

    plt.legend()
    plt.xlabel('t')
    plt.ylabel('y(t)')
    plt.title('Численное решение ОДУ')
    plt.grid(True)
    plt.show()

def print_table(t_euler, y_euler, t_adaptive_euler, y_adaptive_euler, t_rk4, y_rk4, t_milne, y_milne, exact_y):
    max_len = max(len(t_euler), len(t_adaptive_euler), len(t_rk4), len(t_milne))
    headers = ["i", "x_i", "Метод Эйлера", "Адапт. Эйлер", "Рунге-Кутта", "Милн-Симпсон", "Точные значения"]
    
    print(" | ".join(headers))
    print("-" * 108)

    for i in range(max_len):
        row = [
            i,
            f"{t_euler[i]:.5f}" if i < len(t_euler) else "None",
            f"{y_euler[i]:.5f}" if i < len(y_euler) else "None",
            f"{y_adaptive_euler[i]:.5f}" if i < len(y_adaptive_euler) else "None",
            f"{y_rk4[i]:.5f}" if i < len(y_rk4) else "None",
            f"{y_milne[i]:.5f}" if i < len(y_milne) else "None",
            f"{exact_y[i]:.5f}" if exact_y is not None and i < len(exact_y) else "None"
        ]
        print(" | ".join(row))

def evaluate_accuracy(t_euler, y_euler, t_adaptive_euler, y_adaptive_euler, t_rk4, y_rk4, t_milne, y_milne, exact_y):
    epsilon_milne = np.max(np.abs(exact_y - y_milne))
    print(f"Максимальная ошибка метода Милна-Симпсона: {epsilon_milne}")

    interp_adaptive_euler = interp1d(t_adaptive_euler, y_adaptive_euler, fill_value="extrapolate")
    y_adaptive_euler_interpolated_rk4 = interp_adaptive_euler(t_rk4)
    y_adaptive_euler_interpolated_euler = interp_adaptive_euler(t_euler)
    y_adaptive_euler_interpolated_milne = interp_adaptive_euler(t_milne)

    epsilon_rk4 = np.max(np.abs(y_adaptive_euler_interpolated_rk4 - y_rk4))
    epsilon_euler = np.max(np.abs(y_adaptive_euler_interpolated_euler - y_euler))
    epsilon_milne = np.max(np.abs(y_adaptive_euler_interpolated_milne - y_milne))

    print(f"Максимальная погрешность для адаптивного метода Эйлера: {epsilon_milne}")
    print(f"Максимальная погрешность метода Эйлера: {epsilon_euler}")
    print(f"Максимальная погрешность метода Рунге-Кутта 4-го порядка: {epsilon_rk4}")

if __name__ == "__main__":
    equation = select_equation()
    if equation == f1:
        y0, a, b, h = 1, 0, 1, 0.1
    elif equation == f2:
        y0, a, b, h = 0, 0, 7, 1
    elif equation == f3:
        y0, a, b, h = -1, 0, 2, 0.2

    epsilon = 1e-3

    t_euler, y_euler = euler_method(equation, y0, a, b, h)
    t_adaptive_euler, y_adaptive_euler = adaptive_improved_euler_method(equation, y0, a, b, h, epsilon)
    t_rk4, y_rk4 = runge_kutta_4(equation, y0, a, b, h)
    t_milne, y_milne = milne_simpson(equation, y0, a, b, h)

    exact_y = exact_solution(t_euler, equation)

    plot_results(t_euler, t_adaptive_euler, y_euler, y_adaptive_euler, y_rk4, y_milne, exact_y)
    print_table(t_euler, y_euler, t_adaptive_euler, y_adaptive_euler, t_rk4, y_rk4, t_milne, y_milne, exact_y)
    evaluate_accuracy(t_euler, y_euler, t_adaptive_euler, y_adaptive_euler, t_rk4, y_rk4, t_milne, y_milne, exact_y)
