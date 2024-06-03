from enum import Enum


class ResponseType(Enum):
    LINNEAR_APPROXIMATOR = "Линейная аппроксимация"
    SQUARE_APPROXIMATOR = "Квадратичная аппроксимация"
    CUBIC_APPROXIMATOR = "Кубическая аппроксимация"
    EXPONENTIAL_APPROXIMATOR = "Экспоненциальная аппроксимация"
    LOGARITHMIC_APPROXIMATOR = "Логарифмическая аппроксимация"
    POWER_APPROXIMATOR = "Степенная аппроксимация"

