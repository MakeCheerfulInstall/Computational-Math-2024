def proiz_at_point(func, x, eps=1e-6):
     return (func(x + eps) - func(x - eps)) / (2 * eps)
    


#Метод хорд
def horde_method(f, a, b, eps=10e-3):
    x0 = a
    
    for i in range(1, 1000):
        x1 = a - ((b-a)/(f(b) - f(a))) * f(a)
        if abs(x1 - x0) <= eps or abs(f(x1)) <= eps:
            return x1
            break
        if f(x1) * f(x0) < 0:
            b = x1
        else:
            a = x1

        x0 = x1
        
        

def newton_method(f, df, x0, eps=10e-3):
    
    for i in range(1, 1000):
        x1 = x0 - f(x0) / df(x0)

        if (
            abs(x1 - x0) <= eps or
            abs(f(x1) / df(x1)) <= eps or
            abs(f(x1)) <= eps
        ):
            return x1

        x0 = x1


def simple_iteration_method(f, x0, a, b, eps):
    m_f = 0
    x = a
    while x < b:
        m_f = max(m_f, abs(proiz_at_point(f, x)))
        x = x + eps
    if proiz_at_point(f, a) > 0:
        h = -1/m_f
    else:
        h = 1/m_f
    phi = lambda x: x + h * f(x)
    phi_ = lambda x: 1 + р * proiz_at_point(f, x)
    
    x = phi(x0)
    while abs(x-x0) > eps:
        x, x0 = phi(x), x
    return x


    