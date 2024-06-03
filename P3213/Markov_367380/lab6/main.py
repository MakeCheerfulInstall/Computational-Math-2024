import math
from typing import Callable
from matplotlib import pyplot as plt
from P3213.Markov_367380.lab6.dto.request import Request
from P3213.Markov_367380.lab6.dto.response import Response
from P3213.Markov_367380.lab6.solver.solver import Solver
from P3213.Markov_367380.lab6.solver.utils.all_solvers import solvers
from scipy.integrate import solve_ivp


def read_float(prompt: str) -> float:
    while True:
        try:
            value = float(input(prompt))
            return value
        except ValueError:
            print("Ошибка: Пожалуйста, введите корректное число.")


def main() -> None:
    x0 = read_float("Введите значение x0: ")
    xn = read_float("Введите значение xn: ")
    y0 = read_float("Введите значение y0: ")
    e = read_float("Введите значение e: ")
    h = read_float("Введите значение h: ")
    print("Выберите дифференциальное уравнение: ")
    print("1. y' = x + y")
    print("2. y' = y + cos(x)")
    print("3. y' = x^2 + y")
    f_num = input()
    if f_num == "1":
        func: Callable = lambda x, y: x + y
    elif f_num == "2":
        func: Callable = lambda x, y: y + math.cos(x)
    else:
        func: Callable = lambda x, y: x ** 2 + y

    request: Request = Request(x0, xn, y0, e, func)
    n: int = int((request.xn - request.x0) / h) + 1
    xs_init: list[float] = [request.x0 + i * h for i in range(n)]
    ys_init: list[float] = solve_ivp(fun=lambda t, z: request.func(t, z),
                         t_span=(request.x0, request.xn),
                         y0=[request.y0],
                         t_eval=xs_init).y[0]
    for i in range(len(solvers)):
        solver: Solver = solvers[i]
        response: Response = solver.solve(request, h, ys_init)
        print(f"Точность {response.type.value}: {response.e}")

        plt.plot(xs_init, ys_init, label='Exact solution')
        plt.plot(response.xs, response.ys, label='Exact solution')
        plt.show()

if __name__ == '__main__':
    main()