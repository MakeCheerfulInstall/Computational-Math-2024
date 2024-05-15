def input_x_y(file_mode: bool):
    while True:
        try:
            if file_mode:
                # path = input("Введите путь до файла: ")
                # file = open(path)
                file = open(
                    "C:\\Users\\mad_duck\\PycharmProjects\\itmo\\CompMath\\Computational-Math-2024\\P3208\\" + \
                    "Kolomiec_367301\\lab4\\tests\\test_final_table")
                x, y = [[float(i) for i in line.split(" ")] for line in file]
            else:
                x = [float(i) for i in input("Введите значения х через пробел: ").strip().split(" ")]
                y = [float(i) for i in input("Введите значения y через пробел: ").strip().split(" ")]

            if len(x) != len(y):
                print("Не могу составить пары (x, y). Попробуйте снова!")
                continue

            return x, y
        except FileNotFoundError:
            print("Не могу найти файл. Попробуйте снова!")
        except ValueError:
            print("Не могу считать данные. Попробуйте снова!")
        except OSError:
            print("Что-то пошло не так. Попробуйте снова!")


def select_mode():
    while True:
        mode = input("1. Файл режим\n2. Консольный режим\nВыберите номер варианта: ")
        if mode not in "12":
            print("Нет такого варианта. Попробуйте снова!")
            continue
        return mode == "1"
