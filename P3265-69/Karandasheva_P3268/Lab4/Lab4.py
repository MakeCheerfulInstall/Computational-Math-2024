import numpy as np
import matplotlib.pyplot as plt
from dataclasses import dataclass
import math

@dataclass
class Result:
    coefficients: iter
    apply: callable
    function: str
    S: float
    deviation: float
    confidence: float
    r: float | None = None
    def __lt__(self, other):
        return self.deviation < other.deviation
    def __le__(self, other):
        return self.deviation == other.deviation

# Аппроксимация линейной функцией
def Approximation_linear(x, y):
    x = np.array(x)
    y = np.array(y)
    SX, SXX, SY, SXY = np.sum(x), np.sum(x ** 2), np.sum(y), np.sum(x * y)
    b, a = np.linalg.solve(
        np.array([[len(x), SX], [SX, SXX]]),
        np.array([SY, SXY])
    )
    # Сумма квадратов отклонений
    phi = a * x + b
    S = np.sum((phi - y) ** 2)

    return Result(
        coefficients = (a, b),
        apply = lambda x: a * x + b,
        function = f'{a:.4f}x + {b:.4f}',
        S = S,
        r = r(x, y),
        confidence = confidence(phi, y),
        deviation = msr(phi, y)
    )

# Аппроксимация полиномиальной функцией 2-й степени
def Approximation_degree2(x, y):
    x = np.array(x)
    y = np.array(y)
    SX, SXX, SXXX, SXXXX, SY, SXY, SXXY = np.sum(x), np.sum(x**2), np.sum(x**3), np.sum(x**4), np.sum(y), np.sum(x*y), np.sum(x*x*y)
    a0, a1, a2 = np.linalg.solve(
        np.array([[len(x), SX, SXX], [SX, SXX, SXXX], [SXX, SXXX, SXXXX]]),
        np.array([SY, SXY, SXXY])
    )

    # Сумма квадратов отклонений
    phi = a2*x**2 + a1*x + a0
    S = np.sum((phi - y) ** 2)

    return Result(
        coefficients = (a0, a1, a2),
        apply = lambda x: a2*x**2 + a1*x + a0,
        function = f'{a2:.4f}x² + {a1:.4f}x + {a0:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )

# Аппркосимация полиномиальной функцией 3-й степени
def Approximation_degree3(x, y):
    x = np.array(x)
    y = np.array(y)
    SX,SX2, SX3, SX4, SX5, SX6, SY,SXY, SX2Y, SX3Y = np.sum(x), np.sum(x**2), np.sum(x**3), np.sum(x**4), np.sum(x**5), np.sum(x**6) , np.sum(y), np.sum(x*y), np.sum(x*x*y), np.sum(x*x*x*y)
    a0, a1, a2, a3 = np.linalg.solve(
        np.array([[len(x), SX, SX2, SX3], [SX, SX2, SX3, SX4], [SX2, SX3, SX4, SX5], [SX3, SX4, SX5, SX6]]),
        np.array([SY, SXY, SX2Y, SX3Y])
    )

    # Сумма квадратов отклонений
    phi = a3*x**3 + a2*x**2 + a1*x + a0
    S = np.sum((phi - y) ** 2)

    return Result(
        coefficients = (a0, a1, a2),
        apply = lambda x: a3*x**3 + a2*x**2 + a1*x + a0,
        function = f'{a3:.4f}x³ + {a2:.4f}x² + {a1:.4f}x + {a0:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )


# Аппроксимация логарифмической функцией
def Approximation_logarith(x, y):
    x = np.array(x)
    y = np.array(y)
    if x[x < 0]:
        raise LnException('x должен быть больше чем 0')
    X = np.log(x)
    a, b = Approximation_linear(X, y).coefficients

    phi = a*np.log(x) + b
    S = np.sum((phi - y)** 2)

    return Result(
        coefficients = (a, b),
        apply = lambda x: a*math.log(x) + b,
        function = f'{a:.4f}ln(x) + {b:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )

# Аппроксимация степенной функцией
def Approximation_power(x, y):
    x = np.array(x)
    y = np.array(y)
    X, Y = np.log(x), np.log(y)
    B, A = Approximation_linear(X, Y).coefficients
    a, b = math.exp(A), B

    phi = a*x**b
    S = np.sum((phi - y)** 2)

    return Result(
        coefficients = (a, b),
        apply = lambda x: a*math.pow(x, b),
        function = f'{a:.4f}x^{b:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )

# Аппроксимация экспоненциальной функцией
def Approximation_exp(x, y):
    x = np.array(x)
    y = np.array(y)
    Y = np.log(y)
    B, A = Approximation_linear(x, Y).coefficients
    a, b = math.exp(A), B

    phi = a*np.exp(b*x)
    S = np.sum((phi - y)** 2)

    return Result(
        coefficients = (a, b),
        apply = lambda x: a*math.exp(b*x),
        function = f'{a:.4f}e^{b:.4f}x',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )

# Среднеквадратичное отклонение
def msr(phi, y):
    return (np.sum((phi - y) ** 2) / len(y)) ** 0.5

# Достоверность аппроксимации (поиск коэффициента детерминации)
def confidence(phi, y):
    return 1 - np.sum((y - phi) ** 2) / (np.sum(phi ** 2) - np.sum(phi) ** 2 / len(y))

# Коэффициент корреляции
def r(x, y):
    x0, y0 = np.mean(x), np.mean(y)
    return np.sum((x - x0) * (y - y0)) / (np.sum((x - x0) ** 2) * np.sum((y - y0) ** 2)) ** 0.5

# Вывод графиков функций
def show_graph(xs, ys, results):
    o = 5
    x1, x2, y1, y2 = min(xs), max(xs), min(ys), max(ys)
    bx, by = max(abs(x1), abs(x2)) + o, max(abs(y1), abs(y2)) + o
    x = np.linspace(min(xs) - o, max(xs) + o, 200)
    _figure = plt.figure()
    ax = _figure.add_subplot(1, 1, 1)
    plt.grid(True)
    plt.xlim((-bx, bx))
    plt.ylim((-by, by))
    ax.spines['left'].set_position('center')
    ax.spines['bottom'].set_position('center')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    for result in results:
        xt = x
        y = np.vectorize(result.apply)
        try:
            y(x)
        except ValueError:
            xt = x[x > 0]
        finally:
            ax.plot(xt, y(xt), label=result.function)
    ax.plot(xs, ys, 'ro')
    plt.legend()
    plt.show()


def main():
    ask_input = input(
        "Введите f, чтобы взять исходные данные из файла \"" + "test.txt" + "\" или k, чтобы ввести с клавиатуры\n")
    if ask_input == "k":
        x = list(map(float, input("Введите строку X: ").split()))
        y = list(map(float, input("Введите строку Y: ").split()))

    elif ask_input == "f":
        from google.colab import files
        uploaded = files.upload()
        file = open("test.txt", "r")

        lines = list(map(lambda x: x.rstrip('\n'), file.readlines()))
        x = list(map(float, lines[0].split()))
        y = list(map(float, lines[1].split()))

        file.close()

    # Вызов методов аппроксимации
    Approximations = [
        ('линейная функция', Approximation_linear),
        ('полиномиальная функция 2-й степени', Approximation_degree2),
        ('полиномиальная функция 3-й степени', Approximation_degree3),
        ('экспоненциальная функция', Approximation_exp),
        ('логарифмическая функция', Approximation_logarith),
        ('степенная функция', Approximation_power),
    ]

    # Выбираем аппроксимирующую функцию с наименьшим отклонением / наибольшим коэффициентом корреляции
    results = [a[1](x, y) for a in Approximations]
    index_min = min(range(len(results)), key=results.__getitem__)

    # В случае необходимости запись в файл
    write_mode = input('Вывод в файл (y/n): ')

    if write_mode == 'y':
        # Записываем данные в файл
        with open("test.txt", "w") as file:
            file.write("Пример текста для записи в файл\n")
            file.write("Другая строка для записи\n")
        for i, result in enumerate(results):
            name = Approximations[i][0]
            file.write(f'{name}:')
            file.write(f'φ(x) = {result.function}')
            file.write(f'S = {result.S:.4f}')
            file.write(f'δ = {result.deviation:.4f}')
            file.write(f'R² = {result.confidence:.4f}')
        file.write(f'Лучше всего аппроксимирует {Approximations[index_min][0]}: '
            f'δ = {results[index_min].deviation:.3f}')

    # Вывод результатов в консоль
    for i, result in enumerate(results):
        name = Approximations[i][0]
        print(f'--- {name}')
        print(f'φ(x) = {result.function}')
        print(f'S = {result.S:.4f}')
        print(f'δ = {result.deviation:.4f}')
        print(f'R² = {result.confidence:.4f}')
    print(f'Лучше всего аппроксимирует‚ {Approximations[index_min][0]}: '
          f'δ = {results[index_min].deviation:.3f}')

    # Вывод графика
    show_graph(x, y, results)

try:
    main()
except ValueError as e:
    print("Ошибка: ", e)
except KeyboardInterrupt as e:
    print(e)

class LnException(Exception):
    pass
