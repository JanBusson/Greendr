import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class StyleButton extends JButton {

    private static final Dimension BUTTON_SIZE = new Dimension(200, 50); // Fixed size for regular buttons
    private static final Dimension BUTTON_SIZE_DOWN = new Dimension(100, 50); // Fixed size for "Back" and "Continue" buttons
    private Dimension buttonSize;

    public StyleButton(String lbl) {
        super(lbl);
        setBackground(Color.PINK);
        buttonSize = BUTTON_SIZE;
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);
        setMinimumSize(buttonSize);
        setOpaque(true); // paints every pixel of the component
    }

    public StyleButton(String lbl, String name) {
        super(lbl);
        setBackground(Color.PINK);
        buttonSize = BUTTON_SIZE_DOWN;
        setPreferredSize(buttonSize);
        setMaximumSize(buttonSize);
        setMinimumSize(buttonSize);
        setOpaque(true); // paints every pixel of the component
    }

    @Override
    public Dimension getPreferredSize() {
        return buttonSize;
    }

    @Override
    public Dimension getMaximumSize() {
        return buttonSize;
    }

    @Override
    public Dimension getMinimumSize() {
        return buttonSize;
    }
}
