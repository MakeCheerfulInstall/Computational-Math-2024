from dto import *
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
    def choose_diff_ur() -> DiffUrDto:
        return Parser.__choose_enum(DffUrType, 'equation')

    @staticmethod
    def parse_params() -> ParamsDto | None:
        try:
            start, end = list(map(to_float, input('Input start and end of the interval -> ').split(' ')))
            y_0 = to_float(input(f'Input y_0({start}) -> '))
            h_str = input('Input h (length of step) -> ')
            if h_str == '':
                h = (end - start) / 10
            else:
                h = to_float(h_str)

            e_str = input('Input e (accuracy >= 1E-4) -> ')
            if e_str == '':
                e = 0.1
            else:
                e = to_float(e_str)

            if e < 1E-4:
                raise ValueError
            return ParamsDto(
                y_0=y_0,
                interval=(start, end),
                h=h, e=e
            )
        except ValueError:
            print("418 I'm teapot!")
