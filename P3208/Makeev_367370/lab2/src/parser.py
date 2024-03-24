from enum import Enum
from typing import Callable

import method
import equation
from utils import to_float
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
        rang: range = range(1, counter)
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
    def parse_method_data() -> method.MethodData | None:
        ans: str = input('Parse data for solving (a, b, e) from file? [n/filename] -> ')
        if ans.lower() == 'n':
            while True:
                try:
                    ans = list(map(to_float, input('Input 3 float numbers (a, b, e) joined spaces -> ').split(' ')))
                    if len(ans) == 3:
                        break
                except (ValueError, IOError):
                    pass

            return method.MethodData(ans[0], ans[1], ans[2])

        return Parser.__parse_file(ans)

    @staticmethod
    def __parse_file(filename: str) -> method.MethodData | None:
        try:
            with open(filename, 'r') as file:
                ans = list(map(to_float, file.readline().split(' ')))
                return method.MethodData(ans[0], ans[1], ans[2])
        except (FileNotFoundError, IsADirectoryError):
            print('No such file')
        except (ValueError, IOError):
            print('Invalid file format. Make sure you have 3 float numbers in one line like \'1.3 2 3\'')
