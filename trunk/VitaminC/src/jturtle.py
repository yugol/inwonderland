#
## 2009-03-02-12:26 :  jturtle_0.70.py

# jturtle.py: a java awt/swing based turtle graphics module for Jython
# 10. 3. 2009
#
# Copyright (C) 2006 - 2009  Gregor Lingl
# email: glingl@aon.at
#
# This software is provided 'as-is', without any express or implied
# warranty.  In no event will the authors be held liable for any damages
# arising from the use of this software.
#
# Permission is granted to anyone to use this software for any purpose,
# including commercial applications, and to alter it and redistribute it
# freely, subject to the following restrictions:
#
# 1. The origin of this software must not be misrepresented; you must not
#    claim that you wrote the original software. If you use this software
#    in a product, an acknowledgment in the product documentation would be
#    appreciated but is not required.
# 2. Altered source versions must be plainly marked as such, and must not be
#    misrepresented as being the original software.
# 3. This notice may not be removed or altered from any source distribution.

# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE
# THIS IS NOT THE ORIGINAL SOURCE


"""
Turtle graphics is a popular way for introducing programming to
kids. It was part of the original Logo programming language developed
by Wally Feurzig and Seymour Papert in 1966.

Imagine a robotic turtle starting at (0, 0) in the x-y plane. Give it
the command turtle.forward(15), and it moves (on-screen!) 15 pixels in
the direction it is facing, drawing a line as it moves. Give it the
command turtle.left(25), and it rotates in-place 25 degrees clockwise.

By combining together these and similar commands, intricate shapes and
pictures can easily be drawn.

----- turtle.py

This module is an extended reimplementation of turtle.py from the
Python standard distribution up to Python 2.5. (See: http:\\www.python.org)

It tries to keep the merits of turtle.py and to be (nearly) 100%
compatible with it. This means in the first place to enable the
learning programmer to use all the commands, classes and methods
interactively when using the module from within IDLE run with
the -n switch.

Roughly it has the following features added:

- Better animation of the turtle movements, especially of turning the
  turtle. So the turtles can more easily be used as a visual feedback
  instrument by the (beginning) programmer.

- Different turtle shapes, gif-images as turtle shapes, user defined
  and user controllable turtle shapes, among them compound
  (multicolored) shapes. Turtle shapes can be stgretched and tilted, which
  makes turtles zu very versatile geometrical objects.

- Fine control over turtle movement and screen updates via delay(),
  and enhanced tracer() and speed() methods.

- Aliases for the most commonly used commands, like fd for forward etc.,
  following the early Logo traditions. This reduces the boring work of
  typing long sequences of commands, which often occur in a natural way
  when kids try to program fancy pictures on their first encounter with
  turtle graphcis.

- Turtles now have an undo()-method with configurable undo-buffer.

- Some simple commands/methods for creating event driven programs
  (mouse-, key-, timer-events). Especially useful for programming games.

- A scrollable Canvas class. The default scrollable Canvas can be
  extended interactively as needed while playing around with the turtle(s).

- A TurtleScreen class with methods controlling background color or
  background image, window and canvas size and other properties of the
  TurtleScreen.

- There is a method, setworldcoordinates(), to install a user defined
  coordinate-system for the TurtleScreen.

- The implementation uses a 2-vector class named Vec2D, derived from tuple.
  This class is public, so it can be imported by the application programmer,
  which makes certain types of computations very natural and compact.

- Appearance of the TurtleScreen and the Turtles at startup/import can be
  configured by means of a turtle.cfg configuration file.
  The default configuration mimics the appearance of the old turtle module.

- If configured appropriately the module reads in docstrings from a docstring
  dictionary in some different language, supplied separately  and replaces
  the english ones by those read in. There is a utility function
  write_docstringdict() to write a dictionary with the original (english)
  docstrings to disc, so it can serve as a template for translations.

Behind the scenes there are some features included with possible
extensionsin in mind. These will be commented and documented elsewhere.

"""

_ver = "turtle 1.0b1 - for Python 2.6   -  30. 5. 2008, 18:08"

#print _ver

#import time
#import math
#
#from copy import deepcopy
#from math import *    ## for compatibility with old turtle module
#from jturtle_colordict import cd as _cd
#from jturtle_config import JT_DEBUG, PAINT_VARIANT, _CFG, config_dict, readconfig
#from jturtle_colordict import getcolor
#
#
#from jturtle_ScrolledCanvas import ScrolledCanvas
#from jturtle_TurtleScreen import TurtleScreen
#
#from jturtle_Vec2D import Vec2D
#from jturtle_Root import _Root
#from jturtle_TurtleScreenBase import TurtleScreenBase
#from jturtle_Shape import Shape
#from jturtle_Tbuffer import Tbuffer

