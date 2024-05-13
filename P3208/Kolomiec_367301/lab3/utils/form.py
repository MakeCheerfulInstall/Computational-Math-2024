def input_epsilon():
    while True:
        try:
            epsilon = int(input("Введите количество знаков после запятой: "))
            if epsilon <= 0:
                print("Значение должно быть больше нуля. Попробуйте снова!")
                continue
            return epsilon
        except ValueError:
            print("Не могу понять вас. Попробуйте снова!")


def input_variant(variants: list[str], name: str):
    while True:
        try:
            for i in range(len(variants)):
                print(f"{i + 1}. {variants[i]}")
            number = int(input(name))

            if number not in range(1, len(variants) + 1):
                print("Такого номера нет. Попробуйте снова!")
                continue
            return number
        except ValueError:
            print("Не могу понять вас. Попробуйте снова!")


def input_n():
    while True:
        try:
            n = int(input("Введите количество отрезков n: "))
            if n <= 0:
                print("Значение должно быть больше нуля. Попробуйте снова!")
                continue
            return n
        except ValueError:
            print("Не могу понять вас. Попробуйте снова!")


def input_limits():
    while True:
        try:
            a, b = (float(i) for i in input("Введите границы интегрирования через пробел: ").split(" "))
            return a, b
        except ValueError:
            print("Не могу понять вас. Попробуйте снова!")
