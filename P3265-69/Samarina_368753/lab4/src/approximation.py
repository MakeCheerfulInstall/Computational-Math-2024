from valida_input import inpit_pairs_selection, try_to_convert_to_int
from output import output_data
from sympy import symbols, Eq, solve
import math


def approximation():

    pairs = inpit_pairs_selection()
    linear_answer = linear_approximation(pairs)
    sqr_answer = sqr_approximation(pairs)
    cubic_answer = cubic_approximation(pairs)
    log_answer = log_approximation(pairs)
    exp_answer = exp_approximation(pairs)
    gradual_answer = gradual_approximation(pairs)

    answers = []
    try:
        answers.append(linear_answer)
    except TypeError:
        pass
    try:
        answers.append(sqr_answer)
    except TypeError:
        pass
    try:
        answers.append(cubic_answer)
    except TypeError:
        pass
    try:
        answers.append(log_answer)
    except TypeError:
        pass
    try:
        answers.append(exp_answer)
    except TypeError:
        pass
    try:
        answers.append(gradual_answer)
    except TypeError:
        pass
    output_data(
        pairs,
        answers,
        linear_answer,
        sqr_answer,
        cubic_answer,
        log_answer,
        exp_answer,
        gradual_answer,
    )


def linear_approximation(pairs):
    try:
        s_x = 0
        s_y = 0
        s_xx = 0
        s_xy = 0
        for pair in pairs:
            x = pair[0]
            y = pair[1]
            s_x += x
            s_y += y
            s_xx += x * x
            s_xy += x * y
        x_svg = s_x / len(pairs)
        y_svg = s_y / len(pairs)
        b = (s_y - s_x * s_xy / s_xx) / (len(pairs) - s_x * s_x / s_xx)
        a = (s_xy - s_x * b) / s_xx

        compare = []
        fi = []
        pi = []
        compare_sqr = 0
        for pair in pairs:
            temp = a * pair[0] + b
            fi.append(temp)
            e = temp - pair[1]
            temp2 = e / pair[1]
            pi.append(temp2)
            compare.append(e)
            compare_sqr += e * e
        tmp_top = 0
        tmp_bottom_left = 0
        tmp_bottom_right = 0
        for pair in pairs:
            tmp_top += (pair[0] - x_svg) * (pair[1] - y_svg)
            tmp_bottom_left += (pair[0] - x_svg) ** 2
            tmp_bottom_right += (pair[1] - y_svg) ** 2
        pirson = tmp_top / (tmp_bottom_left * tmp_bottom_right) ** 0.5
        S2 = (compare_sqr / len(pairs)) ** 0.5
        # print(f"P1(x) = {a}x + {b}\nS = {compare_sqr} Pirson = {pirson}")
        return [S2, compare_sqr, f"P1(x) = {a}x + {b}", fi, compare, pirson, pi, a, b]
    except ZeroDivisionError:
        print("There are nums < 0, cannot solve  linear approximation")


def sqr_approximation(pairs):
    try:
        s_x = 0
        s_y = 0
        s_xx = 0
        s_xy = 0
        s_xxx = 0
        s_xxxx = 0
        s_xxy = 0
        for pair in pairs:
            x = pair[0]
            y = pair[1]
            s_x += x
            s_y += y
            s_xx += x * x
            s_xy += x * y
            s_xxx += x * x * x
            s_xxxx += x * x * x * x
            s_xxy += x * x * y

        x, y, z = symbols("x y z")
        eq1 = Eq(len(pairs) * x + s_x * y + s_xx * z, s_y)
        eq2 = Eq(s_x * x + s_xx * y + s_xxx * z, s_xy)
        eq3 = Eq(s_xx * x + s_xxx * y + s_xxxx * z, s_xxy)
        solution = solve((eq1, eq2, eq3), (x, y, z))

        a2 = try_to_convert_to_int(solution[x])
        a1 = try_to_convert_to_int(solution[y])
        a0 = try_to_convert_to_int(solution[z])
        fi = []
        pi = []
        compare = []
        compare_sqr = 0
        for pair in pairs:
            temp = a2 + a1 * pair[0] + a0 * pair[0] * pair[0]
            fi.append(temp)
            e = temp - pair[1]
            temp2 = e / pair[1]
            pi.append(temp2)
            compare.append(e)
            compare_sqr += e * e
        S2 = (compare_sqr / len(pairs)) ** 0.5
        return [
            S2,
            compare_sqr,
            f"P1(x) = {a0}x^2 + {a1}x + {a2}",
            fi,
            compare,
            pi,
            a0,
            a1,
            a2,
        ]
    except ZeroDivisionError:
        print("There are nums < 0, cannot solve  linear approximation")


