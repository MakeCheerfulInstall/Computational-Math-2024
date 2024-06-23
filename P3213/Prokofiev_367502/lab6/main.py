import selector as select

def run():
    print("Численное дифференцирование!")
    while True:
        try:
            print('\n', "Выберите метод дифференцирования или выход.")
            print(
                  '\t', "1. Усовершенствованный метод Эйлера", '\n',
                  '\t', "2. Метод Эйлера", '\n',
                  '\t', "3. Метод Милна", '\n',
                  '\t', "4. Выход!")
            choice = int(input("Введите номер действия: ").strip())

            if choice == 1:
                new_input = select.Input(1)
            elif choice == 2:
                new_input = select.Input(2)
            elif choice == 3:
                new_input = select.Input(3)
            elif choice == 4:
                print('Удачи!')
                break
            else:
                print("Неправильный ввод!")
                continue
            del new_input
        except ValueError:
            print("Неправильный ввод!")
        except KeyboardInterrupt:
            return

if __name__ == "__main__":
    run()
