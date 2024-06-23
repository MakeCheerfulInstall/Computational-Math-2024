def hord_method(f, a, b, eps, max_iter = 1000):
    prev = float("inf")
    for i in range(max_iter):
        fa, fb = f(a), f(b)
        x = (a * fb - b * fa) / (fb - fa)
        fx = f(x)
        if abs(x - prev) <= eps and abs(fx) <= eps:
            return x, fx, i+1
        if fa * fx < 0:
            b = x
        else:
            a = x
        prev = x
    raise ValueError("Iterations > max")
