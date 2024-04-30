from enum import Enum
from typing import Callable

import method
import equation
from utils import to_float
from equiation_type import EquationType, EquationSystemType


class Parser:
    @staticmethod
    def choose_method(for_sys: bool = False) -> method.Method:
        return Parser.__choose_enum(method.MethodType, 'method', lambda m: m.for_sys == for_sys)

    @staticmethod
    def choose_single_eq() -> equation.Equation:
        return Parser.__choose_enum(EquationType, 'equation')

    @staticmethod
    def __choose_enum(enum: Enum, name: str, cond: Callable = lambda o: True):
        counter = 1
        enums = []
        for obj in enum:
            if cond(obj.value):
                print('\t', counter, ': ', obj.value, sep='')
                counter += 1
                enums.append(obj.value)

        num: str = ''
        rang: range = range(1, counter)
        while True:
            try:
                if int(num) in rang:
                    break
            except ValueError:
                pass
            num = input('Choose ' + name + ': [' + ('/'.join(str(x) for x in rang)) + '] -> ')

        return enums[int(num) - 1]

    @staticmethod
    def choose_system_eq() -> equation.EquationSystem:
        return Parser.__choose_enum(EquationSystemType, 'equation system')

    @staticmethod
    def parse_method_data() -> method.MethodData | None:
        ans: str = input('Parse data for solving (a, b, e) from file? [n/filename] -> ')
        if ans.lower() == 'n':
            while True:
                try:
                    ans = list(map(to_float, input('Input 3 float numbers (a, b, e) joined spaces -> ').split(' ')))
                    if len(ans) == 3:
                        data = method.MethodData(ans[2], ans[0], ans[1])
                        if data.a >= data.b:
                            print('a should be less than b')
                        else:
                            return data
                except (ValueError, IOError):
                    pass

        return Parser.__parse_file(ans)

    @staticmethod
    def __parse_file(filename: str) -> method.MethodData | None:
        try:
            with open(filename, 'r') as file:
                ans = list(map(to_float, file.readline().split(' ')))
                if len(ans) != 3:
                    raise ValueError
                return method.MethodData(ans[2], ans[0], ans[1])
        except (FileNotFoundError, IsADirectoryError):
            print('No such file')
        except (ValueError, IOError):
            print('Invalid file format. Make sure you have 3 float numbers in one line like \'1.3 2 1E-5\'')

    @staticmethod
    def parse_sys_method_data() -> method.MethodData | None:
        ans: str = input('Parse data for solving (a_x, b_x, a_y, b_y, e) from file? [n/filename] -> ')
        if ans.lower() == 'n':
            while True:
                try:
                    ans = list(map(to_float, input('Input 5 float numbers joined spaces -> ').split(' ')))
                    if len(ans) == 5:
                        data = method.MethodData(ans[4], ans[0], ans[1], ans[2], ans[3])
                        if data.a >= data.b or data.a_y >= data.b_y:
                            print('a should be less than b')
                        else:
                            return data
                except (ValueError, IOError):
                    pass

        return Parser.__parse_sys_file(ans)

    @staticmethod
    def __parse_sys_file(filename: str) -> method.MethodData | None:
        try:
            with open(filename, 'r') as file:
                ans = list(map(to_float, file.readline().split(' ')))
                if len(ans) != 5:
                    raise ValueError
                return method.MethodData(ans[4], ans[0], ans[1], ans[2], ans[3])
        except (FileNotFoundError, IsADirectoryError):
            print('No such file')
        except (ValueError, IOError):
            print('Invalid file format. Make sure you have 5 float numbers in one line like \'1.3 2 3 4 1E-5\'')