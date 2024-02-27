from prettytable import PrettyTable
import matrix


def run(sle, det):
    if det == 0:
        print("СЛАУ имеет вырожденную матрицу")
        return
    else:
        sle = matrix.check_predominance(sle)
        if sle is None:
            return
        epsilon = float(input("Введите точность решения: "))
        # sle = sorted(sle, key=lambda x: x[-1], reverse=False)
        result = express_unknown(sle)
        # matrix.print_matrix(result)
        zero_solve = get_zero_solve(result)
        solving_iterations(0, epsilon, result, zero_solve, get_zero_solve(result), PrettyTable())


def express_unknown(sle) -> list[list[float]]:
    tmp = []
    for i in range(len(sle)):
        row = []
        for j in range(len(sle[i])):
            if i == j:
                row.append(0)
                continue
            row.append((-1) * (sle[i][j] / sle[i][i]))
        row[-1] *= -1
        tmp.append(row)

    return tmp


def print_iteration(count_iter, solve, solve_epsilon, table) -> PrettyTable:
    if count_iter == 0:
        th = ["k"]
        for i in range(len(solve)):
            th.append("x_" + str(i + 1))
        th.append("max|x_k - x_(k-1)|")
        table = PrettyTable(th)
        td = [count_iter]
        for i in solve:
            td.append(i)
        td.append(0)
        table.add_row(td)
        return table
    else:
        td = [count_iter]
        for i in solve:
            td.append(i)
        td.append(max(solve_epsilon))
        table.add_row(td)
    return table


def get_zero_solve(sle) -> list[float]:
    solve = []
    for i in range(len(sle)):
        solve.append(sle[i][-1])
    return solve


def solving_iterations(count_iter, epsilon, sle, solve, solve_epsilon, table):
    table = print_iteration(count_iter, solve, solve_epsilon, table)

    if max(solve_epsilon) < epsilon and count_iter != 0:
        print(table)
        return

    if count_iter > 20:
        print(table)
        return

    tmp_solve = []

    for i in range(len(solve)):
        x = sle[i][-1]
        for j in range(len(sle[i]) - 1):
            x += sle[i][j] * solve[j]
        tmp_solve.append(round(x, 4))

    for i in range(len(solve)):
        solve_epsilon[i] = abs(round(tmp_solve[i] - solve[i], 4))
        solve[i] = tmp_solve[i]

    return solving_iterations(count_iter + 1, epsilon, sle, solve, solve_epsilon, table)
