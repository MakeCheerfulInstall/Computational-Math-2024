def lagrange(x, xarr, yarr, n):
    res = 0
    for i in range(n + 1):
        numerator = 1
        denominator = 1
        for j in range(n + 1):
            if j != i:
                denominator *= (xarr[i] - xarr[j])
                numerator *= (x - xarr[j])
        res += (numerator / denominator) * yarr[i]
    return res
