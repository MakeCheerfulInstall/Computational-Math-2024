from dto import PointTable, Point, ApproxRes
from utils import to_float


class Parser:
    @staticmethod
    def parse_table_from_file(filename: str) -> PointTable | None:
        try:
            x_line: list[float]
            y_line: list[float]
            with open(filename, 'r') as f:
                x_line = list(map(to_float, f.readline().strip().split(' ')))
                y_line = list(map(to_float, f.readline().strip().split(' ')))

            if len(x_line) != len(y_line):
                print('Length of X and Y are not equals')
                return None

            if len(x_line) < 8 or len(x_line) > 12:
                print('Number of points should be between 8 and 12')
                return None

            points: list[Point] = []
            for i in range(len(x_line)):
                points.append(Point(x_line[i], y_line[i]))

            return PointTable(points)
        except (IOError, ValueError):
            print('File not exist or invalid')

    @staticmethod
    def print_res(res: ApproxRes, filename: str) -> None:
        with open(filename, 'w') as file:
            file.write(str(res))
