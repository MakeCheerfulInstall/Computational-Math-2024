from dto import *
from utils import to_float
from enum import Enum
from dto import *

DEFAULT_FILE_IN = 'test'
DEFAULT_FILE_OUT = 'output'


class Parser:
    @staticmethod
    def __choose_enum(enum: Enum, name: str):
        counter = 1
        enums = []
        for obj in enum:
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
    def choose_input_type() -> InputType:
        return Parser.__choose_enum(InputType, 'input type')

    @staticmethod
    def get_def_func_points() -> PointTable | None:
        val: FunctionDto = Parser.__choose_enum(DefFunctions, 'function')
        try:
            start, end, acc = list(map(float, input('Input start of interval, end and number of points -> ').split(' ')))
            return val.create_func_points(start, end, int(acc))
        except ValueError:
            print("418 I'm a teapot")

    @staticmethod
    def parse_table_from_file(filename: str | None) -> PointTable | None:
        if filename is None or filename == '':
            filename = DEFAULT_FILE_IN

        try:
            with open(filename, 'r') as f:
                x_line: list[float] = list(map(to_float, f.readline().strip().split(' ')))
                y_line: list[float] = list(map(to_float, f.readline().strip().split(' ')))

                return PointTable.init(x_line, y_line)
        except (IOError, ValueError):
            print('File not exist or invalid')

    @staticmethod
    def parse_table_from_cmd() -> PointTable | None:
        try:
            x_line: list[float] = list(map(to_float, input().split(' ')))
            y_line: list[float] = list(map(to_float, input().split(' ')))

            return PointTable.init(x_line, y_line)
        except ValueError:
            print("418 I'm a teapot")
