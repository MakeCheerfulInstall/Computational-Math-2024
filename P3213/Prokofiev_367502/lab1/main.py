def exitstr():
    print("Неверный ввод")


def trytoint(x):
    a = 0
    try:
        a = int(x)
    except Exception:
        a = None
    return a


def trytofloat(x):
    a = 0
    try:
        a = float(x)
    except Exception:
        a = None
    return a


def output_k(X, N, delta, k):
    global roundconst
    print(f"После k = {k}:")
    output(X, N)
    print(f"delta =", round(delta, roundconst))
    print()


def output(X, N, rounds = False):
    global roundconst
    if isinstance(X, str):
        print(X)
    else:
        for i in range(N):
            x = X[i]
            if rounds:
                x = round(x, roundconst) #для вывода округляем
            print(f"X[{i+1}] = {x}")


def max_to_diagonal(A, B, N):
    for i in range(N):
        max_val = max(A[i], key=abs)  # Находим максимальное значение в строке по модулю
        max_index = A[i].index(max_val)  # Находим индекс этого значения
        if max_index != i:  # Если максимум не на диагонали, то меняем строки местами
            A[i], A[max_index] = A[max_index], A[i]
            B[i], B[max_index] = B[max_index], B[i]

    #проверка диагонализации
    strog = False
    for i in range(N):
        Sum = 0
        for j in range(N):
            if (i != j):
                Sum += A[i][j]
        if A[i][i] < Sum:
            return False
        elif A[i][i] > Sum:
            strog = True # для одной строки хотя бы должно быть строго
    return strog


def gauss_zeidel(N, A, B, eps, M, k = 0, X = []):
    global roundconst
    delta = 0
    if (X == []): # 0 итерация
        X = [0]*N
        diagonalize = max_to_diagonal(A, B, N)
        if not diagonalize:
            return "Не удалось диагонализировать"
        #начальное приближение
        for i in range(N):
            X[i] = B[i]/A[i][i]
        output_k(X, N, delta, k)
        k = 1
    
    for i in range(N):
        s = 0
        for j in range(i): # до i-1
            s += A[i][j]*X[j]
        for j in range(i+1,N):# c i+1 до конца
            s += A[i][j]*X[j]
        x = (B[i] - s)/ A[i][i]
        d = abs(x - X[i])
        if d > delta:
            delta = d
        X[i] = x

    output_k(X, N, delta, k)

    if delta < eps:
        return X
    else:
        if (k < M):
            return gauss_zeidel(N, A, B, eps, M, k + 1, X)
        else:
            return "Итерации расходятся"


def run():
    global roundconst
    print("""
    N ‒ порядок матрицы
    𝜀 ‒ погрешность вычислений
    A𝑖𝑗 ,B𝑖 ‒ коэффициенты и правые части уравнений системы
    X𝑖 ‒ начальные приближения
    М ‒ максимально допустимое число итераций
    k ‒ порядковый номер итерации;
    i ‒ номер уравнения, а также переменного, которое вычисляется в соответствующем цикле;
    j ‒ номер элемента вида A𝑖𝑗X𝑗 (𝑘) или A𝑖𝑗X𝑗 (𝑘−1) в правой части соотношения.

    Итерационный процесс прекращается либо при выполнения условия:
    𝐦𝐚𝐱 |X𝑖(𝑘) − X𝑖 (𝑘−1)| < 𝜺,
    𝟏≤𝒊≤𝒏

    либо при K = M, т.е. итерации не сходятся

    """)

    roundconst = 3 #trytoint(input("Точность вывода (количество знаков после запятой) = "))
    eps = trytofloat(input("Eps="))
    while eps is None:
        exitstr()
        eps = trytofloat(input("Eps="))
    M = trytoint(input("M="))
    while M is None:
        exitstr()
        M = trytoint(input("M="))
    N = trytoint(input("N="))
    while N is None:
        exitstr()
        N = trytoint(input("N="))

    A = []
    for i in range(N):
        A.append([])
        for j in range(N):
            A[i].append(j)
            A[i][j] = trytofloat(input(f"A[{i+1}][{j+1}] = "))
            while A[i][j] is None:
                exitstr()
                A[i][j] = trytofloat(input(f"A[{i+1}][{j+1}] = "))

    B = []
    for i in range(N):
        B.append(trytofloat(input(f"B[{i+1}] = ")))
        while B[i] is None:
            exitstr()
            B[i] = trytofloat(input(f"B[{i+1}] = "))
    print()
    print()
    X = gauss_zeidel(N, A, B, eps, M)
    print()
    print()
    output(X, N, True)


if __name__ == "__main__":
    run()
