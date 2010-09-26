#!/usr/bin/python
"""       xturtle-example-suite:

             xtx_radioactive.py

A simple drawing suitable as a beginner's
programming example.
Therefore the animation is set to slow
by the command speed(1). So you can easily
follow each and every action of the turtle.

Be patient!
"""

from jturtle import *

def square(length):
    for i in range(4):
        forward(length)
        left(90)

def sector(radius, angle):
    forward(radius)
    left(90)
    circle(radius, angle)
    left(90)
    forward(radius)
    left(120)

def move(x, y):
    up()
    forward(x)
    left(90)
    forward(y)
    left(-90)
    down()

def radioactive(radius1, radius2, side,
        angle=60, outlinecol="black", fillcol="yellow"):
    color(outlinecol)
    move(-(side/2.) , -(side/2.))
    
    fill(1)
    square(side)
    color(fillcol)
    fill(0)
    move((side/2.), (side/2.))
    color(outlinecol)
    right(90 + angle/2.)

    for i in range(3):
        fill(1)
        sector(radius1,angle)
        left((360 - 3 * angle)/3 + 60)
        color(outlinecol)
        fill(0)

    up()
    forward(radius2)
    left(90)
    down()

    color(fillcol)
    fill(1)
    circle(radius2)
    color(outlinecol)
    fill(0)

    up()
    left(90)
    forward(radius2)
    width(1)

def main():
    reset()
    width(5)
    speed(1)
    radioactive(160, 30, 400)
    return "Done!"

if __name__ == '__main__':
    msg = main()
    print msg
    mainloop()


