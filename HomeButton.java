

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class HomeButton extends JButton {
	
    private static final Dimension BUTTON_SIZE = new Dimension(70, 40); // Fixed size for all buttons

    public HomeButton() {
        super("🏠");
        setBackground(Color.GREEN);
        setPreferredSize(BUTTON_SIZE);
        setMaximumSize(BUTTON_SIZE);
        setMinimumSize(BUTTON_SIZE);
        setForeground(Color.BLACK);
		}
    

	@Override
    public Dimension getPreferredSize() {
        return BUTTON_SIZE;
    }

    @Override
    public Dimension getMaximumSize() {
        return BUTTON_SIZE;
    }

    @Override
    public Dimension getMinimumSize() {
        return BUTTON_SIZE;
    }
}


