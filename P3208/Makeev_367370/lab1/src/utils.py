import re


def is_size_valid(size_val: str) -> bool:
    if re.findall(r'^\d{1,2}$', size_val):
        if 0 < int(size_val) <= 20:
            return True

    return False


def to_float(val: str) -> float:
    return float(val.replace(',', '.'))
