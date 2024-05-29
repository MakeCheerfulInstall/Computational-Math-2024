from lab5.funcs.function import Function


class FuncManager:

    def __init__(self, storage: list[Function]) -> None:
        super().__init__()
        self.storage = storage

    def add_func(self, func: Function):
        self.storage.append(func)

    def get_func(self) -> Function:
        return self.storage[self.input_variant() - 1]

    def input_variant(self):
        variants = [func.to_string() for func in self.storage]
        while True:
            try:
                for i in range(len(variants)):
                    print(f"{i + 1}. {variants[i]}")
                number = int(input("Введите номер функции: "))

                if number not in range(1, len(variants) + 1):
                    print("Такого номера нет. Попробуйте снова!")
                    continue
                return number
            except ValueError:
                print("Не могу понять вас. Попробуйте снова!")
