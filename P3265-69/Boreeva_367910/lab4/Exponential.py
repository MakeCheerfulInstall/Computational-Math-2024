import numpy as np
from Equations import *


def exponential(xarr, yarr, n):

    # MHK
    x = np.array(xarr)
    y = np.array(yarr)
    coeffs = np.polyfit(x, np.log(y), 1)
    a = np.exp(coeffs[1])
    b = coeffs[0]

    parr = []
    earr = []
    for i in range(n):
        parr.append(round(a * pow(math.e, b * xarr[i]), 4))
        earr.append(round(parr[i] - yarr[i], 4))

    return parr, earr, SKO(parr, yarr, n), determination(parr, yarr, n)
