#! /usr/bin/python

import time
from java import awt
from javax import swing
from jturtle_config import JT_DEBUG
from jturtle_config import PAINT_VARIANT
from jturtle_config import _CFG
from jturtle_colordict import cd as _cd
from jturtle_colordict import getcolor

class TurtleScreenBase(object):
    """Provide the basic graphics functionality.
       Interface between Tkinter and turtle.py.

       To port turtle.py to some different graphics toolkit
       a corresponding TurtleScreenBase class has to be implemented.
    """

    @staticmethod
    def _blankimage():
        """return a blank image object
        """
        return None

    @staticmethod
    def _image(filename):
        """return an image object containing the
        imagedata from a gif-file named filename.
        """
        image = awt.Toolkit.getDefaultToolkit().getImage(filename)
        mt = awt.MediaTracker(_Screen._canvas)
        mt.addImage(image, 1)
        try:
            mt.waitForAll()
        except Exception, e:
            print e, "Exception while loading image."

        bImage = awt.image.BufferedImage(image.getWidth(), image.getHeight(),
                                         awt.image.BufferedImage.TYPE_INT_ARGB)
        bImage.createGraphics().drawImage(image, 0, 0, None)
        return bImage

    def __init__(self, cv):
        self._total_painting_time = 0.0
        self._draw_line_time = 0.0
        self._draw_poly_time = 0.0
        self._paint_poly_time = 0.0
        self._paint_line_time = 0.0
        self._disableupdate = False
        self._repaintCounter = 0
        self._draw_line_counter = 0
        self._paint_line_counter = 0
        self._draw_poly_counter = 0
        self._paint_poly_counter = 0
        self._repaintImmediatelyCounter = 0
        self._lastpaint = time.clock()
        self._starttime = time.clock()
        w = _CFG['canvwidth']
        h = _CFG['canvheight']
        self.ox, self.oy = w//2, h//2
        print "**", w, h
        self._backgroundimage = None
        self.canvwidth = w
        self.canvheight = h

        self.background = awt.Color.white
        self.itemlist = []
        self.itemdict = {}
        self.index = 0
        self.xscale = self.yscale = 1.0
        print "***", self.ox, self.oy
        self.AT = awt.geom.AffineTransform(self.xscale, 0.0, 0.0, -self.yscale,
                                           self.ox, self.oy)
        self._dragging = None
        self.mouseEventDict = {}
        self.cv = cv
        self.cv.notify_base(self)
        self.cv.mouseClicked = self._handleMouseClick
        self.cv.mousePressed = self._handleMousePress
        self.cv.mouseReleased = self._handleMouseRelease
        self.cv.mouseDragged = self._handleMouseDragged
        self.cv.mouseMoved = self._handleMouseMoved
        self.cv.keyReleased = self._handleKeyRelease
        self.cv.keyPressed = self._handleKeyPress
        self.cv.ready_for_painting = True

    def _createpoly(self):
        """Create an invisible polygon item on canvas self.cv)
        """
        self.index += 1
        origin = self.ox, self.oy
        self.itemdict[self.index] = ["polygon",
                       None, "", "", 0]
        self.itemlist.append(self.index)
        return self.index

    def _drawpoly(self, polyitem, coordlist, fill=None,
                  outline=None, width=None, top=False):
        """Configure polygonitem polyitem according to provided
        arguments:
        coordlist is sequence of coordinates
        fill is filling color
        outline is outline color
        top is a boolean value, which specifies if polyitem
        will be put on top of the canvas' displaylist so it
        will not be covered by other items.
        """
        a_ = time.clock()
        if coordlist is None:
            coordlist = []
        coordlist = list(coordlist)
        if len(coordlist) < 2:
            return
        self._draw_poly_counter += 1
        path = awt.geom.Path2D.Double(awt.geom.Path2D.Double.WIND_NON_ZERO)
        x,y = coordlist.pop(0)
        path.moveTo(x,y)

        while coordlist:
            x,y = coordlist.pop(0)
            path.lineTo(x, y)
        path.closePath()
        path = self.AT.createTransformedShape(path)
        poly = self.itemdict[polyitem]
        poly[1] = path
        if fill is not None:
            poly[2] = getcolor(fill)
        if outline is not None:
            poly[3] = getcolor(outline)
        if width is not None:
            poly[4] = width
        if top:
            self.itemlist.remove(polyitem)
            self.itemlist.append(polyitem)
        self._draw_poly_time += (time.clock()-a_)

    def _createline(self):
        """Create an invisible line item on canvas self.cv)
        """
        self.index += 1
        self.itemdict[self.index] = ["line", None, "black", 1]
        self.itemlist.append(self.index)
        return self.index

    def _drawline(self, lineitem, coordlist=None,
                  fill=None, width=None, top=False):
        """Configure lineitem according to provided arguments:
        coordlist is sequence of coordinates
        fill is drawing color
        width is width of drawn line.
        top is a boolean value, which specifies if polyitem
        will be put on top of the canvas' displaylist so it
        will not be covered by other items.
        """
        self._draw_line_counter += 1
        a_ = time.clock()
        path = None
        if coordlist is None:
            coordlist = []
        coordlist = list(coordlist)
        if coordlist != []:
            x,y = coordlist.pop(0)
            path = awt.geom.Path2D.Double(awt.geom.Path2D.Double.WIND_NON_ZERO)
            path.moveTo(x,y)
            while coordlist:
                x,y = coordlist.pop(0)
                path.lineTo(x, y)
        path = self.AT.createTransformedShape(path)
        line = self.itemdict[lineitem]
        line[1] = path
        if fill is not None:
            line[2] = getcolor(fill)
        if width is not None:
            line[3] = width
        if top:
            self.itemlist.remove(lineitem)
            self.itemlist.append(lineitem)
        self._draw_line_time += (time.clock()-a_)

    def _delete(self, item):
        """Delete graphics item from canvas.
        If item is"all" delete all graphics items.
        """
        if item == "all":
            self.itemlist = []
            self.itemdict = {}
            return
        self.itemlist.remove(item)
        del self.itemdict[item]

    def _update(self):
        """Redraw graphics items on canvas
        """
        aktuell = time.clock()
        size = self.cv.getSize()
        width, height = size.width, size.height

