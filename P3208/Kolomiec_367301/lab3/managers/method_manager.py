from Kolomiec_367301.lab3.methods.method import Method


class MethodManager:

    def __init__(self, storage: list[Method]) -> None:
        super().__init__()
        self.storage = storage

    def add_to_storage(self, method: Method):
        self.storage.append(method)

    def start_calculate(self, method_number: int, func, epsilon):
        self.storage[method_number].calculate(func, epsilon)

    def get_methods_names(self):
        return [method.name for method in self.storage]
