from tools import *


def get_x(f, ff, start, end):
    if f(start) * ff(start) > 0:
        return [start, start + 0.1]
    elif f(end) * ff(end) > 0:
        return [end, end - 0.1]
    else:
        return [start, start + 0.1]

def solve(f, ff, start, end, accuracy):
    iter = 0
    print(get_x(f, ff, start, end))
    x, prev_x = get_x(f, ff, start, end)

    def find_x():
        return x - (x - prev_x) / (f(x) - f(prev_x)) * f(x)

    print_table_header(["#", "x_{i-1}", "x_i", "x_{i+1}", "f(x_i+1)", "delta_x"])
    while abs(x - prev_x) > accuracy and abs(f(x)) >= accuracy:
        iter += 1
        _out = [iter, prev_x, x]
        x = find_x()
        prev_x = _out[-1]
        print_table_row(_out + [x, f(x), abs(x - prev_x)])

    return x
