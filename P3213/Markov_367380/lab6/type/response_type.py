from enum import Enum


class ResponseType(Enum):
    EULER_METHOD = "Метод Эйлера"
    RUNGE_KUTT_METHOD = "Метод Рунге-Кутта 4- го порядка"
    ADAMS_METHOD = "Метод Адамса"

