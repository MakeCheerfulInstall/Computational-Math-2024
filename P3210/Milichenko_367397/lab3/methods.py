

def rectangle_left(func, a, b, n):
    h = (b - a) / n
    result = 0
    for i in range(n):
        result += func(a + i * h)
    result *= h
    return result
        
def rectangle_middle(func, a, b, n):
    h = (b - a) / n
    result = 0
    for i in range(n):
        result += func(a + (i + 0.5) * h)
    result *= h
    return result
        
def rectangle_right(func, a, b, n):
    h = (b - a) / n
    result = 0
    for i in range(1, n + 1):
        result += func(a + i * h)
    result *= h
    return result

def trapezoid_rule(func, a, b, n):
    h = (b - a) / n
    result = (func(a) + func(b)) / 2

    for i in range(1, n):
        result += func(a + i * h)

    result *= h
    return result


def simpson(func, a, b, n):
    h = (b - a) / n
    result = func(a) + func(b)

    for i in range(1, n):
        coef = 3 + (-1)**(i + 1)
        result += coef * func(a + i * h)

    result *= h / 3
    return result
