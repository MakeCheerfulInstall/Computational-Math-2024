def get_file_data():
    data = []
    input_path = "data.txt"

    with open(input_path, encoding="UTF-8") as file:
        try:
            for point in file:
                current_point = tuple(map(float, point.strip().split()))
                if len(current_point) != 2:
                    raise Exception
                data.append(current_point)
        except:
            return None
    return data


def get_console_data():
    data = []
    try:
        string_count = int(input("Введите количество точек: "))
        print("Введите координаты через пробел построчно")
        for i in range(string_count):
            point = input()
            current_point = tuple(map(float, point.strip().split()))
            if len(current_point) != 2:
                raise ValueError
            data.append(current_point)
    except:
        return None

    return data


def get_data():
    print("1 - считать из файла\n2 - считать посредством ввода с консоли")
    while True:
        try:
            get_data_type = int(input("Введите тип считываения: "))
            if get_data_type == 1:
                in_data = get_file_data()
            elif get_data_type == 2:
                in_data = get_console_data()
            else:
                print("Нет такого типа считываения")
            if in_data != None:
                break
            else:
                print("Не получилось корректно считать данные")
        except:
            print("Повторите тип счтывания данных")
    return in_data
