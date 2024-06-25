def solve(n: int, f, a, b, precision):
    res = get_res(n, f, a, b)
    res2 = get_res(n*2, f, a, b)
    if abs(res2 - res) <= precision:
        return res2, n*2
    else:
        return solve(n*2, f, a, b, precision)


def get_res(n, f, a, b):
    h = abs(a - b) / n
    points = []
    i = b
    while i < a:
        points.append(i)
        i += h
        if i > a:
            i = a
    points.append(i)
    return abs(h/2 * (f(points[0]) + f(points[-1]) + 2*sum(f(points[x]) for x in range(1, len(points) - 1))))
