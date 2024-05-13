from Kolomiec_367301.lab3.utils.form import *
from function import Function
from managers.func_manager import FuncManager
from managers.method_manager import MethodManager
from methods.rectangle import Rectangle
from methods.simpson import Simpson
from methods.trapezoid import Trapezoid

run = True
method_manager: MethodManager = MethodManager(
    [Rectangle("Метод Прямоугольника"),
     Trapezoid("Метод Трапеции"),
     Simpson("Метод Симпсон")]
)
func_manager: FuncManager = FuncManager(
    [Function([0, 0, 1]),
     Function([1, -2, 3, -4]),
     Function([4, -1, 0, 1]),
     Function([-10, 7, -3, 1]),
     Function([24, -5, -2, 1])]
)

while run:
    epsilon = input_epsilon()
    method_number = input_variant(method_manager.get_methods_names(), "Введите номер метода: ") - 1
    method_manager.start_calculate(method_number, func_manager.get_func(), epsilon)
