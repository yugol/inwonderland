/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc.ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import vitaminc.Controller;
import vitaminc.toolbox.Tool;
import vitaminc.toolbox.Toolbox;

// vitaminc.ui.ToolButton
/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ToolButton extends JButton {

    private Tool tool;

    public ToolButton() {
        super();
        setText("TB");
    }

    public void initialize(final Controller controller) {
        this.tool = Toolbox.get(getText());
        this.setToolTipText(tool.toHTMLString());

        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                controller.addSnippet(tool.getSnippet());
            }
        });
    }
    private Rectangle textRect = new Rectangle();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String text = getText();
        if (text.length() > 0) {

            Graphics2D g2d = (Graphics2D) g;
            Map dt = (Map) (Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints"));
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, dt.get(RenderingHints.KEY_TEXT_ANTIALIASING));

            Insets i = getInsets();
            textRect.x = i.left - 1;
            textRect.y = i.top;
            textRect.width = getWidth() - (i.right + textRect.x);
            textRect.height = getHeight() - (i.bottom + textRect.y);

            g.setColor(getBackground());
            g.fillRect(textRect.x, textRect.y, textRect.width, textRect.height);

            Font font = getFont();
            FontMetrics fm = getFontMetrics(font);
            int height = fm.getHeight();
            textRect.x += 1;
            textRect.y = textRect.y + (textRect.height - height) / 2;
            textRect.height = height;

            g.setFont(new Font(font.getName(), Font.ITALIC, font.getSize()));
            g.setColor(getForeground());
            String first = text.substring(0, 1);
            g.drawString(first, textRect.x, textRect.y + textRect.height - fm.getDescent());
            int adv = (int) fm.getStringBounds(first, g).getWidth() + 2;

            g.setFont(font);
            g.setColor(SystemColor.controlDkShadow);
            g.drawString(text.substring(1), textRect.x + adv, textRect.y + textRect.height - fm.getDescent());
        }
    }
}
