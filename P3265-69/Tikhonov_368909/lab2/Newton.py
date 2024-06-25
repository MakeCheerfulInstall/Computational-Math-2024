from Equations import calculate_fk, derivative1, derivative2


def newton(a, b, equ, eps):
    if calculate_fk(equ, a) * derivative2(equ, a) > 0:
        prev_x = a
    elif calculate_fk(equ, b) * derivative2(equ, b) > 0:
        prev_x = b
    else:
        raise Exception("Не выполняется условие сходимости.")
    counter = 0
    while True:
        x = prev_x - calculate_fk(equ, prev_x) / derivative1(equ, prev_x)
        counter += 1
        if abs(x - prev_x) <= eps:
            break
        prev_x = x
    return [counter, x]
