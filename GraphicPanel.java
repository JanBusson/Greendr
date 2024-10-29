

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class GraphicPanel extends JPanel {

	protected Font MainFont=new Font("Arial", Font.BOLD, 32);
	public GraphicPanel() {
		setBackground(new Color(179, 225, 175)); // Light green background

		}
	// New Constructor - for better positioning of the elements
	public GraphicPanel(GridBagLayout gridBagLayout) {
		setLayout(gridBagLayout);
        setBackground(new Color(179, 225, 175));
	}
}
