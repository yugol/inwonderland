#!/usr/bin/python
"""       turtle-example-suite:

            tdemo_curlicues.py


Three examples of Ian Stewart's curlicues
from "Another Fine Math You've Got Me Into ..." 
(Dover Publications), separated by 2 second
intervals.

Uses as Python specific features function-
objects as arguments and long int arithmetics
(in warthog: 1000**7).

A curlicue has three arguments: a function,
which determines the Pen'S heading in the n-th
step, a length the turtle travels in each step
and the number of steps. 
"""
from math import *
from jturtle import *
from time import sleep, clock

def stier(n):
    return ((n**3)%1013)/1013.

def wunder(n):
    return ((n**3)%1002)/1002.

def warthog(n):
    return ((n**7)%1050)/1050.

def curlicue(f, dl, n):
    pu(); setpos(0,-85); pd()
    for i in range(n):
        setheading(f(i)*360)
        fd(dl)

def main():
    t = 0
    for args in ((stier,   16, 1013),
                 (wunder,  13, 1002),
                 (warthog, 68, 1050)):
        reset()
        tracer(50)
        speed(0)
        hideturtle()
        a = clock()
        curlicue(*args)
        tracer(1)
        b = clock()
        t += b-a
        if args[0]!=warthog:
            sleep(2)
    return "computing time: %.2f" % t

if __name__ == '__main__':
    main()
    mainloop()

## on my machine: 1.1 sec
