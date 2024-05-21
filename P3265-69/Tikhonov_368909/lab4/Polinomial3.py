import numpy as np
from Equations import *


def polynomial3(xarr, yarr, n):
    # MHK
    x = np.array(xarr)
    y = np.array(yarr)
    coeffs = np.polyfit(x, y, 3)
    a0 = coeffs[3]
    a1 = coeffs[2]
    a2 = coeffs[1]
    a3 = coeffs[0]

    parr = []
    earr = []
    for i in range(n):
        parr.append(round(a0 + a1 * xarr[i] + a2 * (xarr[i] ** 2) + a3 * (xarr[i] ** 3), 4))
        earr.append(round(parr[i] - yarr[i], 4))

    return parr, earr, SKO(parr, yarr, n), determination(parr, yarr, n)
