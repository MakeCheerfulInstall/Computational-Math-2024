from equation_system import EquationSystem
from equation_system_derivative import EquationSystemDerivative


class System:
    def __init__(self, system_type: int):
        self.system_type = system_type
        self.__functions = EquationSystem.get_system(system_type)
        self.__dxfunctions = EquationSystemDerivative.get_system(system_type)

    def get_functions(self) -> tuple:
        return self.__functions

    def get_dxfunctions(self) -> tuple:
        return self.__dxfunctions

