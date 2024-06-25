from Equations import calculate, runge


def euler(equation, x0, y0, lower, upper, h, e):
    xarr = [x0]
    yarr = [y0]
    i = lower
    while i < upper + h:
        if i != lower:
            xarr.append(round(i, 6))
        i += h

    if e is None:
        return __euler(equation, xarr, yarr, h)
    else:
        while True:
            solution = __euler(equation, xarr, yarr, h)
            if runge(solution[1][-1], h, 1, e):
                return solution
            else:
                h /= 2
                xarr = [x0]
                while i < upper + h:
                    if i != lower:
                        xarr.append(round(i, 6))
                    i += h


def __euler(equation, xarr, yarr, h):
    farr = []

    for i in range(len(xarr) - 1):
        farr.append(calculate(equation, xarr[i], yarr[i]))
        yarr.append(round(yarr[i] + h * farr[i], 6))
    return xarr, yarr, farr
