import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GeneralInfo3View extends JPanel{

	private GraphicPanel generalInfoPanel;

	private HeadingLabel generalInfo3Label;
	private JLabel homeTownLabel;
	private JLabel languagesLabel;
	private JLabel selectedLanguagesLabel;
	private JLabel selectedLabel;

	private JTextField homeTownField;
	private MultiSelectComboBox languagesComboBox;

	private StyleButton continueButton;
	private StyleButton backButton;

	private ScreenSwitcher screenSwitcher;

	private int index=0; // Index to track if user has been added - so from the next view when someone clicks back and one more time continue -> no new user created

	public GeneralInfo3View(ScreenSwitcher screenSwitcher) {
		this.screenSwitcher = screenSwitcher;
		setLayout(new BorderLayout());

		generalInfoPanel = new GraphicPanel();
		generalInfoPanel.setLayout(new BorderLayout());

		// Label "General Information" on the top of the screen
		generalInfo3Label = new HeadingLabel("General Information 3/4");
		generalInfoPanel.add(generalInfo3Label, BorderLayout.NORTH);

		// Center panel with main fields
		//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
		JPanel centerPanel = new GraphicPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		homeTownLabel = new JLabel("Home Town:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(homeTownLabel, gbc);

		homeTownField = new JTextField(15);
		homeTownField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(homeTownField, gbc);

		languagesLabel = new JLabel("Languages:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(languagesLabel, gbc);

		// Custom MultiSelectComboBox - my own implementation - see below
		languagesComboBox = new MultiSelectComboBox(new String[]{"English", "Spanish", "French", "German"});
		languagesComboBox.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(languagesComboBox, gbc);

		// Cool feature - it is firstly hidden, but then when you choose a language it appears!
		selectedLabel = new JLabel("Selected: ");
		selectedLabel.hide();
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(selectedLabel,gbc);

		// In this label all our languages will be shown
		selectedLanguagesLabel = new JLabel("");
		gbc.gridx = 1;
		centerPanel.add(selectedLanguagesLabel,gbc);


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
				screenSwitcher.showView("generalInfo2View");
			}
		});

	}

	class ContinueButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String homeTown = homeTownField.getText();
			String[] selectedLanguages= MultiSelectComboBox.getSelectedItems(); // method that i wrote by myself in my class

			// First check - if already user was here - then index would be 1 and we dont need to update database if he clicked back and continue one more time
			if(index>0) {
				screenSwitcher.showView("generalInfo4View");
				System.out.println("No need to create one more time");
			} else {
				// Check if all fields are filled & homeTown consist only of letters - otherwise error
				if (TechnicalFunctions.readyToUpdate3(generalInfoPanel, homeTown, selectedLanguages)) {

					// Tracking that all fields are filled in
					System.out.println("All fields are filled in");

					TechnicalFunctions.updateGeneralInfo3(screenSwitcher, homeTown, selectedLanguagesLabel.getText());
					index++;

					// Check that database set everything successfully
					System.out.println("All fields are filled in database succesfully");

					// Move to the next view (need to define)
					screenSwitcher.showView("generalInfo4View");
					System.out.println("Added new View");
				} else {
					System.out.println("Validation failed. Fix the errors and try again.");
				} 
			}
		}
	}

	// My own implementation of this class
	// Idea is to create a panel where will be combobox as well to make it more beautiful and mostly for multiple choice possibility - could go with normal combobox!!!

	class MultiSelectComboBox extends JPanel {

		private JComboBox<String> comboBox; // Our combobox with all languages
		private static List<String> selectedItems; // Our array list with chosen languages to be shown


		public MultiSelectComboBox(String[] languages) {
			selectedItems = new ArrayList<>();
			String[] string = new String[languages.length+1]; // we create new array of strings to add also the first element in combobox - "Choose languages"

			string[0]="Choose language(s):";
			for (int i=1;i<languages.length+1;i++) { // Copying all languages that will be in combobox
				string[i]=languages[i-1];
			}


			// Creating the combobox with "Choose languages" and all other languages
			comboBox = new JComboBox<>(string);
			// Cool feature - comboBox.setToolTipText("Hello");


			// Adjusting the size
			comboBox.setPreferredSize(new Dimension(200, 25));



			// Adding actionlistener as anonym inner class
			comboBox.addActionListener(new ActionListener() {


				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> combo = (JComboBox<String>) e.getSource();
					String selected = (String) combo.getSelectedItem(); // This is language chosen


					// Main idea now 1. to not have duplicates and 2. to print cool the label with chosen languages
					// We check firstly if it not equal to "Choose language"
					if (selected!="Choose language(s):") {
						// Then we check our array list where we save all the languages to not have duplicates - if yes, we add to the array
						if (!selectedItems.contains(selected)) {
							selectedItems.add(selected);
							// checking for the beginning - if our label is still empty then we just set the text on it with our language selected
							if (selectedLanguagesLabel.getText()=="") {
								selectedLabel.show();
								selectedLanguagesLabel.setText(selected);
							} else {
								// if there is already something on the label then we get everything what is there and then add selected language as well
								selectedLabel.show();
								selectedLanguagesLabel.setText(selectedLanguagesLabel.getText()+", "+selected);

							}

						}
					}

				}
			});

			// Add the combo box to panel
			add(comboBox);

			// Setting the panel to cool background
			this.setBackground(Color.PINK);
		}	

		// This is the function that gives back the whole array of chosen languages from the label - "Choose languages" is not there!
		public static String[] getSelectedItems() {
			String[] LanguagesArray=new String[selectedItems.size()];
			for (int i=0;i<selectedItems.size();i++) {
				LanguagesArray[i]=(String) selectedItems.get(i);
			}
			return LanguagesArray;
		}
	}
}
