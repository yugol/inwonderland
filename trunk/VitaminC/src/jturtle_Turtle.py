#! /usr/bin/python

from jturtle_config import _CFG
from jturtle_RawTurtle import RawTurtle

class Turtle(RawTurtle):
    """RawTurtle auto-crating (scrolled) canvas.

    When a Turtle object is created or a function derived from some
    Turtle method is called a TurtleScreen object is automatically created.
    """
    _pen = None
    _screen = None

    def __init__(self,
                 shape=_CFG["shape"],
                 undobuffersize=_CFG["undobuffersize"],
                 visible=_CFG["visible"]):
        if Turtle._screen is None:
            Turtle._screen = Screen()
        RawTurtle.__init__(self, Turtle._screen,
                           shape=shape,
                           undobuffersize=undobuffersize,
                           visible=visible)
