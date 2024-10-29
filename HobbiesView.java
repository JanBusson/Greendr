import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HobbiesView extends JPanel {

	private GraphicPanel hobbiesPanel;
	private ScreenSwitcher screenSwitcher;

	private HeadingLabel hobbiesLabel;
	private JTextField hobbiesField;

	private StyleButton continueButton;
	private StyleButton backButton;

	private int index=0;

	public HobbiesView(ScreenSwitcher screenSwitcher) {
		this.screenSwitcher = screenSwitcher;
		setLayout(new BorderLayout());

		hobbiesPanel = new GraphicPanel();
		hobbiesPanel.setLayout(new BorderLayout());

		// Label "Hobbies" on the top of the screen
		hobbiesLabel = new HeadingLabel("Hobbies");
		hobbiesPanel.add(hobbiesLabel, BorderLayout.NORTH);

		// Center panel with main field
		//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
		JPanel centerPanel = new GraphicPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel hobbiesFieldLabel = new JLabel("Hobbies:");
		hobbiesFieldLabel.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		centerPanel.add(hobbiesFieldLabel, gbc);

		hobbiesField = new JTextField("List your hobbies here...", 30);
		hobbiesField.setPreferredSize(new java.awt.Dimension(400, 100)); // Set a larger preferred size
		hobbiesField.setBackground(Color.PINK);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2; // making sure that it spans across 2 columns
		gbc.gridheight = 1; // keeping it single line
		centerPanel.add(hobbiesField, gbc);

		hobbiesPanel.add(centerPanel, BorderLayout.CENTER);

		// Creating button in the bottom part of the screen
		JPanel bottomPanel = new GraphicPanel();

		backButton = new StyleButton ("Back","Bottom");
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				screenSwitcher.showView("descriptionView");
			}

		});
		continueButton = new StyleButton("Continue", "Bottom");
		continueButton.addActionListener(new ContinueButtonListener());
		bottomPanel.add(backButton);
		bottomPanel.add(continueButton);

		hobbiesPanel.add(bottomPanel, BorderLayout.SOUTH);

		add(hobbiesPanel);
	}

	class ContinueButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String hobbies = hobbiesField.getText();

			if (index>0) {
				screenSwitcher.showView("finishView");
				System.out.println("No need to create one more time");
			} else {
				// Check if the field is filled
				if (TechnicalFunctions.readyHobbies(hobbiesPanel, hobbies)) {

					// Tracking for us that the field is filled
					System.out.println("Hobbies field is filled");

					// Tracking if we set the variable
					TechnicalFunctions.updateHobbiesView(screenSwitcher, hobbies);
					index++;
					
					// Check that database set everything successfully
					System.out.println("All fields are filled in database succesfully");
					
					// Move to the next view (need to define)
					screenSwitcher.showView("finishCreateProfileView");
					System.out.println("Added new View");
				} else {
					System.out.println("obbies field is filled incorrectly");
				}
			}
		}
	}
}
