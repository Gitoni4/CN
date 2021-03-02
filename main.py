import math
import random
import time


def ex1():
    m = -1
    u = 10 ** m

    while 1.0 + u != 1.0:
        m -= 1
        u = 10 ** m

    return u * 10


def ex2():
    u = ex1()
    a = 1.0
    b = u / 10
    c = u / 10

    return (a + b) + c != a + (b + c)


def ex2b():
    a = 10
    b = ex1()
    c = 0.1

    return (a * b) * c != a * (b * c)


def periodic(x):
    while x > math.pi / 2 or x < - math.pi / 2:
        if x > 0:
            x -= math.pi
        elif x < 0:
            x += math.pi

    return x


def tanL(x, e):
    if x / math.pi / 2 == int(x / math.pi / 2):
        return "Not defined"

    if x > math.pi / 2 or x < - math.pi / 2:
        x = periodic(x)

    mic = 10 ** -12
    f = mic
    C = f
    D = 0
    b = 1
    a = x

    D = b + a * D
    if D == 0:
        D = mic
    C = b + a / C
    if C == 0:
        C = mic
    D = 1 / D
    d = C * D
    f = d * f
    b += 2
    a = (x ** 2) * -1

    while abs(d - 1) < e:
        D = b + a * D
        if D == 0:
            D = mic
        C = b + a / C
        if C == 0:
            C = mic
        D = 1 / D
        d = C * D
        f = d * f
        b += 2

    return f


def tanP(x):
    if x / math.pi / 2 == int(x / math.pi / 2):
        return "Not defined"

    if x > math.pi / 2 or x < - math.pi / 2:
        x = periodic(x)

    c1 = 1 / 3
    c2 = 2 / 15
    c3 = 17 / 315
    c4 = 62 / 2835

    x_2 = x ** 2
    x_3 = x * x_2
    x_4 = x_2 * x_2
    x_6 = x_2 * x_4

    tan = x + x_3 * (c1 + c2 * x_2 + c3 * x_4 + c4 * x_6)

    return tan


def gen():
    i = 1
    while i <= 10000:
        x = random.uniform((-1) * math.pi / 2, math.pi / 2)
        semn = random.random()

        if semn == 0:
            x *= -1

        i += 1

        if tanL(x, 10 ** -11) != "Not defined":
            print("Pentru x = pi /", math.pi / x, "-> tanL =", tanL(x, 10 ** -11),
                  "valoare corecta =", math.tan(x), "->", abs(math.tan(x) - tanL(x, 10 ** -11)))
        else:
            print("Pentru valoarea : ", x, "tangenta nu este definita")

        if tanP(x) != "Not defined":
            print("Pentru x = pi /", math.pi / x, "-> tanP =", tanP(x),
                  "valoare corecta =", math.tan(x), "->", abs(math.tan(x) - tanP(x)))
        else:
            print("Pentru valoarea : ", x, "tangenta nu este definita")


start_time = time.time()
gen()
print(ex1())
print(ex2())
print(ex2b())
execution_time = (time.time() - start_time)
print("Timpul de executie a fost de :", execution_time)
