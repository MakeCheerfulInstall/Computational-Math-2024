from P3213.Markov_367380.lab5.interpolation.interpolation import Interpolation
from P3213.Markov_367380.lab5.interpolation.lagrange_interpolation import LagrangeInterpolation
from P3213.Markov_367380.lab5.interpolation.newton_divided_interpolation import NewtonDividedInterpolation
from P3213.Markov_367380.lab5.interpolation.newton_end_interpolation import NewtonEndInterpolation
from P3213.Markov_367380.lab5.interpolation.stirling_interpolation import StirlingInterpolation
from P3213.Markov_367380.lab5.interpolation.bessel_interpolation import BesselInterpolation


interpolations: list[Interpolation] = [
    LagrangeInterpolation(),
    NewtonDividedInterpolation(),
    NewtonEndInterpolation(),
    StirlingInterpolation(),
    BesselInterpolation(),
]
