from enum import Enum
from typing import Callable

from integral import Integral, IntegralType
from method import Method, MethodList
from utils import to_float
from dto import Interval


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
    def choose_method() -> Method:
        return Parser.__choose_enum(MethodList, 'solving method')

    @staticmethod
    def set_interval() -> Interval:
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

                return Interval(intv[0], intv[1])
            except ValueError:
                print('Expected float numbers')

    @staticmethod
    def set_epsilon() -> float:
        while True:
            eps = input('Set accuracy -> ')
            try:
                return to_float(eps)
            except ValueError:
                print('Expected float number')
