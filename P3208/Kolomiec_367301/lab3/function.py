class Function:
    def __init__(self, func: list[float]) -> None:
        super().__init__()
        self.func = func

    def get_ordinate(self, x):
        result = 0
        for i in range(len(self.func)):
            result += self.func[i] * x ** i
        return result

    def to_string(self):
        result = []
        equation = self.func
        for i in range(len(equation) - 1, -1, -1):
            if equation[i] == 1:
                result.append(f"x^{i}")
                continue
            if equation[i] == 0:
                continue
            if i == 0:
                result.append(str(equation[i]))
                break
            result.append(f"{equation[i]} * x^{i}")
        return " + ".join(result)
