from Equations import *


def linear(xarr, yarr, n):
    # МНК
    delta = SA2(xarr) * n - SA(xarr) ** 2
    delta1 = SAB(xarr, yarr) * n - SA(xarr) * SA(yarr)
    delta2 = SA2(xarr) * SA(yarr) - SA(xarr) * SAB(xarr, yarr)
    a, b = delta1 / delta, delta2 / delta

    parr = []
    earr = []
    for i in range(n):
        parr.append(round(a * xarr[i] + b, 4))
        earr.append(round(parr[i] - yarr[i], 4))

    return parr, earr, __corellation(xarr, yarr, n), SKO(parr, yarr, n), determination(parr, yarr, n)


def __corellation(xarr, yarr, n):
    xavg, yavg = SA(xarr) / n, SA(yarr) / n
    r_numerator, r_denominator = 0, 0

    xd, yd = 0, 0
    for i in range(n):
        r_numerator += (xarr[i] - xavg) * (yarr[i] - yavg)
        xd += (xarr[i] - xavg) ** 2
        yd += (yarr[i] - yavg) ** 2
    r_denominator = math.sqrt(xd * yd)
    return round(r_numerator / r_denominator, 4)
