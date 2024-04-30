from equation_function_x import EquationFunctionX
from equation_function import EquationFunction
from equation_function_derivative import EquationFunctionDerivative
from typing import Callable


class Equation:
    def __init__(self, equation_type: int):
        self.__equation_type = equation_type
        self.__function = EquationFunction.get_function(equation_type)
        self.__dxfunction = EquationFunctionDerivative.get_function(equation_type)
        self.__xfunction = EquationFunctionX.get_function(equation_type)

    def get_function(self) -> Callable:
        return self.__function

    def get_dxfunction(self) -> Callable:
        return self.__dxfunction

    def get_xfunction(self) -> Callable:
        return self.__xfunction
