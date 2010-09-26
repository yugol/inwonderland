#!/usr/bin/python
"""       turtle-example-suite:

          tdemo_pencopydemo.py

Demonstrates copying pens of different
shape types:
(1) polygon
(2) (gif-)image
(3) compound type

plus a little surprise ...
"""

from jturtle import *

def main():
    reset()
    begin_poly()
    fd(10)
    lt(120)
    fd(20)
    end_poly()
    m1 = get_poly()
    lt(120)
    begin_poly()
    fd(20)
    lt(120)
    fd(10)
    end_poly()
    m2=get_poly()
    s= Shape("compound")
    s.addcomponent(m1,"red")
    s.addcomponent(m2,"blue")

    addshape("twocol", s)

    reset()
    ht()

    p = Pen()
    q = p.clone()
    r = q.clone()

    for t in p,q,r:
        t.pu()
        t.pensize(3)
    p.shape("turtle")
    p.left(18)
    q.fd(180)
    r.bk(180)
    r.left(36)

    p.color("red", "yellow")
    q.color("green")
    r.color("violet")

    addshape("huhn01.gif")
    q.shape("twocol")
    r.shape("huhn01.gif")

    turtles = [p,q,r]
    for i in range(4):
        for t in p,q,r:
            c = t.clone()
            c.lt((i+1)*72)
            turtles.append(c)
    for t in turtles:
        t.pd()
        t.fd(80)

    Screen().stats("pencopydemo - clone-part")

    # one more moorhuhn ;-)
    h = Pen()
    h.ht()
    h.pu()
    h.setpos(-500,180)
    addshape("huhn02.gif")
    h.shape("huhn02.gif")
    h.st()
    h.speed(1)
    h.tracer(1,20)
    shape1, shape2 = "huhn01.gif", "huhn02.gif"
    for i in range(120):
        h.fd(12)
        shape2, shape1 = shape1, shape2
        h.shape(shape2)
        
    Screen().stats("pencopydemo - total")
    return "Done!"
    
if __name__ == '__main__':
    msg = main()
    print msg
    mainloop()
