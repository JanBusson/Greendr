import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    private GraphicPanel loginPanel;
    private JLabel headerLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField userField;
    private JPasswordField passField;
    private StyleButton loginButton;
    private StyleButton backButton;
    private ScreenSwitcher screenSwitcher;

    public LoginView(ScreenSwitcher screenSwitcher) {
        this.screenSwitcher = screenSwitcher;

        // Set layout for LoginView panel
        setLayout(new BorderLayout());

        // Header label
        headerLabel = new JLabel("Login", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        
        //External knowledge used -> see 6. Visibility - Oracle Docs file in /quellen
        headerLabel.setOpaque(true);
        headerLabel.setBackground(Color.PINK);
        add(headerLabel, BorderLayout.NORTH);

        // Main panel with form fields and buttons
        //External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
        loginPanel = new GraphicPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new StyleButton("Login");
        backButton = new StyleButton("Back");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        loginPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 20, 0);
        loginPanel.add(backButton, gbc);

        add(loginPanel, BorderLayout.CENTER);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (!username.isEmpty() && !password.isEmpty()) {
              
                    if (TechnicalFunctions.verifyPassword(username, password)) {
                    	JOptionPane.showMessageDialog(screenSwitcher, "Login successful.");
                        // Proceed to the next view after successful login
                    	screenSwitcher.setUser(username);
                        screenSwitcher.showView("welcomePanel");
                    } else {
                        JOptionPane.showMessageDialog(screenSwitcher, "Invalid username or password.");
                    }
                } else {
                    JOptionPane.showMessageDialog(screenSwitcher, "Please enter both username and password.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenSwitcher.showView("startView");
            }
        });
    }
    
    public String getUsername() {
    	if (userField.getText().length()<=0) {
			return "";
		} else {
		return userField.getText();
		}
    }
}