#########################################################

        # variant 01:  -- doesn't work (i. e. repaint?) in some cases
        if PAINT_VARIANT == 1:
            self.cv.repaint(0, 0, width+1, height+1)
            if JT_DEBUG: print "V1     _update(): repaint()"

        # variant 02:  -- works as intended, but
        #                 apparently doesn't perform doublebuffering, so unusable
        elif PAINT_VARIANT == 2:
            g = self.cv.getGraphics()
            self.cv.paintComponent(g)   # (TABOO!)
            if JT_DEBUG: print "V2     _update(): cv.paintComponent()"
        # variant 03:  -- paints immediately - results in too many updates
        #                 and slow performance if drawing has many items
        elif PAINT_VARIANT == 3:
            self.cv.paintImmediately(0, 0, width+1, height+1)
            if JT_DEBUG: print "V3     _update(): repaintImmediately()"
            self._repaintImmediatelyCounter += 1
        # variant 04:  -- paints immediately if last paint more than 0.05s ago
        elif PAINT_VARIANT == 4:
            if aktuell - self._lastpaint < WAIT_FOR_PAINT:
                self.cv.repaint(0, 0, width+1, height+1)
                if JT_DEBUG: print "V4     _update(): repaint()"
            else:
                self.cv.paintImmediately(0, 0, width+1, height+1)
                if JT_DEBUG: print "V4     _update(): repaintImmediately()"
                self._repaintImmediatelyCounter += 1

