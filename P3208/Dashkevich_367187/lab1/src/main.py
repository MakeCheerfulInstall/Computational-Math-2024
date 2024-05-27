import decimal
import random
import sys
import time

from tabulate import tabulate


def get_input_type() -> int:
    print("Select an input type:\n"
          "1. Manual\n"
          "2. By file\n"
          "3. Random matrix generation")
    while True:
        inp = input("Input type (number or name): ")
        if inp == "1" or inp == "Manual":
            return 1
        elif inp == "2" or inp == "By file":
            return 2
        elif inp == "3" or inp == "Random matrix generation":
            return 3
        print("Invalid input, please try again", file=sys.stderr)
        time.sleep(0.5)


def print_matrix(mrx: [[decimal]]):
    print(tabulate(mrx, floatfmt=".5f"))


def matrix_valid(mrx: [[decimal]]) -> str:
    n = len(mrx)
    if n > 20:
        return "matrix too big! Should be n <= 20"
    for i in range(n):
        if len(mrx[i]) != n + 1:
            return f"matrix is incorrect! Line {i + 1} is {len(mrx[i])} long, expected: {n + 1}"
    return "ok"


def get_matrix_from_file() -> [[decimal]]:
    print("Reading matrix from file, please notice:\n"
          "- matrix should be n + 1 by n in size, extra column being the answers vector\n"
          "- separate entries inside a row via single space\n"
          "- n can't be more than 20")
    while True:
        try:
            file = input("Enter a file name:")
            f = open("../resources/" + file)
            ans = []
            for line in f:
                ans.append([decimal.Decimal(x.replace(",", ".")) for x in line.split(" ")])
            res = matrix_valid(ans)
            f.close()
            if res == "ok":
                return ans
            print("Invalid matrix format: " + res, file=sys.stderr)
        except FileNotFoundError:
            print("No such file, make sure it in resources directory", file=sys.stderr)
        except ValueError:
            print("Invalid number value in the file, make sure that there is no letters in the file", file=sys.stderr)
        except EOFError:
            print("End of file reached, check your dimension", file=sys.stderr)
        time.sleep(0.5)


def get_matrix_from_console() -> [[decimal]]:
    print("Enter your matrix. Please note the following:\n"
          "- matrix should be n + 1 by n in size, extra column being the answers vector\n"
          "- separate entries inside a row via single space\n"
          "- n can't be more than 20\n"
          "- max precision of element is 10 digits after point\n"
          "Enter rows one by one below:")
    while True:
        try:
            ans = [[decimal.Decimal(x.replace(",", ".")) for x in input().split(" ")]]
            for i in range(len(ans[0]) - 2):
                ans.append([decimal.Decimal(x.replace(",", ".")) for x in input().split(" ")])
            res = matrix_valid(ans)
            if res == 'ok':
                return ans
            print("Invalid matrix format: " + res, file=sys.stderr)
        except ValueError:
            print("Invalid number format: can't parse", file=sys.stderr)
        time.sleep(0.5)


def get_n() -> int:
    print("Choose your way to enter n (should be <= 20):\n"
          "1. Manual\n"
          "2. By file")
    inp = ""
    while True:
        inp = input("Input way: ")
        if inp == "1" or inp == "2":
            break
    if inp == "1":
        while True:
            try:
                n = int(input("n: "))
                if 0 < n <= 20:
                    return n
                print("n is not in range", file=sys.stderr)
            except ValueError:
                print("n is not a valid number", file=sys.stderr)
            time.sleep(0.5)
    else:
        while True:
            try:
                inp = input("Enter file name (should be in resources dir): ")
                f = open("../resources/" + inp)
                n = int(f.readline())
                f.close()
                if 0 < n <= 20:
                    return n
                print("n is not in range", file=sys.stderr)
            except ValueError:
                print("n is not a valid number", file=sys.stderr)
            except EOFError:
                print("n is not in file", file=sys.stderr)
            except FileNotFoundError:
                print("File not found, check directory", file=sys.stderr)
            time.sleep(0.5)


