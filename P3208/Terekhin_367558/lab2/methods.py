from typing import Final, Callable


def dichotomy_method(a: float, b: float, eps: float) -> float:
    pass


def secant_method(a: float, b: float, eps: float) -> float:
    pass


def simple_iteration_method(a: float, b: float, eps: float) -> float:
    pass


def newton_method(a: float, b: float, eps: float) -> float:
    pass


METHODS: Final[list[tuple[Callable[[float, float, float], float], str]]] = \
    [(dichotomy_method, 'Dichotomy method'),
     (secant_method, 'Secants method'),
     (simple_iteration_method, 'Simple iterations method'),
     (newton_method, "Newton's method")]
METHOD_REQUEST: str = ''
for ind in range(len(METHODS)):
    METHOD_REQUEST += f"{ind + 1}. {METHODS[ind][1]}\n"
METHOD_REQUEST += 'Choose method: '
