import matplotlib.pyplot as plt
import math

class ODESolver:
    def __init__(self, f):
        self.f = f

    def euler(self, x0, y0, h, n):
        x_values = [x0]
        y_values = [y0]
        x = x0
        y = y0

        for i in range(n):
            y += h * self.f(x, y)
            x += h
            x_values.append(x)
            y_values.append(y)

        return x_values, y_values

    def runge_kutta_4(self, x0, y0, h, n):
        x_values = [x0]
        y_values = [y0]
        x = x0
        y = y0

        for i in range(n):
            k1 = h * self.f(x, y)
            k2 = h * self.f(x + 0.5 * h, y + 0.5 * k1)
            k3 = h * self.f(x + 0.5 * h, y + 0.5 * k2)
            k4 = h * self.f(x + h, y + k3)

            y += (k1 + 2 * k2 + 2 * k3 + k4) / 6
            x += h
            x_values.append(x)
            y_values.append(y)

        return x_values, y_values

    def milne(self, x0, y0, h, n):
        x_values = [x0]
        y_values = [y0]
        x = x0
        y = y0

        for i in range(3):
            k1 = h * self.f(x, y)
            k2 = h * self.f(x + 0.5 * h, y + 0.5 * k1)
            k3 = h * self.f(x + 0.5 * h, y + 0.5 * k2)
            k4 = h * self.f(x + h, y + k3)
            y += (k1 + 4 * k2 + k3) * h / 3
            x += h
            x_values.append(x)
            y_values.append(y)

        for i in range(3, n):
            y_next = y_values[-1] + h / 24 * (55 * self.f(x_values[-1], y_values[-1]) -
                                               59 * self.f(x_values[-2], y_values[-2]) +
                                               37 * self.f(x_values[-3], y_values[-3]) -
                                               9 * self.f(x_values[-4], y_values[-4]))
            x += h
            x_values.append(x)
            y_values.append(y_next)

        return x_values, y_values

def f1(x, y):
    return y + (1+x)*y**2

def f2(x, y):
    return y + 2 * x

def f3(x, y):
    return x ** 2 - y

def exact_solution_f1(x):
    return -1 / (x + 1)

def exact_solution_f2(x):
    return x ** 2 / 2 + 1


def exact_solution_f3(x):
    return x ** 2 - 1 + math.exp(-x)

def main():
    print("Выберите уравнение:")
    print("1. y' = y+(1+x)y^2")
    print("2. y' = y + 2x")
    print("3. y' = x^2 - y")
    choice = int(input("Введите номер выбранного уравнения: "))

    if choice == 1:
        f = f1
        exact_solution = exact_solution_f1
    elif choice == 2:
        f = f2
        exact_solution = exact_solution_f2
    elif choice == 3:
        f = f3
        exact_solution = exact_solution_f3
    else:
        print("Некорректный выбор уравнения.")
        return

    x0 = float(input("Введите начальное значение x: "))
    y0 = float(input("Введите начальное значение y: "))
    h = float(input("Введите шаг h: "))
    n = int(input("Введите количество шагов n: "))

    solver = ODESolver(f)

    x_values, y_values_euler = solver.euler(x0, y0, h, n)
    x_values, y_values_rk4 = solver.runge_kutta_4(x0, y0, h, n)
    x_values, y_values_milne = solver.milne(x0, y0, h, n)

    print("Метод Эйлера:")
    inaccuracy_euler = calculate_inaccuracy(x_values, y_values_euler, exact_solution)
    print_table(x_values, y_values_euler, inaccuracy_euler)

    print("\nМетод Рунге-Кутты 4-го порядка:")
    inaccuracy_rk4 = calculate_inaccuracy(x_values, y_values_rk4, exact_solution)
    print_table(x_values, y_values_rk4, inaccuracy_rk4)

    print("\nМетод Милна:")
    inaccuracy_milne = calculate_inaccuracy(x_values, y_values_milne, exact_solution)
    print_table(x_values, y_values_milne, inaccuracy_milne)

    # Строим графики
    plot_method_results(x_values, y_values_euler, y_values_rk4, y_values_milne, exact_solution)


def print_table(x_values, y_values, inaccuracy):
    print("i\t xi\t\t yi\t\t Inaccuracy")
    for i, (x, y, inacc) in enumerate(zip(x_values, y_values, inaccuracy)):
        print(f"{i}\t {x:.4f}\t {y:.4f}\t {inacc:.4f}")


def calculate_inaccuracy(x_values, y_values, exact_solution):
    return [abs(y - exact_solution(x)) for x, y in zip(x_values, y_values)]


def plot_method_results(x_values, y_values_euler, y_values_rk4, y_values_milne, exact_solution):
    plt.figure(figsize=(12, 9))

    # График метода Эйлера
    plt.subplot(3, 1, 1)
    plt.plot(x_values, y_values_euler, label="Метод Эйлера", color='blue')
    plt.plot(x_values, [exact_solution(x) for x in x_values], label="Точное решение", color='black', linestyle='--')
    plt.title("Метод Эйлера")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend()

    # График метода Рунге-Кутты 4-го порядка
    plt.subplot(3, 1, 2)
    plt.plot(x_values, y_values_rk4, label="Метод Рунге-Кутты 4-го порядка", color='red')
    plt.plot(x_values, [exact_solution(x) for x in x_values], label="Точное решение", color='black', linestyle='--')
    plt.title("Метод Рунге-Кутты 4-го порядка")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend()

    # График метода Милна
    plt.subplot(3, 1, 3)
    plt.plot(x_values, y_values_milne, label="Метод Милна", color='green')
    plt.plot(x_values, [exact_solution(x) for x in x_values], label="Точное решение", color='black', linestyle='--')
    plt.title("Метод Милна")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend()

    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    main()

