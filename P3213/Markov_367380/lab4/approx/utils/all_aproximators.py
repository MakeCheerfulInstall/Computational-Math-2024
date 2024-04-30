from P3213.Markov_367380.lab4.approx.approximator import Approximator
from P3213.Markov_367380.lab4.approx.linnear_approximator import LinnearApproximator
from P3213.Markov_367380.lab4.approx.cubic_approximation import CubicApproximator
from P3213.Markov_367380.lab4.approx.exponential_approximator import ExponentialApproximator
from P3213.Markov_367380.lab4.approx.logarithmic_approximation import LogarithmicApproximator
from P3213.Markov_367380.lab4.approx.power_approximation import PowerApproximator
from P3213.Markov_367380.lab4.approx.square_approximator import SquareApproximator


approximators: list[Approximator] = [
    LinnearApproximator(),
    SquareApproximator(),
    CubicApproximator(),
    ExponentialApproximator(),
    LogarithmicApproximator(),
    PowerApproximator()
]
