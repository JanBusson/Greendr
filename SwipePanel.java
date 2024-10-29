import java.awt.Color;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class SwipePanel extends GraphicPanel {

	String currUserLogin;
	String matchUserLogin;

	// Initialize components
	private JLabel topInfo = new JLabel();
	private JLabel profilePic = new JLabel();
	private String descriptionTXT;
	private JLabel description = new JLabel("<html><div style='width:200px;'>" + descriptionTXT + "</div></html>");
	private JLabel additionalInfo = new JLabel();
	private JButton yes = new StyleButton("💕");
	private JButton no = new StyleButton("❌");
	private JButton home = new StyleButton("🏠","Bottom");
	private ScreenSwitcher mainWindow;


	// Constructor
	public SwipePanel(ScreenSwitcher screenSwitcher) {
		this.mainWindow = screenSwitcher;

		currUserLogin =screenSwitcher.getUser();

		setNewMatch();
		setComponents();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Top Panel for profile name and picture
		JPanel topPanel = new GraphicPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topInfo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		topInfo.setFont(MainFont);
		topInfo.setForeground(Color.BLACK);
		topInfo.setBackground(Color.PINK);
		topInfo.setOpaque(true);
		topInfo.setBorder(new EmptyBorder(10, 10, 10, 10)); 
		
		profilePic.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		topPanel.add(topInfo);
		topPanel.add(Box.createVerticalStrut(10)); // Space between components
		topPanel.add(profilePic);

		// Description Panel
		JPanel descPanel = new GraphicPanel();
		description.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		description.setForeground(Color.BLACK);
		description.setBackground(Color.PINK);
		description.setOpaque(true);
		description.setBorder(new EmptyBorder(10, 10, 10, 10)); 
		
		descPanel.add(description);

		// Additional Info Panel
		JPanel infoPanel = new GraphicPanel();
		additionalInfo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		additionalInfo.setForeground(Color.BLACK);
		additionalInfo.setBackground(Color.PINK);
		infoPanel.add(additionalInfo);

		// Button Panel
		JPanel buttonPanel = new GraphicPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		no.setAlignmentX(JButton.CENTER_ALIGNMENT);
		yes.setAlignmentX(JButton.CENTER_ALIGNMENT);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(no);
		buttonPanel.add(Box.createHorizontalStrut(20)); // Space between buttons
		buttonPanel.add(yes);
		buttonPanel.add(Box.createHorizontalGlue());

		// Home Button Panel
		JPanel homeButtonPanel = new GraphicPanel();
		homeButtonPanel.setLayout(new BoxLayout(homeButtonPanel, BoxLayout.X_AXIS));
		home.setAlignmentX(JButton.CENTER_ALIGNMENT);
		homeButtonPanel.add(Box.createHorizontalGlue());
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.showView("welcomePanel");
			}
		});
		homeButtonPanel.add(home);
		homeButtonPanel.add(Box.createHorizontalGlue());

		// Add action listeners to yes and no buttons
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sqlUser.addLikedUser(currUserLogin, matchUserLogin);
				setNewMatch();
				setComponents();
			}
		});

		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setNewMatch();
				setComponents();
			}
		});

		// Add panels to the main panel (this SwipePanel)
		add(Box.createVerticalStrut(40));
		add(topPanel);
		add(Box.createVerticalStrut(10)); // Space between panels
		add(descPanel);
		add(Box.createVerticalStrut(10));
		add(infoPanel);
		add(Box.createVerticalStrut(10));
		add(buttonPanel);
		add(Box.createVerticalStrut(10));
		add(homeButtonPanel);
		add(Box.createVerticalStrut(40));
		
		
	}

	private void setComponents() {
	    // Initialize the topInfo label with the match user's name and age
	    if (matchUserLogin == null) {
	        JOptionPane.showMessageDialog(mainWindow, "That was your last chance, no users left to match.", "Info", JOptionPane.INFORMATION_MESSAGE);
	        mainWindow.showView("welcomePanel");
	    } else {
	        topInfo.setText(sqlUser.getName(matchUserLogin) + ", " + sqlUser.getAge(matchUserLogin));

	        Image profileImage = sqlUser.getProfilePicture(matchUserLogin);
	        if (profileImage != null) {
	            profilePic.setIcon(new ImageIcon(profileImage));
	        }

	        // Initialize the description label
	        descriptionTXT = sqlUser.getDescription(matchUserLogin);
	        description.setText("<html><div style='width:250px;'>" + descriptionTXT + "</div></html>");

	        // Initialize the additional info label
	        String city = sqlUser.getCity(matchUserLogin);
	        String languages = sqlUser.getLanguages(matchUserLogin);
	        String interests = sqlUser.getInterests(matchUserLogin);
	        int ecoScore = sqlUser.getEcoScore(matchUserLogin);
	        String hobbies = sqlUser.getHobbies(matchUserLogin);
	        int height = (int) sqlUser.getHeight(matchUserLogin);
	        String jobTitle = sqlUser.getJobTitle(matchUserLogin);
	        String hometown = sqlUser.getHometown(matchUserLogin);

	        additionalInfo.setText("<html><div style='width:250px;'>"
	            + "City: " + city + "<br>"
	            + "Languages: " + languages + "<br>"
	            + "Interested Gender: " + interests + "<br>"
	            + "EcoScore: " + ecoScore + " Points<br>"
	            + "Hobbies: " + hobbies + "<br>"
	            + "Height: " + height + " cm<br>"
	            + "Job Title: " + jobTitle + "<br>"
	            + "Hometown: " + hometown + "</div></html>");
	        additionalInfo.setBackground(Color.PINK);
	        additionalInfo.setOpaque(true);
	        additionalInfo.setBorder(new EmptyBorder(10, 10, 10, 10)); 
	    }
	}
	private void setNewMatch() {
		// Get a new match user login that the current user has not liked yet
		matchUserLogin = sqlUser.getUserNotLiked(currUserLogin);
		return;
	}
}
