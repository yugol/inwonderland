#!/usr/bin/python
"""        turtle-example-suite:

            tdemo_clickshape.py

Demonstrates
(a) an eventdriven program
(b) using userdefined turtleshapes

- To create a shape click the vertices of
a polygon with right mouse button.

- To make the turtle use this shape for a
short travel, click right mouse button.

Then you might create different shapes ...

 ---------------------------------------
 Play around by clicking into the canvas
 ---------------------------------------
       To exit press STOP button
 ---------------------------------------      
"""

from jturtle import *

def main():
    mode("logo")
    writer = Pen()
    writer.pu()
    writer.ht()
    writer.speed(0)
    writer.goto(-300,-220)
    writer.write("To design a shape click repeatedly left mouse button")
    writer.setx(50)
    writer.write("To use it click right mouse button.")

    begin_poly()
    onscreenclick(goto, 1)
    onscreenclick(go, 3)
    return "EVENTLOOP"


def go(x,y):
    onscreenclick(None,1)
    onscreenclick(None,3)
    end_poly()
    reset()
    s = get_poly()
    s = s[1:]
    if len(s) > 2:
        register_shape("myshape", s)
    shape("myshape")
    resizemode("user")
    color("red", "blue")
    turtlesize(0.5, 0.5, 5)

    delay(0)
    for i in range(360):
        fd(2)
        rt(1)
    delay(40)
    lt(315)
    delay(0)
    for i in range(360):
        fd(2)
        lt(1)
    pencolor("black")
    begin_poly()
    onscreenclick(goto,1)
    onscreenclick(go,3)



if __name__ == '__main__':
    msg = main()
    print msg
    mainloop()

    
