import math

from P3213.Markov_367380.lab5.dto.request import Request


def lagrange(request: Request, x: float) -> float:
    y: float = 0
    xs: list[float] = request.get_xs()
    ys: list[float] = request.get_ys()

    for i in range(request.n):
        numerator: float = 1
        denominator: float = 1
        for j in range(request.n):
            if i == j:
                continue
            numerator *= (x - xs[j])
            denominator *= (xs[i] - xs[j])
        y += ys[i] * (numerator / denominator)

    return y


def get_divided_f(xs: list[float], ys: list[float], i, k, diffs: list[list[float | None]]):
    if k == 0:
        diffs[i][k] = ys[i]
        return diffs[i][k]

    if k == 1:
        diffs[i][k] = (ys[i + k] - ys[i]) / (xs[i + k] - xs[i])
        return diffs[i][k]

    if diffs[i + 1][k - 1] is None:
        diffs[i + 1][k - 1] = get_divided_f(xs, ys, i + 1, k - 1, diffs)
    if diffs[i][k - 1] is None:
        diffs[i][k - 1] = get_divided_f(xs, ys, i, k - 1, diffs)
    return (diffs[i + 1][k - 1] - diffs[i][k - 1]) / (xs[i + k] - xs[i])


def newton_divided(request: Request, x: float) -> float:
    xs: list[float] = request.get_xs()
    ys: list[float] = request.get_ys()
    diffs: list[list[float | None]] = [[None for i in range(request.n)] for j in range(request.n)]
    y: float = ys[0]

    for i in range(1, request.n):
        mult: float = 1
        for j in range(i):
            mult *= (x - xs[j])
        y += get_divided_f(xs, ys, 0, i, diffs) * mult

    return y


def get_end_f(ys: list[float], i, k, diffs: list[list[float | None]]):
    if k == 1:
        diffs[i][k] = ys[i + 1] - ys[i]
        return diffs[i][k]

    if diffs[i + 1][k - 1] is None:
        diffs[i + 1][k - 1] = get_end_f(ys, i + 1, k - 1, diffs)
    if diffs[i][k - 1] is None:
        diffs[i][k - 1] = get_end_f(ys, i, k - 1, diffs)
    return diffs[i + 1][k - 1] - diffs[i][k - 1]


def newton_end(request: Request, x: float) -> float:
    xs: list[float] = request.get_xs()
    ys: list[float] = request.get_ys()
    diffs: list[list[float | None]] = [[None for i in range(request.n)] for j in range(request.n)]
    y: float = ys[0]
    h: float = xs[1] - xs[0]
    fact: float = 1
    h_pow: float = 1

    for i in range(1, request.n):
        mult: float = 1
        fact *= i
        h_pow *= h
        for j in range(i):
            mult *= (x - xs[j])
        y += (get_end_f(ys, 0, i, diffs) * mult) / (fact * h_pow)

    return y


def stirling(request: Request, x: float) -> float:
    xs: list[float] = request.get_xs()
    ys: list[float] = request.get_ys()
    diffs: list[list[float]] = [[0 for i in range(request.n)] for j in range(request.n)]

    for i in range(request.n - 1):
        diffs[i][0] = ys[i + 1] - ys[i]

    for j in range(1, request.n - 1):
        for i in range(request.n - j - 1):
            diffs[i][j] = diffs[i + 1][j - 1] - diffs[i][j - 1]
    y: float = diffs[request.n // 2][0]
    fact: float = 1
    s: int = request.n // 2
    t: float = (x - xs[s]) / (xs[1] - xs[0])
    if abs(t) > 0.25:
        raise ValueError("|t| > 0.25")
    odd: int = 1
    even: int = 1
    mult_odd: float = 1
    mult_even: float = 1

    for i in range(1, request.n):
        fact *= i
        s = (request.n - i) // 2
        if i % 2:
            if odd != 1:
                mult_odd *= (t ** 2 - (odd - 1) ** 2)
            else:
                mult_odd *= t
            odd += 1
            y += ((mult_odd / (2 * fact)) * (diffs[s][i - 1] + diffs[s - 1][i - 1]))
        else:
            mult_even *= t ** 2 - (even - 1) ** 2
            even += 1
            y += (mult_even / fact) * diffs[s][i - 1]

    return y


def bessel(request: Request, x: float) -> float:
    xs: list[float] = request.get_xs()
    ys: list[float] = request.get_ys()
    diffs: list[list[float]] = [[0 for i in range(request.n)] for j in range(request.n)]

    for i in range(request.n - 1):
        diffs[i][0] = ys[i + 1] - ys[i]

    for j in range(1, request.n - 1):
        for i in range(request.n - j - 1):
            diffs[i][j] = diffs[i + 1][j - 1] - diffs[i][j - 1]
    y: float = diffs[request.n // 2][0]
    fact: float = 1
    s: int = request.n // 2
    t: float = (x - xs[s]) / (xs[1] - xs[0])
    if abs(t) < 0.25 or abs(t) > 0.75:
        raise ValueError("|t| < 0.25 or |t| > 0.75")
    odd: int = 0
    even: int = 1
    mult_odd: float = 1
    mult_even: float = 1

    for i in range(1, request.n):
        fact *= i
        s = (request.n - i) // 2
        if i % 2:
            if odd != 0:
                mult_odd *= (t ** 2 - odd ** 2)  # Изменено для многочлена Бесселя
            else:
                mult_odd *= t
            odd += 1
            y += ((mult_odd / (2 * fact)) * (diffs[s][i - 1] + diffs[s - 1][i - 1]))
        else:
            mult_even *= t ** 2 - (even - 1) ** 2
            even += 1
            y += (mult_even / fact) * (diffs[s][i - 1] + diffs[s - 1][i - 1])

    return y