def cubic_approximation(pairs):
    s_x = 0
    s_y = 0
    s_xx = 0
    s_yy = 0
    s_xy = 0
    s_xxx = 0
    s_xxy = 0
    s_xyy = 0
    s_xxxx = 0
    s_xxyy = 0

    for pair in pairs:
        x = pair[0]
        y = pair[1]
        s_x += x
        s_y += y
        s_xx += x * x
        s_yy += y * y
        s_xy += x * y
        s_xxx += x * x * x
        s_xxy += x * x * y
        s_xyy += x * y * y
        s_xxxx += x * x * x * x
        s_xxyy += x * x * y * y

    x, y, z, w = symbols("x y z w")
    eq1 = Eq(len(pairs) * w + s_x * x + s_xx * y + s_xxx * z, s_y)
    eq2 = Eq(s_x * w + s_xx * x + s_xxx * y + s_xxxx * z, s_xy)
    eq3 = Eq(s_xx * w + s_xxx * x + s_xxxx * y + s_xxyy * z, s_xxy)
    eq4 = Eq(s_xxx * w + s_xxxx * x + s_xxyy * y + s_xyy * z, s_xxx)

    solution = solve((eq1, eq2, eq3, eq4), (w, x, y, z))

    # Extract coefficients
    a3 = try_to_convert_to_int(solution[w])
    a2 = try_to_convert_to_int(solution[x])
    a1 = try_to_convert_to_int(solution[y])
    a0 = try_to_convert_to_int(solution[z])

    fi = []
    compare = []
    compare_sqr = 0
    for pair in pairs:
        temp = a3 + a2 * pair[0] + a1 * pair[0] ** 2 + a0 * pair[0] ** 3
        fi.append(temp)
        e = temp - pair[1]
        compare.append(e)
        compare_sqr += e * e

    S2 = (compare_sqr / len(pairs)) ** 0.5

    return [
        S2,
        compare_sqr,
        f"P1(x) = {a0}x^3 + {a1}x^2 + {a2}x + {a3}",
        fi,
        compare,
        a0,
        a1,
        a2,
        a3,
    ]


def log_approximation(pairs):
    try:
        s_x = 0
        s_y = 0
        s_xx = 0
        s_xy = 0
        for pair in pairs:
            x = math.log(pair[0])
            y = pair[1]
            s_x += x
            s_y += y
            s_xx += x * x
            s_xy += x * y
        x_svg = s_x / len(pairs)
        y_svg = s_y / len(pairs)
        b = (s_y - s_x * s_xy / s_xx) / (len(pairs) - s_x * s_x / s_xx)
        a = (s_xy - s_x * b) / s_xx

        compare = []
        fi = []
        pi = []
        compare_sqr = 0
        for pair in pairs:
            temp = a * math.log(pair[0]) + b
            fi.append(temp)
            e = temp - pair[1]
            temp2 = e / pair[1]
            pi.append(temp2)
            compare.append(e)
            compare_sqr += e * e
        tmp_top = 0
        tmp_bottom_left = 0
        tmp_bottom_right = 0
        for pair in pairs:
            tmp_top += (math.log(pair[0]) - x_svg) * (pair[1] - y_svg)
            tmp_bottom_left += (math.log(pair[0]) - x_svg) ** 2
            tmp_bottom_right += (pair[1] - y_svg) ** 2
        pirson = tmp_top / (tmp_bottom_left * tmp_bottom_right) ** 0.5
        S2 = (compare_sqr / len(pairs)) ** 0.5

        return [
            S2,
            compare_sqr,
            f"P1(x) = {a}ln(x) + {b}",
            fi,
            compare,
            pirson,
            pi,
            a,
            b,
        ]
    except ValueError:
        print("There are nums < 0, cannot solve logarithmic approximation")


