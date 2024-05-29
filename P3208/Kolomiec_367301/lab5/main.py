from lab5.funcs.function import Function
from lab5.managers.func_manager import FuncManager
from lab5.managers.method_manager import MethodManager
from lab5.methods.method import Lagranzh, NewtonSeparated, NewtonFinal
from utils import form

method_manager = MethodManager(
    [Lagranzh("Метод Лагранжа"),
     NewtonSeparated("Метод Ньютона с разделенными разностями"),
     NewtonFinal("Метод Ньютона с конечными разностями")]
)

func_manager: FuncManager = FuncManager(
    [Function([0, 0, 1]),
     Function([1, -2, 3, -4]),
     Function([4, -1, 0, 1]),
     Function([-10, 7, -3, 1]),
     Function([24, -5, -2, 1])]
)

while True:
    dots = form.input_x_y(form.select_mode(), func_manager)
    arg = form.input_arg()
    method_manager.start_all(dots, arg)
    method_manager.draw_graphic(dots)
