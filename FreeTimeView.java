import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

public class FreeTimeView extends JPanel{

	private GraphicPanel freeTimePanel;
	private ScreenSwitcher screenSwitcher;

	private JLabel freeTimeLabel;
	private JLabel drinkingLabel;
	private JLabel smokingLabel;
	private JLabel marijuanaLabel;
	private JLabel drugsLabel;

	private JComboBox<String> drinkingField;
	private JComboBox<String> smokingField;
	private JComboBox<String> marijuanaField;
	private JComboBox<String> drugsField;

	private StyleButton continueButton;
	private StyleButton backButton;

	private int index=0;

	public FreeTimeView(ScreenSwitcher screenSwitcher) {

		this.screenSwitcher = screenSwitcher;
		setLayout(new BorderLayout());

		freeTimePanel = new GraphicPanel();
		freeTimePanel.setLayout(new BorderLayout());

		// Label "Free Time" on the top of the screen
		freeTimeLabel = new HeadingLabel("Free Time");
		freeTimePanel.add(freeTimeLabel, BorderLayout.NORTH);

		// Center panel with main fields
		//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
		JPanel centerPanel = new GraphicPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// One type of answers that I use in all comboboxes
		String[] options = { "Never", "Rarely", "Occasionally", "Frequently" };

		drinkingLabel = new JLabel("Drinking:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(drinkingLabel, gbc);

		drinkingField = new JComboBox<>(options);
		drinkingField.setSelectedItem("Select");
		drinkingField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(drinkingField, gbc);

		smokingLabel = new JLabel("Smoking:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(smokingLabel, gbc);

		smokingField = new JComboBox<>(options);
		smokingField.setSelectedItem("Select");
		smokingField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(smokingField, gbc);

		marijuanaLabel = new JLabel("Marijuana:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(marijuanaLabel, gbc);

		marijuanaField = new JComboBox<>(options);
		marijuanaField.setSelectedItem("Select");
		marijuanaField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(marijuanaField, gbc);

		drugsLabel = new JLabel("Drugs:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		centerPanel.add(drugsLabel, gbc);

		drugsField = new JComboBox<>(options);
		drugsField.setSelectedItem("Select");
		drugsField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(drugsField, gbc);

		freeTimePanel.add(centerPanel, BorderLayout.CENTER);

		// Creating button in the bottom part of the screen
		JPanel bottomPanel = new GraphicPanel();

		backButton = new StyleButton("Back","Botton");
		continueButton = new StyleButton("Continue","Bottom");
		continueButton.addActionListener(new ContinueButtonListener());
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				screenSwitcher.showView("generalInfo4View");
			}

		});
		bottomPanel.add(backButton);
		bottomPanel.add(continueButton);

		freeTimePanel.add(bottomPanel, BorderLayout.SOUTH);

		add(freeTimePanel);
	}

	class ContinueButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String drinking = (String) drinkingField.getSelectedItem();
			String smoking = (String) smokingField.getSelectedItem();
			String marijuana = (String) marijuanaField.getSelectedItem();
			String drugs = (String) drugsField.getSelectedItem();

			if (index>0) {
				screenSwitcher.showView("descriptionView");
				System.out.println("No need to create one more time");
			} else {
				// Check if all fields are selected
				if (TechnicalFunctions.readyFreeTime(freeTimePanel, drinking, smoking, marijuana, drugs)) {

					// Tracking for us that all fields are selected
					System.out.println("All fields are selected");

					// Tracking if we set all variables in database

					TechnicalFunctions.updateFreeTimeView(screenSwitcher, drinking, smoking, marijuana, drugs);
					index++;
					
					// Check that database set everything successfully
					System.out.println("All fields are filled in database succesfully");
					
					// Move to the next view (need to define)
					screenSwitcher.showView("descriptionView");
					System.out.println("Added new View");
				} else {
					System.out.println("Field need to be filled in properly");
				}
			}
		}
	}
}