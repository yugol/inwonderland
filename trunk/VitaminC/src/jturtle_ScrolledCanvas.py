#! /usr/bin/python

from java import awt
from javax import swing
from jturtle_config import JT_DEBUG

# preliminary version: no scrolling etc.
class ScrolledCanvas(swing.JPanel):
    
    def __init__ (self, w, h, cw, ch):
        swing.JPanel.__init__(self)
        self.ready_for_painting = False
        #swing.JComponent.__init__(self)
        self.w = w
        self.h = h
        self.cw = cw
        self.ch = ch
        self.setPreferredSize(awt.Dimension(cw, ch))
        self._scroll_panel = swing.JScrollPane(self)
        self._scroll_panel.setPreferredSize(awt.Dimension(w, h))
        self.center_canvas()

    def center_canvas(self):
        vw = self._scroll_panel.getWidth() - 10
        vh = self._scroll_panel.getHeight() - 10
        rx = (self.cw - vw) // 2
        ry = (self.ch - vh) // 2
        rect = awt.Rectangle(rx, ry, vw, vh)
        self.scrollRectToVisible(rect)

    def notify_frame(self, frame):
        self.frame = frame

    def notify_base(self, base):
        self.base = base

    def paintComponent(self, g2):
        if JT_DEBUG: print "------------ ScrolledCanvas: paintComponent %10.4f" % time.clock()
        if self.ready_for_painting:
            self.base.paintCanvas(g2)

    def updateMousePos(self, pos):
        self.frame.updateMousePos(pos)

    def clickAt(self, x, y):
        self.frame.clickAt(x, y)

    def handleKeyPressed(self, event):
        self.frame.handleKeyPressed(event)
