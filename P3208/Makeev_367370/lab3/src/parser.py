from enum import Enum
from typing import Callable, Tuple

from integral import Integral, IntegralType
from utils import to_float


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
    def choose_integral() -> Integral:
        return Parser.__choose_enum(IntegralType, 'function for integral')

    @staticmethod
    def set_interval() -> Tuple[float, float]:
        while True:
            str_int = input('Set interval a b -> ')
            try:
                intv = list(map(to_float, str_int.split(' ')))
                if len(intv) != 2:
                    print('Expected 2 arguments')
                    continue
                if intv[0] >= intv[1]:
                    print('a should be less than b')
                    continue

                return intv
            except ValueError:
                print('Expected float numbers')