#Canvas = ScrolledCanvas
#RawPen = RawTurtle
#Pen = Turtle

import types

import jturtle_config
print "imported jturtle_config"

import jturtle_colordict
print "imported jturtle_colordict"

import jturtle_exceptions
print "imported jturtle_exceptions"

import jturtle_Vec2D
print "imported jturtle_Vec2D"

import jturtle_Shape
print "imported jturtle_Shape"

import jturtle_TPen
print "imported jturtle_TPen"

import jturtle_TNavigator
print "imported jturtle_TNavigator"

import jturtle_Tbuffer
print "imported jturtle_Tbuffer"

import jturtle_ScrolledCanvas
print "imported jturtle_ScrolledCanvas"

import jturtle__Root
print "imported jturtle__Root"

import jturtle__TurtleImage
print "imported jturtle__TurtleImage"

import jturtle_TurtleScreenBase
print "imported jturtle_TurtleScreenBase"

import jturtle_TurtleScreen
print "imported jturtle_TurtleScreen"

import jturtle__Screen
print "imported jturtle__Screen"

import jturtle_RawTurtle
print "imported jturtle_RawTurtle"

import jturtle_Turtle
print "imported jturtle_Turtle"

from jturtle_config import _CFG
from jturtle_Turtle import Turtle
from jturtle__Screen import _Screen
from jturtle_TPen import TPen


_tg_classes = ['ScrolledCanvas', 'TurtleScreen', 'Screen',
               'RawTurtle', 'Turtle', 'RawPen', 'Pen', 'Shape', 'Vec2D']

_tg_screen_functions = ['addshape', 'bgcolor', 'bgpic', 'bye',
        'clearscreen', 'colormode', 'delay', 'exitonclick', 'getcanvas',
        'getshapes', 'listen', 'mode', 'onkey', 'onscreenclick', 'ontimer',
        'register_shape', 'resetscreen', 'screensize', 'setup',
        'setworldcoordinates', 'title', 'tracer', 'turtles', 'update',
        'window_height', 'window_width']

_tg_turtle_functions = ['back', 'backward', 'begin_fill', 'begin_poly', 'bk',
        'circle', 'clear', 'clearstamp', 'clearstamps', 'clone', 'color',
        'degrees', 'distance', 'dot', 'down', 'end_fill', 'end_poly', 'fd',
        'fill', 'fillcolor', 'forward', 'get_poly', 'getpen', 'getscreen',
        'getturtle', 'goto', 'heading', 'hideturtle', 'home', 'ht', 'isdown',
        'isvisible', 'left', 'lt', 'onclick', 'ondrag', 'onrelease', 'pd',
        'pen', 'pencolor', 'pendown', 'pensize', 'penup', 'pos', 'position',
        'pu', 'radians', 'right', 'reset', 'resizemode', 'rt',
        'seth', 'setheading', 'setpos', 'setposition', 'settiltangle',
        'setundobuffer', 'setx', 'sety', 'shape', 'shapesize', 'showturtle',
        'speed', 'st', 'stamp', 'tilt', 'tiltangle', 'towards', 'tracer',
        'turtlesize', 'undo', 'undobufferentries', 'up', 'width',
        'window_height', 'window_width', 'write', 'xcor', 'ycor']

_tg_utilities = ['write_docstringdict', 'done', 'mainloop']

_math_functions = ['acos', 'asin', 'atan', 'atan2', 'ceil', 'cos', 'cosh',
        'e', 'exp', 'fabs', 'floor', 'fmod', 'frexp', 'hypot', 'ldexp', 'log',
        'log10', 'modf', 'pi', 'pow', 'sin', 'sinh', 'sqrt', 'tan', 'tanh']

__all__ = (_tg_classes + _tg_screen_functions + _tg_turtle_functions +
           _tg_utilities + _math_functions)

_alias_list = ['addshape', 'backward', 'bk', 'fd', 'ht', 'lt', 'pd', 'pos',
               'pu', 'rt', 'seth', 'setpos', 'setposition', 'st',
               'turtlesize', 'up', 'width']



try:
    readconfig(_CFG)
except:
    print "No configfile read, reason unknown"


# Constants to control painting-mode in TurtleScreenBase._update().
# See lines 

WAIT_FOR_PAINT = 0.05


_keyCode = {"left":37, "up":38, "right":39, "down":40,
            "return":10, "escape":27, "space":32}

for c in "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ":
    _keyCode[c.lower()] = ord(c)

def mainloop():
    pass

def Screen():
    """Return the singleton screen object.
    If none exists at the moment, create a new one and return it,
    else return the existing one."""
    if Turtle._screen is None:
        Turtle._screen = _Screen()
    return Turtle._screen
		
