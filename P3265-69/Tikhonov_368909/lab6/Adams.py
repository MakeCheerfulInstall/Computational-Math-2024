from Equations import calculate, exact


def adams(equation, x0, lower, upper, yarr, farr, h, e):
    xarr = [x0]
    i = lower
    while i < upper + h:
        if i != lower:
            xarr.append(round(i, 6))
        i += h

    if e is None:
        return __adams(equation, xarr, yarr, farr, h)
    else:
        while True:
            solution = __adams(equation, xarr, yarr, farr, h)
            if solution[3] < e:
                return solution
            else:
                raise Exception("Метод не удовлетворяет заданной точности.")


def __adams(equation, xarr, yarr, farr, h):
    earr = 0
    for i in range(len(yarr)):
        if abs(exact[equation-1][i] - yarr[i]) > earr:
            earr = abs(exact[equation-1][i] - yarr[i])

    for i in range(3, len(xarr) - 1):
        f1 = farr[i] - farr[i - 1]
        f2 = farr[i] - 2 * farr[i - 1] + farr[i - 2]
        f3 = farr[i] - 3 * farr[i - 1] + 3 * farr[i - 2] - farr[i - 3]
        yarr.append(
            round(yarr[i] + h * farr[i] + f1 * (h ** 2) / 2 + f2 * 5 * (h ** 3) / 12 + f3 * 3 * (h ** 4) / 8, 6))
        if i != len(xarr) - 1:
            farr.append(calculate(equation, xarr[i + 1], yarr[i + 1]))
        if abs(exact[equation-1][i] - yarr[i + 1]) > earr:
            earr = abs(exact[equation-1][i] - yarr[i])
    return xarr, yarr, farr, earr
