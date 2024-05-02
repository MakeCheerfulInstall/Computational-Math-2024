def get_break(f, a, b, step):
    try:
        f(a)
    except ArithmeticError:
        return a
    try:
        f(b)
    except ArithmeticError:
        return b
    x = a
    while x <= b:
        try:
            f(x)
            x += step
        except ArithmeticError:
            return x

def left_rectangles(f, a, b, n):
    h = (b - a) / n
    xs = [a + i * h if i != n else b for i in range(n + 1)]
    return h * sum([f(xs[i]) for i in range(n)])


def right_rectangles(f, a, b, n):
    h = (b - a) / n
    xs = [a + i * h if i != n else b for i in range(n + 1)]
    return h * sum([f(xs[i]) for i in range(1, n + 1)])


def middle_rectangles(f, a, b, n):
    h = (b - a) / n
    xs = [a + i * h if i != n else b for i in range(n + 1)]
    return h * sum([f((xs[i - 1] + xs[i]) / 2) for i in range(1, n + 1)])


def trapecia(f, a, b, n):
    h = (b - a) / n
    xs = [a + i * h if i != n else b for i in range(n + 1)]
    ys = [f(x) for x in xs]

    return h * ((ys[0] + ys[n]) / 2 +
                sum([ys[i] for i in range(1, n)]))


def simpson(f, a, b, n):
    h = (b - a) / n
    xs = [a + i * h if i != n else b for i in range(n + 1)]
    ys = [f(x) for x in xs]
    return h / 3 * (ys[0] +
                    4 * sum([ys[i] for i in range(1, n, 2)]) +
                    2 * sum([ys[i] for i in range(2, n - 1, 2)]) +
                    ys[n])


def read_number(s: str):
    while True:
        try:
            return float(input(s))
        except Exception:
            continue


eps = 0.001


def compute(func, a, b, eps, method):
    n = 4
    i0 = method(func, a, b, n)
    i1 = method(func, a, b, n * 2)
    while abs(i1 - i0) > eps:
        n *= 2
        i0 = i1
        i1 = method(func, a, b, n * 2)
        # print("sdf")
    return i1, n


def additional():
    print('f1 = 1 / x')
    print('f2 = 1 / (3 - 4 * x) ** (1 / 5)')
    print('f3 = 1 / (x + 1) ** 0.5')
    functions = [lambda x: 1 / x, lambda x: 1 / (3 - 4 * x) ** (1 / 5), lambda x: 1 / (x + 1) ** 0.5]
    bps = [(0, True), (0.75, True), (-1, True)]

    while True:
        try:
            ind = int(input('Введите номер функции: ')) - 1
            f = functions[ind]
            bp = bps[ind]
            break
        except Exception:
            continue

    a = read_number('Введите нижнюю границу интегрирования (a): ')
    b = read_number('Введите верхнюю границу интегрирования (b): ')

    methods = [left_rectangles, right_rectangles, middle_rectangles, trapecia, simpson]
    method_name = ["Левых прямоугольников",
                   "Правых прямоугольников",
                   "Средних прямоугольников",
                   "Трапеции",
                   "Симпсона"]
    # undefined = get_break(f, a, b, eps)
    eps2 = 0.001
    for name, method in zip(method_name, methods):
        if not bp[1] and a <= bp[0] <= b:
            print("Интеграл не сходится на заданном интервале")
            break
        if bp[0] == a:
            res, _ = compute(f, a + eps2, b, eps, method)
        elif bp[0] == b:
            res, _ = compute(f, a, b - eps2, eps, method)
        elif a <= bp[0] <= b:
            res = compute(f, a, bp[0] - eps2, eps, method)[0] + compute(f, bp[0] + eps2, b, eps, method)[0]
        else:
            res, _ = compute(f, a, b, eps, method)
        if isinstance(res, complex):
            print("Интеграл не определен на заданном интервале")
            break
        print(f'{name} I = {res:.3f}')


def main():
    print('f1 = x ** 2')
    print('f2 = x')
    print('f3 = -x ** 3 - x ** 2 - 2 * x + 1')
    functions = [lambda x: x ** 2, lambda x: x, lambda x: -x ** 3 - x ** 2 - 2 * x + 1]

    while True:
        try:
            f = functions[int(input('Введите номер функции: ')) - 1]
            break
        except Exception:
            continue

    a = read_number('Введите нижнюю границу интегрирования (a): ')
    b = read_number('Введите верхнюю границу интегрирования (b): ')

    methods = [left_rectangles, right_rectangles, middle_rectangles, trapecia, simpson]
    method_name = ["Левых прямоугольников",
                   "Правых прямоугольников",
                   "Средних прямоугольников",
                   "Трапеции",
                   "Симпсона"]
    for name, method in zip(method_name, methods):
        res, n = compute(f, a, b, eps, method)
        print(f'{name} I = {res:.3f} n = {n}')


if __name__ == '__main__':
    additional()