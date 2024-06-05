import math
import matplotlib.pyplot as plt


def plot_function(func, a, b):
    x = [a + i * (b - a) / 1000 for i in range(1001)]
    y = [func(val) for val in x]
    plt.plot(x, y, label="y=f(x)")
    plt.axhline(0, color='black', linewidth=0.5)
    plt.axvline(0, color='black', linewidth=0.5)
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("График функции")
    plt.legend()
    plt.grid(True)
    plt.show()

def plot_system_function(system_func, x_range, y_range):
    x = [x_range[0] + i * (x_range[1] - x_range[0]) / 100 for i in range(101)]
    y = [y_range[0] + i * (y_range[1] - y_range[0]) / 100 for i in range(101)]
    X, Y = [], []
    for xi in x:
        row_x, row_y = [], []
        for yi in y:
            row_x.append(xi)
            row_y.append(yi)
        X.append(row_x)
        Y.append(row_y)
    Z1 = [[system_func(xi, yi)[0] for yi in y] for xi in x]
    Z2 = [[system_func(xi, yi)[1] for yi in y] for xi in x]
    plt.contour(X, Y, Z1, levels=[0], colors='r', linestyles='solid')
    plt.contour(X, Y, Z2, levels=[0], colors='b', linestyles='dashed')
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("График системы уравнений")
    plt.legend()
    plt.grid(True)
    plt.show()


def bisection_method(func, a, b, tol=1e-6, max_iter=1000):
    iter_count = 0
    while (b - a) / 2 > tol:
        c = (a + b) / 2
        if func(c) == 0:
            return c, iter_count
        elif func(c) * func(a) < 0:
            b = c
        else:
            a = c
        iter_count += 1
        if iter_count >= max_iter:
            print("Достигнуто максимальное количество итераций.")
            break

    return (a + b) / 2, iter_count


def newton_method(func, func_prime, x0, tol=1e-6, max_iter=1000):
    x = x0
    iter_count = 0

    while abs(func(x)) > tol:
        if func_prime(x) == 0:
            print("Производная равна нулю. Метод Ньютона не может быть применен.")
            return None, iter_count
        x = x - func(x) / func_prime(x)
        iter_count += 1
        if iter_count >= max_iter:
            print("Достигнуто максимальное количество итераций.")
            return None, iter_count

    return x, iter_count


def simple_iteration_method(phi, x0, tol=1e-6, max_iter=1000):
    x = x0
    iter_count = 0

    while abs(x - phi(x)) > tol:
        x = phi(x)
        iter_count += 1
        if iter_count >= max_iter:
            print("Достигнуто максимальное количество итераций.")
            return None, iter_count

    return x, iter_count


def equation1(x):
    return 4.45 * x**3 + 7.81 * x**2 - 9.62 * x - 8.17

def equation2(x):
    return x**3 - x + 4

def equation3(x):
    return math.sin(x) + math.cos(x)

def equation4(x):
    return math.exp(x) - x**2 + 3

def equation1_prime(x):
    return 13.35*x**2 + 15.62*x - 9.62

def equation2_prime(x):
    return 3 * x**2 - 1

def equation3_prime(x):
    return math.cos(x) - math.sin(x)

def equation4_prime(x):
    return math.exp(x) - 2 * x



def select_equation():
    print("Выберите уравнение:")
    print("1. 4.45*x^3+7.81*x^2-9.62*x-8.17 = 0")
    print("2. x^3 - x + 4 = 0")
    print("3. sin(x) + cos(x) = 0")
    print("4. exp(x) - x^2 + 3 = 0")
    choice = int(input("Введите номер уравнения: "))
    equations = {1: equation1, 2: equation2, 3: equation3, 4: equation4}
    return equations.get(choice, None)


def input_data():
    choice = input("Введите 'клавиатура' для ввода данных с клавиатуры или 'файл' для чтения из файла: ")
    if choice.lower() == 'клавиатура':
        a = float(input("Введите левую границу интервала: "))
        b = float(input("Введите правую границу интервала: "))
        tol = float(input("Введите погрешность вычисления: "))
    elif choice.lower() == 'файл':
        filename = input("Введите имя файла: ")
        with open(filename, 'r') as file:
            a, b, tol = map(float, file.readline().strip().split())
    else:
        print("Неверный ввод.")
        return None
    return a, b, tol


def verify_interval(func, a, b):
    fa = func(a)
    fb = func(b)
    if fa * fb > 0:
        print("На заданном интервале корни отсутствуют или их количество четное.")
        return False
    return True


def check_convergence_condition(phi, a, b):
    phi_derivative = lambda x: abs((phi(x + 1e-6) - phi(x)) / 1e-6)
    max_derivative = max(phi_derivative(a), phi_derivative(b))
    if max_derivative >= 1:
        print("Достаточное условие сходимости не выполняется на заданном интервале.")
        return False
    return True


def output_results(root, func_root, iter_count, filename=None):
    if root is not None:
        result_str = f"Найденный корень: {root}\nЗначение функции в корне: {func_root}\nКоличество итераций: {iter_count}\n"
        if filename:
            with open(filename, 'w') as file:
                file.write(result_str)
            print(f"Результаты записаны в файл '{filename}'.")
        else:
            print(result_str)

def calculate_errors(x, prev_x):
    return [abs(xi - prev_xi) for xi, prev_xi in zip(x, prev_x)]

def output_errors(errors):
    print("Вектор погрешностей:")
    for i, err in enumerate(errors):
        print(f"|x{i+1}(k) - x{i+1}(k-1)| = {err}")


