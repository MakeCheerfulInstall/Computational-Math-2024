def simple_iteration_method(accuracy, matrix):
    max_iteration = 1000
    matrix = make_diagonal(matrix)
    solution_x = []
    d = []

    for i in range(len(matrix)):
        d.append(matrix[i][-1] / matrix[i][i])

    last_x = d
    iteration_amount = 0

    print("Результаты итераций:")
    while True:
        iteration_amount += 1
        solution_x = []
        accuracy_vec = []

        for i in range(len(matrix)):
            result = last_x[i] + d[i]

            for j in range(len(matrix[i]) - 1):
                result += - matrix[i][j] / matrix[i][i] * last_x[j]

            solution_x.append(result)
            accuracy_vec.append(abs(last_x[i] - result))
        cur_accuracy = max(accuracy_vec)
        last_x = solution_x

        if cur_accuracy <= accuracy:
            break
        if iteration_amount >= max_iteration:
            raise ValueError("Программа достигла максимума итераций: " + str(max_iteration) + ". Точность на последней итерации: "  + str(cur_accuracy))
        print(iteration_amount, solution_x)

    return solution_x, iteration_amount, accuracy_vec

def make_diagonal(matrix):
    max_el_index_list = []
    for i in range(len(matrix)):
        if matrix[i] == max(matrix[i][:-1], key=abs):
            max_el_index_list.append(i)
        else:
            max_el_index_list.append(matrix[i].index(max(matrix[i][:-1], key=abs)))
    if len(list(set(max_el_index_list))) != len(max_el_index_list):
        print("Достижение диагонального доминирования невозможно. Метод простых итераций нельзя применить.")
        error_lines = set()
        for i in range(len(max_el_index_list)):
            if max_el_index_list[i] != i:
              error_lines.add(i)
        print("Эти строки не соответствуют условию диагонального доминирования", error_lines)
    diagonal_matrix = []

    try:
        for i in range(len(matrix)):
            diagonal_matrix.append(matrix[max_el_index_list.index(i)])
        print("Матрица преобразована, условие диагонального преобладания выполнено:")
    except ValueError as e:
        print("Матрицу не удалось преобразовать:")
    for line in diagonal_matrix:
        for x in line:
            print(x, end=" ")
        print()

    if len(diagonal_matrix) != len(matrix):
        return matrix
    return diagonal_matrix


def main():
    filename = "file.txt"

    matrix = []
    n = 0
    accuracy = 0

    ask_input = input(
        "Введите f, чтобы вставить матрицу из файла \"" + filename + "\" или k, чтобы ввести матрицу с клавиатуры\n")
    if ask_input == "k":
        print("Введите размерность матрицы:")
        n = int(input())

        if n > 20:
            raise ValueError("n не должно быть больше 20")

        print("Введите точность:")
        accuracy = float(input())
        for i in range(n):
            print(str(i+1) + " строка матрицы:")
            line = input()
            matrix.append([float(x) for x in line.strip().split(" ")])

    elif ask_input == "f":
        file = open(filename, "r")

        n = int(file.readline())
        accuracy = float(file.readline())

        for line in file:
            matrix.append([float(x) for x in line.strip().split(" ")])

        file.close()

    else:
        raise ValueError("Введено неверное значение")


    if len(matrix) != n:
        raise ValueError("Неверное количество строк")
    elif len(matrix) != 0:
        solution = simple_iteration_method(accuracy, matrix)
        print("Количество итераций:", solution[1])
        print("Вектор неизвестных:", solution[0])
        print("Вектор погрешностей:", solution[2])

try:
    main()
except ValueError as e:
    print("Ошибка: ", e)
except KeyboardInterrupt as e:
    print(e)
except ZeroDivisionError as e:
    print("Невозможно применить метод простых итераций для этой матрицы")
