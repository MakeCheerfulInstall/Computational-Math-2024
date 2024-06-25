# Метод Гаусса (прямой ход)

def gauss_(aa, bb):
    n = len(aa)
    sgn = 1
    for r in range(n):  # r - номер опорной строки
        z = aa[r][r]  # опорный элемент
        # перебор всех строк, расположенных ниже r
        if abs(z) < 1.0e-10:  # ноль на диагонали
            # ищем ненулевой элемент ниже
            for j in range(r + 1, n):
                if abs(aa[j][r]) > 1.0e-10:
                    for jj in range(r, n):
                        aa[j][jj], aa[r][jj] = aa[r][jj], aa[j][jj]
                    bb[j], bb[r] = bb[r], bb[j]
                    z = aa[r][r]
                    sgn = -sgn
                    break
            else:
                return None
        for i in range(r + 1, n):
            q = aa[i][r] / z
            for j in range(n):
                aa[i][j] = aa[i][j] - aa[r][j] * q
            bb[i] = bb[i] - bb[r] * q
    return (aa, bb, sgn)


# Вычисление главного определителя

def det_tri(a, sgn=1):
    n = len(a)
    p = sgn
    for i in range(n):
        p = p * a[i][i]
    return p


# Метод Гаусса (обратный ход)

def rev_calc(a, b):
    n = len(b)
    res = [0 for _ in range(n)]
    i = n - 1
    res[i] = b[i] / a[i][i]
    i = i - 1
    while (i >= 0):
        s = b[i]
        for j in range(i + 1, n):
            s = s - a[i][j] * res[j]
        res[i] = s / a[i][i]
        i = i - 1
    return res  