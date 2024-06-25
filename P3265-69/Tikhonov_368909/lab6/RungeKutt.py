from Equations import calculate, runge


def runge_kutt(equation, x0, y0, lower, upper, h, e):
    xarr = [x0]
    yarr = [y0]
    i = lower
    while i < upper + h:
        if i != lower:
            xarr.append(round(i, 6))
        i += h

    if e is None:
        return __runge_kutt(equation, xarr, yarr, h)
    else:
        while True:
            solution = __runge_kutt(equation, xarr, yarr, h)
            if runge(solution[1][-1], h, 4, e):
                return solution
            else:
                h /= 2
                xarr = [x0]
                while i < upper + h:
                    if i != lower:
                        xarr.append(round(i, 6))
                    i += h


def __runge_kutt(equation, xarr, yarr, h):
    for i in range(len(xarr) - 1):
        k1 = h * calculate(equation, xarr[i], yarr[i])
        k2 = h * calculate(equation, xarr[i] + h / 2, yarr[i] + k1 / 2)
        k3 = h * calculate(equation, xarr[i] + h / 2, yarr[i] + k2 / 2)
        k4 = h * calculate(equation, xarr[i] + h, yarr[i] + k3)
        yarr.append(round(yarr[i] + (k1 + 2 * k2 + 2 * k3 + k4) / 6, 6))
    return xarr, yarr
