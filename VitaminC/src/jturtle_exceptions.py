#! /usr/bin/python

class Terminator (Exception):
    """Will be raised in TurtleScreen.update, if _RUNNING becomes False.

    Thus stops execution of turtle graphics script. Main purpose: use in
    in the Demo-Viewer turtle.Demo.py.
    """
    pass


class TurtleGraphicsError(Exception):
    """Some TurtleGraphics Error
    """

