import selector as select


def run():
    while True:
        try:
            print('\n', "Выберите функцию или выход (цифра):")
            print(
                  '\t', "1: x^2 - 3", '\n',
                  '\t', "2: 5/x - 2x", '\n',
                  '\t', "3: e^(2x) - 2", '\n',
                  '\t', "4: 2x^3 - 3x^2 + 5x - 9", '\n',
                  '\t', "5: Выход")

            choice = int(input("Вариант: ").strip())
            if choice == 1:
                new_input = select.Input(1)
                del new_input
                continue
            elif choice == 2:
                new_input = select.Input(2)
                del new_input
                continue
            elif choice == 3:
                new_input = select.Input(3)
                del new_input
                continue
            elif choice == 4:
                new_input = select.Input(4)
                del new_input
                continue
            elif choice == 5:
                print('Выход...')
                break
            else:
                print("Неправильный ввод!")
                continue
        except TypeError:
            print("Неправильный ввод!")
            continue
        except ValueError:
            continue
        except KeyboardInterrupt:
            return


if __name__ == "__main__":
    run()
