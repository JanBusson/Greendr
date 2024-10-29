import javax.swing.*;
import java.awt.*;

public class MainWindowView extends JFrame {
    private static JPanel mainPanel;

    public MainWindowView() {
        setTitle("Greendr App");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        mainPanel = new JPanel(new CardLayout());
        add(mainPanel);
    }

    public static void clearAll() { 
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void addView(String name, JPanel view) {
        mainPanel.add(view, name);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showView(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }
    
 
}