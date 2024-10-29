import javax.swing.*;
import java.awt.*;

public class HeadingLabel extends JLabel {

    public HeadingLabel(String text) {
        super(text);
        setBackground(Color.PINK);
        setOpaque(true);
        setFont(new Font("Arial", Font.BOLD, 32));
        setHorizontalAlignment(JLabel.CENTER);
    }
}
