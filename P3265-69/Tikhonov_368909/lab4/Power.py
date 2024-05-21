import numpy as np
from Equations import *


def power(xarr, yarr, n):
    # MHK
    x = np.array(xarr)
    y = np.array(yarr)
    coeffs = np.polyfit(np.log(x), np.log(y), 1)
    a = np.exp(coeffs[1])
    b = coeffs[0]

    parr = []
    earr = []
    for i in range(n):
        parr.append(round(a * pow(xarr[i], b), 4))
        earr.append(round(parr[i] - yarr[i], 4))

    return parr, earr, SKO(parr, yarr, n), determination(parr, yarr, n)