def system_equations_3(x, y):
    return math.sin(x) + math.cos(y) - 1.5, x * y - 2

def system_equations_derivatives_3(x, y):
    df_dx = math.cos(x)
    df_dy = -math.sin(y)
    dg_dx = y
    dg_dy = x
    return df_dx, df_dy, dg_dx, dg_dy

def system_equations_4(x, y):
    return 3 * x - 2 * y - 5, 2 * x**2 + 3 * y**2 - 10

def system_jacobian_3(x, y):
    return [math.cos(x), -math.sin(y), y, x]

def system_equations_derivatives_4(x, y):
    df_dx = 3
    df_dy = -2
    dg_dx = 4 * x
    dg_dy = 6 * y
    return df_dx, df_dy, dg_dx, dg_dy

def system_jacobian_4(x, y):
    return [3, -2, 4 * x, 6 * y]


def newton_method_systems(func, jacobian, x0, tol=1e-6, max_iter=1000):
    x = list(x0)
    iter_count = 0
    prev_x = None
    errors = []

    while True:
        prev_x = x[:]
        f_x = func(*x)
        jacobian_x = jacobian(*x)
        delta_x = [0, 0]
        det_j = jacobian_x[0] * jacobian_x[3] - jacobian_x[1] * jacobian_x[2]
        if det_j == 0:
            print("Детерминант якобиана равен нулю. Метод Ньютона не может быть применен.")
            return None, iter_count

        inv_jacobian_x = [jacobian_x[3] / det_j, -jacobian_x[1] / det_j,
                          -jacobian_x[2] / det_j, jacobian_x[0] / det_j]

        delta_x[0] = inv_jacobian_x[0] * f_x[0] + inv_jacobian_x[1] * f_x[1]
        delta_x[1] = inv_jacobian_x[2] * f_x[0] + inv_jacobian_x[3] * f_x[1]

        x = [xi - dxi for xi, dxi in zip(x, delta_x)]
        iter_count += 1
        errors.append(calculate_errors(x, prev_x))

        if sum(dxi**2 for dxi in delta_x)**0.5 < tol or iter_count >= max_iter:
            break

    output_errors(errors)
    return x, iter_count

def select_system():
    print("Выберите систему уравнений:")
    print("1. sin(x) + cos(y) - 1.5 = 0\n   xy - 2 = 0")
    print("2. 3x - 2y - 5 = 0\n   2x^2 + 3y^2 - 10 = 0")
    choice = int(input("Введите номер: "))
    if choice == 1:
        return system_equations_3, system_equations_derivatives_3
    elif choice == 2:
        return system_equations_4, system_equations_derivatives_4
    else:
        print("Выбрана недопустимая система уравнений.")
        return None


def input_initial_guess():
    x0 = []
    n = int(input("Введите количество неизвестных: "))
    for i in range(n):
        x = float(input(f"Введите начальное приближение для x{i+1}: "))
        x0.append(x)
    return x0

def output_results_systems(solution, iter_count):
    if solution is not None:
        result_str = "Решение системы:\n"
        for i, val in enumerate(solution):
            result_str += f"x{i + 1} = {val}\n"
        result_str += f"Количество итераций: {iter_count}\n"
        print(result_str)
    else:
        print("Система уравнений не была решена.")

def select_solution_type():
    print("Выберите тип решения:")
    print("1. Решить нелинейное уравнение")
    print("2. Решить систему нелинейных уравнений")
    choice = int(input("Введите номер: "))
    return choice

def main():
    solution_type = select_solution_type()
    if solution_type not in [1, 2]:
        print("Выбран недопустимый тип решения.")
        return

    if solution_type == 1:
        func = select_equation()
        if func is None:
            print("Выбрано недопустимое уравнение.")
            return

        a, b, tol = input_data()
        if a is None or b is None or tol is None:
            print("Неверные данные.")
            return

        if not verify_interval(func, a, b):
            return

        plot_function(func, a, b)

        method = int(input("Выберите метод (1 - Метод половинного деления, 2 - Метод Ньютона, 3 - Метод простой итерации): "))

        if method in [2, 3]:
            x0 = (a + b) / 2
        else:
            x0 = float(input("Введите начальное приближение: "))

        if method == 3 and not check_convergence_condition(lambda x: (2*x + 5)**(1/3), a, b):
            return

        if method == 1:
            root, iter_count = bisection_method(func, a, b, tol)
        elif method == 2:
            if func == equation1:
                func_prime = equation1_prime
            elif func == equation2:
                func_prime = equation2_prime
            elif func == equation3:
                func_prime = equation3_prime
            elif func == equation4:
                func_prime = equation4_prime
            root, iter_count = newton_method(func, func_prime, x0, tol)
        elif method == 3:
            phi = lambda x: (2 * x + 5) ** (1 / 3)
            root, iter_count = simple_iteration_method(phi, x0, tol)
        else:
            print("Выбран недопустимый метод.")

        if method == 1 or method == 2 or method == 3:
            func_root = func(root)
            output_results(root, func_root, iter_count)

    elif solution_type == 2:
        system = select_system()
        if system is None:
            print("Выбрана недопустимая система уравнений.")
            return

        x0 = input_initial_guess()

        solution, iter_count = newton_method_systems(system[0], system[1], x0)

        output_results_systems(solution, iter_count)

        plot_system_function(system[0], (-10, 10), (-10, 10))

if __name__ == "__main__":
    main()