def get_random_matrix() -> [[decimal]]:
    n = get_n()
    ans = []
    for i in range(n):
        ans.append([round(random.random() * 100, 4) for j in range(n + 1)])
    for i in range(n):
        # making sure that matrix is diagonally dominating (I'm not sure how exactly this called in English)
        ans[i][i] = round(sum([abs(ans[i][x]) for x in range(len(ans[i]) - 1)]), 4)
    return ans


def check_diagonal_dominance(mrx: [[decimal]]) -> (bool, [[decimal]]):
    abs_mrx = []
    for i in mrx:
        abs_mrx.append([abs(i[x]) for x in range(len(i) - 1)])
    max_indexes = [i.index(max(i)) for i in abs_mrx]
    if len(set(max_indexes)) != len(max_indexes):
        return False, mrx
    for i in abs_mrx:
        if max(i) <= sum(i) - max(i):
            return False, mrx
    ans = [[] for i in range(len(mrx))]
    j = 0
    for i in max_indexes:
        ans[i] = mrx[j]
        j += 1
    return True, ans


def get_precision() -> decimal:
    while True:
        try:
            ans = decimal.Decimal(input("Enter desired precision: ").replace(",", "."))
            if 0 < ans < 1:
                return ans
            print("precision should be in format of x.xxx...x and in range of [0; 1]", file=sys.stderr)
        except ValueError:
            print("invalid number format, can't parse!", file=sys.stderr)
        time.sleep(0.5)


def do_simple_iteration(c: [[decimal]], d: [decimal], x: [decimal]) -> [decimal]:
    ans_x = []
    for i in range(len(d)):
        ans_x.append(d[i] + sum([x[j] * c[i][j] for j in range(len(c[i]))]))
    return ans_x


def iteration_algo(mrx: [[decimal]], precision: decimal):
    n = len(mrx)
    d = [mrx[i][-1] / mrx[i][i] for i in range(n)]
    iter_count = 0
    x = d.copy()
    c = []
    for i in range(n):
        c_row = []
        for j in range(n):
            if i == j:
                c_row.append(0)
            else:
                c_row.append(-(mrx[i][j] / mrx[i][i]))
        c.append(c_row)
    last_err = sys.float_info.max
    while True:
        iter_count += 1
        x_1 = do_simple_iteration(c, d, x)
        errors = [abs(x_1[i] - x[i]) for i in range(len(x))]
        if max(errors) < precision:
            return x_1, errors, iter_count
        if max(errors) > last_err:
            print("Answer cannot be found, ОФАЕМ С ПОЗОРОМ", file=sys.stderr)
            return None, None, None
        last_err = max(errors)
        x = x_1


def print_results(x_1, errors, iter_count):
    if x_1 is None:
        return
    print(f"Found answer with given precision!\n"
          f"answer vector:")
    n = len(x_1)
    for i in range(n):
        print(f"x_{i + 1}: {x_1[i]}")
    print("errors vector:")
    for i in range(n):
        print(f"|x_{i + 1}({iter_count}) - x_{i + 1}({iter_count - 1})|: {errors[i]}")
    print(f"Answer was found in {iter_count} iterations!")


if __name__ == '__main__':
    matrix = []
    inp_type = get_input_type()
    if inp_type == 1:
        matrix = get_matrix_from_console()
    elif inp_type == 2:
        matrix = get_matrix_from_file()
    else:
        matrix = get_random_matrix()
    res = check_diagonal_dominance(matrix)
    if not res[0]:
        print("No diagonal dominance presence, result may not be correct", file=sys.stderr)
        time.sleep(0.5)
    matrix = res[1]
    print("Your matrix:")
    print_matrix(matrix)
    eps = get_precision()
    res = iteration_algo(matrix, eps)
    print_results(res[0], res[1], res[2])