def exp_approximation(pairs):
    try:
        s_x = 0
        s_y = 0
        s_xx = 0
        s_xy = 0
        for pair in pairs:
            x = pair[0]
            y = math.log(pair[1])
            s_x += x
            s_y += y
            s_xx += x * x
            s_xy += x * y
        x_svg = s_x / len(pairs)
        y_svg = s_y / len(pairs)
        b = (s_y - s_x * s_xy / s_xx) / (len(pairs) - s_x * s_x / s_xx)
        a = (s_xy - s_x * b) / s_xx

        compare = []
        fi = []
        pi = []
        compare_sqr = 0
        for pair in pairs:
            temp = a * pair[0] + b
            fi.append(temp)
            e = temp - math.log(pair[1])
            temp2 = e / math.log(pair[1])
            pi.append(temp2)
            compare.append(e)
            compare_sqr += e * e
        tmp_top = 0
        tmp_bottom_left = 0
        tmp_bottom_right = 0
        for pair in pairs:
            tmp_top += (pair[0] - x_svg) * (math.log(pair[1]) - y_svg)
            tmp_bottom_left += (pair[0] - x_svg) ** 2
            tmp_bottom_right += (math.log(pair[1]) - y_svg) ** 2
        pirson = tmp_top / (tmp_bottom_left * tmp_bottom_right) ** 0.5
        S2 = (compare_sqr / len(pairs)) ** 0.5
        a = round(a, 5)
        return [
            S2,
            compare_sqr,
            f"P1(x) = {a}x + ln({b})",
            fi,
            compare,
            pirson,
            pi,
            a,
            b,
        ]
    except ValueError:
        print("There are nums < 0, cannot solve logarithmic approximation")
    except ZeroDivisionError:
        print("There are 0 in solution, cannot solve exp approximation")


def gradual_approximation(pairs):
    try:
        s_x = 0
        s_y = 0
        s_xx = 0
        s_xy = 0
        for pair in pairs:
            x = math.log(pair[0])
            y = math.log(pair[1])
            s_x += x
            s_y += y
            s_xx += x * x
            s_xy += x * y
        x_svg = s_x / len(pairs)
        y_svg = s_y / len(pairs)
        b = (s_y - s_x * s_xy / s_xx) / (len(pairs) - s_x * s_x / s_xx)
        a = (s_xy - s_x * b) / s_xx

        compare = []
        fi = []
        pi = []
        compare_sqr = 0
        for pair in pairs:
            temp = a * math.log(pair[0]) + b
            fi.append(temp)
            e = temp - math.log(pair[1])
            temp2 = e / math.log(pair[1])
            pi.append(temp2)
            compare.append(e)
            compare_sqr += e * e
        tmp_top = 0
        tmp_bottom_left = 0
        tmp_bottom_right = 0
        for pair in pairs:
            tmp_top += (math.log(pair[0]) - x_svg) * (math.log(pair[1]) - y_svg)
            tmp_bottom_left += (math.log(pair[0]) - x_svg) ** 2
            tmp_bottom_right += (math.log(pair[1]) - y_svg) ** 2
        pirson = tmp_top / (tmp_bottom_left * tmp_bottom_right) ** 0.5
        S2 = (compare_sqr / len(pairs)) ** 0.5
        a = round(a, 5)
        return [
            S2,
            compare_sqr,
            f"P1(x) = {a}ln(x) + {b}",
            fi,
            compare,
            pirson,
            pi,
            a,
            b,
        ]
    except ValueError:
        print("There are nums < 0, cannot solve logarithmic approximation")
    except ZeroDivisionError:
        print("There are 0 in solution, cannot solve gradual approximation")
