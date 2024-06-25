def newton(x, xarr, yarr, n):
    res = 0
    for i in range(n + 1):
        if i == 0:
            res += yarr[i]
        else:
            narr = []
            for j in range(i + 1):
                narr.append(j)
            tmp = __recurrent(xarr, yarr, i + 1, narr)
            for j in range(i):
                tmp *= (x - xarr[j])
            res += tmp
    return res


def __recurrent(xarr, yarr, k, narr):
    if k == 2:
        return (yarr[narr[-1]] - yarr[narr[0]]) / (xarr[narr[-1]] - xarr[narr[0]])
    else:
        return (__recurrent(xarr, yarr, k - 1, __remfirst(narr)) - __recurrent(xarr, yarr, k - 1, __remlast(narr))) / (
                xarr[narr[-1]] - xarr[narr[0]])


def __remfirst(arr):
    new_array = arr.copy()
    new_array.pop(0)
    return new_array


def __remlast(arr):
    new_array = arr.copy()
    new_array.pop()
    return new_array
