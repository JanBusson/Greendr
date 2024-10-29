import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageView extends JPanel {
    private JLabel imageLabel;
    private ScreenSwitcher screenSwitcher;
    private StyleButton button;

    public ImageView(ScreenSwitcher screenSwitcher) {
        this.screenSwitcher = screenSwitcher;
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        button = new StyleButton("Load picture", "Middle");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon imageIcon = TechnicalFunctions.fetchImage(screenSwitcher);
                if (imageIcon != null) {
                    imageLabel.setIcon(imageIcon);
                } else {
                    imageLabel.setText("No image available");
                }
            }
        });

        // Add button to the top (NORTH) of the panel
        add(button, BorderLayout.NORTH);
        // Add imageLabel to the center of the panel
        add(imageLabel, BorderLayout.CENTER);
    }
}
