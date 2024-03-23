from method import Method, MethodData
from parser import Parser


def main():
    ans: str = ''
    while ans not in ['1', '2']:
        ans = input('Do you want to solve one equation (1) or system (2)? [1/2] -> ')

    if ans == '1':
        eq = Parser.choose_single_eq()
    else:
        eq = Parser.choose_system_eq()

    mth: Method = Parser.choose_method(False)
    data: MethodData = Parser.parse_method_data()
    mth.solve(eq, data)


if __name__ == '__main__':
    main()
