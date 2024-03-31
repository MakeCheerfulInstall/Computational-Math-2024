import math
from abc import abstractmethod
from typing import Final, Callable

from functions import Describable, Function


class Method(Describable):
    option_name = 'method'

    def __init__(self, description: str) -> None:
        super().__init__(description)
        self.step: int = 0
        self.prev: float = math.inf
        self.eps: float = 0.01
        self.b: float = 10
        self.a: float = -10
        self.func: Function | None = None

    @abstractmethod
    def execute(self) -> tuple[float, int, float]:
        pass

    def set_arguments(self, func: Function, a: float, b: float, eps: float) -> None:
        self.func = func
        self.a = a
        self.b = b
        self.eps = eps


class DichotomyMethod(Method):
    def __init__(self) -> None:
        super().__init__('Dichotomy method')

    def execute(self) -> tuple[float, int, float]:
        self.step = 0
        self.prev = 0
        if self.func is not None:
            ans: float = self.do_iteration(self.func.func)
            return ans, self.step, self.func.func(ans)
        else:
            raise TypeError('Function is not defined')

    def do_iteration(self, func: Callable[[float], float]) -> float:
        self.step += 1
        x: float = (self.a + self.b) / 2
        if abs(x - self.prev) < self.eps:
            return x
        self.prev = x
        if func(x) * func(self.a) < 0:
            self.b = x
            return self.do_iteration(func)
        else:
            self.a = x
            return self.do_iteration(func)


class NewtonMethod(Method):
    def __init__(self) -> None:
        super().__init__("Newton's method")

    def execute(self) -> tuple[float, int, float]:
        self.step = 0
        if self.func is not None:
            self.set_first_approx(self.func)
            ans: float = self.do_iteration(self.func)
            return ans, self.step, self.func.func(ans)
        else:
            raise TypeError('Function is not defined')

    def do_iteration(self, func: Function):
        self.step += 1
        x: float = self.prev - func.func(self.prev) / func.first_derivation(self.prev)
        if abs(x - self.prev) < self.eps:
            return x
        self.prev = x
        return self.do_iteration(func)

    def set_first_approx(self, func: Function) -> None:
        while True:
            if func.func(self.a) * func.second_derivation(self.a) > 0:
                self.prev = self.a
                break
            elif func.func(self.b) * func.second_derivation(self.b) > 0:
                self.prev = self.b
                break
            else:
                x: float = (self.a + self.b) / 2
                self.step += 1
                if func.func(self.a) * func.func(x) < 0:
                    self.b = x
                else:
                    self.a = x


class SecantsMethod(NewtonMethod):
    def __init__(self) -> None:
        super().__init__()
        self.description = 'Secants Method'
        self.pprev: float = math.inf

    def execute(self) -> tuple[float, int, float]:
        self.step = 0
        if self.func is not None:
            self.set_first_approx(self.func)
            ans: float = self.do_iteration(self.func)
            return ans, self.step, self.func.func(ans)
        else:
            raise TypeError('Function is not defined')

    def do_iteration(self, func: Function):
        self.step += 1
        x: float = (self.prev - (self.prev - self.pprev) / (func.func(self.prev) - func.func(self.pprev))
                    * func.func(self.prev))
        if abs(x - self.prev) < self.eps:
            return x
        self.prev = x
        return self.do_iteration(func)

    def set_first_approx(self, func: Function) -> None:
        super().set_first_approx(func)
        self.pprev = self.prev + self.eps


class SimpleIterationMethod(Method):
    def __init__(self) -> None:
        super().__init__('Simple Iteration Method')
        self.scale = 100
        self.param: float = 0

    def execute(self) -> tuple[float, int, float]:
        if self.func is not None:
            phi: Callable[[float], float] = lambda x: x + self.param * self.func.func(x)
            if self.check_convergence(self.func):
                ans: float = self.do_iteration(phi)
                return ans, self.step, self.func.func(ans)
            else:
                print('Convergence condition not met. Try to use smaller interval')
                return 0, 0, 0
        else:
            raise TypeError('Function is not defined')

    def do_iteration(self, phi: Callable[[float], float]) -> float:
        self.step += 1
        x: float = phi(self.prev)
        if abs(x - self.prev) < self.eps:
            return x
        self.prev = x
        return self.do_iteration(phi)

    def set_arguments(self, func: Function, a: float, b: float, eps: float) -> None:
        super().set_arguments(func, a, b, eps)
        max_val: float = max([func.first_derivation(x / self.scale) for x in range(int(self.a * self.scale),
                                                                                   int(self.b * self.scale))])
        self.param = 1 / max_val
        if func.first_derivation(self.a) > 0 and func.first_derivation(self.b) > 0:
            self.param *= -1

    def check_convergence(self, func: Function) -> bool:
        phi_derivation: Callable[[float], float] = lambda x: 1 + self.param * func.first_derivation(x)
        self.prev = self.a if phi_derivation(self.a) < phi_derivation(self.b) else self.b
        return max(phi_derivation(self.a), phi_derivation(self.b)) < 1


METHODS: Final[list[Method]] = [DichotomyMethod(), NewtonMethod(), SecantsMethod(), SimpleIterationMethod()]
