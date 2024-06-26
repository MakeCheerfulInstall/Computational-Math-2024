from gauss_method import *  # Импорт всех функций из модуля gauss_method

# Импорт конкретных функций из модуля gauss_method
from gauss_method import calculated_residuals

while True:
    try:
        print("Choose the command:")
        # Пользователь выбирает режим работы программы
        input_variant = int(
            input(
                "1) Manual matrix input\n2) Input matrix from file\n3) Generate a random matrix\n4) Exit the program\n"
            )
        )
        # Проверка корректности выбора пользователя
        if input_variant in range(1, 5):
            break
        print("Invalid command")
        continue
    except ValueError:
        print("Incorrect value entered")
        continue

# Словарь, сопоставляющий каждой команде ее функцию обработчика
switch_command = {
    1: hand_matrix_input,
    2: open_file_matrix,
    3: random_matrix,
    4: exit,
}

# Выполнение выбранной команды из меню
matrix = switch_command.get(input_variant, exit)()

print("Matrix read:")
print_matrix(matrix)
first_matrix = matrix[:]  # Копирование исходной матрицы для вычисления остатков

# Применение метода Гаусса к матрице
matrix, swap_counts = method_Gauss(matrix)

print("rows swap = ", swap_counts, "\n")  # Вывод количества перестановок строк

print("Matrix after forward pass:")
print_matrix_float(matrix)  # Вывод матрицы после приведения к треугольному виду

# Проверка, удалось ли привести матрицу к треугольному виду
if not is_triangular(matrix):
    print("Failed to transform the matrix to triangular form")
    exit()

# Вычисление определителя матрицы
det = triangular_matrix_determinant(matrix) * ((-1) ** swap_counts)
if det == 0:
    print("Determinant of the matrix = 0, cannot solve by back substitution")
    exit()
else:
    print("Determinant of the matrix = ", det, "\n")

# Нахождение решений системы методом обратного хода
print_answers(reversal_method(matrix))

# Вывод остатков системы
print_residuals(calculated_residuals(first_matrix, reversal_method(matrix)))
