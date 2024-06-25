import unittest
from ..src.main import *


class MyTestCase(unittest.TestCase):
    def test_matrix_valid(self):
        mrx = [
            [3.00001, 4, 5, 1],
            [2, 2, 23, 4],
            [1, 3, 6, 10],
        ]
        self.assertEqual(matrix_valid(mrx), "ok")
        mrx.append([])
        self.assertTrue(matrix_valid(mrx) == "matrix is incorrect! Line 1 is 4 long, expected: 5")
        mrx = [[1 for i in range(21)] for j in range(20)]
        self.assertEqual(matrix_valid(mrx), "ok")
        mrx[2].append(1)
        self.assertEqual(matrix_valid(mrx), "matrix is incorrect! Line 3 is 22 long, expected: 21")
        mrx = [[1 for i in range(22)] for j in range(21)]
        self.assertEqual(matrix_valid(mrx), "matrix too big! Should be n <= 20")

    def test_check_diagonal_dominance(self):
        mrx = [
            [2.9999999999, 1, 4, 1],
            [2, 23, 3, 4],
            [11, 3, 6, 10]
        ]
        out_mrx = [
            [11, 3, 6, 10],
            [2, 23, 3, 4],
            [2.9999999999, 1, 4, 1]
        ]
        res = check_diagonal_dominance(mrx)
        self.assertTrue(res[0])
        self.assertEqual(res[1], out_mrx)
        mrx[0][0] = 3
        res = check_diagonal_dominance(mrx)
        self.assertFalse(res[0])
        self.assertEqual(res[1], mrx)
        mrx = [
            [1, 2, 3],
            [2, 4, 3]
        ]
        res = check_diagonal_dominance(mrx)
        self.assertFalse(res[0])
        self.assertEqual(res[1], mrx)

    def test_simple_iteration(self):
        c = [
            [0, -0.1, -0.1],
            [-0.2, 0, -0.1],
            [-0.2, -0.2, 0]
        ]
        d = [1.2, 1.3, 1.4]
        x = [1.2, 1.3, 1.4]
        res = do_simple_iteration(c, d, x)
        for i in range(len(res)):
            res[i] = round(res[i], 2)
        self.assertEqual(res, [0.93, 0.92, 0.9])

    def test_algo(self):
        mrx = [
            [10, 1, 1, 12],
            [2, 10, 1, 13],
            [2, 2, 10, 14]
        ]
        eps = 0.01
        res = iteration_algo(mrx, eps)
        for i in range(len(res[0])):
            res[0][i] = round(res[0][i], 4)
        for i in range(len(res[1])):
            res[1][i] = round(res[1][i], 4)
        self.assertEqual(res[0], [0.9996, 0.9995, 0.9993])
        self.assertEqual(max(res[1]), 0.0031)
        self.assertEqual(res[2], 5)


if __name__ == '__main__':
    unittest.main()
