from non_lineare_quation import *
from non_lineare_system import *
while True:
    try:
        print("Choose the variant:")
        input_variant = int(
            input(
                "1) Non-linear equation\n2) System of non-linear equations\n"
            )
        )
        if input_variant in range(1, 3):
            break
        print("Invalid variant")
        continue
    except ValueError:
        print("Incorrect value entered")
        continue

switch_command = {
    1: non_lineare_quation,
    2: non_lineare_system,
    3: exit,
}
switch_command.get(input_variant, exit)()