#########################################################

    def stats(self, tag=""):
        """makes some primitive analysis of drawing
        parts of the module."""
        print "---------------------------------------------------"
        print "Statistik: %s" % tag
        print "%6d Items auf dem Screen." % len(self.itemlist)
        print "%6d repaints, davon %6d repaintImmediately" % (self._repaintCounter, self._repaintImmediatelyCounter)
        print "Total painting time:%10.6f" % self._total_painting_time
        print "Average time per painting: %8.6f" % (self._total_painting_time/self._repaintCounter)
        print "Draw Lines (%d) : %10.6f" % (self._draw_line_counter, self._draw_line_time)
        print "Paint Lines %4d items %10.6f" % (self._paint_line_counter, self._paint_line_time)
        print "Draw Polys (%d) : %10.6f" % (self._draw_poly_counter, self._draw_poly_time)
        print "Paint Polys %4d items  %10.6f sec." % (self._paint_poly_counter, self._paint_poly_time)
        trt = time.clock() - self._starttime
        print "Total runtime:%10.6f" % trt
        print "Percentage of painting-time: %8.4f" % (100*self._total_painting_time/trt)

    def paintCanvas(self, g2):
        if JT_DEBUG: pass
        self._lastpaint =  a = time.clock()
        self._repaintCounter += 1
        if self._disableupdate: return

        self.fillBackground(g2)

        if self._backgroundimage:
            at = awt.geom.AffineTransform()
            at.setToTranslation(0,0)
            g2.drawImage(self._backgroundimage, at, self._canvas)

        oldAt = g2.getTransform()
        newAt = awt.geom.AffineTransform(self.xscale, 0.0, 0.0, -self.yscale,
                                         self.ox, self.oy)

        for item in self.itemlist:
            if self._dragging == item:
                continue
            data = self.itemdict[item]
            if data[0] == "line":
                self._paint_line_counter += 1
                a_ = time.clock()
                _, path, color, width = data
                if path is None:
                    continue
                if color == "":
                    continue
                g2.setStroke(awt.BasicStroke(width, awt.BasicStroke.CAP_ROUND,
                                                    awt.BasicStroke.JOIN_ROUND))
                if isinstance(color, str):
                    color = getcolor(color)
                g2.setPaint(awt.Color(*color))
                g2.draw(path)
                self._paint_line_time += (time.clock()-a_)
            elif data[0] == "polygon":
                _, path, fill, outline, width = data
                if path is not None:
                    self._paint_poly_counter += 1
                    a_ = time.clock()
                    if width is None:
                        width = 1
                    if fill:
                        g2.setPaint(awt.Color(*fill))
                        g2.fill(path)
                    g2.setStroke(awt.BasicStroke(width, awt.BasicStroke.CAP_ROUND,
                                                        awt.BasicStroke.JOIN_ROUND))
                    if outline != "":
                        r, g, b = outline
                        g2.setPaint(awt.Color(r, g, b))
                    g2.draw(path)
                    self._paint_poly_time += (time.clock()-a_)
            elif data[0] == "dot":
                _, pt, size, color = data
                if color == "":
                    continue
                x, y = pt.x, pt.y
                x = int(round(x-1.0*size/2))
                y = int(round(y-1.0*size/2))
                size = int(round(size))
                e = awt.geom.Ellipse2D.Double(x, y, size, size)
                g2.setPaint(awt.Color(*color))
                g2.fill(e)
            elif data[0] == "text":
                _, pth, txt, fnt, txtcolor, (dwx, dwy) = data
                pt = pth.getCurrentPoint()
                x, y = pt.x, pt.y
                g2.setPaint(txtcolor)
                g2.setFont(fnt)
                g2.drawString(txt, x+dwx, y+dwy)
            elif data[0] == "image":
                _, pt, image, (dx, dy) = data
                if pt is None:
                    continue
                x, y = pt.x, pt.y
                at = awt.geom.AffineTransform()
                at.setToTranslation(x+dx,y+dy)
                if image is not None:
                    g2.drawImage(image, at, self._canvas)
        if self._dragging:
            data = self.itemdict[self._dragging]
            if data[0] == "polygon":
                _, path, fill, outline, width = data
                if path is not None:
                    if width is None:
                        width = 1
                    if fill:
                        g2.setPaint(awt.Color(*fill))
                        g2.fill(path)
                    g2.setStroke(awt.BasicStroke(width))
                    if outline != "":
                        r, g, b = outline
                        g2.setPaint(awt.Color(r, g, b))
                    g2.draw(path)
            elif data[0] == "image":
                _, coords, image = data
                x, y = coords[0]
                at = awt.geom.AffineTransform()
                at.setToTranslation(x,y)
                if image is not None:
                    g2.drawImage(image, at, self._canvas)
        e = time.clock()
        self._total_painting_time += e - a
        if self._repaintCounter % 50 == 0:
            if JT_DEBUG:
                print "************ %6d (%6d)- %10.5f " % (self._repaintCounter, self._repaintImmediatelyCounter,
                                                       (time.clock() - self._starttime))


    def fillBackground(self, g2):
        background = self.background
        oldcolor = g2.color
        g2.color = background
        size = self.cv.getSize()
        width, height = size.width, size.height
        g2.fillRect(0, 0, width, height)
        g2.color = oldcolor



    def _delay(self, delay):
        """Delay subsequent canvas actions for delay ms."""
        time.sleep(0.001*delay)

    def _iscolorstring(self, color):
        """Check if the string color is a legal Tkinter color string.
        """
        return color in _cd or color.startswith("#")  # provisional

    def _bgcolor(self, color=None):
        """Set canvas' backgroundcolor if color is not None,
        else return backgroundcolor."""
        if color is not None:
            ct = getcolor(color)
            r,g,b = ct
            self.background = awt.Color(r, g, b)
            self._update()
        else:
            return self.background

    def _write(self, pos, txt, align, font, pencolor):
        """Write txt at pos in canvas with specified font
        and color.
        Return text item and x-coord of right bottom corner
        of text's bounding box."""
        # print "_write ==>", pos, txt, align, font, pencolor
        x, y = pos
        pt = awt.geom.Path2D.Double()
        pt.moveTo(x,y)
        pt = self.AT.createTransformedShape(pt)
        fontname = font[0]
        if len(font) > 1:
            fontsize = font[1]*3//2
        else:
            fontsize = 12
        fontstyle = awt.Font.PLAIN
        if len(font) > 2:
            if ("bold" in font[2:]) or ("bold" in font[2]):
                fontstyle = fontstyle | awt.Font.BOLD
            if ("italic" in font[2:]) or ("italic" in font[2]):
                fontstyle = fontstyle | awt.Font.ITALIC
        thisfont = awt.Font(fontname, fontstyle, fontsize)
        textcolor = awt.Color(*getcolor(pencolor))

        fm = self.cv.getFontMetrics(thisfont)
        width = fm.stringWidth(txt)
        height = fm.maxDescent #+ fm.maxAscent
        if align == "center":
            dwx = -width//2
            dwy = -height
            retx = pos[0]+width//2
        elif align == "right":
            dwx = -width
            dwy = -height
            retx = pos[0]
        else: # align == "left"
            dwx = 0
            dwy = -height
            retx = pos[0]+width
        self.index += 1
        self.itemdict[self.index] = ["text", pt, txt, thisfont, textcolor, (dwx, dwy)]
        self.itemlist.append(self.index)
        self._update()
        return self.index, retx

    def _dot(self, pos, size, color):
        """may be implemented for some other graphics toolkit"""
        x, y = pos
        p = awt.geom.Point2D.Double(x, y)
        q = self.AT.transform(p, None)
        self.index += 1
        self.itemdict[self.index] = ["dot", q, size, getcolor(color)]
        self.itemlist.append(self.index)
        return self.index

    def _onclick(self, item, fun, num=1, add=None):
        """Bind fun to mouse-click event on turtle.
        fun must be a function with two arguments, the coordinates
        of the clicked point on the canvas.
        num, the number of the mouse-button defaults to 1
        """
        print "_onclick performed:",
        if fun is None:
            if ("onclick", item, num) in self.mouseEventDict:
                del self.mouseEventDict[("onclick", item, num)]
        else:
            print "on"
            self.mouseEventDict[("onclick", item, num)] = fun

    def _handleMousePress(self, event):
        x = event.x
        y = event.y
        x1 = (x - self.ox) / self.xscale
        y1 = (self.oy - y) / self.yscale
        button = event.button
        for turtle in self._turtles:
            item = turtle.turtle._item
            if ("onclick", item, button) in self.mouseEventDict:
                if self._type(item) == "polygon":
                    poly = self.itemdict[item][1]
                    if poly.contains(x, y):
                        self.mouseEventDict[("onclick", item, button)](x1,y1)
                elif self._type(item) == "image":
                    p = self.itemdict[item][1]
                    imx, imy = p.x, p.y
                    im = self.itemdict[item][2]
                    w, h = self.itemdict[item][3] #im.getWidth(), im.getHeight()
                    if imx + w <=x <= imx-w and imy+h <= y <= imy-h:
                        self.mouseEventDict[("onclick", item, button)](x1,y1)

    def _onrelease(self, item, fun, num=1, add=None):
        """Bind fun to mouse-button-release event on turtle.
        fun must be a function with two arguments, the coordinates
        of the point on the canvas where mouse button is released.
        num, the number of the mouse-button defaults to 1

        If a turtle is clicked, first _onclick-event will be performed,
        then _onscreensclick-event.
        """
        print "_onrelease performed:",
        if fun is None:
            if ("onrelease", item, num) in self.mouseEventDict:
                del self.mouseEventDict[("onrelease", item, num)]
        else:
            print "on"
            self.mouseEventDict[("onrelease", item, num)] = fun

    def _handleMouseRelease(self, event):
        if self._dragging:
            self._dragging = None
        x = event.x
        y = event.y
        x1 = (x - self.ox) / self.xscale
        y1 = (self.oy - y) / self.yscale
        button = event.button
        for turtle in self._turtles:
            item = turtle.turtle._item
            if ("onrelease", item, button) in self.mouseEventDict:
                if self._type(item) == "polygon":
                    poly = self.itemdict[item][1]
                    if poly.contains(x, y):
                        self.mouseEventDict[("onrelease", item, button)](x1,y1)
                elif self._type(item) == "image":
                    pass

    def _ondrag(self, item, fun, num=1, add=None):
        """Bind fun to mouse-move-event (with pressed mouse button) on turtle.
        fun must be a function with two arguments, the coordinates of the
        actual mouse position on the canvas.
        num, the number of the mouse-button defaults to 1

        Every sequence of mouse-move-events on a turtle is preceded by a
        mouse-click event on that turtle.
        """
        if num == 1: num = 16
        if num == 2: num = 8
        if num == 3: num = 4
        print "_ondrag performed:",
        if fun is None:
            if ("ondrag", item, num) in self.mouseEventDict:
                del self.mouseEventDict[("ondrag", item, num)]
        else:
            print "on"
            self.mouseEventDict[("ondrag", item, num)] = fun

    def _handleMouseDragged(self, event):
        x = event.x
        y = event.y
        x1 = (x - self.ox) / self.xscale
        y1 = (self.oy - y) / self.yscale
        button = event.modifiers
        for turtle in self._turtles:
            item = turtle.turtle._item
            if self._dragging and self._dragging != item:
                continue
            if ("ondrag", item, button) in self.mouseEventDict:
                if self._type(item) == "polygon":
                    poly = self.itemdict[item][1]
                    if poly.contains(x,y):
                        if not self._dragging:
                            self._dragging = item
                        self.mouseEventDict[("ondrag", item, button)](x1,y1)
                elif self._type(item) == "image":
                    pass

    def _onscreenclick(self, fun, num=1, add=None):
        """Bind fun to mouse-click event on canvas.
        fun must be a function with two arguments, the coordinates
        of the clicked point on the canvas.
        num, the number of the mouse-button defaults to 1

        If a turtle is clicked, first _onclick-event will be performed,
        then _onscreensclick-event.
        """
        if fun is None:
            if ("onscreenclick", num) in self.mouseEventDict:
                del self.mouseEventDict[("onscreenclick", num)]
        else:
            print "on"
            self.mouseEventDict[("onscreenclick", num)] = fun

    def _handleMouseClick(self, event):
        x, y = self.toTurtleCoordinates(event)
        #x = (event.x - self.ox) / self.xscale
        #y = (self.oy - event.y) / self.yscale
        button = event.button
        if ("onscreenclick", button) in self.mouseEventDict:
            self.mouseEventDict[("onscreenclick", button)](x,y)
        self.cv.clickAt(x, y)

    def _onkey(self, fun, key):
        """Bind fun to key-release event of key.
        Canvas must have focus. See method listen
        """
        code = _keyCode.get(key.lower(), 0)
        if fun is None:
            if ("onkey", code) in self.mouseEventDict:
                del self.mouseEventDict[("onkey", code)]
        else:
            self.mouseEventDict[("onkey", code)] = fun

    def _handleKeyRelease(self, event):
        code = event.getKeyCode()
        if ("onkey", code) in self.mouseEventDict:
            self.mouseEventDict[("onkey", code)]()

    def _handleKeyPress(self, event):
        self.cv.handleKeyPressed(event)

    def _listen(self):
        """Set focus on canvas (in order to collect key-events)
        """
        self.cv.requestFocus()

    def _ontimer(self, fun, t):
        """Install a timer, which calls fun after t milliseconds.
        """
        def _fun(event):
            fun()
        timer = swing.Timer(t, None)
        timer.setRepeats(False)
        timer.actionPerformed = _fun
        timer.start()

    def _createimage(self, image):
        """Create and return image item on canvas.
        """
        self.index += 1
        self.itemdict[self.index] = ["image", None, self._blankimage(), (0, 0)]
        self.itemlist.append(self.index)
        self._update()
        return self.index

    def _drawimage(self, item, (x, y), image):
        """Configure image item as to draw image object
        at position (x,y) on canvas)
        """
        p = awt.geom.Point2D.Double(x, y)
        q = self.AT.transform(p, None)
        w, h = 0, 0
        if image:
            w, h = image.getWidth(), image.getHeight()
        self.itemdict[item][1] = q
        self.itemdict[item][2] = image
        self.itemdict[item][3] = (-w//2, -h//2)

    def _setbgpic(self, item, image):
        """Configure image item as to draw image object
        at center of canvas. Set item to the first item
        in the displaylist, so it will be drawn below
        any other item ."""
        self._backgroundimage = image

    def _type(self, item):
        """Return 'line' or 'polygon' or 'image' depending on
        type of item.
        """
        return self.itemdict[item][0]

    def _pointlist(self, item):
        """returns list of coordinate-pairs of points of item
        Example (for insiders):
        >>> from turtle import *
        >>> getscreen()._pointlist(getturtle().turtle._item)
        [(0.0, 9.9999999999999982), (0.0, -9.9999999999999982),
        (9.9999999999999982, 0.0)]
        >>> """
        # not needed anay more (?)

    def _rescale(self, xscalefactor, yscalefactor):
        trafo = awt.geom.AffineTransform(xscalefactor, 0.0, 0.0, yscalefactor,
                                         self.ox*(1-xscalefactor),
                                         self.oy*(1-yscalefactor))
        for item in self.itemdict:
            geobj = self.itemdict[item][1]
            if isinstance(geobj, awt.Shape):
                newpath = trafo.createTransformedShape(geobj)
                self.itemdict[item][1] = newpath
            elif isinstance(geobj, awt.geom.Point2D):
                q = trafo.transform(geobj, None)
                self.itemdict[item][1] = q

    def _setscrollregion(self, srx1, sry1, srx2, sry2):
        trafo = awt.geom.AffineTransform(1.0, 0.0, 0.0, 1.0,
                                         -self.ox-srx1, -self.oy-sry1)
        for item in self.itemdict:
            geobj = self.itemdict[item][1]
            if isinstance(geobj, awt.Shape):
                newpath = trafo.createTransformedShape(geobj)
                self.itemdict[item][1] = newpath
            elif isinstance(geobj, awt.geom.Point2D):
                q = trafo.transform(geobj, None)
                self.itemdict[item][1] = q
        self.ox = -srx1
        self.oy = -sry1
        self.AT = awt.geom.AffineTransform(self.xscale, 0.0, 0.0, -self.yscale,
                                           self.ox, self.oy)


    def _resize(self, canvwidth=None, canvheight=None, bg=None):
        """Resize the canvas, the turtles are drawing on. Does
        not alter the drawing window.
        """
        self.cw = canvwidth
        self.ch = canvheight
        self.cv.setPreferredSize(awt.Dimension(canvwidth, canvheight))
        self.cv.center_canvas()
        self.cv._scroll_panel.repaint()
        # for now: left out

    def _window_size(self):
        """ Return the width and height of the turtle window.
        """
        return self.canvwidth, self.canvheight
        # think about this again

    def toTurtleCoordinates(self, event):
        x = (event.x - self.ox) / self.xscale
        y = (self.oy - event.y) / self.yscale
        return x, y

    def _handleMouseMoved(self, event):
        self.cv.updateMousePos(self.toTurtleCoordinates(event))
