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

public class GeneralInfo2View extends JPanel{

	private GraphicPanel generalInfoPanel;

	private HeadingLabel generalInfo2Label;
	private JLabel genderLabel;
	private JLabel sexualityLabel;
	private JLabel interestGenderLabel;

	private JComboBox<String> genderField;
	private JComboBox<String> sexualityField;
	private JComboBox<String> interestGenderField;

	private StyleButton continueButton;
	private StyleButton backButton;
	private ScreenSwitcher screenSwitcher;

	private int index=0;  // Index to track if user has been added - so from the next view when someone clicks back and one more time continue -> no new user created

	public GeneralInfo2View(ScreenSwitcher screenSwitcher ) {

		this.screenSwitcher=screenSwitcher;
		setLayout(new BorderLayout());

		generalInfoPanel = new GraphicPanel();
		generalInfoPanel.setLayout(new BorderLayout());

		// Label "General Information 2" on the top of the screen
		generalInfo2Label = new HeadingLabel("General Information 2/4");
		generalInfoPanel.add(generalInfo2Label, BorderLayout.NORTH);

		//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen	
	
		// Center panel with main fields
		JPanel centerPanel = new GraphicPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		genderLabel = new JLabel("Gender:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(genderLabel, gbc);

		genderField = new JComboBox<>(new String[] { "Male", "Female", "Other" });
		genderField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(genderField, gbc);

		sexualityLabel = new JLabel("Sexuality:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(sexualityLabel, gbc);

		sexualityField = new JComboBox<>(new String[] { "Heterosexual", "Homosexual", "Bisexual", "Other" });
		sexualityField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(sexualityField, gbc);

		interestGenderLabel = new JLabel("Interest Gender:");
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(interestGenderLabel, gbc);

		interestGenderField = new JComboBox<>(new String[] { "Male", "Female", "Other" });
		interestGenderField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(interestGenderField, gbc);

		generalInfoPanel.add(centerPanel, BorderLayout.CENTER);


		// Creating button in the bottom part of the screen
		// To do that we create new panel and load it with button
		JPanel bottomPanel = new GraphicPanel();

		continueButton = new StyleButton("Continue", "Bottom");
		continueButton.addActionListener(new ContinueButtonListener());

		backButton = new StyleButton("Back","Bottom");

		bottomPanel.add(backButton);
		bottomPanel.add(continueButton);

		generalInfoPanel.add(bottomPanel, BorderLayout.SOUTH);

		// Adding the main panel to this JPanel
		add(generalInfoPanel, BorderLayout.CENTER);

		// When backButton pressed -> just go back to previous view (need to define previous view)
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenSwitcher.showView("generalInfo1View");
			}
		});
	}    


	class ContinueButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String gender = (String) genderField.getSelectedItem();
			String sexuality = (String) sexualityField.getSelectedItem();
			String interestGender = (String) interestGenderField.getSelectedItem();

			// First check - if already user was here - then index would be 1 and we dont need to update database if he clicked back and continue one more time
			if(index>0) {
				screenSwitcher.showView("generalInfo3View");
				System.out.println("No need to create one more time");
			} else {
				// Check if all fields are filled
				if (TechnicalFunctions.readyToUpdate2(generalInfoPanel, gender, sexuality, interestGender)) {
					// Tracking for us that all fields are filled in
					System.out.println("All fields are filled in");

					TechnicalFunctions.updateGeneralInfo2(screenSwitcher, gender, sexuality, interestGender);
					
					// Check that database set everything successfully
					System.out.println("All fields are filled in database succesfully");
					
					// Move to the next view (need to define)
					screenSwitcher.showView("generalInfo3View");
					System.out.println("Added new View");
					index++;
				} else {
					System.out.println("Fields not filled in");
				}
			}
		}
	}
}
