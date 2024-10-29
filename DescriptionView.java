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

public class DescriptionView extends JPanel{

    private GraphicPanel descriptionPanel;
    private ScreenSwitcher screenSwitcher;

    private HeadingLabel descriptionLabel;
    private JTextField descriptionField;
    
    private StyleButton continueButton;
    private StyleButton backButton;
    
    private int index=0;

    public DescriptionView(ScreenSwitcher screenSwitcher) {
    	this.screenSwitcher = screenSwitcher;
    	setLayout(new BorderLayout());
    	
        descriptionPanel = new GraphicPanel();
        descriptionPanel.setLayout(new BorderLayout());

        // Label "Description" on the top of the screen
        descriptionLabel = new HeadingLabel("Description");
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);

        // Center panel with main field
        //External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
        JPanel centerPanel = new GraphicPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(descriptionLabel, gbc);
        
        descriptionField = new JTextField("Describe yourself in 1 sentence.... Work hard, live harder", 30);
        descriptionField.setPreferredSize(new java.awt.Dimension(400, 100)); // Set a larger preferred size
        descriptionField.setBackground(Color.PINK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // making sure that it spans across 2 columns
        gbc.gridheight = 1; // keeping it single line
        centerPanel.add(descriptionField, gbc);

        descriptionPanel.add(centerPanel, BorderLayout.CENTER);

        // Creating button in the bottom part of the screen
        JPanel bottomPanel = new GraphicPanel();
        
        backButton = new StyleButton("Back","Bottom");
        backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				screenSwitcher.showView("freeTimeView");
			}
        	
        });
        continueButton = new StyleButton("Continue","Bottom");
        continueButton.addActionListener(new ContinueButtonListener());
        bottomPanel.add(backButton);
        bottomPanel.add(continueButton);

        descriptionPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(descriptionPanel);
    }

    class ContinueButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = descriptionField.getText();

            if (index>0) {
            	screenSwitcher.showView("hobbiesView");
				System.out.println("No need to create one more time");
            } else {
            // Check if the field is filled
           if (TechnicalFunctions.readyDescription(descriptionPanel, description)) {

            // Tracking for us that the field is filled
            System.out.println("Description field is filled");

            TechnicalFunctions.updateDescriptionView(screenSwitcher, description);
            index++;
            
            // Check that database set everything successfully
			System.out.println("All fields are filled in database succesfully");
			
			// Move to the next view (need to define)
            screenSwitcher.showView("hobbiesView");
            System.out.println("Added new View");
           } else {
        	   System.out.println("Description field is filled inproperly");
           }
        }
        }
    }
}