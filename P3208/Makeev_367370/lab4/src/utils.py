def to_float(val: str) -> float:
    return float(val.replace(',', '.'))


def avg(data: list[float]) ->float:
    return sum(data) / len(data)