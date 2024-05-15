from lab4.methods.avaliable.cubic import Cubic
from lab4.methods.avaliable.exp import Exp
from lab4.methods.avaliable.linal import Linal
from lab4.methods.avaliable.logarithmic import Logarithmic
from lab4.methods.avaliable.power import Power
from lab4.methods.avaliable.square import Square
from managers.method_manager import MethodManager
from utils.form import *

method_manager = MethodManager(
    [Linal("Линейная аппроксимация", "a * x + b"),
     Square("Квадратичная аппроксимация", "a * x^2 + b * x + c"),
     Cubic("Кубическая аппроксимация", "a * x^3 + b * x^2 + c * x + d"),
     Exp("Экспоненциальная функция", "a * e ^ (b * x)"),
     Power("Степенная функция", "a * x ^ b"),
     Logarithmic("Логарифмическая функция", "a * ln(x) + b")]
)

while True:
    dots = input_x_y(select_mode())
    method_manager.draw_all_results(dots)
