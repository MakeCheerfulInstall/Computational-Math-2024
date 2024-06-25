import numpy as np
import os
import errors as err


def from_nowhere() -> tuple:
    try:
        n = int(input("Matrix size: "))  # get the matrix size
    except ValueError:
        raise err.MatrixError("matrix size should be an integer")

    if n <= 0:  # check matrix size is correct
        raise err.MatrixError("invalid matrix size")

    A = np.random.rand(n, n)
    b = np.random.rand(n)

    # print generated matrix
    print()
    print("Generated matrix")
    print_matrix(A)
    print("Generated free numbers column")
    print_arr(b)
    print()

    return A, b


def from_file() -> tuple:
    """
      This function performs SLAE parsing from the specific file.
    """
    filename = str(input("Enter filename: ")).replace("/", os.sep).replace("\\", os.sep)

    if not (os.access(filename, os.F_OK)):
        raise err.FileError("file not found")

    if not (os.access(filename, os.R_OK)):
        raise err.FileError("file is not readable - lack of permissions")

    try:
        A = np.loadtxt(filename, unpack=True, dtype=float)  # load data as matrix
        A = np.transpose(A)  # transpose the matrix

        b = A[len(A) - 1]
        A = np.delete(A, (len(A) - 1), axis=0)

        if len(A) <= 0:  # check matrix size is correct
            raise err.MatrixError("invalid matrix size")

        return A, b
    except Exception:
        raise err.MatrixError(f"invalid matrix format in the '{filename}' file")  # notify user about difficulties


def from_keyboard() -> tuple:
    """
      This function performs SLAE parsing from the CLI.
    """
    try:
        n = int(input("Matrix size: "))  # get the matrix size
    except ValueError:
        raise err.MatrixError("matrix size should be an integer")

    if n <= 0:  # check matrix size is correct
        raise err.MatrixError("invalid matrix size")

    A = []
    print("Fill in the matrix:")
    for i in range(n):  # read the matrix row by row
        try:
            print(f"Put {i + 1}-th row: ", end='')
            row = [float(i) for i in input().replace(",", ".").split(' ', n - 1)]
            A.append(row)
        except ValueError:
            raise err.MatrixError("matrix members should be numbers")

    print("Enter free members column:\n", end='')
    b = [float(i) for i in input().split(' ', n - 1)]

    return np.array(A, float), np.array(b, float)


def print_matrix(matrix: list) -> None:
    """
      This function performs formatted output of the provided matrix.
    """
    if (matrix_size := len(matrix)) == 1:
        print("----------------------------")
        print(matrix[0][0])
        print("----------------------------")
        return

    print("----------------------------")
    for i in matrix:
        for j in i:
            print(f" {j:7.3f} ", end="")
        print()
    print("----------------------------")
    return


def print_arr(arr: list) -> None:
    """
      This function performs formatted output of the provided array.
    """
    if (len(arr) == 0):
        print("Empty array")
        return

    for i in arr:
        print(f" {i:7.3f} ", end="")
    print()
    return