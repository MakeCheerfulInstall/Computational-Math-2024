import numpy as np
import matplotlib.pyplot as plt
import warnings
from data import get_data
from calculation import linear_approximation, quadratic_approximation, exponential_approximation, power_approximation, \
    logarithmic_approximation, cubic_approximation


warnings.filterwarnings("ignore")


def print_result(data):
    if data != None:
        print(f"Функция: {data['func_string']}\nR^2 = {'%.3f' % data['coeff']}\nКритерий минимации: {'%.3f' % data['minimization_criterion']}\nСреднеквадратичное отклонение: %.3f" % (data['standard_deviation']))
        indexes = [index for (value, index) in enumerate(data) if index == "pirson_coefficient"]
        if len(indexes) == 1:
            print(f"Коэффициент Пирсона: {data['pirson_coefficient']}")
    else:
        print("Введеные точки не удовлетворяют ОДЗ")
    print("-" * 50)


def plot_charts(x, y, abscissa, ordinates, funcs):
    plt.plot(x, y, 'o')
    n = len(ordinates)
    gr = plt.gca()
    gr.spines['left'].set_position('zero')
    gr.spines['top'].set_color('none')
    gr.spines['bottom'].set_position('zero')
    gr.spines['right'].set_color('none')

    for i in range(n):
        plt.plot(abscissa, ordinates[i], label=funcs[i])
    plt.legend()
    plt.show()


def show_charts(graphs, points):
    x = [point[0] for point in points]
    y = [point[1] for point in points]

    ordinates = np.linspace(min(x) - 1, max(x) + 1, 80)
    funcs, every_func_y = [], []
    for graph in graphs:
        funcs.append(graph["func_string"])
        function = graph["func"]
        temp = []
        for cord_x in ordinates:
            temp.append(function(cord_x))
        every_func_y.append(temp)
    plot_charts(x, y, ordinates, every_func_y, funcs)


def run():
    in_data = get_data()
    print(f"Исходные данные: {in_data}\n")
    lin_res = linear_approximation(in_data)
    print("Линейная аппроксимация")
    print_result(lin_res)
    quadratic_res = quadratic_approximation(in_data)
    print("Квадратичная аппроксимация")
    print_result(quadratic_res)
    cub_res = cubic_approximation(in_data)
    print("Кубическая аппроксимация")
    print_result(cub_res)
    exp_res = exponential_approximation(in_data)
    print("Экспоненциальная аппроксимация")
    print_result(exp_res)
    pow_res = power_approximation(in_data)
    print("Cтепенная аппроксимация")
    print_result(pow_res)
    log_res = logarithmic_approximation(in_data)
    print("Логарифмическая аппроксимация")
    print_result(log_res)
    results = [lin_res, quadratic_res, cub_res, exp_res, pow_res, log_res]
    results = [res for res in results if res != None]
    standard_deviations = [res['standard_deviation'] for res in results]
    min_value, index = min((value, index) for (index, value) in enumerate(standard_deviations))
    print(f"Лучшая аппроксимация {results[index]['name']}: {results[index]['func_string']}\n"
          f"Её минимальное среди остальных среднеквадратичное отклонение: {min_value}\n")
    show_charts(results, in_data)


if __name__ == '__main__':
    run()
