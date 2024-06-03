import numpy as np
from Equations import *


def logarithmic(xarr, yarr, n):
    # MHK
    x = np.array(xarr)
    y = np.array(yarr)
    coeffs = np.polyfit(np.log(x), y, 1)
    a = float(coeffs[1])
    b = float(coeffs[0])

    parr = []
    earr = []
    for i in range(n):
        parr.append(round(a * math.log(xarr[i]) + b, 4))
        earr.append(round(parr[i] - yarr[i], 4))

    return parr, earr, SKO(parr, yarr, n), determination(parr, yarr, n)
