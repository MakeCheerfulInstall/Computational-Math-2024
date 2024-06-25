from Equations import *
import numpy as np


def polynomial2(xarr, yarr, n):

    # MHK
    x = np.array(xarr)
    y = np.array(yarr)
    coeffs = np.polyfit(x, y, 2)
    a0 = coeffs[2]
    a1 = coeffs[1]
    a2 = coeffs[0]

    parr = []
    earr = []
    for i in range(n):
        parr.append(round(a0 + a1 * xarr[i] + a2 * (xarr[i] ** 2), 4))
        earr.append(round(parr[i] - yarr[i], 4))

    return parr, earr, SKO(parr, yarr, n), determination(parr, yarr, n)
