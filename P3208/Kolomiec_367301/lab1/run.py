import determinant
import matrix
import iteration_method

variant = input("Выберите формат ввода данных:\n-> 1. Файл\n-> 2. Консоль\n-> 3. Генерация матрицы\n-> ")
while variant != "exit":
    if variant == "1" or variant == "2" or variant == "3":
        test = matrix.read() \
            if variant == "2" else matrix.read_from_file() \
            if variant == "1" else matrix.generate_matrix()

        square_matrix = matrix.make_square(test)
        det = determinant.calculate(square_matrix, len(test))
        iteration_method.run(test, det)
    variant = input("Выберите формат ввода данных:\n-> 1. Файл\n-> 2. Консоль\n-> 3. Генерация матрицы\n-> ")
print("До свидания!")
