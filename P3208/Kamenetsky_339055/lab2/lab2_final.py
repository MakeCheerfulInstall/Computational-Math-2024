import math
import numpy as np
import matplotlib.pyplot as plt
from sympy import *

# Функция для построения графика
def plot_function(func, a, b):
    x = np.linspace(a, b, 1000)
    y = func(x)
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
    x = np.linspace(x_range[0], x_range[1], 100)
    y = np.linspace(y_range[0], y_range[1], 100)
    X, Y = np.meshgrid(x, y)
    Z1, Z2 = system_func(X, Y)
    plt.contour(X, Y, Z1, levels=[0], colors='r', linestyles='solid')
    plt.contour(X, Y, Z2, levels=[0], colors='b', linestyles='dashed')
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("График системы уравнений")
    plt.legend()
    plt.grid(True)
    plt.show()

#Метод хорд
def chord_method(func, a, b, tol, max_iter=1000):
    print(tol)
    an = np.copy(a)
    bn = np.copy(b)
    iter_count = 0
    x0 = (an*func(bn)-bn*func(an))/(func(bn)-func(an))
    x= np.copy(x0)
    while abs(x-x0)>tol or iter_count<1:
        iter_count+=1
        f_x=func(x)
        f_a=func(an)
        f_b=func(bn)
        if (f_x>0 and f_a<0) or (f_x<0 and f_a>0):
            x0=np.copy(x)
            bn=np.copy(x0)
            x=(an*func(bn)-bn*func(an))/(func(bn)-func(an))
        elif (f_x>0 and f_b<0) or (f_x<0 and f_b>0):
            x0=np.copy(x)
            an=np.copy(x0)
            x=(an*func(bn)-bn*func(an))/(func(bn)-func(an))
        if iter_count>max_iter:
            print("Достигнуто максимальное количество итераций")
            break
    return x, iter_count, func(x)


# Метод Ньютона
def newton_method(func, func_prime, a, tol=0.01):
    iter_count = 0
    xi = np.copy(a)
    f1 = func(xi)
    f2 = func_prime(xi)
    xi_1 = xi-func(xi)/func_prime(xi)
    while abs(xi_1-xi) > tol:
        xi = np.copy(xi_1)
        f1 = func(xi)
        f2 = func_prime(xi)
        xi_1 = xi-func(xi)/func_prime(xi)
        iter_count += 1
    return xi, iter_count, func(xi)


# Метод простой итерации
def simple_iteration_method(xi, tol, func, tot, func_prime):
    if (func_prime(xi) >= 1):
        raise SystemExit("Не выполнилось условие сходимости")
    iter_count = 1
    xi_1 = func(xi)
    xi_2 = func(xi_1)
    while abs(xi_1-xi)>tol:
        iter_count += 1
        xi = np.copy(xi_1)
        xi_1 = func(xi)
        xi_2 = func(xi_1)
    return xi_1, iter_count, tot(xi_1)

# Функции уравнений
def equation1(x):
    return 4.45 * x**3 + 7.81 * x**2 - 9.62 * x - 8.17

def equation2(x):
    return x**3 - x + 4

def equation3(x):
    return np.sin(x) + np.cos(x)

def equation4(x):
    return np.exp(x) - x**2 + 3

# Функции производных уравнений
def equation1_prime(x):
    return 13.35*x**2 + 15.62*x - 9.62

def equation2_prime(x):
    return 3 * x**2 - 1

def equation3_prime(x):
    return np.cos(x) - np.sin(x)

def equation4_prime(x):
    return np.exp(x) - 2 * x



# Выбор уравнения
def select_equation():
    print("Выберите уравнение:")
    print("1. 4.45*x^3+7.81*x^2-9.62*x-8.17 = 0")
    print("2. x^3 - x + 4 = 0")
    print("3. sin(x) + cos(x) = 0")
    print("4. exp(x) - x^2 + 3 = 0")
    choice = int(input("Введите номер уравнения: "))
    equations = {1: equation1, 2: equation2, 3: equation3, 4: equation4}
    return equations.get(choice, None)


# Ввод исходных данных
def input_data():
    choice = input("Введите 't' для ввода данных с клавиатуры или 'f' для чтения из файла: ")
    if choice.lower() == 't':
        a = float(input("Введите левую границу интервала: "))
        b = float(input("Введите правую границу интервала: "))
        tol = 0.01
#        tol_str = tol_str.replace(",", ".")
#        tol = float(tol_str)
    elif choice.lower() == 'f':
        filename = input("Введите имя файла: ")
        with open(filename, 'r') as file:
            a, b, tol = map(float, file.readline().strip().split())
    else:
        print("Неверный ввод.")
        return None
    return a, b, tol


# Проверка наличия корней на интервале
def verify_interval(func, a, b):
    fa = func(a)
    fb = func(b)
    if fa * fb > 0:
        print("На заданном интервале корни отсутствуют или их количество четное.")
        return False
    return True


# Проверка достаточного условия сходимости для метода простой итерации
def check_convergence_condition(phi, a, b):
    phi_derivative = lambda x: abs((phi(x + 1e-6) - phi(x)) / 1e-6)
    max_derivative = max(phi_derivative(a), phi_derivative(b))
    if max_derivative >= 1:
        print("Достаточное условие сходимости не выполняется на заданном интервале.")
        return False
    return True


