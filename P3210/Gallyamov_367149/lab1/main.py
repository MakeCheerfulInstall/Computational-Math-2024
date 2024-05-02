from random import randint


def get_random_matrix(n):
    mn = -1000
    mx = 1000
    a = [
        [randint(mn, mx) for  __ in range(n)]
        for _ in range(n)
    ]
    b = [randint(mn, mx) for  __ in range(n)]
    return a, b
    

def get_data():
    random_word = 'RANDOM'

    sn = input('введите размерность матрицы (n): ').strip()
    while not sn.isdigit() or int(sn) < 1 or int(sn) > 20:
        print('-' * 50)
        print('n должно быть числом на отрезке [1;20]')
        sn = input('введите размерность матрицы (n): ').strip()
        
    n = int(sn)
    
    rw = input(f'введите "{random_word}" для генерации рандомной матрицы (A и B): ')
    if rw == 'RANDOM':
        a, b = get_random_matrix(n)
        print('Сгенерированная матрица: ')
        for i in range(n):
            print(*a[i], '|', b[i], sep='\t')
        print('-' * 50)
    else:    
        print('введите элементы матрицы A:')
        a = []
        for i in range(n):
            fl = False
            while not fl:
                try:
                    print(f'Введите строчку ({i}) (n чисел): ')
                    cur = list(map(float, input().split()))
                    assert len(cur) == n
                    fl = True
                except Exception:
                    pass  
            a.append(cur)
            
        print('введите элементы матрицы B:')
        fl = False
        while not fl:
            try:
                print('Введите строчку (n чисел): ')
                b = list(map(float, input().split()))
                assert len(b) == n
                fl = True
            except Exception:
                pass  
        
    return n, a, b


def get_data_from_file():
    with open('input.txt') as f:
        n = int(f.readline())
        f.readline()
        a = [
            list(map(float, f.readline().split()))
            for _ in range(n)
        ]
        f.readline()
        b = list(map(float, f.readline().split()))
        return n, a, b


def get_determinant(triangular_matrix, k):
    res = 1
    for i in range(len(triangular_matrix)):
        res *= triangular_matrix[i][i]
    return (-1) ** k * res


def get_solution_and_k(a, b):
    x = [None] * n
    k_ = 0
    
    for i in range(0, n - 1):
        while a[i][i] == 0:
            a = a[:i] + a[i + 1:] + [a[i]]
            b = b[:i] + b[i + 1:] + [b[i]]
            k_ += 1
        
        for k in range(i + 1, n):
            c = a[k][i] / a[i][i]
            a[k][i] = 0
            for j in range(i + 1, n):
                a[k][j] = a[k][j] - c * a[i][j]
            b[k] = b[k] - c * b[i]
        
    
    for i in range(n - 1, -1, -1):
        s = 0
        for j in range(i + 1, n):
            s = s + a[i][j] * x[j]
        x[i] = (b[i] - s) / a[i][i]

    return x, k_, a, b


def get_r(a, b, x):
    n = len(a)
    r = [0] * n
    for i in range(n):
        for j in range(n):
            r[i] += a[i][j] * x[j]
        r[i] -= b[i]
    return r


n, a, b = get_data_from_file()

first_a = [el[:] for el in a]
first_b = b[:]

x, k, a, b = get_solution_and_k(a, b)

print('Определитель: ', get_determinant(a, k))

print('Треугольная матрица: ')

for i in range(n):
    print(*a[i], '|', b[i], sep='\t')
print('-' * 50)

print('Вектор неизвестных: ', *map(lambda xi: round(xi, 5), x))

print('Вектор невязок: ', *get_r(first_a, first_b, x))
