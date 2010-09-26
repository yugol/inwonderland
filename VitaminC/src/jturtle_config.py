#! /usr/bin/python

from os.path import isfile
from os.path import join
from os.path import split
import sys

import os


JT_DEBUG = False

##PAINT_VARIANT = 1   # simply use repaint
##PAINT_VARIANT = 2   # call paintComponent directly (not ecommended)
##PAINT_VARIANT = 3   # simply use repaintImmediately
##PAINT_VARIANT = 4   # use repaintImmediately only if last paint hsppened more than WAIT_FOR_PAINT secods ago

PAINT_VARIANT = 3

_CFG = {"width": 840, # Screen
    "height": 650,
    "canvwidth": 800,
    "canvheight": 600,
    "leftright": None,
    "topbottom": None,
    "mode": "standard", # TurtleScreen
    "colormode": 1.0,
    "delay": 10,
    "undobuffersize": 1000, # RawTurtle
    "shape": "arrow",
    "pencolor": "black",
    "fillcolor": "",
    "resizemode": "auto",
    "visible": True,
    "language": "english", # docstrings
    "exampleturtle": "turtle",
    "examplescreen": "screen",
    "title": "Python Turtle Graphics - Jython version",
    "using_IDLE": False
}

##print "cwd:", os.getcwd()
##print "__file__:", __file__
##
##def show(dictionary):
##    print "=========================="
##    for key in sorted(dictionary.keys()):
##        print key, ":", dictionary[key]
##    print "=========================="
##    print

def config_dict(filename):
    """Convert content of config-file into dictionary."""
    f = open(filename, "r")
    cfglines = f.readlines()
    f.close()
    cfgdict = {}
    for line in cfglines:
        line = line.strip()
        if not line or line.startswith("#"):
            continue
        try:
            key, value = line.split("=")
        except:
            print "Bad line in config-file %s:\n%s" % (filename, line)
            continue
        key = key.strip()
        value = value.strip()
        if value in ["True", "False", "None", "''", '""']:
            value = eval(value)
        else:
            try:
                if "." in value:
                    value = float(value)
                else:
                    value = int(value)
            except:
                pass # value need not be converted
        cfgdict[key] = value
    return cfgdict

def readconfig(cfgdict):
    """Read config-files, change configuration-dict accordingly.

    If there is a turtle.cfg file in the current working directory,
    read it from there. If this contains an importconfig-value,
    say 'myway', construct filename turtle_mayway.cfg else use
    turtle.cfg and read it from the import-directory, where
    turtle.py is located.
    Update configuration dictionary first according to config-file,
    in the import directory, then according to config-file in the
    current working directory.
    If no config-file is found, the default configuration is used.
    """
    default_cfg = "turtle.cfg"
    cfgdict1 = {}
    cfgdict2 = {}
    if isfile(default_cfg):
        cfgdict1 = config_dict(default_cfg)
        #print "1. Loading config-file %s from: %s" % (default_cfg, os.getcwd())
    if "importconfig" in cfgdict1:
        default_cfg = "turtle_%s.cfg" % cfgdict1["importconfig"]
    try:
        head, tail = split(__file__)
        cfg_file2 = join(head, default_cfg)
    except:
        cfg_file2 = ""
    if isfile(cfg_file2):
        #print "2. Loading config-file %s:" % cfg_file2
        cfgdict2 = config_dict(cfg_file2)
##    show(_CFG)
##    show(cfgdict2)
    _CFG.update(cfgdict2)
##    show(_CFG)
##    show(cfgdict1)
    _CFG.update(cfgdict1)
##    show(_CFG)
