from enum import Enum
from typing import Callable

import method
import equation
from equiation_type import EquationType


class Parser:
    @staticmethod
    def choose_method(for_sys: bool) -> method.Method:
        return Parser.__choose_enum(method.MethodType, 'method', lambda m: m.for_sys == for_sys)

    @staticmethod
    def choose_single_eq() -> equation.Equation:
        return Parser.__choose_enum(EquationType, 'equation')

    @staticmethod
    def __choose_enum(enum: Enum, name: str, cond: Callable = lambda o: True):
        counter = 1
        for obj in enum:
            if cond(obj.value):
                print('\t', counter, ': ', obj.value, sep='')
                counter += 1

        num: str = ''
        rang: range = range(1, len(enum) + 1)
        while True:
            try:
                if int(num) in rang:
                    break
            except ValueError:
                pass
            num = input('Choose ' + name + ': [' + ('/'.join(str(x) for x in rang)) + '] -> ')

        return list(enum)[int(num) - 1].value

    @staticmethod
    def choose_system_eq() -> equation.EquationSystem:
        return

    @staticmethod
    def parse_method_data() -> method.MethodData:
        pass

    @staticmethod
    def __parse_file(filename: str) -> None:
        try:
            with open(filename, 'r') as file:
                print('Not implemented yet')
        except (FileNotFoundError, IsADirectoryError):
            print('No such file')

        return
