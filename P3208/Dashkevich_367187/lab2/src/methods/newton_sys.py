from tools import *


def solve(sys, x, y, eps):
    max_iters = 24
    iter = 1
    jac = calc_jacobian(sys, x, y)
    x_iter = x - calc_delta_x(sys, x, y) / jac
    y_iter = y - calc_delta_y(sys, x, y) / jac
    print_table_header(["#", "x", "y", "x_dif", "y_dif"])
    dif_x = abs(x_iter - x)
    dif_y = abs(y_iter - y)
    print_table_row([iter, x_iter, y_iter, dif_x, dif_y])
    while iter < max_iters:
        iter += 1
        x = x_iter
        y = y_iter

        jac = calc_jacobian(sys, x, y)
        x_iter = x - calc_delta_x(sys, x, y) / jac
        y_iter = y - calc_delta_y(sys, x, y) / jac
        dif_x = abs(x_iter - x)
        dif_y = abs(y_iter - y)

        print_table_row([iter, x_iter, y_iter, dif_x, dif_y])
        if max(dif_x, dif_y) <= eps:
            break
    if iter == max_iters:
        print("Reached max iterations, answer is not in desired precision")
    return [x_iter, y_iter]


def calc_jacobian(sys, x, y):
    return sys[0][1](x) * sys[1][2](y) - sys[0][2](y) * sys[1][1](x)


def calc_delta_x(sys, x, y):
    return sys[0][0](x, y) * sys[1][2](y) - sys[0][2](y) * sys[1][0](x, y)


def calc_delta_y(sys, x, y):
    return sys[0][1](x) * sys[1][0](x, y) - sys[0][0](x, y) * sys[1][1](x)
