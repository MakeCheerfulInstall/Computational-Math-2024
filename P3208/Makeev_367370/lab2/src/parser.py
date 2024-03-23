import method
import equation
from equiation_type import EquationType


class Parser:
    @staticmethod
    def choose_method(for_sys: bool) -> method.Method:
        counter = 1
        for m in method.MethodType:
            if m.value.for_sys == for_sys:
                print('\t', counter, ': ', m.value, sep='')
                counter += 1

        num: str = ''
        rang: range = range(1, len(method.MethodType) + 1)
        while True:
            try:
                if int(num) in rang:
                    break
            except ValueError:
                pass
            num = input('Choose method: [' + ('/'.join(str(x) for x in rang)) + '] -> ')

        return list(method.MethodType)[int(num) - 1].value

    @staticmethod
    def choose_single_eq() -> equation.Equation:
        counter = 1
        for eq in EquationType:
            print('\t', counter, ': ', eq.value, sep='')
            counter += 1

        num: str = ''
        rang: range = range(1, len(EquationType) + 1)
        while True:
            try:
                if int(num) in rang:
                    break
            except ValueError:
                pass
            num = input('Choose equation: [' + ('/'.join(str(x) for x in rang)) + '] -> ')

        return list(EquationType)[int(num) - 1].value

    @staticmethod
    def choose_system_eq() -> equation.EquationSystem:
        return

    @staticmethod
    def parse_file_data(filename: str) -> equation.AbstractEquation | None:
        try:
            with open(filename, 'r') as file:
                print('Not implemented yet')
                # TODO
        except (FileNotFoundError, IsADirectoryError):
            print('No such file')

        return
