import os

from method import Method, MethodData
from parser import Parser


def main():
    os.chdir('/' + '/'.join(__file__.split('/')[:-2]) + '/resources')

    ans: str = ''
    while ans not in ['1', '2']:
        ans = input('Do you want to solve one equation (1) or system (2)? [1/2] -> ')

    is_sys: bool = ans == '2'
    if not is_sys:
        eq = Parser.choose_single_eq()
        mth = Parser.choose_method()
        data = Parser.parse_method_data()
    else:
        eq = Parser.choose_system_eq()
        mth = Parser.choose_method(True)
        data = Parser.parse_sys_method_data()

    if data is None:
        print('Oops! Something went wrong')
    else:
        res = mth.solve(eq, data, is_sys)
        if res is None:
            print('Oops! Something went wrong')
        else:
            print(res)


if __name__ == '__main__':
    main()
