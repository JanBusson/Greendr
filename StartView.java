import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartView extends JPanel {
    private GraphicPanel mainPanel;
    private JLabel welcomeLabel;
    private StyleButton loginButton;
    private StyleButton registerButton;
    private StyleButton helpButton;
    private ScreenSwitcher screenSwitcher;

    public StartView(ScreenSwitcher screenSwitcher) {
        this.screenSwitcher = screenSwitcher;

        // Set layout for StartView panel
        setLayout(new BorderLayout());

        // Header label
        welcomeLabel = new JLabel("Welcome to Greendr", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(Color.PINK);
        add(welcomeLabel, BorderLayout.NORTH);

        // Main panel with buttons
        //External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
        mainPanel = new GraphicPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        loginButton = new StyleButton("Login");
        registerButton = new StyleButton("Register");
        helpButton = new StyleButton("Help");

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(loginButton, gbc);

        gbc.gridy = 1;
        mainPanel.add(registerButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(helpButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenSwitcher.showView("loginView");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenSwitcher.showView("registerView");
            }
        });

        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenSwitcher.showView("helpView");
            }
        });
    }
}
