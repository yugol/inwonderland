#!/usr/bin/python
"""        turtle-example-suite:

            tdemo_planet.py

Gravitational system simulation using the
approximation method from Feynman-lectures,
p.9-8, using turtlegraphics

Example: heavy central body, light planet,
Note the (small) movement of the sun!

Demostrates two features of the xturtle
module:
(1) use of "compound turtle shapes"-
planets consist of two semicircles of
different colour.
(2) Vec2D class (derived from tuple) allows
for a very concise formulation of the orbit
calculation algorithm. (Note that methods
like pos() return vectors.)
"""
from jturtle import Screen, Turtle, Shape, mainloop, Vec2D as Vec
from time import sleep

mS = 2.e30
mE = 6.e24
mM = 7.35e22

G = 6.67e-11
rE = 1.5e11
rM = 3.85e8

vE = 3.e4
vM = 1.e3
DT = 7200.

f2 = 1.e10      # 2.e11 ... volles System

class GravSys(object):
    def __init__(self):
        self.planets = []
        self.dt = DT
    def init(self):
        for p in self.planets:
            p.init()
    def start(self):
        for i in range(10000):
            s.tracer(False)
            for p in self.planets:
                p.step()
            x,y = self.planets[1].pos()
            s.setworldcoordinates(x-f2,y-f2,
                                  x+f2,y+f2)
            s.update()
            s.tracer(True)
            
class Star(Turtle):
    def __init__(self, m, x, v, gravSys, shape):
        Turtle.__init__(self, shape)
        #self.resizemode("user")
        gravSys.planets.append(self)
        self.gravSys = gravSys
        self.dt = self.gravSys.dt
        self.penup()
        self.m = m
        self.setpos(x)
        self.vel = v
        self.pendown()
    def init(self):
        self.vel = self.vel + 0.5*self.dt*self.acc()
    def acc(self):
        a = Vec(0,0)
        for planet in self.gravSys.planets:
            if planet != self:
                r = planet.pos()-self.pos()
                a += (G*planet.m/abs(r)**3)*r
        return a
    def step(self):
        self.setpos(self.pos() + self.dt*self.vel)
        if self != sun:
            self.setheading(self.towards(sun))
        self.vel = self.vel + self.dt*self.acc()

## create compound yellow/blue turtleshape for planets
## yellow semicircle will point towards the sun
def createPlanetShape():
    s.tracer(0,0)
    t = Turtle()
    t.ht()
    t.pu()
    t.fd(6)
    t.lt(90)
    t.begin_poly()
    t.circle(6, 180)
    t.end_poly()
    m1 = t.get_poly()
    t.begin_poly()
    t.circle(6,180)
    t.end_poly()
    m2 = t.get_poly()

    planetshape = Shape("compound")
    planetshape.addcomponent(m1,"orange")
    planetshape.addcomponent(m2,"blue")
    s.register_shape("planet", planetshape)
    s.tracer(True,0)
    

def main():
    global s, sun
    s = Screen()
    s.setup(500, 500, 50, 50)
    s.screensize(450, 450)
    createPlanetShape()
    ## setup gravitational system
    s.setworldcoordinates(-2.e11, -2.e11, 2.e11, 2.e11)
    gs = GravSys()
    sun = Star(mS, Vec(0.,0.), Vec(0.,0.), gs, "circle")
    sun.color("yellow")
    sun.turtlesize(1.8)
    sun.pu()
    earth = Star(mE, Vec(rE,0.), Vec(0.,vE), gs, "planet")
    earth.pencolor("green")
    earth.shapesize(0.8)
    moon = Star(mM, Vec(rE+rM,0.), Vec(0.,vE+vM), gs, "planet")
    moon.pencolor("blue")
    moon.shapesize(0.5)
    gs.init()
    gs.start()
    return "Done!"

if __name__ == '__main__':
    msg = main()
    print msg
#    mainloop()

