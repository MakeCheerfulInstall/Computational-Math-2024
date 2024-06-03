from Equations import derivative_sys, graph, calculate_fx_sys


def systemSimpleIterations(x1_0, x2_0, equ, eps):
    # Условие сходимости
    if not (round(max(derivative_sys(equ, 1, 1)), 1) <= 0.6 < 1):
        raise Exception("Условие сходимости не выполняется.")
    prev_x_1, prev_x_2 = x1_0, x2_0
    counter = 0
    while True:
        counter += 1
        x1, x2 = calculate_fx_sys(equ, prev_x_1, prev_x_2)
        if abs(x1 - prev_x_1) and abs(x2 - prev_x_2) < eps:
            break
        prev_x_1, prev_x_2 = x1, x2
    return [counter, x1, x2]
