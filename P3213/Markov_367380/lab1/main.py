from matrix import Matrix, SquareMatrix
from validator import Validator

input = Validator.safe_input


def main():
    print("Welcome!")
    option: int = input("How would you like to input data? (with cli - 1, from file - 2(default)): ")

    if option == "1":
        matrix, results, precision = cli_input()
    else:
        matrix, results, precision = file_input()

    print(f"Matrix:\n{matrix}\n")
    print(f"Results:\n{results}\n")
    if matrix.get_max_by_module() == 0:
        print("Matrix max element is 0 :(")
        return

    matrix.rearrange(results)
    print(f"Matrix after rearrange:\n{matrix}\n")
    print(f"Results after rearrange:\n{results}\n")

    right_side_matrix: SquareMatrix = get_right_side_matrix(matrix)
    print(f"Matrix after division:\n{right_side_matrix}\n")

    right_side_results: Matrix = get_right_side_results(matrix, results)
    print(f"Results after division:\n{right_side_results}\n")

    new_x, x, iteration_count = perform(precision,right_side_matrix, right_side_results)

    print("Error vectors: ")
    print(new_x - x)
    print()

    print(f"To get result script needed {iteration_count} iterations")
    print()

    print("Solution:")
    print(new_x)
    print()


def perform(precision: float, right_side_matrix: Matrix, right_side_results: Matrix) -> tuple:
    x: Matrix = right_side_results
    new_x: Matrix = right_side_results
    diff: float = float("inf")
    iteration_count: int = 0
    while diff > precision:
        iteration_count += 1
        x = new_x
        new_x = right_side_matrix * x + right_side_results
        diff = (new_x - x).get_max_by_module()
    return new_x, x, iteration_count


def get_right_side_matrix(matrix: SquareMatrix) -> SquareMatrix:
    result_data: list = [
        [0 if i == j else -matrix.get_data()[i][j] / matrix.get_data()[i][i]
         for j in range(matrix.get_column_count())]
        for i in range(matrix.get_row_count())]
    return SquareMatrix(matrix.get_row_count(), result_data)


def get_right_side_results(matrix: SquareMatrix, results: Matrix) -> Matrix:
    result_data: list = [
        [results.get_data()[i][0] / matrix.get_data()[i][i]]
        for i in range(matrix.get_row_count())]
    return Matrix(results.get_row_count(), results.get_column_count(), result_data)


def cli_input() -> tuple:
    str_dimension: str = ""
    while not Validator.validate_dimension(str_dimension):
        print("Please input matrix dimension")
        str_dimension = input("(make sure it's lower than 21 and greater than 0): ")
    given_dimension: int = int(str_dimension)

    str_precision: str = ""
    while not Validator.validate_precision(str_precision):
        print("Please input precision")
        str_precision = input("(make sure it's greater than 1e-9): ")
    given_precision: float = round(float(str_precision.replace(",", ".")), 9)

    ans: str = input("Would you like to input matrix by yourself? y/n ")
    if ans == "y":
        data: list = []
        results = []
        print("(make sure elements of the same row separated with ' ')")
        print("(make sure that count of elements is equals to given dimension and one left for numbers)")
        for i in range(given_dimension):
            given_row = []
            while len(given_row) != given_dimension + 1:
                given_row = Validator.validate_input(input())
                if len(given_row) == given_dimension + 1:
                    break
                print("(make sure that count of elements is equals to given dimension)")
            data.append(given_row[:-1])
            results.append([given_row[-1]])
        return SquareMatrix(given_dimension, data), Matrix(given_dimension, 1, results), given_precision
    else:
        return SquareMatrix.generateMatrix(given_dimension), Matrix.generate_result(given_dimension), given_precision


def file_input() -> tuple:
    print("Make sure your file stands such format:")
    print("dimension (make sure it's lower than 21 and greater than 0)")
    print("precision")
    print("matrix (optional)")
    print("(make sure elements of the same row separated with ' ')")
    print("(make sure that count of elements is equals to given dimension and one left for numbers)")

    while True:
        print("Make sure your file stands such format:")
        print("dimension (make sure it's lower than 21 and greater than 0)")
        print("precision")
        print("matrix (optional)")
        print("(make sure elements of the same row separated with ' ')")
        print("(make sure that count of elements is equals to given dimension and one left for numbers)")

        filename: str = input("Please input filename with full/relative path: ")

        try:
            open(filename)
        except FileNotFoundError:
            print("File not found!")
            continue

        with open(filename) as f:
            input_data: list = f.readlines()

        if len(input_data) < 2:
            print("READ AGAIN!!!")
            continue

        if not (Validator.validate_precision(input_data[1]) and Validator.validate_dimension(input_data[0])):
            print("READ AGAIN!!!")
            continue

        given_dimension: int = int(input_data[0])
        given_precision: float = round(float(input_data[1].replace(",", ".")), 9)

        if len(input_data) == given_dimension + 2:
            data: list = []
            results = []
            for i in range(given_dimension):
                given_row = Validator.validate_input(input_data[i + 2])
                if len(given_row) != given_dimension + 1:
                    print("Exception in matrix")
                    break
                data.append(given_row[:-1])
                results.append([given_row[-1]])
            else:
                return SquareMatrix(given_dimension, data), Matrix(given_dimension, 1, results), given_precision
        else:
            return SquareMatrix.generateMatrix(given_dimension), Matrix.generate_result(
                given_dimension), given_precision


if __name__ == "__main__":
    main()
