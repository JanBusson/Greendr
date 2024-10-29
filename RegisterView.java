import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterView extends JPanel {
	private GraphicPanel registerPanel;
	private JLabel headerLabel;
	private JLabel userLabel;
	private JLabel passLabel;
	private JTextField userField;
	private JPasswordField passField;
	private StyleButton registerButton;
	private StyleButton backButton;
	private JLabel messageLabel;
	private ScreenSwitcher screenSwitcher;

	private int index=0; // Index to track if user has been added - so from the next view when someone clicks back and one more time continue -> no new user created

	public RegisterView(ScreenSwitcher screenSwitcher) {
		this.screenSwitcher = screenSwitcher;

		// Set layout for RegisterView panel
		setLayout(new BorderLayout());

		// Idea is to create center panel
		// Fill center panel with all components
		// Add this center panel to our registrationpanel
		// Why? To use GridBagLayout!

		// Header label
		headerLabel = new JLabel("Register");
		headerLabel.setBackground(Color.PINK);
		//External knowledge used -> see 6. Visibility - Oracle Docs file in /quellen
		headerLabel.setOpaque(true); //Paints all pixels of the Component
		headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
		headerLabel.setHorizontalAlignment(JLabel.CENTER); //JLabel.CENTER - ia a constant defined in JLabel class - we need to align content within label
		add(headerLabel, BorderLayout.NORTH);

		// Center panel with form fields
		//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
		registerPanel = new GraphicPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(); // GridBagConstraints is a class used in conjunction with GridBagLayout to specify constraints for each component at the layout
		//External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
		gbc.insets = new Insets(10, 10, 10, 10); //set margins (insets) around each cell in the grid. Here, each component will have 10 pixels of padding on all sides
		gbc.fill = GridBagConstraints.HORIZONTAL; // component will expand horizontally to fill the available space but not vertically

		userLabel = new JLabel("Username:");
		userLabel.setFont(new Font("Arial", Font.BOLD, 18));

		gbc.gridx = 0; //positioning the label
		gbc.gridy = 0;
		registerPanel.add(userLabel, gbc);

		userField = new JTextField(15);
		userField.setBackground(Color.PINK);
		gbc.gridx = 1;
		registerPanel.add(userField, gbc);

		passLabel = new JLabel("Password:");
		passLabel.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 1;
		registerPanel.add(passLabel, gbc);

		passField = new JPasswordField(15);
		passField.setBackground(Color.PINK);
		gbc.gridx = 1;
		registerPanel.add(passField, gbc);

		messageLabel = new JLabel(""); // This label will show messages like registration success or error
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		registerPanel.add(messageLabel, gbc);

		add(registerPanel, BorderLayout.CENTER);

		// Bottom panel with buttons
		JPanel bottomPanel = new GraphicPanel();
		bottomPanel.setLayout(new FlowLayout());

		registerButton = new StyleButton("Register","Bottom"); // Creating a button half of the size using another constructor
		backButton = new StyleButton("Back","Bottom"); // Creating a button half of the size using another constructor

		bottomPanel.add(backButton);
		bottomPanel.add(registerButton);
		add(bottomPanel, BorderLayout.SOUTH);

		// Action listeners
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenSwitcher.showView("startView");
			}
		});

		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userField.getText();
				String password = new String(passField.getPassword());

				// First check - if already user was here - then index would be 1 and we dont need to update database if he clicked back and continue one more time
				if(index>0) {
					screenSwitcher.showView("generalInfo1View");
					System.out.println("No need to create one more time");
				} else {

					if (!username.isEmpty() && !password.isEmpty()) {
						try {
							// Checking if the username is unique in database
							if (TechnicalFunctions.checkUnique(username,password)) {

								// Checking if the password is valid -> calling function isPasswordValid
								// Password must be at least 8 characters long, contain at least one number, one lowercase letter, one uppercase letter, and one special character
								if (!TechnicalFunctions.isPasswordValid(password)) {
									JOptionPane.showMessageDialog(registerPanel, "Password must be at least 8 characters long, contain at least one number, one lowercase letter, one uppercase letter, and one special character.");
									return;
								}
								// Adding username and password to database
								TechnicalFunctions.registerUser(username,password);
								//ScreenSwitcher User
								screenSwitcher.setUser(username);
								index++;
								System.out.println("Created credentials succesfully");

								// After successful registration, switch to GeneralInfo1View
								screenSwitcher.showView("generalInfo1View");
							} else {
								messageLabel.setText("Username already exists. Please choose another one.");
								messageLabel.setForeground(Color.RED);
							}
						} catch (SQLException ex) {
							ex.printStackTrace();
							messageLabel.setText("Database error during registration: " + ex.getMessage());
							messageLabel.setForeground(Color.RED);
						}

					} else {
						messageLabel.setText("Please enter both username and password.");
						messageLabel.setForeground(Color.RED);
					}
				}
			}
		});
	}

	//Important method!
	// Returns the username that user put in - we need to then assign all variables in database WHERE username = ...
	public String getUsername() {
		if (userField.getText().length()<=0) {
			return "No user";
		} else {
			return userField.getText();
		}
	}
}