def _getpen():
    """Create the 'anonymous' turtle if not already present."""
    if Turtle._pen is None:
        Turtle._pen = Turtle()
    return Turtle._pen

def _getscreen():
    """Create a TurtleScreen if not already present."""
    if Turtle._screen is None:
        Turtle._screen = Screen()
    return Turtle._screen

def write_docstringdict(filename="turtle_docstringdict"):
    """Create and write docstring-dictionary to file.

    Optional argument:
    filename -- a string, used as filename
                default value is turtle_docstringdict

    Has to be called explicitely, (not used by the turtle-graphics classes)
    The docstring dictionary will be written to the Python script <filname>.py
    It is intended to serve as a template for translation of the docstrings
    into different languages.
    """
    docsdict = {}

    for methodname in _tg_screen_functions:
        key = "_Screen."+methodname
        docsdict[key] = eval(key).__doc__
    for methodname in _tg_turtle_functions:
        key = "Turtle."+methodname
        docsdict[key] = eval(key).__doc__

    f = open("%s.py" % filename,"w")
    keys = sorted([x for x in docsdict.keys()
                        if x.split('.')[1] not in _alias_list])
    f.write('docsdict = {\n\n')
    for key in keys[:-1]:
        f.write('%s :\n' % repr(key))
        f.write('        """%s\n""",\n\n' % docsdict[key])
    key = keys[-1]
    f.write('%s :\n' % repr(key))
    f.write('        """%s\n"""\n\n' % docsdict[key])
    f.write("}\n")
    f.close()

def read_docstrings(lang):
    """Read in docstrings from lang-specific docstring dictionary.

    Transfer docstrings, translated to lang, from a dictionary-file
    to the methods of classes Screen and Turtle and - in revised form -
    to the corresponding functions.
    """
    modname = "turtle_docstringdict_%(language)s" % {'language':lang.lower()}
    module = __import__(modname)
    docsdict = module.docsdict
    for key in docsdict:
        #print key
        try:
            eval(key).im_func.__doc__ = docsdict[key]
        except:
            print "Bad docstring-entry: %s" % key

_LANGUAGE = _CFG["language"]

try:
    if _LANGUAGE != "english":
        read_docstrings(_LANGUAGE)
except ImportError:
    print "Cannot find docsdict for", _LANGUAGE
except:
    print ("Unknown Error when trying to import %s-docstring-dictionary" %
                                                                  _LANGUAGE)


def getmethparlist(ob):
    "Get strings describing the arguments for the given object"
    argText1 = argText2 = ""
    # bit of a hack for methods - turn it into a function
    # but we drop the "self" param.
    if type(ob)==types.MethodType:
        fob = ob.im_func
        argOffset = 1
    else:
        fob = ob
        argOffset = 0
    # Try and build one for Python defined functions
    if type(fob) in [types.FunctionType, types.LambdaType]:
        try:
            counter = fob.func_code.co_argcount
            items2 = list(fob.func_code.co_varnames[argOffset:counter])
            realArgs = fob.func_code.co_varnames[argOffset:counter]
            defaults = fob.func_defaults or []
            defaults = list(map(lambda name: "=%s" % repr(name), defaults))
            defaults = [""] * (len(realArgs)-len(defaults)) + defaults
            items1 = map(lambda arg, dflt: arg+dflt, realArgs, defaults)
            if fob.func_code.co_flags & 0x4:
                items1.append("*"+fob.func_code.co_varnames[counter])
                items2.append("*"+fob.func_code.co_varnames[counter])
                counter += 1
            if fob.func_code.co_flags & 0x8:
                items1.append("**"+fob.func_code.co_varnames[counter])
                items2.append("**"+fob.func_code.co_varnames[counter])
            argText1 = ", ".join(items1)
            argText1 = "(%s)" % argText1
            argText2 = ", ".join(items2)
            argText2 = "(%s)" % argText2
        except:
            pass
    return argText1, argText2

def _turtle_docrevise(docstr):
    """To reduce docstrings from RawTurtle class for functions
    """
    import re
    if docstr is None:
        return None
    turtlename = _CFG["exampleturtle"]
    newdocstr = docstr.replace("%s." % turtlename,"")
    parexp = re.compile(r' \(.+ %s\):' % turtlename)
    newdocstr = parexp.sub(":", newdocstr)
    return newdocstr

def _screen_docrevise(docstr):
    """To reduce docstrings from TurtleScreen class for functions
    """
    import re
    if docstr is None:
        return None
    screenname = _CFG["examplescreen"]
    newdocstr = docstr.replace("%s." % screenname,"")
    parexp = re.compile(r' \(.+ %s\):' % screenname)
    newdocstr = parexp.sub(":", newdocstr)
    return newdocstr

