import math

from enum import Enum
from typing import Callable


class Function2DxEnum(Enum):
    FIRST = lambda x: math.log(x)
    SECOND = lambda x: math.sqrt(x)

    @staticmethod
    def get_function(function_type: int) -> Callable:
        match function_type:
            case 1:
                return Function2DxEnum.FIRST
            case 2:
                return Function2DxEnum.SECOND
