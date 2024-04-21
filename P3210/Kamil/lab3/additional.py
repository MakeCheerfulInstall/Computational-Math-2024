from math import sqrt


def left_rectangles_method(func, a, b, n):
    h = (b - a) / n
    xs = [a + i * h for i in range(n + 1)] # [x0, x1, ..., xn]
    return h * sum([func(xs[i]) for i in range(n)])


def right_rectangles_method(func, a, b, n):
    h = (b - a) / n
    xs = [a + i * h for i in range(n + 1)] # [x0, x1, ..., xn]
    return h * sum([func(xs[i]) for i in range(1, n + 1)])


def middle_rectangles_method(func, a, b, n):
    h = (b - a) / n
    xs = [a + i * h for i in range(n + 1)] # [x0, x1, ..., xn]
    return h * sum([func((xs[i - 1] + xs[i]) / 2) for i in range(1, n + 1)])


def trapezoid_method(func, a, b, n):
    h = (b - a) / n
    xs = [a + i * h for i in range(n + 1)]  # [x0, x1, ..., xn]
    ys = [func(x) for x in xs]              # [y0, y1, ..., yn]
    return h * ((ys[0] + ys[n]) / 2 +
                sum([ys[i] for i in range(1, n)]))


def simpson_method(func, a, b, n):
    h = (b - a) / n
    xs = [a + i * h for i in range(n + 1)]  # [x0, x1, ..., xn]
    ys = [func(x) for x in xs]              # [y0, y1, ..., yn]
    return h / 3 * (ys[0] +
                4 * sum([ys[i] for i in range(1, n, 2)]) +
                   2 * sum([ys[i] for i in range(2, n - 1, 2)]) +
                ys[n])


def find_breakpoints(func, a, b, eps):
    breakpoints = []
    x = a
    while x <= b:
        try:
            func(x)
        except Exception:
            breakpoints.append(x)
        x = round(x + eps, 3)
    return breakpoints


def try_to_compute(func, x):
    try:
        return func(x)
    except Exception:
        return None


def compute(func, a, b, eps, method):
    n = 4
    i0 = method(func, a, b, n)
    i1 = method(func, a, b, n * 2)
    while abs(i1 - i0) > eps:
        n *= 2
        i0 = i1
        i1 = method(func, a, b, n * 2)
    return i1
    #return i1, n * 2
    

methods = [left_rectangles_method,
           right_rectangles_method,
           middle_rectangles_method,
           trapezoid_method,
           simpson_method]
eps = 0.1

print('f1 = 1 / x')
print('f2 = 1 / sqrt(x)')
print('f3 = 1 / (1 - x)')
func_number = input('input function number (1 or 2 or 3): ')
while func_number not in {'1', '2', '3'}:
    func_number = input('input function number (1 or 2 or 3): ')
    
while 1:
    try:
        a = float(input('input a (real number, the lower limit of integration): '))
    except Exception:
        continue
    break
while 1:
    try:
        b = float(input('input b (real number, the upper limit of integration): '))
    except Exception:
        continue
    break

func_number = int(func_number)
if func_number == 1:
    f = lambda x: 1 / x
elif func_number == 2:
    f = lambda x: 1 / sqrt(x)
else:
    f = lambda x: 1 / (1 - x)

breakpoints = find_breakpoints(f, a, b, 0.01)
print('breakpoints: ', *breakpoints)
for bp in breakpoints:
    y1 = try_to_compute(f, bp - eps)
    y2 = try_to_compute(f, bp + eps)
    if y1 is not None and y2 is not None and abs(y1 - y2) > eps:
        print('integral does not converge!')
stop = False
if not stop:    
    if a in breakpoints:
        a += eps
    elif b in breakpoints:
        b -= eps
    elif breakpoints:
        bp = breakpoints[0]
        for method in methods:
            res = compute(f, a, bp - eps, eps, method)
            res += compute(f, bp + eps, b, eps, method)
            print(f'{method.__name__} I = {res}')
        
    if not breakpoints or  a - eps in breakpoints or b + eps in breakpoints:
        for method in methods:
            res = compute(f, a, b, eps, method)
            print(f'{method.__name__} I = {res}')