## The following mechanism makes all methods of RawTurtle and Turtle available
## as functions. So we can enhance, change, add, delete methods to these
## classes and do not need to change anything here.


for methodname in _tg_screen_functions:
    pl1, pl2 = getmethparlist(eval('_Screen.' + methodname))
    if pl1 == "":
        print ">>>>>>", pl1, pl2
        continue
    defstr = ("def %(key)s%(pl1)s: return _getscreen().%(key)s%(pl2)s" %
                                   {'key':methodname, 'pl1':pl1, 'pl2':pl2})
    exec defstr
    eval(methodname).__doc__ = _screen_docrevise(eval('_Screen.'+methodname).__doc__)

for methodname in _tg_turtle_functions:
    pl1, pl2 = getmethparlist(eval('Turtle.' + methodname))
    if pl1 == "":
        print ">>>>>>", pl1, pl2
        continue
    defstr = ("def %(key)s%(pl1)s: return _getpen().%(key)s%(pl2)s" %
                                   {'key':methodname, 'pl1':pl1, 'pl2':pl2})
    exec defstr
    eval(methodname).__doc__ = _turtle_docrevise(eval('Turtle.'+methodname).__doc__)


done = mainloop # = TK.mainloop
del pl1, pl2, defstr

if __name__ == "__main__":
    s = Screen()
    def switchpen():
        if isdown():
            pu()
        else:
            pd()

    def demo1():
        """Demo of old turtle.py - module"""
        reset()
        tracer(True)
        up()
        backward(100)
        down()
        # draw 3 squares; the last filled
        width(3)
        for i in range(3):
            if i == 2:
                fill(1)
            for _ in range(4):
                forward(20)
                left(90)
            if i == 2:
                color("maroon")
                fill(0)
            up()
            forward(30)
            down()
        width(1)
        color("black")
        # move out of the way
        tracer(False)
        up()
        right(90)
        forward(100)
        right(90)
        forward(100)
        right(180)
        down()
        # some text
        write("startstart", 1)
        write("start", 1)
        color("red")
        # staircase
        for i in range(5):
            forward(20)
            left(90)
            forward(20)
            right(90)
        # filled staircase
        tracer(True)
        fill(1)
        for i in range(5):
            forward(20)
            left(90)
            forward(20)
            right(90)
        fill(0)
##        # more text
##
    def demo2():
        """Demo of some new features."""
        speed(1)
        st()
        pensize(3)
        setheading(towards(0, 0))
        radius = distance(0, 0)/2.0
        rt(90)
        for _ in range(18):
            switchpen()
            circle(radius, 10)
        write("wait a moment...")
        while undobufferentries():
            undo()
        reset()
        lt(90)
        colormode(255)
        laenge = 10
        pencolor("green")
        pensize(3)
        lt(180)
        for i in range(-2, 16):
            if i > 0:
                begin_fill()
                fillcolor(255-15*i, 0, 15*i)
            for _ in range(3):
                fd(laenge)
                lt(120)
            laenge += 10
            lt(15)
            speed((speed()+1)%12)
        end_fill()

        lt(120)
        pu()
        fd(70)
        rt(30)
        pd()
        color("red","yellow")
        speed(0)
        fill(1)
        for _ in range(4):
            circle(50, 90)
            rt(90)
            fd(30)
            rt(90)
        fill(0)
        lt(90)
        pu()
        fd(30)
        pd()
        shape("turtle")

        tri = getturtle()
        tri.resizemode("auto")
        turtle = Turtle()
        turtle.resizemode("auto")
        turtle.shape("turtle")
        turtle.reset()
        turtle.left(90)
        turtle.speed(0)
        turtle.up()
        turtle.goto(280, 40)
        turtle.lt(30)
        turtle.down()
        turtle.speed(6)
        turtle.color("blue","orange")
        turtle.pensize(2)
        tri.speed(6)
        setheading(towards(turtle))
        count = 1
        while tri.distance(turtle) > 4:
            turtle.fd(3.5)
            turtle.lt(0.6)
            tri.setheading(tri.towards(turtle))
            tri.fd(4)
            if count % 20 == 0:
                turtle.stamp()
                tri.stamp()
                switchpen()
            count += 1
        tri.write("CAUGHT! ", font=("Arial", 16, "bold"), align="right")
        tri.pencolor("black")
        tri.pencolor("red")

        def baba(xdummy, ydummy):
            clearscreen()
            bye()

        time.sleep(2)

        while undobufferentries():
            tri.undo()
            turtle.undo()
        tri.fd(50)
        tri.write("  Click me!", font = ("Courier", 12, "bold") )
        tri.onclick(baba, 1)

    demo1()
    s.stats("demo1")
    demo2()
    s.stats("Ende")
##    exitonclick()
