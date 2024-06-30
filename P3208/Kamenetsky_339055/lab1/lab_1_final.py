# Функция для перестановки и проверки сходимости
def relocate(A, B, n):
    new_matrix_b = [0 for i in range(n)]
    new_matrix = [[0 for i in range(n)] for i in range(n)]
    flag = 0;
    for i in range(n):
        for j in range(n):
            if abs(A[j][i]) > abs(module_sum(A[j])) - abs(A[j][i]):
                new_matrix[i]=A[j]
                new_matrix_b[i]=B[j]
                flag += 1;
                break

    if flag != n:
        print("Условие не может быть выполнено")
        exit()

    for i in range(n):
        devider = new_matrix[i][i]
        for j in range(n):
            new_matrix[i][j]=new_matrix[i][j]/devider*-1
        new_matrix_b[i] = new_matrix_b[i]/devider
        new_matrix[i][i] = 0
    if check_convergence_condition(new_matrix):
        print("Условие сходимости не выполнено")
        exit()
    return new_matrix, new_matrix_b

# Функция для подсчета суммы строки
def module_sum(row):
    sum = 0
    for i in range(len(row)):
        sum += abs(row[i])
    return sum

# Функция для нахождения ответа
def answer(array, B, k, n):
    indexes = B[:]
    end = B[:]
    it = 0
    while True:
        it+=1
        s=0
        for i in range(n):
            for j in range(n):
                s += float(indexes[j]) * float(array[i][j])
            s += B[i]
            indexes[i] = s
            s = 0
        print(it, indexes)
        if(max(max_value(indexes, end, n)) <= k):
            break
        end = indexes[:]


# Условие сходимости
def check_convergence_condition(matrix_c):
    max_element = max([sum(map(abs, row)) for row in matrix_c])
    if max_element>=1:
        return True
    return False

# Функция по поиску разницы значений массивов
def max_value(A, B, n):
    array_result = [0 for i in range(n)]
    for i in range(n):
        array_result[i] = abs(abs(A[i]) - abs(B[i]))
    return array_result

def key_word():
    # Ввод размерности матрицы
    n = int(input("Введите размерность матрицы: "))

    # Ввод матрицы A
    A = []
    print("Введите матрицу A:")
    for _ in range(n):
        row = list(map(float, input().split()))
        A.append(row)

    # Ввод вектора b
    print("Введите вектор b:")
    b = list(map(float, input().split()))

    # Ввод точности
    print("Введите точность")
    k = float(input())

    A, b = relocate(A, b, n)
    answer(A, b, k, n)

def file_mode():
    with open("lab1/task.txt", "r") as file:
        lines = [line.strip() for line in file]
        n=int(lines[0])
        lines.pop(0)
        A = [[0 for i in range(n)] for i in range(n)]
        for i in range(n):
            A[i] = list(map(float, lines[0].split()))
            lines.pop(0)
        b=list(map(float, lines[0].split()))
        lines.pop(0)
        k=float(lines[0])
        A, b = relocate(A, b, n)
        answer(A, b, k, n)

def choose_mode():
    if (input("Вы желаете взять данные из файла task.txt? [y]")=="y"):
        file_mode()
    else:
        key_word()
choose_mode()
