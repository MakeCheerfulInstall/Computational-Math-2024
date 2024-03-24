from equation_roots import EquationRoots


class Validator:
    @staticmethod
    def validate_equation_type(given_equation_type: str) -> bool:
        try:
            return 1 <= int(given_equation_type) <= 3
        except ValueError:
            return False

    @staticmethod
    def validate_system_type(given_equation_type: str) -> bool:
        try:
            return 1 <= int(given_equation_type) <= 2
        except ValueError:
            return False

    @staticmethod
    def validate_method_type(given_method_type: str) -> bool:
        try:
            return 1 <= int(given_method_type) <= 3
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
    def validate_start(given_start: str, equation_type: int) -> bool:
        if equation_type > 1:
            try:
                float(given_start.replace(",", "."))
                return True
            except ValueError:
                return False
        else:
            return True

    @staticmethod
    def validate_area(equation_type: int, left: float, right: float, start: float | None) -> bool:
        roots: list = EquationRoots.get_roots(equation_type)
        count: int = 0
        for root in roots:
            if left <= root <= right:
                count += 1
            if count >= 2:
                return False
        if count != 1:
            return False

        return start == None or left <= start <= right


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
