import math
from solvers import Solver


class Input:
    data = {}
    type_method = 0
    type_task = 0

    h = 0
    acc = 0

    def __init__(self,  type_method):
        self.type_method = type_method
        self.data = {}
        self.type_task = 0

        self.h = 0
        self.acc = 0

        self.select_task()
        self.calculation()

    def select_task(self):
        print('\n', "Выберите задачу Коши:")
        print(
              '\t', "1. y' = y + (1 + x) * y^2, на [1; 2] при y(1) = -1", '\n',
              '\t', "2. y' = x^2 - 2y, на [0; 1] при y(0) = 1", '\n',
              '\t', "3. y' = e^(2x) + y, на [0; 2.5] при y(0) = 1")
        while True:
            try:
                choice = int(input("Задача номер: ").strip())

                if 1 <= choice <= 3:
                    self.data["fun"], self.data["acc_fun"], self.data["a"], self.data["b"], self.data["y0"] = get_task(choice)
                    self.input_h()
                    if self.type_method == 3:
                        self.choose_accuracy()
                    break
                get_ready_answer(4)
            except ValueError:
                get_ready_answer(1)
                continue

    def input_h(self):
        print('\n', "Введите шаг вычислений (Пример: 0.2)")
        while True:
            try:
                self.h = float(input("Шаг вычисления: ").strip())
                if self.h > (self.data["b"] - self.data["a"]) / 4 and self.type_method == 2:
                    get_ready_answer(5)
                    continue
                elif self.h > self.data["b"] - self.data["a"] and self.type_method == 1:
                    get_ready_answer(5)
                    continue
                if self.h <= 0:
                    get_ready_answer(6)
                    continue
                break
            except ValueError:
                get_ready_answer(1)
                continue

    def choose_accuracy(self):
        print('\n', "Введите точность вычислений для Метода Милна")
        while True:
            try:
                self.acc = float(input("Кол-во знаков после запятой: ").strip())
                if self.acc % 1 != 0 or self.acc <= 0:
                    get_ready_answer(2)
                    continue
                break
            except ValueError:
                get_ready_answer(2)
                continue

    def calculation(self):
        while True:
            try:
                calculator = Solver(self.type_method, self.data, self.h, self.acc)
                calculator.solve()
                del calculator
                break
            except TypeError:
                break
            except ValueError:
                break


# Выбор задачи и получение её данных
def get_task(task_id):
    if task_id == 1:
        return lambda x, y: y + (1 + x) * y ** 2, lambda x: -1 / x, 1, 2, -1
    elif task_id == 2:
        return lambda x, y: x ** 2 - 2 * y, lambda x: 0.25 * (2 * x ** 2 - 2 * x + 3 * math.exp(-2 * x) + 1), 0, 1, 1
    elif task_id == 3:
        return lambda x, y: math.exp(2 * x) + y, lambda x: math.exp(2 * x), 0, 2.5, 1
    else:
        return


def get_ready_answer(type_answer):
    answers = {
        1: "Неправильный ввод!",
        2: "Неправильный ввод точности!",
        3: "Неправильный формат данных в файле!",
        4: "Фукнции нет в списке!",
        5: "Слишком большой шаг для промежутка!",
        6: "Шаг вычисления должен быть больше 0!",
    }
    print(answers.get(type_answer, "Неправильный выбор готового ответа!"))
