import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpView extends JPanel {
    private JPanel helpPanel;
    private JLabel headerLabel;
    private JLabel userLabel;
    private JTextField userField;
    private JButton resetButton;
    private JButton backButton;
    private JLabel passwordTitleLabel;
    private JLabel passwordLabel;
    private JTextField passwordField;
    private ScreenSwitcher screenSwitcher;

    public HelpView(ScreenSwitcher screenSwitcher) {
        this.screenSwitcher = screenSwitcher;

        // Set layout for HelpView panel
        setLayout(new BorderLayout());

        // Header label
        headerLabel = new JLabel("Reset Password");
        headerLabel.setBackground(Color.PINK);
        headerLabel.setOpaque(true);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // Center panel with form fields
        //External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
        helpPanel = new GraphicPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        helpPanel.add(userLabel, gbc);

        userField = new JTextField(15);
        gbc.gridx = 1;
        helpPanel.add(userField, gbc);

        resetButton = new StyleButton("Reset Password");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        helpPanel.add(resetButton, gbc);

        passwordTitleLabel = new JLabel("New Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        helpPanel.add(passwordTitleLabel, gbc);

        passwordField = new JTextField("");
        passwordField.setVisible(false);
        gbc.gridx = 1;
        helpPanel.add(passwordField, gbc);

        add(helpPanel, BorderLayout.CENTER);

        // Bottom panel with back button
        backButton = new StyleButton("Back");
        JPanel bottomPanel = new GraphicPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();

                if (!username.isEmpty()) {
                    if (TechnicalFunctions.checkExistence(username)) {
                        String newPassword = TechnicalFunctions.resetPassword(username);
                        passwordField.setText(newPassword); // Display the new password in the label
                        passwordField.setVisible(true);
                        revalidate();
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(screenSwitcher, "Username does not exist.");
                    }
                } else {
                    JOptionPane.showMessageDialog(screenSwitcher, "Please enter a username.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenSwitcher.showView("startView");
            }
        });
    }
}
