import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GeneralInfo1View extends JPanel {

	private JPanel generalInfoPanel;

	private HeadingLabel generalInfo1Label;
	private JLabel nameLabel;
	private JLabel ageLabel;
	private JLabel heightLabel;
	private JLabel cityLabel;
	private JLabel photoLabel;

	private JTextField nameField;
	private JTextField ageField;
	private JTextField heightField;
	private JTextField cityField;

	private StyleButton uploadPhotoButton;
	private StyleButton continueButton;
	private StyleButton backButton;

	private ScreenSwitcher screenSwitcher;

	private int index=0; // Index to track if user has been added - so from the next view when someone clicks back and one more time continue -> no new user created

	public GeneralInfo1View(ScreenSwitcher screenSwitcher) {
		this.screenSwitcher = screenSwitcher;

		setLayout(new BorderLayout());

		generalInfoPanel = new JPanel(new BorderLayout());

		// Label "General Information" on the top of the screen
		generalInfo1Label = new HeadingLabel("General Information 1/4");
		generalInfoPanel.add(generalInfo1Label, BorderLayout.NORTH);

		// Special label for the photo, where we will put our picture in
		photoLabel = new JLabel();
		photoLabel.setPreferredSize(new Dimension(150, 150));
		photoLabel.setHorizontalAlignment(JLabel.CENTER);
		photoLabel.setVerticalAlignment(JLabel.CENTER);

		//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
		
		// Center panel with main fields
		JPanel centerPanel = new GraphicPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		nameLabel = new JLabel("Name:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(nameLabel, gbc);

		nameField = new JTextField(15);
		nameField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(nameField, gbc);

		ageLabel = new JLabel("Age:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(ageLabel, gbc);

		ageField = new JTextField(15);
		ageField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(ageField, gbc);

		heightLabel = new JLabel("Height (cm):");
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(heightLabel, gbc);

		heightField = new JTextField(15);
		heightField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(heightField, gbc);

		cityLabel = new JLabel("City:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		centerPanel.add(cityLabel, gbc);

		cityField = new JTextField(15);
		cityField.setBackground(Color.PINK);
		gbc.gridx = 1;
		centerPanel.add(cityField, gbc);

		uploadPhotoButton = new StyleButton("Upload Photo");
		uploadPhotoButton.setBackground(Color.PINK);
		uploadPhotoButton.addActionListener(new UploadPhotoButtonListener(photoLabel)); // Inner class -> see below
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2; // making sure that it is 2 columns wide
		centerPanel.add(uploadPhotoButton, gbc);

		// Adding our photolabel as well
		gbc.gridy = 5;
		centerPanel.add(photoLabel, gbc);

		generalInfoPanel.add(centerPanel, BorderLayout.CENTER);

		// Creating button in the bottom part of the screen
		// To do that we create new panel and load it with button
		JPanel bottomPanel = new GraphicPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		// Firstly comes the text that is shown on the button / second string is for me to check where i want to position it
		backButton = new StyleButton("Back","Bottom");
		// When backButton pressed -> just go back to previous view (need to define previous view)
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenSwitcher.showView("registerView");
			}
		});
		continueButton = new StyleButton("Continue", "Bottom");
		continueButton.addActionListener(new ContinueButtonListener());
		bottomPanel.add(backButton);
		bottomPanel.add(continueButton);

		generalInfoPanel.add(bottomPanel, BorderLayout.SOUTH);

		// Adding the main panel to this JPanel
		add(generalInfoPanel, BorderLayout.CENTER);
	}

	class UploadPhotoButtonListener implements ActionListener {
		private JLabel photoLabel;

		public UploadPhotoButtonListener(JLabel photoLabel) {
			this.photoLabel = photoLabel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//External knowledge used -> see 1. Java Image Upload file in /quellen			
			//External knowledge used -> see 11. JFileChooser in /quellen		
			
			// Creates a new file chooser dialog
			JFileChooser fileChooser = new JFileChooser();
			// Only files can be selected
			// Other options -> .DIRECTORIES_ONLY and .FILES_AND_DIRECTORIES
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// Restrict the types of files that can be selected by using file filters
			fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif", "bmp"));
			// Custom title for the dialog
			fileChooser.setDialogTitle("Select an Image File");
			// Approve button with customized text
			fileChooser.setApproveButtonText("Open");
			// Why null? dialog should have no parent component, meaning it will be centered on the screen or relative to the main application frame
			// int result!
			int result = fileChooser.showOpenDialog(null);

			// 3 different possibilities of result
			// JFileChooser.APPROVE_OPTION - int 0
			// JFileChooser.CANCEL_OPTION - int 1
			// JFileChooser.ERROR_OPTION - int -1

			// But we look only at Approve
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					Image img = ImageIO.read(selectedFile);
					// Checking if image exists and we could read the image
					// If no -> Error -> The selected file is not a valid image.
					// If yes -> rescale and put in the label
					if (img != null) {
						// Resize the image while maintaining the aspect ratio
						int imgWidth = img.getWidth(null); // null because ImageObserver interface is used to receive notifications as the image is being loaded. We don't need to track the image loading process
						int imgHeight = img.getHeight(null); // null because ImageObserver interface is used to receive notifications as the image is being loaded. We don't need to track the image loading process
						int labelWidth = photoLabel.getWidth();
						int labelHeight = photoLabel.getHeight();

						int newWidth;
						int newHeight;

						if (imgWidth > imgHeight) {
							newWidth = labelWidth;
							newHeight = (imgHeight * labelWidth) / imgWidth;
						} else {
							newHeight = labelHeight;
							newWidth = (imgWidth * labelHeight) / imgHeight;
						}
						// Renders the image at specified size
						// Different types of scaling:
						// .SCALE_SMOOTH - high quality, but may be slower
						// .SCALE_FAST - priority for speed over quality
						// .SCALE_DEFAULT - compromise between 2 other
						// etc.
						Image resizedImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

						// Add image to the Label and setting cool border for the label
						photoLabel.setIcon(new ImageIcon(resizedImg));
						photoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					} else {
						JOptionPane.showMessageDialog(null, "The selected file is not a valid image.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to load the image.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	class ContinueButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String age = ageField.getText();
			String height = heightField.getText();
			String city = cityField.getText();
			
			//External knowledge used -> see 3. Icon - Oracle Docs file in /quellen
			Icon photo = photoLabel.getIcon();
			// First check - if already user was here - then index would be 1 and we dont need to update database if he clicked back and continue one more time
			if(index>0) {
				screenSwitcher.showView("generalInfo2View");
				System.out.println("No need to create one more time");
			} else {

				// Checking if all fields are filled in & name/city have only letters & age/height have only numbers & age < 18 & age > 120
				if (TechnicalFunctions.readyToUpdate1(generalInfoPanel, name,age,height,city,photo)) {
					// Updating database
					TechnicalFunctions.updateGeneralInfo1(screenSwitcher, name, age, height, city,photo);
					index++;

					// Check that database set everything successfully
					System.out.println("All fields are filled in database succesfully");

					// Move to the next view (need to define)
					screenSwitcher.showView("generalInfo2View");
					System.out.println("Added new View");
				} else {
				System.out.println("Validation failed. Fix the errors and try again.");
				} 
			}
		}
	}
}