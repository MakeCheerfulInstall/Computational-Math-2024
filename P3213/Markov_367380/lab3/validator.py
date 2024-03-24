from typing import Callable

from function import Function2


class Validator:
    @staticmethod
    def validate_function_type(given_function_type: str) -> bool:
        try:
            return 1 <= int(given_function_type) <= 3
        except ValueError:
            return False

    @staticmethod
    def validate_function2_type(given_function2_type: str) -> bool:
        try:
            return 1 <= int(given_function2_type) <= 2
        except ValueError:
            return False

    @staticmethod
    def renew_sqrt_bounds(given_function2: Function2, left: float, right: float) -> tuple:
        a: float = left
        b: float = right
        f: Callable = given_function2.get_function()
        try:
            f(a)
        except ValueError:
            a = 0
        except ZeroDivisionError:
            pass

        try:
            f(b)
        except ValueError:
            b = 0
        except ZeroDivisionError:
            pass

        return a, b

    @staticmethod
    def validate_function2(given_function2: Function2, left: float, right: float, precision: float) -> tuple:
        step: float = precision
        a: float = left
        b: float = right
        is_exists: bool = True
        F: Callable = given_function2.get_functiondx()
        dots: float | None = None
        try:
            F(a)
        except Exception:
            dots = a
            is_exists = not is_exists

        try:
            F(b)
        except Exception:
            dots = b
            is_exists = not is_exists

        current = a + step
        while current < b:
            try:
                F(current)
            except Exception:
                dots = current
                is_exists = False
            current += step
        return is_exists, dots



    @staticmethod
    def validate_method_type(given_method_type: str) -> bool:
        try:
            return 1 <= int(given_method_type) <= 3
        except ValueError:
            return False

    @staticmethod
    def validate_modification(given_modification: str, function_type: int) -> bool:
        try:
            return function_type != 1 or 1 <= int(given_modification) <= 3
        except ValueError:
            return False

    @staticmethod
    def validate_left(given_left: str) -> bool:
        try:
            float(given_left.replace(",", "."))
            return True
        except ValueError:
            return False

    @staticmethod
    def validate_right(given_right: str) -> bool:
        try:
            float(given_right.replace(",", "."))
            return True
        except ValueError:
            return False


    @staticmethod
    def validate_precision(given_left: str) -> bool:
        try:
            return float(given_left.replace(",", ".")) > 0
        except ValueError:
            return False

    @staticmethod
    def safe_input(prompt: str = ""):
        try:
            user_input = input(prompt)
            return user_input
        except EOFError:
            print("An unexpected EOF occurred.")
            exit(0)
