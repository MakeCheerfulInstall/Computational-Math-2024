modes = {
    "left": lambda x, y: x,
    "middle": lambda x, y: (x + y)/2,
    "right": lambda x, y: y
}


def solve(n: int, f, a, b, precision, mode):
    res = get_res(n, f, a, b, mode)
    res2 = get_res(n*2, f, a, b, mode)
    if abs(res2 - res) <= precision:
        return res2, n*2
    else:
        return solve(n*2, f, a, b, precision, mode)


def get_res(n: int, f, a, b, mode):
    h = abs(a - b) / n
    points = []
    i = b
    while i < a:
        j = i
        i += h
        if i > a:
            i = a
        points.append(modes[mode](j, i))
    return abs(sum([h * f(x) for x in points]))