from P3208.Terekhin_367558.lab1.main import get_precision
from P3208.Terekhin_367558.lab2.main import request_from_list
from P3208.Terekhin_367558.lab2.readers import ConsoleReader, AbstractReader
from P3208.Terekhin_367558.lab3.integrals import INTEGRALS, Integral
from P3208.Terekhin_367558.lab3.methods import Method, METHODS

if __name__ == '__main__':
    reader: AbstractReader = ConsoleReader('Read integral')
    integral: Integral = request_from_list(INTEGRALS)
    b_lim, t_lim, precision = reader.read_tuple('Input integral limits using two numbers: ')
    method: Method = request_from_list(METHODS)
    method.set_function(integral.function)
    ans: float = method.calculate_integral(b_lim, t_lim, precision)
    print('Calculated answer is: ', round(ans, get_precision(precision)))
