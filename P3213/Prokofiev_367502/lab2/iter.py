def iter_method(f, phi, x0, eps, max_iterations=1000):
    x = x0
    for i in range(max_iterations):
        x_new = phi(x)
        if abs(x_new - x) <= eps:
            return x_new, f(x_new), i+1
        x = x_new
    raise ValueError("Iterations > max")


def iter_method_system(f1, f2, x0, y0, eps, max_iterations=1000):
    x_prev, y_prev = x0, y0
    for i in range(max_iterations):
        x_new = f1(x_prev, y_prev)
        y_new = f2(x_prev, y_prev)
        if abs(x_new - x_prev) <= eps and abs(y_new - y_prev) <= eps:
            return x_new, y_new, i+1

        x_prev, y_prev = x_new, y_new
    raise ValueError("Iterations > max")
