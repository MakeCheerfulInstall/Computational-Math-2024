def newt_method(f, df, x0, eps, max_iter=1000):
    prev = x0
    for i in range(max_iter):
        dfx = df(prev)
        fx = f(prev)
        if dfx == 0:
            raise ValueError("Производная равна нулю. Невозможно продолжить.")
        x = prev - fx / dfx
        if abs(x - prev) <= eps or abs(f(x)) <= eps:
            return x, f(x), i+1
        prev = x
    raise ValueError("Iterations > max")
