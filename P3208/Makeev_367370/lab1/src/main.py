from utils import is_size_valid
from matrix import Equation
import os


def main():
    os.chdir('/' + '/'.join(__file__.split('/')[:-2]) + '/resources')
    try:
        inp: str = ''
        while inp.lower() not in ['y', 'n']:
            inp = input("Do you want to input matrix from file? (y/n): ")
        if inp.lower() == "y":
            filepath: str = input("Enter filepath: ")
            equation: Equation = Equation.parse_file(filepath)
            if equation:
                print("\nParsed equation:")
                print(equation)
        else:
            str_n: str = ''
            while not is_size_valid(str_n):
                str_n = input("Enter N (<=20): ")

            inp = ''
            while inp.lower() not in ['y', 'n']:
                inp = input("Do you want to randomize matrix? (y/n): ")
            if inp.lower() == "y":
                equation = Equation.make_random(int(str_n))
                if equation:
                    print("\nRandomized equation:")
                    print(equation)
            else:
                equation = Equation.parse_commandline(int(str_n))

        if equation:
            equation.solve()
    except (KeyboardInterrupt, EOFError):
        print("\nBye bye...")


if __name__ == '__main__':
    main()
