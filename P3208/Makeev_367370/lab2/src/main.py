import os

from method import Method, MethodData
from parser import Parser


def main():
    os.chdir('/' + '/'.join(__file__.split('/')[:-2]) + '/resources')

    ans: str = ''
    while ans not in ['1', '2']:
        ans = input('Do you want to solve one equation (1) or system (2)? [1/2] -> ')

    if ans == '1':
        eq = Parser.choose_single_eq()
    else:
        eq = Parser.choose_system_eq()

    mth: Method = Parser.choose_method(False)
    data: MethodData | None = Parser.parse_method_data()

    if data is None:
        print('Oops! Something went wrong')
    else:
        mth.solve(eq, data)


if __name__ == '__main__':
    main()
