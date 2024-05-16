from enum import Enum


class ResponseType(Enum):
    LAGRANGE_INTERPOLATION = "Полином Лагранжа"
    NEWTON_DIVIDED_INTERPOLATION = "Полином Ньютона с разделенными разностями"
    NEWTON_END_INTERPOLATION = "Полином Ньютона с конечными разностями"
    STIRLING_INTERPOLATION = "Многочлен Стирлинга"
    BESSEL_INTERPOLATION = "Многочлен Бесселя"

