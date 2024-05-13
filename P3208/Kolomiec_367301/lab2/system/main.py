import matplotlib.pyplot as plt
import numpy as np


# Определение системы уравнений
def equations(x):
    # Пример системы уравнений
    f1 = x[0] ** 2 + x[1] ** 2 - 4
    f2 = x[0] * x[1] - 1
    return [f1, f2]


# Вычисление Якобиана системы уравнений
def jacobian(x):
    # Якобиан для примера системы уравнений
    return [[2 * x[0], 2 * x[1]], [x[1], x[0]]]


# Метод Ньютона для решения системы уравнений
def newton_system(equations, jacobian, x0, tolerance=1e-6, max_iterations=100):
    x = x0
    iteration = 0
    errors = []

    while iteration < max_iterations:
        f = equations(x)
        J = jacobian(x)
        delta_x = np.linalg.solve(J, [-f[0], -f[1]])
        x = [x[0] + delta_x[0], x[1] + delta_x[1]]
        error = np.sqrt(delta_x[0] ** 2 + delta_x[1] ** 2)
        errors.append(error)
        iteration += 1

        if error < tolerance:
            break

    return x, iteration, errors


# Пользовательский ввод начальных приближений
x0 = [float(input("Введите начальное приближение для x1: ")),
      float(input("Введите начальное приближение для x2: "))]

# Решение системы уравнений
solution, iterations, errors = newton_system(equations, jacobian, x0)

# Вывод результатов
print("Решение системы уравнений:")
print("x1 =", solution[0])
print("x2 =", solution[1])
print("Количество итераций:", iterations)

# Вывод вектора погрешностей
print("Вектор погрешностей:")
for i in range(len(errors)):
    print("|x_{0}(k) - x_{0}(k-1)| = {1}".format(i + 1, errors[i]))

# Проверка правильности решения системы уравнений
print("Проверка правильности решения:")
print("f1(x) =", equations(solution)[0])
print("f2(x) =", equations(solution)[1])

# Построение графика функций
x_vals = np.linspace(-5, 5, 400)
y_vals1 = np.sqrt(4 - x_vals ** 2)
y_vals2 = 1 / x_vals

plt.plot(x_vals, y_vals1, label='x1^2 + x2^2 - 4 = 0')
plt.plot(x_vals, y_vals2, label='x1 * x2 - 1 = 0')
plt.scatter(solution[0], solution[1], color='red', label='Solution')
plt.xlabel('x1')
plt.ylabel('x2')
plt.title('Graph of the system of equations')
plt.legend()
plt.grid(True)
plt.show()
