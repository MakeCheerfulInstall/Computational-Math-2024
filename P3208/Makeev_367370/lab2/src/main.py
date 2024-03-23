def main():
    ans: str = ''
    while ans.lower() not in ['y', 'n']:
        ans = input('Do you want to input from file? [y/n] ->')

    if ans.lower() == 'y':
        ans = input('Your filepath ->')
        try:
            with open(ans, 'r') as file:
                print('Not implemented yet')
        except (FileNotFoundError, IsADirectoryError):
            print('No such file')
            return
    else:
        while ans not in ['1', '2']:
            ans = input('Do you want to solve one equation (1) or system (2)? [1/2] ->')

        if ans == '1':
            Solver.chooseSingleEq()
        else:
            Solver.chooseSystemEq()


if __name__ == '__main__':
    main()
