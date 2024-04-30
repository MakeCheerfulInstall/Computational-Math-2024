from typing import Callable
from function_enum import FunctionEnum
from function2_enum import Function2Enum
from function2dx_enum import Function2DxEnum


class Function:
    def __init__(self, function_type: int):
        self.__function_type = function_type
        self.__function = FunctionEnum.get_function(function_type)

    def get_function(self) -> Callable:
        return self.__function


class Function2:
    def __init__(self, function_type: int):
        self.__function_type = function_type
        self.__function = Function2Enum.get_function(function_type)
        self.__functiondx = Function2DxEnum.get_function(function_type)

    def get_function(self) -> Callable:
        return self.__function

    def get_functiondx(self) -> Callable:
        return self.__functiondx
