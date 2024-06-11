from methods import linear
from utils import *


def aproximate(points):
    _points = copy.deepcopy(points)

    for i in range(len(_points)):
        _points[i][0] = m.log(points[i][0], m.e)

    koofs = linear.aproximate(_points).get_koofs()
    return Function(koofs, FunctionType.logarithm)
