import os


def inpit_pairs_selection():
    while True:
        try:
            print("Choose the quation:")
            input_selection = int(input("1) Hand input \n2) File input\n"))
            if input_selection in range(1, 3):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    switch_command = {
        1: pairs_input_hand,
        2: pairs_input_file,
    }
    pairs = switch_command.get(input_selection, exit)()
    return pairs


def pairs_input_hand():
    pairs = []
    i = 1
    while i <= 12:
        try:
            temp = (
                input(f"Enter pair {i} x and y. For stop enter end:\n")
                .strip()
                .replace(",", ".")
                .split(" ")
            )
            if temp == ["end"] and i >= 9:
                i += 1
                break
            elif temp == ["end"] and i < 9:
                print("Minimum 8 pairs, please add more")
                i += 1
                continue
            x, y = map(float, temp)
            i += 1

        except ValueError:
            print("Incorrect value entered")
            continue
        pair = [x, y]
        pairs.append(pair)
    print(f"Entered {i-1} pairs")

    return pairs


def pairs_input_file():
    current_working_directory = os.path.dirname(__file__)
    file_name = input("Enter the relative path to your file\n")
    print()
    file_path = os.path.join(current_working_directory, file_name)
    with open(file_path, "r", encoding="utf-8") as file:
        # Читаем строки из файла
        lines = file.readlines()

    if len(lines) >= 8 and len(lines) <= 12:
        pairs = []
        for line in lines:
            try:
                var = line.replace(",", ".").split(" ")
                x, y = map(float, var)
                x = try_to_convert_to_int(x)
                y = try_to_convert_to_int(y)
                pair = [x, y]
                pairs.append(pair)
            except ValueError:
                print("Incorrect data in the specified file")
                exit()

            except UnboundLocalError:
                print("Incorrect data in the specified file")
                exit()
            continue

    else:
        print("Uncorrect count of lines in the specified file")
        exit()
    print(f"Readed {len(pairs)} pairs")
    for pair in pairs:
        print(f"pair[{pair[0]}, {pair[1]}]")
    print("\n\n")
    return pairs


def try_to_convert_to_int(number):
    try:
        number_float = float(number)
        if number_float.is_integer():
            return int(number_float)
        else:
            return number_float
    except ValueError:
        return float(number)
