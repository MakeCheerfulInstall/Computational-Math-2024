from lab4.model.function import Function
from lab4.utils.form import input_variant


class FuncManager:

    def __init__(self, storage: list[Function]) -> None:
        super().__init__()
        self.storage = storage

    def add_func(self, func: Function):
        self.storage.append(func)

    def get_func(self):
        return self.storage[input_variant([func.to_string() for func in self.storage], "Введите номер функции: ") - 1]
