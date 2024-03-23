from parser import Parser
from equation import AbstractEquation


def main():
    ans: str = ''
    eq: AbstractEquation
    while ans not in ['1', '2']:
        ans = input('Do you want to solve one equation (1) or system (2)? [1/2] -> ')

    if ans == '1':
        eq = Parser.choose_single_eq()
    else:
        eq = Parser.choose_system_eq()

    if eq is None:
        print("Oops! Something went wrong")

    eq.solve()


if __name__ == '__main__':
    main()
