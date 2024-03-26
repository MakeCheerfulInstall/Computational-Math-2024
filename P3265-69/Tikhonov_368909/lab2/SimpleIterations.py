from Equations import calculate_fk, derivative1


def simpleIterations(a, b, equ, eps):
    lm = -1 / max(derivative1(equ, a), derivative1(equ, b))

    # Условие сходимости
    if 1 + lm * derivative1(equ, a) > 1 or 1 + lm * derivative1(equ, b) > 1:
        raise Exception("Условие сходимости не выполняется.")

    prev_x = a
    counter = 0
    while True:
        x = prev_x + lm * calculate_fk(equ, prev_x)
        counter += 1
        if abs(x - prev_x) < eps:
            break
        prev_x = x
    return [counter, x]