# Функция вывода результатов
def output_results(root, func_root, iter_count, filename=None):
    if root is not None:
        result_str = f"Найденный корень: {root}\nЗначение функции в корне: {func_root}\nКоличество итераций: {iter_count}\n"
        if filename:
            with open(filename, 'w') as file:
                file.write(result_str)
            print(f"Результаты записаны в файл '{filename}'.")
        else:
            print(result_str)

# Функция для вычисления вектора погрешностей
def calculate_errors(x, prev_x):
    return np.abs(x - prev_x)

# Функция для вывода вектора погрешностей
def output_errors(errors):
    print("Вектор погрешностей:")
    for i, err in enumerate(errors):
        print(f"|x{i+1}(k) - x{i+1}(k-1)| = {err}")



def system_equations_3(x, y):
    return 0.1*x**2+x+0.2*y**2-0.3, 0.2*x**2+y+0.1*x*y-0.7

def system_equations_4(x, y):
    return x + np.sin(y) + 0.4, 2*y - np.cos(x+1)

# возвращает модуль суммы производных
def system_equations_derivatives_3(x, y):
    df_dx = -0.2*x
    df_dy = -0.4*y
    dg_dx = -0.4*x-0.1*y
    dg_dy = -0.1*x
    return abs(df_dx + df_dy), abs(dg_dx + dg_dy)

def system_equations_derivatives_4(x, y):
    df_dx = 0
    df_dy = -np.cos(y)
    dg_dx = -np.sin(x+1)/2
    dg_dy = 0
    return abs(df_dx + df_dy), abs(dg_dx + dg_dy)

# Выраженная функция через x
def system_equations_3_phi(x, y):
    return 0.3-0.1*x**2-0.2*y**2, 0.7-0.2*x**2-0.1*x*y

def system_equations_4_phi(x, y):
    return -np.sin(y)-0.4, np.cos(x+1)/2

# Метод простой итерации для системы
def simple_iteration_system(phi, x0, max_iter=1000, tol=0.01):
    iter_count = 0
    x=x0
    for i in range(max_iter):
        modules = []
        iter_count+=1
        x_new = phi(*x)
        for i in range(len(x)):
            modules.append(abs(x_new[i]-x[i]))
        if max(modules)<tol:
            return x_new, iter_count
        x = x_new
    return x_new, iter_count

#Проверка условия сходимости для системы
def check_system_convergence_condition(x0, func2):
    if max(func2(x0[0], x0[1]))<1:
        return True
    return False

def select_system():
    print("Выберите систему уравнений:")
    print("1. 0.1x^2 + x + 0.2y^2 - 0.3 = 0\n   0.2x^2 + y + 0.1xy - 0.7 = 0")
    print("2. x + sin(y) + 0.4 = 0\n   2y - cos(x+1) = 0")
    choice = int(input("Введите номер: "))
    if choice == 1:
        return system_equations_3, system_equations_derivatives_3, system_equations_3_phi
    elif choice == 2:
        return system_equations_4, system_equations_derivatives_4, system_equations_4_phi
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
    # Выбор типа решения
    solution_type = select_solution_type()
    if solution_type not in [1, 2]:
        print("Выбран недопустимый тип решения.")
        return

    # Решение нелинейного уравнения
    if solution_type == 1:
        func = select_equation()
        if func is None:
            print("Выбрано недопустимое уравнение.")
            return

        # Ввод исходных данных для уравнения
        a, b, tol = input_data()
        if a is None or b is None or tol is None:
            print("Неверные данные.")
            return

        # Проверка наличия корней на интервале
        if not verify_interval(func, a, b):
            return

        # Построение графика функции уравнения
        plot_function(func, a, b)

        # Выбор метода
        method = int(input("Выберите метод (1 - Метод хорд, 2 - Метод Ньютона, 3 - Метод простой итерации): "))

        # Выбор начального приближения для методов Ньютона и простой итерации
        if method in [1, 2, 3]:
            x0 = (a + b) / 2
        else:
            x0 = float(input("Введите начальное приближение: "))

        # Проверка достаточного условия сходимости для метода простой итерации
        if method == 3 and not check_convergence_condition(lambda x: (2*x + 5)**(1/3), a, b):
            return

        # Решение уравнения
        if method == 1:
            root, iter_count, func_root = chord_method(func, a, b, tol)
        elif method == 2:
            if func == equation1:
                func_prime = equation1_prime
            elif func == equation2:
                func_prime = equation2_prime
            elif func == equation3:
                func_prime = equation3_prime
            elif func == equation4:
                func_prime = equation4_prime
            root, iter_count, func_root = newton_method(func, func_prime, a, tol)
        elif method == 3:
            tot = lambda x: 12/11*x - 1/11*(x**3) - 4/11
            tot_prime = lambda x: 12/11 - 3*x**2/11
            root, iter_count, func_root = simple_iteration_method(a, tol, tot, func, tot_prime)
        else:
            print("Выбран недопустимый метод.")

        # Вывод результатов
        if method == 1 or method == 2 or method == 3:
            output_results(root, func_root, iter_count)
    # Решение системы нелинейных уравнений
    elif solution_type == 2:
        system = select_system()
        if system is None:
            print("Выбрана недопустимая система уравнений.")
            return

        x0 = input_initial_guess()
        plot_system_function(system[0], (-10, 10), (-10, 10))
        if check_system_convergence_condition(x0, system[1]):
            print("Условие сходимости выполнено")
            solution, iter_count = simple_iteration_system(system[2], x0)

            output_results_systems(solution, iter_count)

        else:
            print("Условие сходимости не выполнено")
if __name__ == "__main__":
    main()
