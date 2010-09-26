#! /usr/bin/python

from java import awt
from javax import swing
from vitaminc.ui import DrawingWindow
from jturtle_config import _CFG
from jturtle_ScrolledCanvas import ScrolledCanvas

#class _Root(swing.JFrame):
class _Root(DrawingWindow):
    """Root class for Screen based on Tkinter."""

    def __init__(self):
        swing.JFrame.__init__(self, title="Jython turtle graphics",
                              visible=True,
                              size=(_CFG['width'], _CFG['height']))
        self.setVisible(True)
        self.componentResized = self._handleComponentResized

        self._width = _CFG['width']
        self._height = _CFG['height']
        self._canvwidth = _CFG['canvwidth']
        self._canvheight = _CFG['canvheight']

        self._canvas = ScrolledCanvas(self._width, self._height, self._canvwidth, self._canvheight)
        self._canvas.notify_frame(self)

        self._statusBar = swing.JTextField()
        self._statusBar.setEditable(False)
        self._statusBar.setText(" X: -  Y: -")
        self._statusBar.setFocusable(False)

        layout = awt.BorderLayout()
        self.getContentPane().setLayout(layout)
        self.getContentPane().add(self._canvas._scroll_panel, awt.BorderLayout.CENTER)
        self.getContentPane().add(self._statusBar, awt.BorderLayout.SOUTH)

        # self.setContentPane(self._canvas._scroll_panel)

        self.pack()
        self.repaint()


    def setupcanvas(self, width, height, cwidth, cheight):
        self._canvas.setPreferredSize(awt.Dimension(cwidth, cheight))
        self._canvas._scroll_panel.setPreferredSize(awt.Dimension(width, height))
        self._canvas.center_canvas()
        self.pack()
        self.repaint()
        self._canvas.notify_frame(self)
        self.pack()
        self.setVisible(True)

    def _getcanvas(self):
        return self._canvas

    def ondestroy(self, destroy):
        def _destroy(event):
            print "_Root.ondestroy._destroy"
            destroy()

    def destroy(self):
        print "_Root.destroy"

    def win_width(self):
        return self._width

    def win_height(self):
        return self._height

    def _handleComponentResized(self, event):
        self._canvas.center_canvas()
        self.repaint()

    def updateMousePos(self, pos):
        self._statusBar.setText(" X: " + str(pos[0]) + "  Y: " + str(pos[1]))

