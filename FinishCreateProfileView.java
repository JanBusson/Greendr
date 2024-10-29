import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinishCreateProfileView extends JPanel {

    private GraphicPanel finishProfilePanel;
    private ScreenSwitcher screenSwitcher;

    private HeadingLabel congratulationLabel;
    private StyleButton startEcoTestButton;

    public FinishCreateProfileView(ScreenSwitcher screenSwitcher) {
    	this.screenSwitcher = screenSwitcher;
    	setLayout(new BorderLayout());
    	
        finishProfilePanel = new GraphicPanel();
        finishProfilePanel.setLayout(new BorderLayout());

        // Label "Congratulations! It's time for eco-test!" in the center of the screen
        String partyPopperEmoji = "&#x1F389;";
        String sunglassesEmoji = "&#x1F60E;";
        congratulationLabel = new HeadingLabel("<html><div style='text-align: center;'>Congratulations! " + partyPopperEmoji + "<br/>It's time for eco-test! " + sunglassesEmoji + "</div></html>");
        finishProfilePanel.add(congratulationLabel, BorderLayout.CENTER);

        // Creating button in the bottom part of the screen
        JPanel bottomPanel = new GraphicPanel();
        startEcoTestButton = new StyleButton("Start Eco-Test");
        startEcoTestButton.addActionListener(new StartEcoTestButtonListener());
        bottomPanel.add(startEcoTestButton);

        finishProfilePanel.add(bottomPanel, BorderLayout.SOUTH);

        add(finishProfilePanel);
    }

    class StartEcoTestButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        	// Move to the Eco-Test view
            System.out.println("Starting Eco-Test...");
            screenSwitcher.showView("quizView");
        }
    }
}
