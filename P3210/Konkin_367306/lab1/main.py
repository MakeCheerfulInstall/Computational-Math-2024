from matrix_io import from_file, from_keyboard, from_nowhere, print_matrix, print_arr
from linalg import gauss
import numpy as np
import math
import sys


def choose_input_method() -> str:
    print("Welcome to SLAE Calculator")
    print("Calculation method:")
    print("1) Keyboard")
    print("2) File")
    print("3) Randomly generated")

    choice = str(input("Your choice: "))
    while choice not in ["1", "2", "3"]:
        print("Invalid choice! Try again.")
        choice = str(input("Another choice: "))

    return choice


def main() -> None:
    match choose_input_method():
        case "1":
            A, b = from_keyboard()
        case "2":
            A, b = from_file()
        case _:
            A, b = from_nowhere()

    # calculate correct determinant for validation purposes
    det_correct = np.linalg.det(A)

    try:
        x, r, det = gauss(A, b)

        # print an extended matrix
        print()
        print("Triangular matrix")
        print_matrix(A)
        print("Free numbers column")
        print_arr(b)

        print()

        if math.isnan(det) or det == 0:
            print("Determinant is 0")
            return

        # print determinant variants
        print("Determinant[aka correct]: ", det_correct)
        print("Determinant[lib]: ", det)

        print()

        # calculated unknown vector
        print("Unknown vector")
        print_arr(x)

        print()

        # print residual vector
        print("Residual vector")
        print_arr(r)

        return
    except Exception as e:
        print(e)
        return


if __name__ == "__main__":
    try:
        sys.tracebacklimit = 0
        main()
    except EOFError:
        print("\nEnd of stream detected. Performing exit...")
    except KeyboardInterrupt:
        print("\nKeyboard interrupt detected. Performing exit...")