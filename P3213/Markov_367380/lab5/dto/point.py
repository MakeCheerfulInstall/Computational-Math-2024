class Point:
    def __init__(self, x: float, y: float):
        self.x: float = x
        self.y: float = y

    def __eq__(self, other):
        if isinstance(other, Point):
            return self.x == other.x
        return False

    def __lt__(self, other):
        if isinstance(other, Point):
            return self.x < other.x
        return NotImplemented

    def __le__(self, other):
        if isinstance(other, Point):
            return self.x <= other.x
        return NotImplemented

    def __gt__(self, other):
        if isinstance(other, Point):
            return self.x > other.x
        return NotImplemented

    def __ge__(self, other):
        if isinstance(other, Point):
            return self.x >= other.x
        return NotImplemented
