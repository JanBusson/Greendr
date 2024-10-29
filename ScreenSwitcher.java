
import javax.swing.*;
import java.awt.*;

public class ScreenSwitcher extends JFrame {
    private JPanel mainPanel;
    private String user="";

    public ScreenSwitcher() {
        setTitle("Greendr App");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        mainPanel = new JPanel(new CardLayout());
        add(mainPanel);
    }

    public void addView(String name, JPanel view) {
    	view.setName(name);
    	mainPanel.add(view, name);
        System.out.println("Added view: " + name);
    }

    public void showView(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }
    
    public JPanel getView(String name) {
        Component[] components = mainPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && component.getName().equals(name)) {
            	return (JPanel) component;
            }
        }
        return null; // Return null if view with given name is not found or not of type RegisterView
    }
    public String getUser() {
    	return this.user;
    }
    public void setUser(String user) {
    	this.user=user;
    }
}
