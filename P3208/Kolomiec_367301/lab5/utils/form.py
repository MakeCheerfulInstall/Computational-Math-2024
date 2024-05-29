from enum import Enum

from lab5.managers.func_manager import FuncManager


class Mode(Enum):
    FILE = 1
    CONSOLE = 2
    FUNC = 3


def input_x_y(file_mode: Mode, func_manager: FuncManager):
    while True:
        try:
            if file_mode == Mode.FILE:
                path = input("Введите путь до файла: ")
                file = open(path)
                #  file = open("C:\\Users\\mad_duck\\PycharmProjects\\itmo\\CompMath\\Computational-Math-2024\\P3208\\"
                #    "Kolomiec_367301\\lab5\\tests\\test_final_table")
                x, y = [[float(i) for i in line.split(" ")] for line in file]
            else:
                x = [float(i) for i in input("Введите значения х через пробел: ").strip().split(" ")]
                if file_mode == Mode.CONSOLE:
                    y = [float(i) for i in input("Введите значения y через пробел: ").strip().split(" ")]

                    if len(x) != len(y):
                        print("Не могу составить пары (x, y). Попробуйте снова!")
                        continue
                else:
                    func = func_manager.get_func()
                    y = func.get_all_ordinates(x)
            return x, y
        except FileNotFoundError:
            print("Не могу найти файл. Попробуйте снова!")
        except ValueError:
            print("Не могу считать данные. Попробуйте снова!")
        except OSError:
            print("Что-то пошло не так. Попробуйте снова!")


def input_arg():
    while True:
        try:
            epsilon = float(input("Введите аргумент: "))
            return epsilon
        except ValueError:
            print("Не могу понять вас. Попробуйте снова!")


def select_mode():
    while True:
        try:
            mode = input(
                "1. Файл режим\n2. Консольный режим\n3. Посчитать ординату по функции\nВыберите номер варианта: ")
            if mode not in "123":
                print("Нет такого варианта. Попробуйте снова!")
                continue
            return Mode(int(mode))
        except ValueError:
            print("Не могу понять вас. Попробуйте снова!")
