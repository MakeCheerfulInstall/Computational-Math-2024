from abc import abstractmethod
from typing import Final, Any, TextIO

from P3208.Terekhin_367558.lab1.exceptions import ParsingError
from functions import Describable


class AbstractReader(Describable):
    def __init__(self, description: str):
        super().__init__(description)

    def check_interval(self, a: float, b: float, bounds: list[float]) -> int:
        cnt: int = 0
        for i in range(len(bounds)):
            if a <= bounds[i] <= b:
                cnt += 1
        return cnt

    @abstractmethod
    def read_data(self, bounds: list[float]) -> tuple[float, float, float]:
        pass

    @abstractmethod
    def read_point(self) -> tuple[float, float, float]:
        pass


class ConsoleReader(AbstractReader):
    def __init__(self, description: str):
        super().__init__(description)

    def read_first_approx(self, bounds: list[float]) -> tuple[float, float]:
        print('Input first approximation interval using two numbers:')
        while True:
            try:
                a, b = map(float, input().split(' '))
                a, b = min(a, b), max(a, b)
                cnt: int = self.check_interval(a, b, bounds)
                if cnt > 1:
                    raise IndexError('Interval contains more than one answer')
                if cnt == 0:
                    raise IndexError('There is no answer for this interval')
                return a, b
            except (ValueError, IndexError) as e:
                print(e)
                print('Try again: ')

    def read_precision(self) -> float:
        print('Input precision:')
        while True:
            try:
                eps: float = float(input())
                if eps <= 0 or eps > 1:
                    raise ValueError('Precision is a positive float less then 1')
                return eps
            except ValueError as e:
                print(e)
                print('Try again: ')

    def read_data(self, bounds: list[float]) -> tuple[float, float, float]:
        a, b = self.read_first_approx(bounds)
        return a, b, self.read_precision()

    def read_point(self) -> tuple[float, float, float]:
        print('Input approximation point using x and y coordinates:')
        while True:
            try:
                x, y = map(float, input().split(' '))
                break
            except ValueError as e:
                print(e)
                print('Try again: ')
        eps: float = self.read_precision()
        return x, y, eps


class FileReader(AbstractReader):
    def __init__(self, description: str) -> None:
        super().__init__(description)
        self.file: TextIO | Any = None

    def read_file_name(self) -> None:
        print('Enter file name with extension:')
        while True:
            try:
                filename: str = input()
                self.file = open(filename, "r")
                break
            except FileNotFoundError:
                print('No such file. Try again:')

    def read_first_approx(self, bounds: list[float]) -> tuple[float, float]:
        try:
            a, b = map(float, self.file.readline().split(' '))
            a, b = min(a, b), max(a, b)
            cnt: int = self.check_interval(a, b, bounds)
            if cnt > 1:
                raise IndexError('Interval contains more than one answer')
            if cnt == 0:
                raise IndexError('There is no answer for this interval')
            return a, b
        except (ValueError, IndexError) as e:
            raise ParsingError(str(e))

    def read_coordinates(self) -> tuple[float, float]:
        try:
            a, b = map(float, self.file.readline().split(' '))
            return a, b
        except ValueError as e:
            raise ParsingError(str(e))

    def read_precision(self) -> float:
        try:
            eps: float = float(self.file.readline())
            if eps <= 0 or eps > 1:
                raise ValueError('Precision should be a positive float less then 1')
            return eps
        except ValueError as e:
            raise ParsingError(str(e))

    def read_data(self, bounds: list[float]) -> tuple[float, float, float]:
        while True:
            try:
                self.read_file_name()
                a, b = self.read_first_approx(bounds)
                return a, b, self.read_precision()
            except ParsingError as e:
                print(e)
                print('Try another file')

    def read_point(self) -> tuple[float, float, float]:
        while True:
            try:
                self.read_file_name()
                a, b = self.read_coordinates()
                return a, b, self.read_precision()
            except ParsingError as e:
                print(e)
                print('Try another file')


READERS: Final[list[AbstractReader]] =\
    [ConsoleReader('From console'), FileReader('From file')]
