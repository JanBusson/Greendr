import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GeneralInfo4View extends JPanel{

	private GraphicPanel generalInfoPanel;

	private HeadingLabel generalInfo4Label;
	private JLabel workPlaceLabel;
	private JLabel jobTitleLabel;
	private JLabel collegeUniLabel;

	private JTextField workPlaceField;
	private JTextField jobTitleField;
	private JTextField collegeUniField;

	private StyleButton continueButton;
	private StyleButton backButton;

	private ScreenSwitcher screenSwitcher;
	private int index=0;

	public GeneralInfo4View(ScreenSwitcher screenSwitcher) {

		this.screenSwitcher = screenSwitcher;
		setLayout(new BorderLayout());

		generalInfoPanel = new GraphicPanel();
		generalInfoPanel.setLayout(new BorderLayout());

		// Label "General Information 4" on the top of the screen
		generalInfo4Label = new HeadingLabel("General Information 4");
		generalInfoPanel.add(generalInfo4Label, BorderLayout.NORTH);

		// Center panel with main fields
		//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
		JPanel centerPanel = new GraphicPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		workPlaceLabel = new JLabel("Workplace:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(workPlaceLabel, gbc);

		workPlaceField = new JTextField(15);
		workPlaceField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(workPlaceField, gbc);

		jobTitleLabel = new JLabel("Job Title:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(jobTitleLabel, gbc);

		jobTitleField = new JTextField(15);
		jobTitleField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(jobTitleField, gbc);

		collegeUniLabel = new JLabel("College/University:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(collegeUniLabel, gbc);

		collegeUniField = new JTextField(15);
		collegeUniField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(collegeUniField, gbc);

		generalInfoPanel.add(centerPanel, BorderLayout.CENTER);

		// Creating button in the bottom part of the screen
		JPanel bottomPanel = new GraphicPanel();

		backButton = new StyleButton("Back","Bottom");
		continueButton = new StyleButton("Continue","Bottom");
		continueButton.addActionListener(new ContinueButtonListener());

		bottomPanel.add(backButton);
		bottomPanel.add(continueButton);

		generalInfoPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		// Adding the main panel to this JPanel
		add(generalInfoPanel);

		// When backButton pressed -> just go back to previous view (need to define previous view)
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenSwitcher.showView("generalInfo3View");
			}
		});
	}

	class ContinueButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String workPlace = workPlaceField.getText();
			String jobTitle = jobTitleField.getText();
			String collegeUni = collegeUniField.getText();

			if (index>0) { 
				screenSwitcher.showView("freeTimeView");
				System.out.println("No need to create one more time");
			} else {
				// Check if all fields are filled - they all can contain numbers and special characters
				if (TechnicalFunctions.readyToUpdate4(generalInfoPanel, workPlace, jobTitle, collegeUni)) {
					// Tracking for us that all fields are filled in
					System.out.println("All fields are filled in");

					// Tracking if we set all variables
					TechnicalFunctions.updateGeneralInfo4(screenSwitcher, workPlace, jobTitle, collegeUni);
					index++;
					
					// Check that database set everything successfully
					System.out.println("All fields are filled in database succesfully");
					
					// Move to the next view (need to define)
					screenSwitcher.showView("freeTimeView");
					System.out.println("Added new View");
				} else {
					System.out.println("Fields are not ready!"); 
				}
			}
		}
	}
}