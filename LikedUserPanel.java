import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class LikedUserPanel extends GraphicPanel {

    HeadingLabel title = new HeadingLabel("Liked Users");
    StyleButton homeButton = new StyleButton("🏠","Bottom");
    ArrayList<String> likedUserNames = new ArrayList<>();
    ArrayList<Integer> likedUserAges = new ArrayList<>();
    ArrayList<Image> likedUserProfilePictures = new ArrayList<>();
    private ScreenSwitcher mainWindow;
    String currentUsername;

    public LikedUserPanel(ScreenSwitcher screenSwitcher) {
        mainWindow=screenSwitcher;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(MainFont);
        title.setForeground(Color.BLACK);


        // Add the title to the main panel
        add(title);

        currentUsername= mainWindow.getUser();
        initLikedUsers(currentUsername,this); // Initialize liked users
        setPanel(); // Set the panel with liked users
        homeButton.setAlignmentX(CENTER_ALIGNMENT);
        add(homeButton);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.showView("welcomePanel");
            }
        });
        repaint();
    }

    private void initLikedUsers(String currentUsername, JPanel panel) {
        try {
            List<String> likedUsernames = sqlUser.getLikedUsers(currentUsername);
           
            for (String username : likedUsernames) {
                String name = sqlUser.getName(username);
                int age = sqlUser.getAge(username);
                Image profilePicture = sqlUser.getProfilePicture(username);

                likedUserNames.add(name);
                likedUserAges.add(age);
                likedUserProfilePictures.add(profilePicture);
            }
        } catch (Exception e) {  // Updated to catch general exceptions including potential SQL and IO exceptions
            e.printStackTrace();
        }
    }

    private void setPanel() {
    	GraphicPanel usersContainer = new GraphicPanel();
        usersContainer.setLayout(new BoxLayout(usersContainer, BoxLayout.Y_AXIS));

        for (int i = 0; i < likedUserNames.size(); i++) {
            JPanel userPanel = createUserPanel(likedUserNames.get(i), likedUserAges.get(i), likedUserProfilePictures.get(i));
            usersContainer.add(userPanel);
            usersContainer.add(Box.createVerticalStrut(10)); // Add some spacing between users
        }

        JScrollPane scrollPane = new JScrollPane(usersContainer);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);

        add(scrollPane);
        revalidate();
        repaint();
    }

    private GraphicPanel createUserPanel(String name, int age, Image profilePicture) {
    	GraphicPanel userPanel = new GraphicPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        // Create and add profile picture
        ImageIcon profilePictureIcon = new ImageIcon(profilePicture);
        JLabel picLabel = new JLabel(profilePictureIcon);
        userPanel.add(picLabel);

        // Create and add name label
        JLabel nameLabel = new JLabel(name);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setForeground(Color.BLACK);
        userPanel.add(Box.createHorizontalStrut(10)); // Add spacing between picture and text
        userPanel.add(nameLabel);

        // Create and add age label
        JLabel ageLabel = new JLabel(", " + age + " years old");
        ageLabel.setForeground(Color.BLACK);
        ageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(Box.createHorizontalStrut(10)); // Add spacing between name and age
        userPanel.add(ageLabel);

        return userPanel;
    }
}
