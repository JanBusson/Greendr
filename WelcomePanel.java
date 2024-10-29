
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends GraphicPanel {

	JLabel title = new JLabel("What do you want to do?");
	private JButton LogoutButton = new StyleButton("Log out 👋");
	private JButton FindMatchButton = new StyleButton("Find me a match 💕•");
	private JButton LikedUsersButton = new StyleButton("Liked Profiles 😍");
	private ScreenSwitcher screenSwitcher;

	public WelcomePanel(ScreenSwitcher screenSwitcher1) {

		screenSwitcher = screenSwitcher1;
		// allign the Layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setFont(MainFont);
		title.setForeground(Color.BLACK);
		title.setBackground(Color.PINK);
		title.setOpaque(true);
		title.setBorder(new EmptyBorder(10, 10, 10, 10)); 
		
		LogoutButton.setAlignmentX(CENTER_ALIGNMENT);
		FindMatchButton.setAlignmentX(CENTER_ALIGNMENT);
		LikedUsersButton.setAlignmentX(CENTER_ALIGNMENT);

		add(Box.createVerticalGlue());
		add(title);
		add(Box.createVerticalStrut(20)); // Add some space between components
		add(FindMatchButton);
		add(Box.createVerticalStrut(20));
		add(LikedUsersButton);
		add(Box.createVerticalStrut(20));
		add(LogoutButton);
		add(Box.createVerticalGlue());
		// Add action listeners to buttons
		LogoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		FindMatchButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String matchedUser = sqlUser.getUserNotLiked(screenSwitcher1.getUser());
		        
		        if (matchedUser == null) {
		            // Assuming screenSwitcher1 or another accessible component is your parent component
		            JOptionPane.showMessageDialog(screenSwitcher1, "No users left to match.", "Info", JOptionPane.INFORMATION_MESSAGE);
		            return;
		        } else {
		            SwipePanel swipePanel = new SwipePanel(screenSwitcher);
		            screenSwitcher.addView("swipePanel", swipePanel);
		            screenSwitcher.showView("swipePanel");
		        }
		    }
		});


		LikedUsersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LikedUserPanel likedUserPanel = new LikedUserPanel(screenSwitcher);
				screenSwitcher.addView("likedUserPanel", likedUserPanel);
				screenSwitcher.showView("likedUserPanel");
			}
		});

		// create SwipePanel and likedUsersPanel

	}
}
