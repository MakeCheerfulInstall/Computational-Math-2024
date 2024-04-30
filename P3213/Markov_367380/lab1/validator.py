class Validator:
    @staticmethod
    def validate_dimension(given_dimension: str) -> bool:
        try:
            return 1 <= int(given_dimension) <= 20
        except ValueError:
            return False

    @staticmethod
    def validate_precision(given_precision: str) -> bool:
        try:
            return round(float(given_precision.replace(",", ".")), 9) > 0
        except ValueError:
            return False

    @staticmethod
    def validate_input(input_string: str) -> list:
        try:
            validated_row = list(map(lambda x: round(float(x.replace(",", ".")), 3), input_string.split()))
            return validated_row
        except ValueError:
            return []

    @staticmethod
    def safe_input(prompt: str = ""):
        try:
            user_input = input(prompt)
            return user_input
        except EOFError:
            print("An unexpected EOF occurred.")
            exit(0)

